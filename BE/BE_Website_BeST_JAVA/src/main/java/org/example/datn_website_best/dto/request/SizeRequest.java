package org.example.datn_website_best.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SizeRequest {

    @NotBlank(message = "Tên kích cỡ là bắt buộc")
    private String name;
}
