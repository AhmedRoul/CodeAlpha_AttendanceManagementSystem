package controllers;

import DataAcessObject.JWTBlacklistDao;
import DataAcessObject.imp.JWTBlacklistDaoImp;
import Utils.CookiesUtil;
import Utils.JwtUtil;
import org.jboss.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("logout")
public class Logout extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Logout.class);
    private JWTBlacklistDao blacklist;
    private JwtUtil jwtUtil;

    public Logout(){
        blacklist=new JWTBlacklistDaoImp();
        jwtUtil=new JwtUtil();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String Token= CookiesUtil.getToken(req.getCookies());
        String email=jwtUtil.extractUsername(Token);
        blacklist.InsertToken(Token,email);
        Cookie cookie = new Cookie("Token", null);
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
        resp.sendRedirect(req.getContextPath()+"/login");

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("posted");
        String Token= CookiesUtil.getToken(req.getCookies());
       String email=jwtUtil.extractUsername(Token);
       blacklist.InsertToken(Token,email);
        Cookie cookie = new Cookie("Token", null);
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
       resp.sendRedirect(req.getContextPath()+"/login");

    }
}
