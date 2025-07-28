package com.ecommerce.happyshopping.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;
    // it is a good practice to generate a default constructor for jpa

}
