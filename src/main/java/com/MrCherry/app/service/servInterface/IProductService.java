package com.MrCherry.app.service.servInterface;

import com.MrCherry.app.dto.OrderItemDto;
import com.MrCherry.app.dto.ProductDto;
import com.MrCherry.app.model.OrderItem;

import java.util.List;

public interface IProductService {
    //CRUD BASICO
    ProductDto create(ProductDto productDto);
    void deleteById(Long idProduct);
    ProductDto findById(Long productId);
    ProductDto findByName(String productName);
    List<ProductDto> finAll();

    //CONSULTAS DE PRODUCTOS
    List<ProductDto> findAvailableProducts();
    ProductDto enableProduct(Long productId);

    //GESTION DE STOCK
    ProductDto setStock(Long id, int stock);
    ProductDto increaseStock(Long id, int addedStock);
    ProductDto decreaseStock(Long id, int decreasedStock);
    boolean checkStockById(Long id);

    //PROCESO DE VENTA
    void SaleProccess(List<OrderItem> orderItems);
    void returnProduct(List<OrderItemDto> orderItems);
}
