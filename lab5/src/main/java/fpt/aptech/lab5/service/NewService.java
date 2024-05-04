package fpt.aptech.lab5.service;

import fpt.aptech.lab5.entities.Categories;
import fpt.aptech.lab5.entities.Products;
import fpt.aptech.lab5.repository.CategoryRepository;
import fpt.aptech.lab5.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewService {
    
    @Autowired
    CategoryRepository repository;
    @Autowired
    ProductRepository repository1;
    
    public Products save(Products p) {
        
        return repository1.save(p);
        
    }
    
    public List<Products> findAll() {
        return repository1.findAll();
    }
    
    public List<Products> findByPrice(double min, double max) {
        
        return repository1.findByPrice(min, max);
        
    }
    
    public Products findById(int id) {
        
        return repository1.findById(id).get();
        
    }
    
    public List<Categories> findAllCat() {
        
        return repository.findAll();
        
    }
    
    public List<Products> findByIdCat(Categories categoryid) {
        
        return repository1.findByIdCat(categoryid);
        
    }
    
    public Categories findCatById(int categoryid) {
        
        return repository.findById(categoryid).get();
        
    }
    
    public void delete(int id) {
        Products p = repository1.findById(id).get();
        repository1.delete(p);
    }
}
