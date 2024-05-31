package Utils;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import DataAcessObject.JWTBlacklistDao;
import DataAcessObject.Userdao;
import DataAcessObject.imp.JWTBlacklistDaoImp;
import DataAcessObject.imp.Userdaoimp;
import Entitys.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JwtUtil {
    private String SECREAT_KEY = "49A342B6DA6DBFF49B967AB25A5A7";
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration );
    }
    public  <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final  Claims claims=extarctAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extarctAllClaims(String token){
        return Jwts.parser().setSigningKey(SECREAT_KEY).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());

    }

    public String generateToken(User userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,userDetails.getEmail());
    }
    private String createToken(Map<String,Object>claims,String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,SECREAT_KEY).compact();
    }
    public Boolean validateToken(String token,User userDetails){
        final String username =extractUsername(token);
        if(InBlacklist(token))
            return false;
        return (username.equals(userDetails.getEmail())) && !isTokenExpired(token);
    }
    public User getUser(HttpServletRequest req){

        String token= CookiesUtil.getToken( req.getCookies());

        Userdao userdao = new Userdaoimp();
        if(InBlacklist(token))
            return null;
        String email=null;

        email = extractUsername(token);
        User user = new User();
        user.setEmail(email);
        try {
            user = userdao.IsExist(user);
        } catch (SQLException e) {

            System.err.println(e.getMessage());
            return null;
        }

        if (user != null)
            if (validateToken(token, user))
                return user;

        return null;

    }
    public boolean InBlacklist(String token){
        JWTBlacklistDao jwtBlacklistDao=new JWTBlacklistDaoImp();
        return jwtBlacklistDao.IsExist(token);
    }
}