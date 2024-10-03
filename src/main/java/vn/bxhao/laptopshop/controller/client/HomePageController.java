package vn.bxhao.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vn.bxhao.laptopshop.domain.dto.RegisterDTO;
import vn.bxhao.laptopshop.servic.ProductService;

@Controller
public class HomePageController {

    private ProductService productService;

    public HomePageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getMethodName(Model model) {
        model.addAttribute("products", this.productService.FindAllProduct());
        return "client/homepage/show";
    }

    @GetMapping("/products")
    public String getProductDetailAll(Model model) {
        return "client/product/show";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") RegisterDTO hao) {
        return "client/auth/register";
    }

}
