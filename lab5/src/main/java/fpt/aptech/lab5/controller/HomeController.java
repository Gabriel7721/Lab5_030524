package fpt.aptech.lab5.controller;

import fpt.aptech.lab5.dto.ProductDTO;
import fpt.aptech.lab5.entities.Categories;
import org.springframework.ui.Model;

import fpt.aptech.lab5.entities.Products;
import fpt.aptech.lab5.service.NewService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    
    @Autowired
    NewService service;
    
    @Value("${upload.path}")
    private String fileUpload;
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("min", 0.0);
        model.addAttribute("max", 0.0);
        model.addAttribute("list", service.findAll());
        model.addAttribute("category", service.findAllCat());
        return "index";
    }
    
    @PostMapping("/search")
    public String search(Model model, @RequestParam("min") double min, @RequestParam("max") double max) {
        
        if (min == 0 && max == 0) {
            model.addAttribute("min", 0.0);
            model.addAttribute("max", 0.0);
            model.addAttribute("list", service.findAll());
            model.addAttribute("category", service.findAllCat());
        } else {
            model.addAttribute("min", 0.0);
            model.addAttribute("max", 0.0);
            
            List<Products> list = service.findByPrice(min, max);
            
            model.addAttribute("list", list);
            model.addAttribute("category", service.findAllCat());
        }
        
        return "index";
    }
    
    @PostMapping("/filter")
    public String search(Model model, @RequestParam("id") int categoryid) {
        
        if (categoryid == 0) {
            model.addAttribute("min", 0.0);
            model.addAttribute("max", 0.0);
            model.addAttribute("list", service.findAll());
            model.addAttribute("category", service.findAllCat());
        } else {
            Categories category = service.findCatById(categoryid);
            
            model.addAttribute("min", 0.0);
            model.addAttribute("max", 0.0);
            
            List<Products> list = service.findByIdCat(category);
            
            model.addAttribute("list", list);
            model.addAttribute("category", service.findAllCat());
        }
        
        return "index";
    }

    // Create a private method to prepare the data for form
    private void prepareModel(Model model, ProductDTO p) {
        List<Categories> list = service.findAllCat();
        model.addAttribute("product", p);
        model.addAttribute("category", list);
    }
    
    @GetMapping("/create")
    public String create(Model model) {
        prepareModel(model, new ProductDTO());
        return "create";
    }
    
    @PostMapping("/createNew")
    public String createNew(Model model, @Validated @ModelAttribute("product") ProductDTO product, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        
        if (product.getPhoto() == null || product.getPhoto().isEmpty()) {
            bindingResult.rejectValue("photo", "error.photo", "Please choose your photo to upload");
        }
        
        if (bindingResult.hasErrors()) {
            prepareModel(model, product);
            return "create";
        }
        
        MultipartFile multipartFile = product.getPhoto();
        String fileName = multipartFile.getOriginalFilename();
        //FileCopyUtils.copy(product.getPhoto().getBytes(), new File(fileName));
        Path uploadDirectory = Paths.get(fileUpload);

        // check the folder and created if needed
        if (!Files.exists(uploadDirectory)) {
            Files.createDirectories(uploadDirectory);
        }
        
        Path filePath = uploadDirectory.resolve(fileName);
        multipartFile.transferTo(filePath);
        
        Products newProduct = new Products(product.getId(), product.getName(), product.getPrice(), product.getQuantity(), fileName, product.getCategoryid());
        service.save(newProduct);
        return "redirect:/";
    }

    // transfer the product id to edit page
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        Products p = service.findById(id);
        model.addAttribute("product", p);
        model.addAttribute("category", service.findAllCat());
        return "update";
    }

    // save edit product
    @PostMapping("/save")
    public String save(Model model, @Validated @ModelAttribute("product") ProductDTO product, BindingResult result) throws IOException {

        // Kiểm tra xem có file mới được tải lên không
        MultipartFile multipartFile = product.getPhoto();
        
        if (result.hasErrors()) {
            prepareModel(model, product);
            return "update"; // Trở lại form nếu có lỗi
        }

        // Tìm sản phẩm hiện tại trong cơ sở dữ liệu
        Products existingProduct = service.findById(product.getId());
        
        if (!multipartFile.isEmpty()) {
            // Xử lý file mới
            String fileName = multipartFile.getOriginalFilename();
            FileCopyUtils.copy(multipartFile.getBytes(), new File(fileName));
            // Cập nhật đường dẫn ảnh mới
            existingProduct.setPhoto(fileName);
        }

        // Cập nhật thông tin khác
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setCategoryid(product.getCategoryid());

        // Lưu cập nhật vào cơ sở dữ liệu
        service.save(existingProduct);
        
        return "redirect:/"; // Chuyển hướng về trang danh sách sau khi lưu
    }
    
    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        service.delete(id);
        return "redirect:/";
    }
}
