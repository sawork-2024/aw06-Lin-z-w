package com.example.webpos.order.client;

import com.example.webpos.order.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-server")
public interface ProductClient {

    @GetMapping("/product")
    List<Product> getProductsByIds(@RequestParam("ids") List<String> ids);

    @PutMapping("/product/{id}/quantity")
    void updateProductQuantity(@PathVariable("id") String id, @RequestBody int quantity);
}
