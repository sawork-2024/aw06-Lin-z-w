package com.example.webpos.order.service;

import com.example.webpos.order.client.ProductClient;
import com.example.webpos.order.model.Product;
import com.example.webpos.order.rest.dto.OrderDto;
import com.example.webpos.order.rest.dto.OrderItemsInnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

//    @CacheEvict(value = "products", allEntries = true)
//    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {

        // 获取订单中所有商品的信息
        List<String> productIds = orderDto.getItems().stream()
                .map(OrderItemsInnerDto::getId)
                .collect(Collectors.toList());

        CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
        // 调用 getProductsByIds 方法实现从 Product 服务获取商品信息
        List<Product> products = circuitbreaker.run(() -> productClient.getProductsByIds(productIds), throwable -> {
            // 处理异常，比如调用 Product 服务失败，则返回空列表
            return null;
        });

        if (products == null) {
            throw new IllegalArgumentException("Failed to get products from Product service.");
        }

        // 检查每个商品的库存是否足够
        for (OrderItemsInnerDto item : orderDto.getItems()) {
            Product product = products.stream()
                    .filter(p -> p.getId().equals(item.getId()))
                    .findFirst()
                    .orElse(null);
            if (product == null || product.getQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException("Product with ID " + item.getId() + " does not have enough stock.");
            }
        }

        // 如果所有商品库存足够，则逐个减少库存量
        for (OrderItemsInnerDto item : orderDto.getItems()) {
            Product product = products.stream()
                    .filter(p -> p.getId().equals(item.getId()))
                    .findFirst()
                    .orElse(null);
            if (product != null) {
                int newQuantity = product.getQuantity() - item.getQuantity();
                // 调用 updateProductQuantity 方法实现更新商品库存信息
                // 因为没有设置updateProductQuantity的返回值，所以不使用断路器也无影响。
                productClient.updateProductQuantity(product.getId(), newQuantity);
            }
        }
        // 在此处实现订单创建的逻辑，可能涉及到数据库的插入操作等

        // 返回创建的订单信息
        return orderDto;
    }
}
