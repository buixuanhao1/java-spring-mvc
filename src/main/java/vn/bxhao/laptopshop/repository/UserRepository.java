package vn.bxhao.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.bxhao.laptopshop.domain.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    User findByEmail(String email);

    List<User> findAll();

    List<User> findById(long id);

    void deleteById(long id);

    public boolean existsByEmail(String email);

}
