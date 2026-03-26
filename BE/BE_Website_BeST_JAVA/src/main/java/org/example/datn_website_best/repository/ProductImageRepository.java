package org.example.datn_website_best.repository;


import org.springframework.transaction.annotation.Transactional;
import org.example.datn_website_best.dto.response.ProductImageResponse;
import org.example.datn_website_best.model.ProductDetail;
import org.example.datn_website_best.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Query("""
                SELECT new org.example.datn_website_best.dto.response.ProductImageResponse(
                p.imageByte) 
            FROM ProductImage p  WHERE p.productDetail.id = :id
                """)
    List<ProductImageResponse> findListImageByIdProductDetail(@Param("id") Long id);

    @Query(value = """
            SELECT new org.example.datn_website_best.dto.response.ProductImageResponse(
                p.imageByte) 
            FROM ProductImage p 
            """)
    List<ProductImageResponse> listProductImageResponse();

    @Transactional
    void deleteByProductDetail(ProductDetail productDetail);
}
