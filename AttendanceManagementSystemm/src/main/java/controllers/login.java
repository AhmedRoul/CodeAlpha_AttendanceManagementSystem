package controllers;

import DataAcessObject.Studentdao;
import DataAcessObject.Userdao;
import DataAcessObject.imp.Studentdaoimp;
import DataAcessObject.imp.Userdaoimp;
import Entitys.Role;
import Entitys.User;
import Utils.HibernateSessionFactoryListener;
import Utils.JwtUtil;
import org.jboss.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("login")

public class login  extends HttpServlet {
    private Studentdao Studentdao;
    private Userdao userdao;
    private JwtUtil jwtUtils=new JwtUtil();
    private static final Logger logger = Logger.getLogger(login.class);

    public login(){
        Studentdao=new Studentdaoimp();
        userdao=new Userdaoimp();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Post");
        req.getRequestDispatcher("login/View/login.html").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.info("Post");
        User user=new User();
        String username=req.getParameter("username");
        user.setPassword(req.getParameter("pass"));
        if (username.contains("@")) {
            user.setEmail(username);
        } else {
            user.setUsername(username);
        }
        logger.info(user.getEmail());
        try {
            user =userdao.Check(user);
        } catch (SQLException e) {
          logger.warn("Error in database try again ");
          logger.warn(e.getMessage());
        }
        if(user==null){

            logger.warn("unvaildUsernameAndPassword");
            req.setAttribute("unvaildUsernameAndPassword",true);
            req.getRequestDispatcher("login/View/login.html").forward(req,resp);
        }
        logger.info("jwt : create");
        String jwt= jwtUtils.generateToken(user);

        Cookie cookie = new Cookie("Token", jwt);
        logger.info("jwt :"+jwt);
        logger.info("username :"+user.getUsername());
        cookie.setMaxAge(60*60);
        resp.addCookie(cookie);
        logger.info(jwt);

        logger.info("sendRedirect");
        if(user.getRole().equals(Role.Admin))
        resp.sendRedirect(req.getContextPath()+"/Admin");
        else if(user.getRole().equals(Role.instructor))
        resp.sendRedirect(req.getContextPath()+"/Instructor");
        else if(user.getRole().equals(Role.Student))
            resp.sendRedirect(req.getContextPath()+"/Student");
        else
            resp.sendRedirect(req.getContextPath()+"/login");

    }
}
