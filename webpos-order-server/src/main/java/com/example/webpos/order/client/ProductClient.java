package com.example.webpos.order.client;

import com.example.webpos.order.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/products")
    List<Product> getProductsByIds(@RequestParam("ids") List<String> ids);

    @PutMapping("/products/{id}/quantity")
    void updateProductQuantity(@PathVariable("id") String id, @RequestBody int quantity);
}
