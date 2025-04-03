package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/order")
public class OrderController {

	OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> getOrdersForUser(HttpServletRequest request) {
		try {
		User user=(User)request.getAttribute("authenticatedUser");
		if(user == null) {
			return ResponseEntity.status(401).body(Map.of("error","user not authenticated"));
		}
		
		Map<String,Object> response = 	orderService.getOrdersForUser(user);
		
		return ResponseEntity.ok(response);
	}
		catch (IllegalArgumentException e) {
			return ResponseEntity.status(401).body(Map.of("error",e.getMessage()));
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(Map.of("error","unexpected Exception"));
		}
	}
	
}
