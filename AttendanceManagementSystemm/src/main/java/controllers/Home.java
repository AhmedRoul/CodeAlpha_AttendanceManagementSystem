package controllers;

import DataAcessObject.Instructordao;
import DataAcessObject.imp.InstructordaoImp;
import Entitys.Role;
import Entitys.User;
import Utils.JwtUtil;
import org.jboss.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AttendanceManagementSystemm")
public class Home extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Logout.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("working!");
        getUser(req,resp);
    }
    private void getUser(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        JwtUtil jwtUtils=new JwtUtil();
        User user = jwtUtils.getUser(req);

        if (user == null) {
            resp.sendRedirect("/login");
        }
        if (user.getRole().equals(Role.Admin)) {
            resp.sendRedirect("/Admin");

        } else if (user.getRole().equals(Role.Student)) {
            resp.sendRedirect("/Student");
        } else if (user.getRole().equals(Role.instructor)) {

            resp.sendRedirect("/Instructor");
        }
        else
        {
            resp.sendRedirect("/logout");
        }


    }
}
