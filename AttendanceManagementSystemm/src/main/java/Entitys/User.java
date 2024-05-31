package Entitys;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Override
    public String toString() {
        return "User{" +
                "role=" + role +
                ", username='" + username + '\'' +
                ", Password='" + Password + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", CheckPassword=" + CheckPassword +
                '}';
    }

    Role role;
    String username;
    String Password;
    int id;
    String email;
    Boolean CheckPassword;
}
