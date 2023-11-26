package com.mycompany.inventory.controller;

import com.mycompany.inventory.Utils.Util;
import com.mycompany.inventory.model.Product;
import com.mycompany.inventory.response.ProductResponseRest;
import com.mycompany.inventory.services.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {

    IProductService iProductService;

    public ProductRestController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    /**
     *
     * @param picture
     * @param name
     * @param price
     * @param account
     * @param categoryId
     * @return
     * @throws IOException
     */
    @PostMapping("/products")
    public ResponseEntity<ProductResponseRest> save (@RequestParam("picture") MultipartFile picture,
                                                     @RequestParam("name") String name,
                                                     @RequestParam("price") int price,
                                                     @RequestParam("account") int account,
                                                     @RequestParam("categoryId") Long categoryId) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setAccount(account);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response = iProductService.save(product, categoryId);
        return response;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> searchById(@PathVariable Long id){
        ResponseEntity<ProductResponseRest> response = iProductService.searchById(id);
        return response;
    }

    /**
     *
     * @param name
     * @return
     */
    @GetMapping("/products/filter/{name}")
    public ResponseEntity<ProductResponseRest> searchByName(@PathVariable String name){
        ResponseEntity<ProductResponseRest> response = iProductService.searchByName(name);
        return response;
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> deleteById(@PathVariable Long id){
        ResponseEntity<ProductResponseRest> response = iProductService.deleteById(id);
        return response;
    }

}
