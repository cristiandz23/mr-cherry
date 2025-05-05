package com.MrCherry.app.controller;

import com.MrCherry.app.dto.ProductDto;
import com.MrCherry.app.service.servInterface.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("product/api/")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping("create")
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto productDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(productDto));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product with id " + id + " was deleted");
    }
    @GetMapping("get-by-id/{id}")
    public ResponseEntity<ProductDto> finById( @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));

    }
    @GetMapping("get-by-name/{name}")
    public ResponseEntity<ProductDto> findByName(@Valid @PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByName(name));

    }
    @GetMapping("get-all")
    public ResponseEntity<List<ProductDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());

    }

    @PutMapping("manual-enable/{id}")
    public ResponseEntity<ProductDto> enableOrDisableProduct(@PathVariable Long id,@RequestParam boolean enable){
        return ResponseEntity.status(HttpStatus.OK).body(productService.enableOrDisableProduct(id,enable));

    }
    @GetMapping("get-all-available")
    public ResponseEntity<List<ProductDto>> findAvailableProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAvailableProducts());

    }
    @PutMapping("/stock/{id}")
    public ResponseEntity<ProductDto> setStock(@PathVariable Long id,@RequestParam int stock){
        return ResponseEntity.status(HttpStatus.OK).body(productService.setStock(id,stock));

    }


}
