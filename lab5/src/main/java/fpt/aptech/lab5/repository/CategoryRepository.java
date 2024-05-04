package fpt.aptech.lab5.repository;

import fpt.aptech.lab5.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categories, Integer> {

}
