package controllers;

import Entitys.Role;
import Entitys.User;
import Utils.JwtUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Student")
public class StudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         JwtUtil jwtUtils=new JwtUtil();
        User user = jwtUtils.getUser(req);

        if (user == null) {
            resp.sendRedirect("/logout");

        }

        if (user.getRole().equals(Role.Admin)) {
            resp.sendRedirect("/Admin");

        } else if (user.getRole().equals(Role.instructor)) {
            resp.sendRedirect("/Instructor");
        }
        req.getRequestDispatcher("Student.jsp").forward(req, resp);
    }


}
