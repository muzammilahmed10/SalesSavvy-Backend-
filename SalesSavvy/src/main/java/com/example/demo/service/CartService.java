package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductImage;
import com.example.demo.entity.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository,
                       ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }

    public int getCartItemCount(int userId) {
        return cartRepository.countTotalItems(userId);
    }

    public void addToCart(int userId, int productId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with userId: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found With Id: " + productId));

        Optional<CartItem> existingItem = cartRepository.findByUserAndProduct(userId, productId);

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartRepository.save(cartItem);
        } else {
            CartItem newItem = new CartItem(user, product, quantity);
            cartRepository.save(newItem);
        }
    }

    public Map<String, Object> getCartItems(int userId) {
        List<CartItem> cartItems = cartRepository.findCartItemsWithProductDetails(userId);
        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        response.put("username", user.getUsername());
        response.put("role", user.getRole().name());

        List<Map<String, Object>> products = new ArrayList<>();
        int overallTotalPrice = 0;

        if (cartItems.isEmpty()) {
            response.put("cart", new HashMap<>()); // Return empty cart object
            return response;
        }

        for (CartItem cartItem : cartItems) {
            Map<String, Object> productDetails = new HashMap<>();
            Product product = cartItem.getProduct();
            List<ProductImage> productImages = productImageRepository.findByProduct_ProductId(product.getProductId());
            String imageUrl = "default-image-url"; // Default image

            if (productImages != null && !productImages.isEmpty()) {
                imageUrl = productImages.get(0).getImageUrl();
            }

            productDetails.put("productId", product.getProductId());
            productDetails.put("imageUrl", imageUrl);
            productDetails.put("name", product.getName());
            productDetails.put("description", product.getDescription());
            productDetails.put("price_per_unit", product.getPrice());
            productDetails.put("quantity", cartItem.getQuantity());
            productDetails.put("total_price", cartItem.getQuantity() * product.getPrice().doubleValue());

            products.add(productDetails);

            overallTotalPrice += cartItem.getQuantity() * product.getPrice().doubleValue();
        }

        Map<String, Object> cart = new HashMap<>();
        cart.put("products", products);
        cart.put("overall_total_price", overallTotalPrice);

        response.put("cart", cart);
        return response;
    }

    public void updateCartItemQuantity(int userId, int productId, int quantity) {
        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Optional<CartItem> existingItem = cartRepository.findByUserAndProduct(userId, productId);

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            if (quantity == 0) {
                deleteCartItem(userId, productId);
            } else {
                cartItem.setQuantity(quantity);
                cartRepository.save(cartItem);
            }
        }
    }

    public void deleteCartItem(int userId, int productId) {
        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));

        cartRepository.deleteCartItem(userId, productId);
    }
}
