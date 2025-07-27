package com.ecommerce.happyshopping.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    // getters and setters under the hood are rquired by jpa so always define them
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank
    @Size(min = 5, max = 100, message = "Size should be greater than 5 characters")
    private String categoryName;

    // it is a good practice to generate a default constructor for jpa

}
