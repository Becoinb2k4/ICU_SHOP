package org.example.datn_website_best.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionDetailRequest {
    @NotNull(message = "Id của đợt giảm giá chi tiết là bắt buộc")
    private Long idPromotionDetail;
    @NotNull(message = "Số lượng của sản phẩm chi tiết là bắt buộc")
    @PositiveOrZero(message = "Số lượng không được âm")
    private Integer quantity;
}
