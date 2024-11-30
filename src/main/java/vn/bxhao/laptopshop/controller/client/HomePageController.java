package vn.bxhao.laptopshop.controller.client;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vn.bxhao.laptopshop.domain.Role;
import vn.bxhao.laptopshop.domain.User;
import vn.bxhao.laptopshop.domain.dto.RegisterDTO;
import vn.bxhao.laptopshop.service.ProductService;
import vn.bxhao.laptopshop.service.UserService;

@Controller
public class HomePageController {

    private ProductService productService;
    private UserService userService;
    private final PasswordEncoder passwordEncoder;

    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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
    public String handleRegister(@ModelAttribute("registerUser") @Validated RegisterDTO hao,
            BindingResult newUserBindingResult) {

        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (newUserBindingResult.hasErrors()) {
            return "client/auth/register";
        }
        User user = this.userService.RegisterDtoToUser(hao);
        String hashPassword = this.passwordEncoder.encode(user.getPassWord());
        Role role = this.userService.FindRoleByName("USER");
        user.setPassWord(hashPassword);
        user.setRole(role);
        this.userService.handleSaveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String getDenyPage() {
        return "client/auth/deny";
    }

}
