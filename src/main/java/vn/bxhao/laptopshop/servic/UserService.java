package vn.bxhao.laptopshop.servic;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.bxhao.laptopshop.repository.UserRepository;
import vn.bxhao.laptopshop.domain.Role;
import vn.bxhao.laptopshop.domain.User;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleSaveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> FindByEmail(String email) {
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
        return this.userRepository.findByName(name);
    }

}
