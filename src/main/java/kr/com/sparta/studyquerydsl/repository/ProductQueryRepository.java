package kr.com.sparta.studyquerydsl.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import kr.com.sparta.studyquerydsl.dto.ProductDto;
import kr.com.sparta.studyquerydsl.dto.ProductDto.SearchRequest;
import kr.com.sparta.studyquerydsl.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Repository;

import java.net.PasswordAuthentication;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static kr.com.sparta.studyquerydsl.entity.QProduct.product;
import static kr.com.sparta.studyquerydsl.entity.QReview.review;
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
    public Page<Product> findProductsByCategory(SearchRequest request,Pageable pageable) {
        List<Product> products =  queryFactory.selectFrom(product)
            .where(
                eqCategory(request.category()),
                goeMinPrice(request.minPrice()),
                loeMaxPrice(request.maxPrice())
                )
            .limit(pageable.getPageSize()) //페이지당 몇 개를 가져올 지
            .offset(pageable.getOffset()) //몇 번째 페이지부터 가져올 것인지.
            .fetch();

    /*
    페이지 객체에는
    int getTotalPages(); 라는 전체의 페이지 갯수,
    또는
    long getTotalElements(); 라는 전체 원소의 갯수에 대한 것도 응답해주게 되어있다.
    따라서 이를 위한 추가 쿼리문을 작성해야할 필요가 있다.
     */

        //n
        Long totalCount = Optional.ofNullable(queryFactory.select(Wildcard.count). //wildcard.count를 사용하면 69번째에서 쓰인 product의 갯수를 반환해준다.
            from(product)
            .where(
                eqCategory(request.category()),
                goeMinPrice(request.minPrice()),
                loeMaxPrice(request.maxPrice())
            )
            .fetchOne())//하나만 조회할 때는 fetchOne을 사용한다. 이는 nullable이기 때문에 위에 Optional.ofNullable을 사용하여 null이어도 에러가 뜨지 않게 수정해야한다.
            .orElse(0L); //null일 시에는 0이라고 나오게 설정. 이걸 설정하지 않으면 500에러가 날 수 있으니 주의해야 한다.

        return new PageImpl<>(products, pageable, totalCount);
    }


    //조건 조회(카테고리)
    public Page<ProductDto.SearchResponse> search(SearchRequest request,Pageable pageable) {
        List<ProductDto.SearchResponse> products =  queryFactory.select(
                Projections.constructor(
                    ProductDto.SearchResponse.class,
                    //파라미터 값
                    product,
                    //리뷰개수(서브쿼리 활용)
                    select(Wildcard.count)
                        .from(review)
                        .where(product.id.eq(review.product.id)) //product의 id와 review의 product id가 동일한 경우에만 호출
                ) //constructor는 dto의 생성자를 이용해서 처리하는 방식으로 직관성 때문에 많이 사용된다.
            )
            .from(product)
            .where(
                eqCategory(request.category()),
                goeMinPrice(request.minPrice()),
                loeMaxPrice(request.maxPrice())
            )
            .limit(pageable.getPageSize()) //페이지당 몇 개를 가져올 지
            .offset(pageable.getOffset()) //몇 번째 페이지부터 가져올 것인지.
            .fetch();

        Long totalCount = Optional.ofNullable(queryFactory.select(Wildcard.count). //wildcard.count를 사용하면 69번째에서 쓰인 product의 갯수를 반환해준다.
                from(product)
                .where(
                    eqCategory(request.category()),
                    goeMinPrice(request.minPrice()),
                    loeMaxPrice(request.maxPrice())
                )
                .fetchOne())//하나만 조회할 때는 fetchOne을 사용한다. 이는 nullable이기 때문에 위에 Optional.ofNullable을 사용하여 null이어도 에러가 뜨지 않게 수정해야한다.
            .orElse(0L); //null일 시에는 0이라고 나오게 설정. 이걸 설정하지 않으면 500에러가 날 수 있으니 주의해야 한다.

        return new PageImpl<>(products, pageable, totalCount);
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
