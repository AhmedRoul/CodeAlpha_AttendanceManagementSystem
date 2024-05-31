package Entitys;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JWTBlackList {
    private int id;
    private String email;
    private String token;
}
