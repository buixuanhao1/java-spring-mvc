package vn.bxhao.laptopshop.controller.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import vn.bxhao.laptopshop.domain.Order;
import vn.bxhao.laptopshop.domain.OrderDetail;
import vn.bxhao.laptopshop.domain.Product;
import vn.bxhao.laptopshop.domain.User;
import vn.bxhao.laptopshop.repository.OrderDetailRepository;
import vn.bxhao.laptopshop.repository.OrderRepository;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping("/admin/order")
    public String getOrder(Model model) {
        model.addAttribute("orders", this.orderRepository.findAll());
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    String getOrderDetail(@PathVariable Long id, Model model, HttpSession session) {

        Order order = this.orderRepository.findOrderById(id);
        model.addAttribute("orderDetails", order.getOrderDetails());
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    String ViewUpdateOrder(@PathVariable Long id, Model model) {
        model.addAttribute("newOrder", this.orderRepository.findOrderById(id));
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    String UpdateOrder(@ModelAttribute("newOrder") Order oder) {
        this.orderRepository.save(oder);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete")
    @Transactional
    public String GetDeleteOrder(@RequestParam("id") Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is required!");
        }
        this.orderDetailRepository.deleteByOrderId(id);
        this.orderRepository.deleteById(id);
        return "redirect:/admin/order";
    }
}
