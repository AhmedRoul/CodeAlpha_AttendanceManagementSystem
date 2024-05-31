package controllers;

import DataAcessObject.Instructordao;
import DataAcessObject.coursedao;
import DataAcessObject.imp.InstructordaoImp;
import DataAcessObject.imp.coursedaoimp;
import Entitys.Role;
import Entitys.User;
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
import java.util.Objects;

@WebServlet(name="Instructor")
public class Instructor extends HttpServlet {
    private JwtUtil jwtUtils=new JwtUtil();
    private Instructordao instructordao=new InstructordaoImp();
    private static final Logger logger = Logger.getLogger(Instructor.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Entitys.Instructor myInstructor= init(req, resp);
        String value=req.getParameter("formPost");
        if(Objects.equals(value, "MyProfile")){
            req.setAttribute("instructorDataISExist",true);
        }
        else if (Objects.equals(value, "MyCourses")) {
            req.setAttribute("MyCoursespage",true);
            MyCourses(myInstructor.getId(),req,resp);
        }


        req.getRequestDispatcher(Views.INSTRUCTOR).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Entitys.Instructor myInstructor= init(req, resp);


        Entitys.Instructor instructor= new Entitys.Instructor();
        String department=req.getParameter("department");
        instructor.setFirstName(req.getParameter("firstName"));
        instructor.setLastName(req.getParameter("lastName"));
        instructor.setDetails(req.getParameter("details"));
        instructor.setId(myInstructor.getId());
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
        req.setAttribute("instructorDataISExist",true);

        req.setAttribute("instructorData", instructor);
        req.getRequestDispatcher(Views.INSTRUCTOR).forward(req, resp);
    }
    private void MyCourses(int idInstructor ,HttpServletRequest req,HttpServletResponse resp) throws IOException {
        coursedao coursedap=new coursedaoimp();
        try {
            coursedap.get(idInstructor);
            req.setAttribute("MyCoursesData",coursedap.get(idInstructor));

        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    private Entitys.Instructor getUser(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        User user = jwtUtils.getUser(req);

        if (user == null) {
            resp.sendRedirect("/logout");
            return null;
        }

        if (user.getRole().equals(Role.Admin)) {
            resp.sendRedirect("/Admin");
            return null;
        } else if (user.getRole().equals(Role.Student)) {
            resp.sendRedirect("/Student");
            return null;
        }

        Entitys.Instructor instructor = null;
        try {
            Instructordao instructordao1 = new InstructordaoImp();
            instructor = instructordao1.getInstructor(user.getEmail());
        } catch (SQLException e) {
            logger.error("Error getting instructor", e);
            throw new ServletException("Error getting instructor", e);
        }

        if (instructor == null) {
            resp.sendRedirect("/Instructor");
            logger.info("Instructor is null");
            return null;
        }

        return instructor;
    }

    private Entitys.Instructor init(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Entitys.Instructor instructor = getUser(req, resp);
        req.setAttribute("instructorData", instructor);

        return instructor;
    }
}