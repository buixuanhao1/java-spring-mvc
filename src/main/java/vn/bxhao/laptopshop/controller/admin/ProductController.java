package vn.bxhao.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.bxhao.laptopshop.domain.Product;
import vn.bxhao.laptopshop.domain.Role;
import vn.bxhao.laptopshop.domain.User;
import vn.bxhao.laptopshop.service.ProductService;
import vn.bxhao.laptopshop.service.UploadServic;

@Controller
public class ProductController {

    private UploadServic uploadServic;
    private ProductService productService;

    public ProductController(UploadServic uploadServic, ProductService productService) {
        this.uploadServic = uploadServic;
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        model.addAttribute("products", productService.FindAllProducts());
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getProductView(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String addProduct(@ModelAttribute("newProduct") @Validated Product hao,
            BindingResult newUserBindingResult,
            @RequestParam("hao_File") MultipartFile file) {

        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (newUserBindingResult.hasErrors()) {
            return "admin/product/create";
        }
        String nameImage = this.uploadServic.handleSaveUpLoadFile(file, "Product");
        Product product = new Product();
        if (hao != null) {
            product.setImage(nameImage);
            product.setDetailDesc(hao.getDetailDesc());
            product.setFactory(hao.getFactory());
            product.setName(hao.getName());
            product.setPrice(hao.getPrice());
            product.setShortDesc(hao.getShortDesc());
            product.setQuantity(hao.getQuantity());
            product.setTarget(hao.getTarget());
        }

        this.productService.SaveProduct(product);

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    String getDetailProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", this.productService.FindProduct(id).get());
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    String ViewUpdateProduct(@PathVariable Long id, Model model) {
        model.addAttribute("newProduct", this.productService.FindProduct(id).get());
        return "admin/product/updateProduct";
    }

    @PostMapping("/admin/product/update")
    String UpdateProduct(@ModelAttribute("newProduct") @Validated Product hao,
            BindingResult newUserBindingResult,
            @RequestParam("hao_File") MultipartFile file) {

        Product currentProduct = productService.FindProduct(hao.getId()).get();
        String image = uploadServic.handleSaveUpLoadFile(file, "product");
        if (currentProduct != null) {
            currentProduct.setName(hao.getName());
            currentProduct.setDetailDesc(hao.getDetailDesc());
            currentProduct.setFactory(hao.getFactory());
            currentProduct.setImage(image);
            currentProduct.setShortDesc(hao.getShortDesc());
            currentProduct.setPrice(hao.getPrice());
            currentProduct.setQuantity(hao.getQuantity());
            currentProduct.setTarget(hao.getTarget());

        }
        this.productService.SaveProduct(currentProduct);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String DeleteProduct(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String PostDeleteProduct(@RequestParam("id") Long id) {
        productService.DeleteProductById(id);
        return "redirect:/admin/product";
    }

}
