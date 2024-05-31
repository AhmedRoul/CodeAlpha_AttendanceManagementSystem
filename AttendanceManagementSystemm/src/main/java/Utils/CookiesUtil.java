package Utils;

import javax.servlet.http.Cookie;

public class CookiesUtil {
    public static String getToken(Cookie[]cookies ){
        if(cookies!=null)
        for(Cookie cookie:cookies){
            if (cookie.getName().equals("Token"))
                return  cookie.getValue();

        }
        return null;
    }
}
