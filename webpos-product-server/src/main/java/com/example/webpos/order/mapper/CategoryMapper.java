package com.example.webpos.order.mapper;

import com.example.webpos.order.rest.dto.CategoryDto;

public class CategoryMapper {
    public static CategoryDto mapToDto(String category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category); // 这里先将类别名称作为ID
        categoryDto.setName(category);
        return categoryDto;
    }
}
