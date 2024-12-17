package com.crio.project.onlineGrocery.service;

import com.crio.project.onlineGrocery.dto.ProductDto;
import com.crio.project.onlineGrocery.entity.ProductEntity;
import com.crio.project.onlineGrocery.exception.ResourceNotFoundException;
import com.crio.project.onlineGrocery.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository,ModelMapper modelMapper){
        this.productRepository=productRepository;
        this.modelMapper=modelMapper;
    }

    public ProductDto createProduct(ProductDto productDto){
        ProductEntity product=modelMapper.map(productDto,ProductEntity.class);
        return modelMapper.map(productRepository.save(product),ProductDto.class);
    }

    public Optional<ProductDto> getProductById(Long id) {
        return productRepository.findById(id).map(productEntity -> modelMapper.map(productEntity, ProductDto.class));
    }
    public List<ProductDto> getAllProduct(){
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public Boolean deleteProductById(Long id){
        if(productRepository.existsById(id)){
            throw new ResourceNotFoundException("Product with id "+id+" does not exist");
        }
        productRepository.deleteById(id);
        return true;
    }

    public ProductDto updateProduct(ProductDto productDto,Long id){
        ProductEntity existingProduct=productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product with id "+id+" does not exist"));
        modelMapper.map(productDto,existingProduct);
        return modelMapper.map(existingProduct,ProductDto.class);
    }

}

