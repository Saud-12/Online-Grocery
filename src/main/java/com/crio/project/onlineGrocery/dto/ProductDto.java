package com.crio.project.onlineGrocery.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;

    @NotNull
    @Size(min=3,max=15)
    private String name;

    @NotNull
    @Size(min=3,max=15)
    private String category;

    @Digits(integer = 10,fraction = 2)
    @NotNull
    private Double price;

    @NotNull
    private int quantity;
}