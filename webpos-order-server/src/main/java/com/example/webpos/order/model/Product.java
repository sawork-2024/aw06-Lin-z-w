package com.example.webpos.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    private String id; // 修改为 int 类型，以匹配数据库中的主键类型
    //    private int id2;
    private double price;
    private String category;
    private int quantity;
    private String name;
    private int stock;
    private String img; // 修改为 img，以匹配数据库中的字段名称
}