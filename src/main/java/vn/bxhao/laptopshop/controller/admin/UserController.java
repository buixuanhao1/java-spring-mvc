package vn.bxhao.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import vn.bxhao.laptopshop.domain.Role;
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

    @RequestMapping("/admin/user/create")
    public String getAdminView(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") @Validated User hao,
            BindingResult newUserBindingResult,
            @RequestParam("hao_File") MultipartFile file) {

        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (newUserBindingResult.hasErrors()) {
            return "/admin/user/create";
        }

        String avatar = uploadServic.handleSaveUpLoadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(hao.getPassWord());
        Role role = this.userService.FindRoleByName(hao.getRole().getName());
        hao.setPassWord(hashPassword);
        hao.setRole(role);
        hao.setAvatar(avatar);
        this.userService.handleSaveUser(hao);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user")
    public String getUserView(Model model) {
        List<User> users = userService.FindAllUser();
        model.addAttribute("users", users);

        return "admin/user/show";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetail(Model model, @PathVariable Long id) {
        List<User> users = userService.FindUserById(id);
        model.addAttribute("user", users.get(0));
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/update{id}")
    public String updateUser(Model model, @PathVariable Long id) {
        List<User> user = userService.FindUserById(id);
        model.addAttribute("newUser", user.get(0));
        return "admin/user/updateUser";
    }

    @PostMapping("/admin/user/update")
    public String updateUserPage(@ModelAttribute("newUser") User hao,
            @RequestParam("hao_File") MultipartFile file) {
        User currentUser = userService.FindUserById(hao.getId()).get(0);
        String avatar = uploadServic.handleSaveUpLoadFile(file, "avatar");
        Role role = this.userService.FindRoleByName(hao.getRole().getName());
        if (currentUser != null) {
            currentUser.setAddress(hao.getAddress());
            currentUser.setEmail(hao.getEmail());
            currentUser.setPhone(hao.getPhone());
            currentUser.setAvatar(avatar);
            currentUser.setRole(role);

        }
        this.userService.handleSaveUser(currentUser);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/delete{id}")
    public String deleteUser(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/user";
    }

}
