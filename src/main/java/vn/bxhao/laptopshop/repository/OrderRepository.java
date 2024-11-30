package vn.bxhao.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.bxhao.laptopshop.domain.Order;
import vn.bxhao.laptopshop.domain.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAll();

    Order findByUser(User user);

    Order findOrderById(long id);

    void deleteById(Long id);
}
