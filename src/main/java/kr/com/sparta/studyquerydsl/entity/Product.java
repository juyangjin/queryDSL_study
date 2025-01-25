package kr.com.sparta.studyquerydsl.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

/*
todo: 해당 어노테이션들이 무슨 의미인가? 3가지 적어보기
 */
@Entity
@Getter
@Table(name = "product")
public class Product {

    /*
    Id와 GeneratedValue가 무엇인지 자세히 적어 설명하기
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private double price;

    @Column(name = "stockQuantity")
    private int stockQuantity;

    /*
    todo:OnetoMany와 ManytoOne의 차이점과 이 중에 어떤 걸 어떨 때 써야할 지 고민해보기. 조사해보기
     */
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Order> orders;
}
