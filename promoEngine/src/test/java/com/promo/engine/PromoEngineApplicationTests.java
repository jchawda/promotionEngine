package com.promo.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.promo.model.Product;
import com.promo.model.Promotion;
import com.promo.service.PromotionImpl;

@SpringBootTest
class PromoEngineApplicationTests {
	
	HashMap<String, List<Promotion>> promos = new HashMap<String, List<Promotion>>();
	
	PromotionImpl promotionImpl = new PromotionImpl();
	List<Product> products = new ArrayList();
	
	/*
	 * 
	 *Loading all the promotions and product list 
	 *before using them in the test cases.
	 *
	 *Active Promotions
	 *3 of A's for 130
	 *2 of B's for 45
	 *C & D for 30
	 *
	 *Active Products A,B,C,D
	 *
	 * */
	@BeforeEach
	void loadObj() {
		
		promotionImpl = new PromotionImpl();
		promotionImpl.createPromotion(promos, new Promotion("a", 130, 0, 3));
		promotionImpl.createPromotion(promos, new Promotion("b", 45, 0, 2));
		promotionImpl.createPromotion(promos, new Promotion("cd", 30, 0, 1));
		
		products = new ArrayList();
		products.add(new Product("A", 0));
		products.add(new Product("B", 0));
		products.add(new Product("C", 0));
		products.add(new Product("D", 0));
	}

	/*
	 * Scenario 1
	 * 
	 * 1 * A = 50
	 * 1 * B = 30
	 * 1 * C = 20
	 * 
	 * Total = 100
	 * */
	@Test
	void scenario1() {
				
		PromotionImpl promotionImpl = new PromotionImpl();
		HashMap<String, Product>purchaseProduct = new HashMap<String, Product>();
		purchaseProduct = PromoEngineApplication.addProductToList(purchaseProduct, new Product("A", 1));
    	purchaseProduct = PromoEngineApplication.addProductToList(purchaseProduct, new Product("B", 1));
    	purchaseProduct = PromoEngineApplication.addProductToList(purchaseProduct, new Product("C", 1));
    	assertEquals(100, promotionImpl.getTotalBillValue(purchaseProduct, promos));
	}
	
	/*
	 * Scenario 2
	 * 
	 * 5 * A = 130 + 2*50
	 * 5 * B = 45 + 45 + 30
	 * 1 * C = 20
	 * 
	 * Total = 370
	 * */
	
	@Test
	void scenario2() {
		
		PromotionImpl promotionImpl = new PromotionImpl();
		HashMap<String, Product>purchaseProduct = new HashMap<String, Product>();
		purchaseProduct = PromoEngineApplication.addProductToList(purchaseProduct, new Product("A", 5));
    	purchaseProduct = PromoEngineApplication.addProductToList(purchaseProduct, new Product("B", 5));
    	purchaseProduct = PromoEngineApplication.addProductToList(purchaseProduct, new Product("C", 1));
    	assertEquals(370, promotionImpl.getTotalBillValue(purchaseProduct, promos));
	}
	
	/*
	 * Scenario 3
	 * 
	 * 3 * A = 130
	 * 5 * B = 45 + 45 + 30
	 * 1 * C = 0
	 * 1 * D = 30
	 * 
	 * Total = 280
	 * */	
	@Test
	void scenario3() {
		
		PromotionImpl promotionImpl = new PromotionImpl();
		HashMap<String, Product>purchaseProduct = new HashMap<String, Product>();
		purchaseProduct = PromoEngineApplication.addProductToList(purchaseProduct, new Product("A", 3));
    	purchaseProduct = PromoEngineApplication.addProductToList(purchaseProduct, new Product("B", 5));
    	purchaseProduct = PromoEngineApplication.addProductToList(purchaseProduct, new Product("C", 1));
    	purchaseProduct = PromoEngineApplication.addProductToList(purchaseProduct, new Product("D", 1));
    	assertEquals(280, promotionImpl.getTotalBillValue(purchaseProduct, promos));
	}

	
	/*
	 * Scenario 4 (Add promotion and purchase)
	 * 
	 * Adding new promotion to the existing product A with 20% off on buying quantity of 4.
	 * Now we have 2 active promotions of product A. Price will be calculated on both the promotions 
	 * and whose value will be highest, that price will be taken for calculation of the total amount.
	 * 
	 * 4 * A = Promotion 1 = 130 + 50 = 180 OR Promotion 2 = 200 % 20 = 160
	 * 5 * B = 45 + 45 + 30
	 * 1 * C = 0
	 * 1 * D = 30
	 * 
	 * Total = 330
	 * */	
	@Test
	void addNewPromotionAndPurchase() {
		
		PromotionImpl promotionImpl = new PromotionImpl();
		promotionImpl.createPromotion(promos, new Promotion("A".toLowerCase(), 0, 20, 4));
		HashMap<String, Product>purchaseProduct = new HashMap<String, Product>();
		purchaseProduct = PromoEngineApplication.addProductToList(purchaseProduct, new Product("A", 4));
    	purchaseProduct = PromoEngineApplication.addProductToList(purchaseProduct, new Product("B", 5));
    	purchaseProduct = PromoEngineApplication.addProductToList(purchaseProduct, new Product("C", 1));
    	purchaseProduct = PromoEngineApplication.addProductToList(purchaseProduct, new Product("D", 1));
    	assertEquals(330, promotionImpl.getTotalBillValue(purchaseProduct, promos));
	}

}
