package com.mycompany.inventory.services;

import com.mycompany.inventory.Utils.Util;
import com.mycompany.inventory.dao.ICategoryDao;
import com.mycompany.inventory.dao.IProductDao;
import com.mycompany.inventory.model.Category;
import com.mycompany.inventory.model.Product;
import com.mycompany.inventory.response.ProductResponseRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchById(Long id) {
        ProductResponseRest productResponseRest = new ProductResponseRest();
        List<Product> productList = new ArrayList<>();
        try {
            //search product by id
            Optional<Product> product = iProductDao.findById(id);
            if(product.isPresent()){
                byte[] imagenDescompreses = Util.decompressZLib(product.get().getPicture());
                product.get().setPicture(imagenDescompreses);
                productList.add(product.get());
                productResponseRest.getProductResponse().setProducts(productList);
                productResponseRest.setMetadata("Respuesta ok", "00", "Producto encontrado");
            }else{
                productResponseRest.setMetadata("Respuesta nok", "-1", "Producto no encontrado");
                return new ResponseEntity<ProductResponseRest>(productResponseRest, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.getStackTrace();
            productResponseRest.setMetadata("Respuesta nok", "-1", "Error al buscar producto");
            return new ResponseEntity<ProductResponseRest>(productResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(productResponseRest, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchByName(String name) {
        ProductResponseRest productResponseRest = new ProductResponseRest();
        List<Product> productList = new ArrayList<>();
        List<Product> productListAux = new ArrayList<>();
        try {
            //search product by name
            productListAux = iProductDao.findByNameContainingIgnoreCase(name);
            if(productListAux.size() > 0){
                productListAux.stream().forEach( (p) -> {
                    byte[] imagenDescompreses = Util.decompressZLib(p.getPicture());
                    p.setPicture(imagenDescompreses);
                    productList.add(p);
                });
                productResponseRest.getProductResponse().setProducts(productList);
                productResponseRest.setMetadata("Respuesta ok", "00", "Productos encontrados");
            }else{
                productResponseRest.setMetadata("Respuesta nok", "-1", "Productos no encontrados");
                return new ResponseEntity<ProductResponseRest>(productResponseRest, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.getStackTrace();
            productResponseRest.setMetadata("Respuesta nok", "-1", "Error al buscar productos por nombre");
            return new ResponseEntity<ProductResponseRest>(productResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(productResponseRest, HttpStatus.OK);
    }
}