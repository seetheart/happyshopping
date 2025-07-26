package com.ecommerce.happyshopping.repositories;

import com.ecommerce.happyshopping.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
