package Entitys;

public enum Role {
    Admin,
    Student,
    instructor;

    public static  Role getRole(String email){
        if(email.contains("student"))
            return Student;
        else if (email.contains("instructor"))
            return instructor;
        else if(email.contains("admin"))
            return Admin;
        return Student;

    }
}
