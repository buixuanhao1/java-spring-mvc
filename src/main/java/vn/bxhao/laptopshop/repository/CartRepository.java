package vn.bxhao.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.bxhao.laptopshop.domain.Cart;
import vn.bxhao.laptopshop.domain.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);

    Cart findCartById(Long id);

    void deleteById(Long id);

    @Query("SELECT COUNT(cd) FROM Cart c JOIN c.cartDetails cd WHERE c.id = :id")
    int countCartDetailsByCartId(@Param("id") Long id);
}
