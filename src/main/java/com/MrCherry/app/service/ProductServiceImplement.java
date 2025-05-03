package com.MrCherry.app.service;

import com.MrCherry.app.dto.ProductDto;
import com.MrCherry.app.mapper.ProductMapper;
import com.MrCherry.app.model.OrderItem;
import com.MrCherry.app.model.Product;
import com.MrCherry.app.repository.ProductRepository;
import com.MrCherry.app.service.servInterface.IProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImplement implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;




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
        Product product = productRepository.findById(productId).orElseThrow(
                ()->new RuntimeException("no se encontro")
        );
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto findByName(String productName) {
        Product product = productRepository.findByName(productName).orElseThrow(
                () -> new RuntimeException("mensae")
        );
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> finAll() {
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        if(products.isEmpty())
            throw new RuntimeException("la lista esta vacia no se encontraron produtos");
        for(Product product : products){
            productDtos.add(productMapper.toDto(product));
        }
        return productDtos;
    }

    @Override
    public List<ProductDto> findAvailableProducts() {
        List<Product> products = productRepository.findByStockGreaterThanAndActiveTrue(0);
        if(products.isEmpty())
            throw new RuntimeException("No hay productos disponibles");
        return productMapper.toDto(products);
    }

    @Override
    public ProductDto enableProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()->new RuntimeException("no se encontro")
        );
        product.setActive(true);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto setStock(Long id, int stock) {
        Product product = productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("no se encontro")
        );
        product.setStock(stock);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto increaseStock(Long id, int addedStock) {
        Product product = productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("no se encontro")
        );
        product.setStock(product.getStock() + addedStock);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto decreaseStock(Long id, int decreasedStock) {
        Product product = productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("no se encontro")
        );
        if(product.getStock()<decreasedStock)
            throw new RuntimeException("No hay stock disponivle");
        product.setStock(product.getStock() - decreasedStock);
        product = productRepository.save(product);
        return productMapper.toDto(product);        }

    @Override
    public boolean checkStockById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("no se encontro")
        );
        return product.getStock() > 0;
    }

    @Override
    @Transactional
    public void SaleProccess(List<OrderItem> orderItems) {
        orderItems.forEach(orderItem -> {
            this.decreaseStock(orderItem.getProduct().getId(),orderItem.getQuantity());
        });
    }

    @Override
    public void returnProcess(List<OrderItem> orderItems) {
        orderItems.forEach(orderItem -> {
            this.increaseStock(orderItem.getProduct().getId(),orderItem.getQuantity());
        });
    }
}
