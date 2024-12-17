package com.crio.project.onlineGrocery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;

    @NotNull
    @Digits(integer=10,fraction=2)
    @JsonProperty("total_price")
    private Double totalPrice;

    @NotNull
    @JsonProperty("customer_id")
    private Long customerId;

    @NotEmpty
    @JsonProperty("product_ids")
    private List<Long> productIds=new ArrayList<>();
}
