package com.springboot.pruebatecnica.Respository;

import com.springboot.pruebatecnica.Models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductsRepository extends JpaRepository<Products, Integer> {

}
