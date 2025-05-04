package com.MrCherry.app.mapper;

import com.MrCherry.app.dto.ImageDto;
import com.MrCherry.app.dto.ProductDto;
import com.MrCherry.app.model.Image;
import com.MrCherry.app.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);

    Product toProduct(ProductDto productDto);

    List<ProductDto> toDto(List<Product> products);

    Image toImage(ImageDto imageDto);
    ImageDto toDto(Image image);



}
