package kr.com.sparta.studyquerydsl.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

/*
todo: 해당 어노테이션들이 무슨 의미인가? 3가지 적어보기
@Entity는 Component와 동일한 역할을 하며
 */
@Entity
@Getter
@Table(name = "product")
public class Product {

    /*
    Id와 GeneratedValue가 무엇인지 자세히 적어 설명하기
    Id는 해당 엔터티의 기본 키를 지정한다는 의미로 반드시 테이블 생성시 필요하다.

    @GeneratedValue는 기본 키의 값을 자동으로 생성한다는 의미이다.
    IDENTITY는 주로 MYSQL 등에서 AUTO_INCREMENT와 연계되어 사용된다.

    AUTO_INCREMENT는 동시에 여러 클라이언트가 값을 삽입했을 때,
    ID 값이 동일하다면 에러메시지가 발생하기 때문에 데이터 베이스에서
    자동으로 한 개씩 ID 값을 늘려줄 수 있게 해주는 속성이다.

    SEQUENCE는 데이터 베이스의 시퀸스를 사용하여 기본 키를 생성하는데 이는 주로 Oracle,H2 등에서 사용된다.
    시퀸스의 이름을 커스터마이징 할 수 있다는 특징이 있다.
    sequnceName(데베에 등록된 시퀸스명)으로 시퀸스를 분리해서 저장할 수 있고,
    allocationSize로 한 번에 사용할 시퀸스 덩어리 사이즈도 정할 수 있다.
    여기서 쓰인 시퀸스란 데이터 베이스의 유니크값, 즉 유일한 값을 생성해주는 오라클의 객체이다.

    TABLE은 별도의 키 생성용 테이블을 사용해서 기본 키를 생성하는 방법이다.
    데이터베이스를 독립적으로 사용할 수 있지만 키 생성 테이블이 존재하는 탓에 성능이 다소 떨어질 수 있다.
    이는 모든 데이터베이스에서 적용이 가능하지만, 성능 때문에 잘 쓰지 않는다고 한다.
    아마도 내가 할 때 에러가 정 안 잡히거나 이 부분에 문제가 날 가능성은 높지 않겠지만,
    생소한 데이터 베이스를 쓰다가 안된다면 마지막의 보루로  써볼 거 같다.

    AUTO는 사용하는 데이터베이스에 따라 자동으로 적절한 방법이 작동하는 방식이다.
    이는 hibernate.dialect에 설정된 DB 방언 종류에 따라 hibernate가 자동으로 전략을 선택하는 것인데
    버전에 따라 다르다는 취약점이 존재한다. 위에 쓰인것처럼 MYSQL이면 자동으로 IDENTITY 전략을 선택할 것 같지만
    실제로는 버전에 따라 다르게 선택할 수 있다고 한다.

    구글링 결과 Hibernate 5부터 MySQL에서의 GenerationType.AUTO는
    IDENTITY가 아닌 TABLE을 기본 시퀀스 전략으로 채택하고 있다는 사실을 확인하였다.
    결론적으로 봤을 때 자동선택이라는 점에서 편리하다고 느낄 지 몰라도 하이버네이트가 가끔 내 생각과 다르게 돌아갈 때가 있다는 것 이므로
    AUTO가 아닌 그때 그때 내 상황에 맞는 방식을 채택하는 게 조금 더 안전한 방식이라는 판단이 들었다.
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
