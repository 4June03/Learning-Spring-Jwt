package com.SpringDevteria.demo.JPA.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntrospectResponse {
    private Boolean valid; //xác định token valid, còn hiệu lực hay không
}
