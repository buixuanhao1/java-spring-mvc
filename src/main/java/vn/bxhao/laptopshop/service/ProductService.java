package vn.bxhao.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.bxhao.laptopshop.domain.Cart;
import vn.bxhao.laptopshop.domain.CartDetail;
import vn.bxhao.laptopshop.domain.Order;
import vn.bxhao.laptopshop.domain.OrderDetail;
import vn.bxhao.laptopshop.domain.Product;
import vn.bxhao.laptopshop.domain.User;
import vn.bxhao.laptopshop.repository.CartDetailRepository;
import vn.bxhao.laptopshop.repository.CartRepository;
import vn.bxhao.laptopshop.repository.OrderDetailRepository;
import vn.bxhao.laptopshop.repository.OrderRepository;
import vn.bxhao.laptopshop.repository.ProductRepository;
import vn.bxhao.laptopshop.repository.UserRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private CartDetailRepository cartDetailRepository;
    private CartRepository cartRepository;
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository, UserService userService,
            CartDetailRepository cartDetailRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
    }

    public Product SaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public List<Product> FindAllProducts() {
        return this.productRepository.findAll();
    }

    public Optional<Product> FindProduct(Long id) {
        return productRepository.findById(id);
    }

    public void DeleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> FindAllProduct() {
        return productRepository.findAll();
    }

    public void handleAddProductToCart(Long productId, String email, HttpSession session) {
        User user = this.userService.FindByEmail(email);
        if (user != null) {
            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);

                cart = this.cartRepository.save(otherCart);
            }

            Optional<Product> productOptional = this.productRepository.findById(productId);

            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();

                // check sản phẩm đã từng được thêm vào giỏ hàng chưa
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);
                if (oldDetail == null) {
                    CartDetail cd = new CartDetail();
                    cd.setCart(cart);
                    cd.setPrice(realProduct.getPrice());
                    cd.setProduct(realProduct);
                    cd.setQuantity(1);
                    this.cartDetailRepository.save(cd);
                    session.setAttribute("sum", this.cartRepository.countCartDetailsByCartId(user.getCart().getId()));
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                }

            }
        }
    }

    public void handleDeleteDetailProduct(Long productId, String email, HttpSession session) {
        User user = this.userService.FindByEmail(email);
        if (user != null) {
            this.cartDetailRepository.deleteById(productId);
            int sum = this.cartRepository.countCartDetailsByCartId(user.getCart().getId());
            session.setAttribute("sum", sum);

        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {

        Cart cart = this.cartRepository.findByUser(user);

        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            if (cartDetails != null) {

                Order order = new Order();
                order.setUser(user);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverName(receiverName);
                order.setReceiverPhone(receiverPhone);
                order = this.orderRepository.save(order);
                order.setStatus("Pending");
                double total = 0;
                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    total += cd.getPrice();

                    orderDetail.setOrder(order);
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setQuantity(cd.getQuantity());
                    this.orderDetailRepository.save(orderDetail);
                    this.cartDetailRepository.deleteById(cd.getId());
                }

                order.setTotalPrice(total);

                this.cartRepository.deleteById(cart.getId());

                session.setAttribute("sum", 0);

            }
        }

    }
}
