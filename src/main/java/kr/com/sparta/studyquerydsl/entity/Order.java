package kr.com.sparta.studyquerydsl.entity;

import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    Todo: 연관관계와 JoinColumn에 대하여 완벽하게 설명할 수 있을 정도로 적어보자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    /*
    Todo: LocalDateTime은 왜 Local이 붙을까 개인적인 궁금증 찾아보기
     */
    @Column(name = "orderDate")
    private LocalDateTime orderDate;
}