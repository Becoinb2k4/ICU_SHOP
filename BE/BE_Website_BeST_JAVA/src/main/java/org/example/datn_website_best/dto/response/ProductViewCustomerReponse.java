package org.example.datn_website_best.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductViewCustomerReponse {

    private Long idProduct;

    private String nameProduct;

    private byte[] imageByte;

    private Long idBrand;

    private String nameBrand;

    private Long idCategory;

    private String nameCategory;

    private Long idMaterial;

    private String nameMaterial;

    private Long idTargetUser;

    private String nameTargetUser;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private BigDecimal minPriceAfterDiscount;

    private BigDecimal maxPriceAfterDiscount;


}
