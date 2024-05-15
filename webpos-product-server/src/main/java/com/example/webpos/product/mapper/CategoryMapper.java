package com.example.webpos.product.mapper;

import com.example.webpos.product.rest.dto.CategoryDto;

public class CategoryMapper {
    public static CategoryDto mapToDto(String category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category); // 这里先将类别名称作为ID
        categoryDto.setName(category);
        return categoryDto;
    }
}
