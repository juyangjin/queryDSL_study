package kr.com.sparta.studyquerydsl.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    todo: 변수 자료형이 int와 Integer가 따로 있는 이유는 무엇인지? 자료형을 long과 Long을 썼을 때 각각 무슨 차이가 있는지?
     */
    @Column(name = "age")
    private int age;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

}
