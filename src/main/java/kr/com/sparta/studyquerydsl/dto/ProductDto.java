package kr.com.sparta.studyquerydsl.dto;


import kr.com.sparta.studyquerydsl.entity.Product;

public class ProductDto {

    public record SearchResponse(Long id, String name, String category, Double price, int stock_quantity, int reviewCount) {

        public SearchResponse(Product product, long reviewCount) {
            this(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStockQuantity(),
                (int) reviewCount
            );
        }

    }

    public record SearchRequest(String category, Double minPrice, Double maxPrice) {

    }

}
