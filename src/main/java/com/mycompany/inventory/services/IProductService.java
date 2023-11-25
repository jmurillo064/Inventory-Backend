package com.mycompany.inventory.services;

import com.mycompany.inventory.model.Product;
import com.mycompany.inventory.response.ProductResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductService {

    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
    public ResponseEntity<ProductResponseRest> searchById(Long id);
//    public ResponseEntity<ProductResponseRest>
//    public ResponseEntity<ProductResponseRest>

}
