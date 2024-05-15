package com.example.webpos.order.mapper;

import com.example.webpos.order.model.Product;
import com.example.webpos.order.rest.dto.ProductDto;


public class ProductMapper {

    public static ProductDto mapToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory());
        productDto.setQuantity(product.getQuantity());
        productDto.setName(product.getName());
        productDto.setStock(product.getStock());
        productDto.setImg(product.getImg());
        return productDto;
    }
}
