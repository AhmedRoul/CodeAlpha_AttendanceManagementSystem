    package Filters;

    import javax.servlet.*;
    import javax.servlet.annotation.WebFilter;
    import javax.servlet.http.Cookie;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.IOException;
    import java.sql.SQLException;
    import java.util.Objects;

    import DataAcessObject.Userdao;
    import DataAcessObject.imp.Userdaoimp;
    import Entitys.User;
    import Utils.CookiesUtil;
    import Utils.JwtUtil;
    import View.Views;
    import controllers.login;
    import org.jboss.logging.Logger;

    public class jwtFilter implements Filter {
        private JwtUtil jwtUtil;
        private Userdao userdao;
        private static final Logger logger = Logger.getLogger(jwtFilter.class);
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            jwtUtil = new JwtUtil(); // Initialize jwtUtil here
            userdao = new Userdaoimp(); // Initialize userdao here
        }


        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

            HttpServletRequest req = (HttpServletRequest) servletRequest;
            HttpServletResponse res = (HttpServletResponse) servletResponse;

            // Allow access to the login page without a JWT token
            if (req.getRequestURI().equals("/AttendanceManagementSystemm/login")) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            String authorizationHeader = req.getHeader("Authorization");
            Cookie[] cookies = req.getCookies();

            // Check if the request is for a static resource
            if (isStaticResource(req)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            String stringToken = null;

            stringToken = CookiesUtil.getToken(cookies);

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                stringToken = authorizationHeader.substring(7);
            }

            if (stringToken != null) {
                String email;

                try {
                    email = jwtUtil.extractUsername(stringToken);
                } catch (NullPointerException e) {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.getWriter().write("Invalid token.");
                    System.err.println(e.getMessage());
                    return;
                }

                User user = new User();
                user.setEmail(email);

                try {
                    user = userdao.IsExist(user);
                } catch (SQLException e) {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.getWriter().write("Invalid token.");
                    System.err.println(e.getMessage());
                    return;
                }

                if (user != null) {
                    if (jwtUtil.validateToken(stringToken, user)) {

                        if (req.getRequestURI().equals("/AttendanceManagementSystemm/")) {
                            switch (user.getRole()){
                                case instructor ->  res.sendRedirect(req.getRequestURI()+"/Instructor");
                                case Admin -> res.sendRedirect(req.getRequestURI()+"/Admin");
                                case Student -> res.sendRedirect(req.getRequestURI()+"/Student");
                            }
                        }
                       filterChain.doFilter(req, res);

                    } else {
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        res.getWriter().write("Invalid token.");
                    }
                } else {
                    res.sendRedirect(req.getContextPath() + "/login");
                }

            } else {
                res.sendRedirect(req.getContextPath() + "/login");
            }
        }

        @Override
        public void destroy() {

        }
        private boolean isStaticResource(HttpServletRequest req) {
            String uri = req.getRequestURI();
            return uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".jpg") || uri.endsWith(".png")
                    || uri.endsWith(".gif")||uri.endsWith(".jsp");
        }
        private  void IsExternalLinkResourse(HttpServletRequest req,HttpServletResponse res) throws IOException {
            String url = req.getRequestURI();
            if(url.contains("ajax")){
                url=url.split("ajax")[1];
                url="https://cdnjs.cloudflare.com/ajax"+url;
                res.sendRedirect(url);
            }
            else if (url.contains("npm")){
                url=url.split("npm")[1];
                url="https://cdn.jsdelivr.net/npm"+url;
                res.sendRedirect(url);
            }
        }

    }