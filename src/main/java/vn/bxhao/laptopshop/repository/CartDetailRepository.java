package vn.bxhao.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.bxhao.laptopshop.domain.Cart;
import vn.bxhao.laptopshop.domain.CartDetail;
import vn.bxhao.laptopshop.domain.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    CartDetail findByCartAndProduct(Cart cart, Product product);

    void deleteById(Long id);
}
