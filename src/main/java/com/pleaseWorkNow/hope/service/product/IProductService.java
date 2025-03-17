package com.pleaseWorkNow.hope.service.product;

import com.pleaseWorkNow.hope.dto.ProductDto;
import com.pleaseWorkNow.hope.model.Product;
import com.pleaseWorkNow.hope.request.AddProductRequest;
import com.pleaseWorkNow.hope.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    void deleteProductById(Long id);

    Product updateProduct(ProductUpdateRequest request, Long productId);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByNameAndBrand(String name, String brand);

    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
