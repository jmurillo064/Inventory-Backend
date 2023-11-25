package com.mycompany.inventory.services;

import com.mycompany.inventory.dao.ICategoryDao;
import com.mycompany.inventory.dao.IProductDao;
import com.mycompany.inventory.model.Category;
import com.mycompany.inventory.model.Product;
import com.mycompany.inventory.response.ProductResponseRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService{

    private ICategoryDao iCategoryDao;
    private IProductDao iProductDao;

    public ProductServiceImpl(ICategoryDao iCategoryDao, IProductDao iProductDao) {
        this.iCategoryDao = iCategoryDao;
        this.iProductDao = iProductDao;
    }

    @Override
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
        ProductResponseRest productResponseRest = new ProductResponseRest();
        List<Product> productList = new ArrayList<>();
        try {
            //search category to set in the product object
            Optional<Category> category = iCategoryDao.findById(categoryId);
            if(category.isPresent()){
                product.setCategory(category.get());
            }else{
                productResponseRest.setMetadata("Respuesta nok", "-1", "Categoría no encontrada");
                return new ResponseEntity<ProductResponseRest>(productResponseRest, HttpStatus.NOT_FOUND);
            }

            //save the product
            Product productSave = iProductDao.save(product);
            if(null != productSave){
                productList.add(productSave);
                productResponseRest.getProductResponse().setProducts(productList);
                productResponseRest.setMetadata("Respuesta ok", "00", "Producto guardado");
            } else {
                productResponseRest.setMetadata("Respuesta nok", "-1", "Producto no guardado");
                return new ResponseEntity<ProductResponseRest>(productResponseRest, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.getStackTrace();
            productResponseRest.setMetadata("Respuesta nok", "-1", "Error al guardar producto");
            return new ResponseEntity<ProductResponseRest>(productResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(productResponseRest, HttpStatus.OK);
    }
}
