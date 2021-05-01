package com.SG.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.SG.ecommerce.entity.Product;

@CrossOrigin("http://localhost:4200")
// why not here @Repository resource
public interface ProductRepository extends JpaRepository<Product,Long>{

}
