package com.mycompany.inventory.services;

import org.springframework.http.ResponseEntity;

import com.mycompany.inventory.response.CategoryResponseRest;

public interface ICategoryServices {

	public ResponseEntity<CategoryResponseRest> search();
	public ResponseEntity<CategoryResponseRest> searchById(Long id);
	
}
