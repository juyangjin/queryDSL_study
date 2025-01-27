package kr.com.sparta.studyquerydsl.dto;


public class ProductDto {

    public record SearchRequest(String category, Double minPrice, Double maxPrice) {

    }

}
