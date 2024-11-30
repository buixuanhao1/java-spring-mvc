package vn.bxhao.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.bxhao.laptopshop.repository.RoleRepository;
import vn.bxhao.laptopshop.repository.UserRepository;
import vn.bxhao.laptopshop.domain.Role;
import vn.bxhao.laptopshop.domain.User;
import vn.bxhao.laptopshop.domain.dto.RegisterDTO;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User handleSaveUser(User user) {
        return userRepository.save(user);
    }

    public User FindByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> FindAllUser() {
        return userRepository.findAll();
    }

    public List<User> FindUserById(long id) {
        return userRepository.findById(id);
    }

    public User SaveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public Role FindRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public User RegisterDtoToUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setName(registerDTO.getFistName() + registerDTO.getLastName());
        user.setPassWord(registerDTO.getPassword());
        user.setEmail(registerDTO.getEmail());
        return user;
    }

    public boolean CheckEmailExists(String email) {
        return this.userRepository.existsByEmail(email);
    }

}
