package com.dispart.dto.orderdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParListParams {
    private String provId;
    private BigDecimal amt;
}
