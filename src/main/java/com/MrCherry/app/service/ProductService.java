package com.MrCherry.app.service;

import com.MrCherry.app.dto.ProductDto;
import com.MrCherry.app.mapper.ProductMapper;
import com.MrCherry.app.model.OrderItem;
import com.MrCherry.app.model.Product;
import com.MrCherry.app.repository.ProductRepository;
import com.MrCherry.app.service.servInterface.IProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;




    public Product findProduct(Long productId){
        return productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("asd")
        );
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        product.setCreatedAt(LocalDate.now());
        product.setActive(true);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public void deleteById(Long idProduct) {
        if(!productRepository.existsById(idProduct))
            throw new RuntimeException("no found");
        productRepository.deleteById(idProduct);
    }

    @Override
    public ProductDto findById(Long productId) {
        return productMapper.toDto(findProduct(productId));
    }

    @Override
    public ProductDto findByName(String productName) {
        Product product = productRepository.findByName(productName).orElseThrow(
                () -> new RuntimeException("mensae")
        );
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        for(Product product : products){
            productDtos.add(productMapper.toDto(product));
        }
        return productDtos;
    }

    @Override
    public ProductDto enableOrDisableProduct(Long productId, boolean status) {
        Product product = findProduct(productId);
        product.setActive(status);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> findAvailableProducts() {
        List<Product> products = productRepository.findByStockGreaterThanAndActiveTrue(0);
        return productMapper.toDto(products);
    }

    @Override
    public ProductDto setStock(Long id, int stock) {
        Product product = findProduct(id);
        product.setStock(stock);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto increaseStock(Long id, int addedStock) {
        Product product = findProduct(id);
        int newStock = product.getStock() + addedStock;
        if(newStock != product.getStock()){
            product.setStock(newStock);
            product = productRepository.save(product);
        }

        return productMapper.toDto(product);
    }

    @Override
    public ProductDto decreaseStock(Long id, int decreasedStock) {
        Product product = findProduct(id);
        if(product.getStock()<decreasedStock)
            throw new RuntimeException("No hay stock disponivle");
        int newStock = product.getStock() - decreasedStock;
        if(newStock != product.getStock()){
            product.setStock(newStock);
            product = productRepository.save(product);
        }
        return productMapper.toDto(product);
    }

    @Override
    public boolean checkStockById(Long id) {
        Product product = findProduct(id);
        return product.getStock() > 0;
    }

    @Override
    @Transactional
    public void SaleProccess(List<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {
            Product product = this.findProduct(item.getProduct().getId());
            if (!product.isActive())
                throw new RuntimeException("El producto " + product.getName() + " no está disponible");

            if (product.getStock() < item.getQuantity())
                throw new RuntimeException("Stock insuficiente para el producto " + product.getName());
        }
        orderItems.forEach(orderItem -> {
            this.decreaseStock(orderItem.getProduct().getId(),orderItem.getQuantity());
        });
    }

    @Override
    @Transactional
    public void returnProcess(List<OrderItem> orderItems) {
        orderItems.forEach(orderItem -> {
            this.increaseStock(orderItem.getProduct().getId(),orderItem.getQuantity());
        });
    }
}
