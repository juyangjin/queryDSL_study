package kr.com.sparta.studyquerydsl.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.com.sparta.studyquerydsl.dto.ProductDto.SearchRequest;
import kr.com.sparta.studyquerydsl.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Repository;

import java.net.PasswordAuthentication;
import java.util.List;

import static kr.com.sparta.studyquerydsl.entity.QProduct.product;
import static org.hibernate.query.results.Builders.fetch;

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
        return queryFactory.selectFrom(product).fetch();
    }

    //조건 조회(카테고리)
    public List<Product> findProductsByPrice() {
        return queryFactory.selectFrom(product)
            .where(
                product.price.eq(30000.0))
            .fetch();
    }

    //조건 조회(카테고리)
    public List<Product> findProductsByCategory(SearchRequest request) {
        return queryFactory.selectFrom(product)
            .where(
                eqCategory(request.category()),
                goeMinPrice(request.minPrice()),
                loeMaxPrice(request.maxPrice())
                )
            .fetch();
    }


    private BooleanExpression eqPrice(Double price) {
        if(price == null){
            return null;
        }

        return product.price.eq(price);
    }



    private BooleanExpression eqCategory(String category){
        if(category == null) return null;
        return product.category.eq(category);
    }


    private BooleanExpression goeMinPrice(Double minPrice){
        if(minPrice == null) return null;
        /*
        gt : great then
        goe : great or equals
         */
        return product.price.goe(minPrice);
    }
    private BooleanExpression loeMaxPrice(Double maxPrice){
        if(maxPrice == null) return null;
        return product.price.loe(maxPrice);
    }



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
