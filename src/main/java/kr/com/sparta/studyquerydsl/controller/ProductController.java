package kr.com.sparta.studyquerydsl.controller;

import kr.com.sparta.studyquerydsl.dto.ProductDto;
import kr.com.sparta.studyquerydsl.entity.Product;
import kr.com.sparta.studyquerydsl.repository.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final  ProductQueryRepository productQueryRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(productQueryRepository.findAllProducts(), HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<?> search() {
        return new ResponseEntity<>(productQueryRepository.findProductsByCategory(), HttpStatus.OK);
    }
//    @GetMapping
//    public ResponseEntity<?> search(@ModelAttribute ProductDto.SearchRequest request) {
//        return new ResponseEntity<>(productQueryRepository.findAllProducts(), HttpStatus.OK);
//    }
}
