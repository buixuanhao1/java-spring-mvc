package vn.bxhao.laptopshop.servic;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.bxhao.laptopshop.domain.Product;
import vn.bxhao.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
}
