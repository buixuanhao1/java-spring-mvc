package vn.bxhao.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import jakarta.servlet.ServletContext;
import vn.bxhao.laptopshop.domain.User;
import vn.bxhao.laptopshop.servic.UploadServic;
import vn.bxhao.laptopshop.servic.UserService;

@Controller
public class UserController {
    private final UserService userService;
    private final UploadServic uploadServic;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadServic uploadServic, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadServic = uploadServic;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/")
    public String getHomePage() {
        System.out.println(userService.FindByEmail("haogolike2@gmail.com"));
        return "hello";
    }

    @RequestMapping("/admin/user/create")
    public String getAdminView(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") User hao,
            @RequestParam("hao_File") MultipartFile file) {

        String avatar = uploadServic.handleSaveUpLoadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(hao.getPassWord());
        // this.userService.handleSaveUser(hao);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user")
    public String getUserView(Model model) {
        List<User> users = userService.FindAllUser();
        model.addAttribute("users", users);

        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetail(Model model, @PathVariable Long id) {
        List<User> users = userService.FindUserById(id);
        model.addAttribute("user", users.get(0));
        return "admin/user/detail";
    }

    @RequestMapping("/admin/user/update{id}")
    public String updateUser(Model model, @PathVariable Long id) {
        List<User> user = userService.FindUserById(id);
        model.addAttribute("newUser", user.get(0));
        return "admin/user/updateUser";
    }

    @RequestMapping("/admin/user/delete{id}")
    public String deleteUser(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("user", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/user";
    }

    @PostMapping("/admin/user/update")
    public String updateUserPage(@ModelAttribute("newUser") User hao) {
        User currentUser = userService.FindUserById(hao.getId()).get(0);
        if (currentUser != null) {
            currentUser.setAddress(hao.getAddress());
            currentUser.setEmail(hao.getEmail());
            currentUser.setPhone(hao.getPhone());
        }
        this.userService.handleSaveUser(currentUser);
        return "redirect:/admin/user";
    }
}
