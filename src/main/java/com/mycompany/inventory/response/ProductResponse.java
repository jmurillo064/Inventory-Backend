package com.mycompany.inventory.response;

import com.mycompany.inventory.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    List<Product> products;

}
