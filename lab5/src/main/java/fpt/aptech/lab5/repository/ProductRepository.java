package fpt.aptech.lab5.repository;

import fpt.aptech.lab5.entities.Categories;
import fpt.aptech.lab5.entities.Products;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Products, Integer> {

    @Query("SELECT p FROM Products p WHERE p.price BETWEEN :min AND :max")
    List<Products> findByPrice(@Param("min") double min, @Param("max") double max);

    @Query("SELECT p FROM Products p WHERE p.categoryid = :categoryid")
    List<Products> findByIdCat(@Param("categoryid") Categories categoryid);
}
