package com.mycompany.inventory.services;

import com.mycompany.inventory.model.Product;
import com.mycompany.inventory.response.ProductResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductService {

    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
    public ResponseEntity<ProductResponseRest> searchById(Long id);
    public ResponseEntity<ProductResponseRest> searchByName(String name);
    public ResponseEntity<ProductResponseRest> deleteById(Long id);
    public ResponseEntity<ProductResponseRest> search();
    public ResponseEntity<ProductResponseRest> update(Product product, Long categoryId, Long idProducto);
    public ResponseEntity<ProductResponseRest> sellProductById(Long idProducto, int cantidad);

}
