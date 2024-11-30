package vn.bxhao.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.bxhao.laptopshop.domain.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAll();

    void deleteById(Long id);

    void deleteByOrderId(Long orderId);
}
