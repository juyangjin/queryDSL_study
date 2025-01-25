package kr.com.sparta.studyquerydsl.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.com.sparta.studyquerydsl.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Repository;

import java.net.PasswordAuthentication;
import java.util.List;

import static kr.com.sparta.studyquerydsl.entity.QProduct.product;

/*
todo: Repository,RequiredArgsConstructor 어노테이션의 의미 찾아서 적기
Bean으로 등록해주기, Component 설정해주기 < 모두 같은의미인 듯 하다.
 */
@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    //전체 조회
    /*
    .fetch()는 앞의 쿼리문을 실행시키겠다는 의미
     */
    public List<Product> findAllProducts() {
        return queryFactory.selectFrom(product
        ).fetch();
    }
//
//    //조건 조회(카테고리)
//    public List<Product> findProductsByCategory() {
//        return null;
//    }
//
//    private BooleanExpression eqCategory(String category){
//        return null;
//    }
//
//    private BooleanExpression gteMenPrice(Double minPrice){
//        return null;
//    }
//    private BooleanExpression lteMaxPrice(Double maxPrice){
//        return null;
//    }
//
//    //페이징 조회
//    public Page<Product> findProductsByPage(){
//        return null;
//    }
//
//    public Page<Product> findProductsByPageBySort(){
//        return null;
//    }
//
//    public Page<Product> searchProducts(){
//        return null;
//    }
//
//    /*
//    Todo: OrderSpecifier이 무엇인지, 이것은 왜 ? 형태인건지, 뒤에 붙은 []는 무슨 의미인지 찾기
//    Pageable이 무슨 뜻인지, 정확히 어떤 구조로 페이징이 되게 만드는 것인지. 매개변수로 받아오는 이유가 뭔지 적기
//     */
//    private OrderSpecifier<?>[]getSortOrders(Pageable pageable) {
//        return new OrderSpecifier[0];
//    }
}
