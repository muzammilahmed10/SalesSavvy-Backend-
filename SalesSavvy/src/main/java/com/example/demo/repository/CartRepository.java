package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.CartItem;

import jakarta.transaction.Transactional;

public interface CartRepository extends JpaRepository<CartItem, Integer>{

	@Query("SELECT c from CartItem c where c.user.userId = :userId AND c.product.productId = :productId ")
	Optional<CartItem> findByUserAndProduct(int userId, int productId);
	
	 @Query("SELECT COALESCE(SUM(c.quantity),0) FROM CartItem c WHERE c.user.userId = :userId")
	    int countTotalItems(int userId);
	 
	 @Query("SELECT c from CartItem c Join Fetch c.product p left join Fetch ProductImage pi on p.productId = pi.product.productId where c.user.userId = :userId")
		List<CartItem> findCartItemsWithProductDetails(int userId);
	 
	 @Query("UPDATE CartItem c SET c.quantity  = :quantity WHERE c.id = :cartItemId")
	 public void updateCartItemQuantity(int cartItemId, int quantity);

	@Modifying
	@Transactional
	@Query("DELETE FROM CartItem c WHERE c.user.userId = :userId AND c.product.productId = :productId")
	void deleteCartItem(int userId, int productId);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM CartItem c WHERE c.user.userId = :userId")
	void deleteAllCartItemsByUser(int userId);
     
}
