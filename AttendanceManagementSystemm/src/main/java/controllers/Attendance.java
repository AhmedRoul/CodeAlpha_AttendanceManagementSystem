package controllers;

import DataAcessObject.Admindao;
import DataAcessObject.Attendancedao;
import DataAcessObject.Instructordao;
import DataAcessObject.coursedao;
import DataAcessObject.imp.Admindaoimp;
import DataAcessObject.imp.AttendancedaoImp;
import DataAcessObject.imp.InstructordaoImp;
import DataAcessObject.imp.coursedaoimp;
import Entitys.*;
import Utils.JwtUtil;
import View.Views;
import org.jboss.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet(name="attendance",urlPatterns = {"/attendance/*"})
public class Attendance extends HttpServlet {
    private final JwtUtil jwtUtils=new JwtUtil();
    private static final Logger logger = Logger.getLogger(Attendance.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user=jwtUtils.getUser(req);
        getUser(req,resp,user);

        if(user.getRole().equals(Role.Admin))
        {
            initAttendance(req,resp,user );
            req.getRequestDispatcher(Views.ADMIN).forward(req, resp);
        }
        else if (user.getRole().equals(Role.instructor))
        {
            initAttendance(req,resp,user);
            req.getRequestDispatcher(Views.INSTRUCTOR).forward(req, resp);
        }
        else
        {
            resp.sendRedirect("/logout");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user=jwtUtils.getUser(req);

        int numberweek= Integer.parseInt(req.getParameter("numberweek"));
        char typeAttendance=req.getParameter("typeAttendance").charAt(0);
        int idAttendance = Integer.parseInt(req.getParameter("idAttendance"));
        Attendancedao attendancedao=new AttendancedaoImp();
        try {
            if(user.getRole().equals(Role.instructor))
            attendancedao.AddCourseAttendance(idAttendance,typeAttendance,user.getId(),numberweek);
           else if(user.getRole().equals(Role.Admin)){
                attendancedao.AddCourseAttendance(idAttendance,typeAttendance,1,numberweek);
            }
           else {
                resp.sendRedirect("/AttendanceManagementSystemm/Student");
            }

        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        logger.info("here!!!");
        resp.sendRedirect("/AttendanceManagementSystemm/attendance?courseId="+req.getParameter("courseId"));

    }
    private  void initAttendance(HttpServletRequest req,HttpServletResponse resp,User UserIA) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        req.setAttribute("Attendance",true);


        if(pathInfo!=null)
            resp.sendRedirect("/AttendanceManagementSystemm/attendance");

        String courseId=req.getParameter("courseId");
        coursedao coursedao=new coursedaoimp();

        try {
            if(UserIA.getRole().equals(Role.instructor)){
                req.setAttribute("CoursesInfoAtten",coursedao.get(( UserIA).getId()));

            }
            else if (UserIA.getRole().equals(Role.Admin)) {
                req.setAttribute("CoursesInfoAtten",coursedao.get());
            }
            logger.info("No course ID provided");

        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }

        if(courseId==null){
            logger.info("No course ID provided");
        }
        else {
            logger.info("course id: "+courseId);
            ADDAttendance(req, courseId);
        }


    }

    private void ADDAttendance(HttpServletRequest req, String IDCourse) throws ServletException, IOException {
        Attendancedao attendancedao=new AttendancedaoImp();
        coursedao coursedao=new coursedaoimp();
        try
        {
            Map<Student, List<periodsClass>> attendanceMap= attendancedao.getCourseAttendance(IDCourse);

            req.setAttribute( "CourseInfoattendate",coursedao.getCourse(IDCourse));

            req.setAttribute( "CourseAttendatesISExist",true);
            req.setAttribute( "attendanceMap",attendanceMap);
            req.setAttribute( "instructors",coursedao.getInstructorsTeachCourse(IDCourse));

        }
        catch (Exception e){
            logger.warn(e.getMessage());
            logger.warn(e.getCause());
            logger.info(e.getLocalizedMessage());
        }
    }
    private Object  getUser(HttpServletRequest req, HttpServletResponse resp,User user)  throws ServletException, IOException {

        if (user.getRole().equals(Role.Admin)) {
            Entitys.Admin admin = null;
            try {
                Admindao admindao = new Admindaoimp();
                admin = admindao.getAdmin(user.getEmail());
            } catch (SQLException e) {
                resp.sendRedirect("/admin");
            }

            if (admin == null) {
                resp.sendRedirect("/admin");
                logger.info("is null ");
            }
            req.setAttribute("Admin", admin);
            return admin;
        }
        else if (user.getRole().equals(Role.instructor)) {
            Entitys.Instructor instructor = null;
            try {
                Instructordao instructordao1 = new InstructordaoImp();
                instructor = instructordao1.getInstructor(user.getEmail());
                req.setAttribute("instructorData", instructor);
            } catch (SQLException e) {
                logger.error("Error getting instructor", e);
                throw new ServletException("Error getting instructor", e);
            }

            if (instructor == null) {
                resp.sendRedirect("/Instructor");
                logger.info("Instructor is null");
                return null;
            }
        }
        else if (user.getRole().equals(Role.Student)) {
            resp.sendRedirect("/Student");
            return null;
        }
        return null;
    }


}
