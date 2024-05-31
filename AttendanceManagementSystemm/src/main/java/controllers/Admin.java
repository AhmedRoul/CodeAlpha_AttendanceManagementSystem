package controllers;

import DataAcessObject.Admindao;
import DataAcessObject.Instructordao;
import DataAcessObject.Studentdao;
import DataAcessObject.coursedao;
import DataAcessObject.imp.*;
import Entitys.*;
import Utils.JwtUtil;
import View.Views;
import org.jboss.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="Admin")
public class Admin  extends HttpServlet {
    private JwtUtil jwtUtils=new JwtUtil();
    private Admindao admindao=new Admindaoimp();
    private static final Logger logger = Logger.getLogger(Admin.class);

    private void  SetUser(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        User user=jwtUtils.getUser(req);

        if(user==null){
            resp.sendRedirect("/logout");
        }
        else {
            if (user.getRole().equals(Role.Student)) {
                resp.sendRedirect("/Student");
            }
            else if (user.getRole().equals(Role.instructor)) {

                resp.sendRedirect("/Instructor");
                return;
            }
        }
        logger.info(user.getEmail());


        Entitys.Admin admin=null;
        try {
            admin=  admindao.getAdmin(user.getEmail());
        } catch (SQLException e) {
            resp.sendRedirect("/admin");
        }
        if(admin==null){
            resp.sendRedirect("/admin");
        }
        req.setAttribute("Admin", admin);

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SetUser(req,resp);
        req.setAttribute("Employees" ,false);
        String requestURI = req.getRequestURI();

        String path = req.getRequestURI();


        req.getRequestDispatcher(Views.ADMIN).forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String [] formPost =req.getParameter("formPost").split("/",2);
        String Request=formPost[0];
        String Value=null;
        try {
            logger.info(formPost[0]);
            logger.info(req.getParameter("formPost"));
            Value = formPost[1];
        }
        catch (Exception e){
            Value="1";
        }
        SetUser(req,resp);
        switch (Request) {
            case "Instructor:Members" -> InstructorMembers(req, resp);
            case "Instructor:Member_profile" -> InstructorMemberProfile(req, resp, Value);
            case "Student:Members" -> StudentMembers(req, resp);
            case "Student:Member_profile" -> StudentMemberProfile(req, resp,Value);
            case "Courses:Info" -> CoursesInfo(req, resp);
            case "Courses:course_Info" -> CourseInfo(req, resp,Value);

        }
        req.getRequestDispatcher(Views.ADMIN).forward(req,resp);

    }
    private void InstructorMembers(HttpServletRequest req, HttpServletResponse resp)throws  ServletException, IOException  {

        req.setAttribute("Instructors",true);
        Instructordao instructordao=new  InstructordaoImp();
        try {
            req.setAttribute("InstructorsList",instructordao.get());
        }
        catch (SQLException e){
            logger.info(e.getMessage());
        }
    }
    private void InstructorMemberProfile(HttpServletRequest req, HttpServletResponse resp,String  Value)throws  ServletException, IOException  {

        int id;

        if(Value.contains("editInstructor")){
            String [] str= Value.split("/", 2);
            id= Integer.parseInt(str[0]);
            editInstructor(req,id);
        }
        else
            id= Integer.parseInt(Value);

        req.setAttribute("InstructorProfile",true);

        Instructordao instructordao=new  InstructordaoImp();
        try {

            req.setAttribute("InstructorProfileData",instructordao.getInstructor(id));
        }
        catch (SQLException e){
            logger.info(e.getMessage());
        }
    }
    private void editInstructor(HttpServletRequest req,int id)  {
        Entitys.Instructor instructor= new Entitys.Instructor();
        String department=req.getParameter("department");
        instructor.setFirstName(req.getParameter("firstName"));
        instructor.setLastName(req.getParameter("lastName"));
        instructor.setDetails(req.getParameter("details"));
        instructor.setId(id);
        Instructordao instructordao=new  InstructordaoImp();
        try {
             instructordao.update(instructor);
            req.setAttribute("editInstructor",true);
        }
        catch (Exception e){
            logger.info(e.getMessage());
            req.setAttribute("editInstructor",false);
        }
        req.setAttribute("editInstructorExist",true);
    }
    private  void StudentMembers(HttpServletRequest req, HttpServletResponse resp){

        req.setAttribute("Students",true);
        Studentdao studentdao=new Studentdaoimp();
        try {
            req.setAttribute("StudentsList",studentdao.get());
        }
        catch (SQLException e){
            logger.info(e.getMessage());
        }
    }
    private  void  StudentMemberProfile(HttpServletRequest req, HttpServletResponse resp,String  Value)throws  ServletException, IOException  {
        int id;

        if(Value.contains("editStudent")){
            String [] str= Value.split("/", 2);
            id= Integer.parseInt(str[0]);
            editStudent(req,id);
            req.setAttribute("editStudentExist",true);

        }
        else
            id= Integer.parseInt(Value);


        req.setAttribute("StudentProfile",true);

        Studentdao studentdao=new Studentdaoimp();
        try {
            req.setAttribute("StudentProfileData",studentdao.getStudent(id));
        }
        catch (SQLException e){
            logger.info(e.getMessage());
        }
    }
    private void editStudent(HttpServletRequest req,int id)  {
        Entitys.Student student=new Entitys.Student();

        student.setFirstName(req.getParameter("firstName"));
        student.setLastName(req.getParameter("lastName"));

        student.setId(id);

        Studentdao studentdao=new Studentdaoimp();
        try {
            studentdao.update(student);
            req.setAttribute("editStudent",true);
        }
        catch (Exception e){
            logger.info(e.getMessage());
            req.setAttribute("editStudent",false);
        }

    }
    private  void CoursesInfo(HttpServletRequest req, HttpServletResponse resp){

        req.setAttribute("Courses",true);
        coursedao coursedao=new coursedaoimp();
        try {
            req.setAttribute("CoursesList",coursedao.get());
        }
        catch (SQLException e){
            logger.info(e.getMessage());
        }
    }
    private  void  CourseInfo(HttpServletRequest req, HttpServletResponse resp,String  Value)throws  ServletException, IOException  {
        String id;

        if(Value.contains("editCourse")){
            String [] str= Value.split("/", 2);
            id= str[0];
            editCourse(req,id);
        }
        else
            id= Value;

        req.setAttribute("CourseInfo",true);

        coursedao  coursedao0=new coursedaoimp();
        try {
            req.setAttribute("CourseData",coursedao0.getCourse(id));
           logger.info( coursedao0.getStudentRegistertionCourse(id).toString());
            req.setAttribute("StudentsData",coursedao0.getStudentRegistertionCourse(id));
            req.setAttribute("instructorsData",coursedao0.getInstructorsTeachCourse(id));
        }
        catch (SQLException e){
            logger.info(e.getMessage());
        }
    }
    private void editCourse(HttpServletRequest req,String id)  {
        Course course=new Course();

        course.setName(req.getParameter("NameCourse"));
        course.setYear(req.getParameter("Year"));
        course.setId(id);


        coursedao  coursedao1=new coursedaoimp();
        try {
            coursedao1.update(course);
            req.setAttribute("editCourse",true);
        }
        catch (Exception e){
            logger.info(e.getMessage());
            req.setAttribute("editCourse",false);
        }
        req.setAttribute("editCourseExist",true);
    }





}
