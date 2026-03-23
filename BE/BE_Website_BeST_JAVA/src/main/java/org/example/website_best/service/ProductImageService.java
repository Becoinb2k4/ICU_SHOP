package org.example.datn_website_best.service;

import org.springframework.transaction.annotation.Transactional;
import org.example.datn_website_best.dto.response.ProductImageResponse;
import org.example.datn_website_best.model.ProductDetail;
import org.example.datn_website_best.model.ProductImage;
import org.example.datn_website_best.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductImageService {
    @Autowired
    ProductImageRepository productImageRepository;

    public List<ProductImageResponse> findAll() {
        return productImageRepository.listProductImageResponse();
    }

    public List<ProductImageResponse> findListImageByIdProductDetail(Long id) {
        return productImageRepository.findListImageByIdProductDetail(id);
    }
    @Transactional
    public boolean createProductImage(ProductDetail productDetail, List<byte[]> listImage) {
        try {
            for (byte[] imageBytes : listImage){
                ProductImage productImage = ProductImage.builder()
                        .productDetail(productDetail)
                        .imageByte(imageBytes)
                        .build();
                productImageRepository.save(productImage);
            }
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

}
