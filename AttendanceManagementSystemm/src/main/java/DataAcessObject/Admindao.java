package DataAcessObject;

import Entitys.Admin;

import java.sql.SQLException;
import java.util.List;

public interface Admindao {
    List<Entitys.Admin> get() throws SQLException;
    boolean save(Admin c) throws SQLException;

    Admin getAdmin(String email) throws SQLException;

    boolean update(Admin c);

    boolean delete(int id);
}
