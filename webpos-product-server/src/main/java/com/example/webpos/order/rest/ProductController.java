package com.example.webpos.order.rest;

import com.example.webpos.order.mapper.CategoryMapper;
import com.example.webpos.order.mapper.ProductMapper;
import com.example.webpos.order.model.Product;
import com.example.webpos.order.rest.api.ProductApi;
import com.example.webpos.order.rest.dto.CategoryDto;
import com.example.webpos.order.rest.dto.ProductDto;
import com.example.webpos.order.rest.dto.UpdateProductQuantityRequestDto;
import com.example.webpos.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.webpos.order.mapper.ProductMapper.mapToDto;

@RestController
public class ProductController implements ProductApi {
//    @Autowired
//    CacheManager cacheManager;

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<ProductDto> showProductById(@PathVariable String productId) {
        System.out.println("ProductController.showProductById() called with productId: " + productId + System.currentTimeMillis());
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
        System.out.println("ProductController.listProducts() called" + System.currentTimeMillis());
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

    @Override
    public ResponseEntity<Void> updateProductQuantity(String productId, UpdateProductQuantityRequestDto updateProductQuantityRequestDto) {
        productService.updateProductQuantity(productId, updateProductQuantityRequestDto.getQuantity());
        return ResponseEntity.ok().build();
    }

    //    @RequestMapping(value = "/product/clearCache", method = RequestMethod.GET)
//    public void clearCache() {
//        cacheManager.getCache("product").clear();
//    }
}
