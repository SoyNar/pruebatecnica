package com.springboot.pruebatecnica.Respository;
import com.springboot.pruebatecnica.Models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductsRepository extends JpaRepository<Products, Integer> {
    boolean existsByName(String name);
    Optional<Products> findById(Integer id);
}
