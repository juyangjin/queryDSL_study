package kr.com.sparta.studyquerydsl.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {


    /*
    todo: jpa 사용시, 컨텍스트에서 관리하고, 처리하고 ? 무슨 말인지 찾아보자
     */
    @PersistenceContext
    private EntityManager entityManager;
    /*
    QeuryDSL을 만들 때 JPA를 활용하기 위하여 jpaQueryFactory를 만들 때 entitymanager를 매개변수로 넣어준다.
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}