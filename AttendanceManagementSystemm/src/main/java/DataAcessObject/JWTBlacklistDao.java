package DataAcessObject;

public interface JWTBlacklistDao {

    public  boolean IsExist(String Token);
    public  boolean InsertToken(String Token,String email);
}
