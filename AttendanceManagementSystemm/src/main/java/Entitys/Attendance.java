package Entitys;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {
    int enroll_no;
    String class_date;
    int Id_student ;
    String ID_course;
}
