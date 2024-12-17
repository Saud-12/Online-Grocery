package com.crio.project.onlineGrocery.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class CustomerDto {

    private Long id;
    @NotBlank(message = "Name of the Customer cannot be Blank")
    @NotNull
    @Size(min=3,max=20,message = "Name of the Customer should be within 3-20 characters")
    private String name;

    @Email
    @NotNull
    @NotBlank(message = "Email cannot be Blank")
    private String email;

    @NotNull
    @Size(max = 200)
    private String address;

    @NotNull
    private String phone;
}
