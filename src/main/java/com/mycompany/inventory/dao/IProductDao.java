package com.mycompany.inventory.dao;

import com.mycompany.inventory.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface IProductDao extends CrudRepository<Product, Long> {
}