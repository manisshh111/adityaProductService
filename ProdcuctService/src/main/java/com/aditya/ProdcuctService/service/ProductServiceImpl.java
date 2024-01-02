package com.aditya.ProdcuctService.service;

import com.aditya.ProdcuctService.ProductRepository;
import com.aditya.ProdcuctService.entity.Product;
import com.aditya.ProdcuctService.exception.ProductServiceCustomException;
import com.aditya.ProdcuctService.model.ProductRequest;
import com.aditya.ProdcuctService.model.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
@NoArgsConstructor
public class ProductServiceImpl implements  ProductService{

    private ProductRepository productRepository ;

    @Override
    public long addProduct(ProductRequest productRequest){
        //log.info("Adding Product..") ;
        Product product = Product.builder()
                          .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build() ;

        productRepository.save(product) ;

        return product.getProductId() ;

    }

    public ProductResponse getProductById(long productId){
        log.info("Get the product for productid: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductServiceCustomException("Product with given id not found", "PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = new ProductResponse();
        copyProperties(product,productResponse);

        return  productResponse ;
    }
    public void reduceQuantity(long productId, long quantity){
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductServiceCustomException("Product with given Id not found",
                        "PRODUCT_NOT_FOUND")) ;

        if(product.getQuantity()<quantity){
            throw  new ProductServiceCustomException("Product does not have sufficient Quantity",
                    "INSUFFICIENT");
        }

        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
        log.info("Product Quantity updated Successfully");
    }
}
