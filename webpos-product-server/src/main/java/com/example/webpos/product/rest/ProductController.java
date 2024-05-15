package com.example.webpos.product.rest;

import com.example.webpos.product.mapper.CategoryMapper;
import com.example.webpos.product.mapper.ProductMapper;
import com.example.webpos.product.model.Product;
import com.example.webpos.product.rest.api.ProductApi;
import com.example.webpos.product.rest.dto.CategoryDto;
import com.example.webpos.product.rest.dto.ProductDto;
import com.example.webpos.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.webpos.product.mapper.ProductMapper.mapToDto;

@RestController
public class ProductController implements ProductApi {
//    @Autowired
//    CacheManager cacheManager;

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<ProductDto> showProductById(@PathVariable String productId) {
        System.out.println("ProductController.showProductById() called with productId: " + productId);
        Product product = productService.getProductById(productId);
        if (product != null) {
            ProductDto productDto = mapToDto(product);
            return ResponseEntity.ok(productDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<ProductDto>> listProducts() {
        System.out.println("ProductController.listProducts() called");
        List<ProductDto> productDtos = productService.getAllProducts().stream()
                .map(ProductMapper::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    @Override
    public ResponseEntity<List<CategoryDto>> listCategories() {
        List<String> categories = productService.getAllCategories();
        List<CategoryDto> categoryDtoList = categories.stream()
                .map(CategoryMapper::mapToDto)
                .collect(Collectors.toList());
        System.out.println("listCategories() called");
        return ResponseEntity.ok(categoryDtoList);
    }

//    @RequestMapping(value = "/product/clearCache", method = RequestMethod.GET)
//    public void clearCache() {
//        cacheManager.getCache("product").clear();
//    }
}
