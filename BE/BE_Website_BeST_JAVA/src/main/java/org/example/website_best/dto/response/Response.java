package org.example.datn_website_best.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private String status;
    private String mess;
}
