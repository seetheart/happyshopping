package com.ecommerce.happyshopping.service;

import com.ecommerce.happyshopping.exceptions.APIException;
import com.ecommerce.happyshopping.exceptions.ResourceNotFoundException;
import com.ecommerce.happyshopping.model.Category;
import com.ecommerce.happyshopping.model.Product;
import com.ecommerce.happyshopping.payload.CategoryDTO;
import com.ecommerce.happyshopping.payload.ProductDTO;
import com.ecommerce.happyshopping.payload.ProductResponse;
import com.ecommerce.happyshopping.repositories.CategoryRepository;
import com.ecommerce.happyshopping.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<Product> products = productPage.getContent();

        if (products.isEmpty()) {
            throw new APIException("No products found");
        }

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setLastPage(productPage.isLast());
        productResponse.setTotalElements(productPage.getNumberOfElements());

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId, "categoryId"));

        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category, pageable);

        List<Product> products = productPage.getContent();

        if (products.isEmpty()) {
            throw new APIException("No products found");
        }

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setLastPage(productPage.isLast());
        productResponse.setTotalElements(productPage.getNumberOfElements());

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Product> productPage = productRepository.findByProductNameContainingIgnoreCase(keyword, pageable);

        List<Product> products = productPage.getContent();

        if (products.isEmpty()) {
            throw new APIException("No products found");
        }

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setLastPage(productPage.isLast());
        productResponse.setTotalElements(productPage.getNumberOfElements());

        return productResponse;
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId, "categoryId"));

        Product product = modelMapper.map(productDTO, Product.class);
        product.setImage("default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }


    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId, "productId"));

        Product product = modelMapper.map(productDTO, Product.class);
        product.setProductId(savedProduct.getProductId());

        productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId, "productId"));

        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId, "productId"));

        String fileName = fileService.uploadFile(path, image);
        product.setImage(fileName);
        productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }
}
