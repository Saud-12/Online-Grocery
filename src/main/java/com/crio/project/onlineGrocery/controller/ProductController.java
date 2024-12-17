package com.crio.project.onlineGrocery.controller;

import com.crio.project.onlineGrocery.dto.ProductDto;
import com.crio.project.onlineGrocery.exception.ResourceNotFoundException;
import com.crio.project.onlineGrocery.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        return productService.getProductById(productId).map(productDto -> ResponseEntity.ok(productDto)).orElseThrow(()->new ResourceNotFoundException("Product with id "+productId+" does not exist"));
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getProduct(){
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto){
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid ProductDto productDto, @PathVariable Long productId){
        return ResponseEntity.ok(productService.updateProduct(productDto,productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long productId){
        boolean isDeleted=productService.deleteProductById(productId);
        if(isDeleted){
            return ResponseEntity.ok(true);
        }return ResponseEntity.notFound().build();
    }
}

