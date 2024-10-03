package vn.bxhao.laptopshop.repository;

import org.springframework.stereotype.Repository;

import vn.bxhao.laptopshop.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
