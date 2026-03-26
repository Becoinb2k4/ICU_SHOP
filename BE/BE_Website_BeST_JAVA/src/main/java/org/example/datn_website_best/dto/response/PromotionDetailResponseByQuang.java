package org.example.datn_website_best.dto.response;

import lombok.*;
import org.example.datn_website_best.model.Promotion;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionDetailResponseByQuang {
    private Promotion promotion;
    private List<ProductPromotionResponseByQuang> productPromotionResponseByQuangs;
}
