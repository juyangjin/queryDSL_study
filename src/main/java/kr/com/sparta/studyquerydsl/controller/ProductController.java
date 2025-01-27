package kr.com.sparta.studyquerydsl.controller;

import kr.com.sparta.studyquerydsl.dto.ProductDto;
import kr.com.sparta.studyquerydsl.entity.Product;
import kr.com.sparta.studyquerydsl.repository.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
        return new ResponseEntity<>(productQueryRepository.findProductsByPrice(), HttpStatus.OK);
    }

    @GetMapping("search2")
    public ResponseEntity<?> search2(@ModelAttribute ProductDto.SearchRequest request, Pageable pageable) {
        return new ResponseEntity<>(productQueryRepository.findProductsByCategory(request, pageable), HttpStatus.OK);
    }

    @GetMapping("search3")
    public ResponseEntity<?> search3(@ModelAttribute ProductDto.SearchRequest request, Pageable pageable) {
        return new ResponseEntity<>(productQueryRepository.search(request, pageable), HttpStatus.OK);
    }

}
