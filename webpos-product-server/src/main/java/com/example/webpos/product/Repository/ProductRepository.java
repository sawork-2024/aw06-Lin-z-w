package com.example.webpos.product.Repository;

import com.example.webpos.product.model.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductRepository {

    @Select("SELECT * FROM products")
    List<Product> getAllProducts();

    @Select("SELECT * FROM products WHERE id = #{productId}")
    Product getProductById(@Param("productId") String productId);

    @Insert("INSERT INTO products (id, price, category, quantity, name, stock, img) " +
            "VALUES (#{id}, #{price}, #{category}, #{quantity}, #{name}, #{stock}, #{img})")
    void addProduct(Product product);

    @Update("UPDATE products SET quantity = #{quantity} WHERE id = #{id}")
    void updateProductQuantity(Product product);

    @Select("SELECT * FROM products WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Product> findByNameContaining(@Param("name") String name);

    @Select("SELECT * FROM products WHERE id IN (#{productIds}) for update")
    List<Product> getProductByIds(List<String> productIds);
}