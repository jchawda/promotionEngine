package com.promo.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.promo.model.Product;
import com.promo.model.Promotion;
import com.promo.service.PromotionImpl;

@SpringBootApplication
public class PromoEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromoEngineApplication.class, args);
		
		HashMap<String, List<Promotion>> promos = new HashMap<String, List<Promotion>>();
		
		PromotionImpl promotionImpl = new PromotionImpl();
		promotionImpl.createPromotion(promos, new Promotion("a", 130, 0, 3));
		promotionImpl.createPromotion(promos, new Promotion("b", 45, 0, 2));
		promotionImpl.createPromotion(promos, new Promotion("cd", 30, 0, 1));
		
		List<Product> products = new ArrayList();
		products.add(new Product("A", 0));
		products.add(new Product("B", 0));
		products.add(new Product("C", 0));
		products.add(new Product("D", 0));
		
		HashMap<String, Product>purchaseProduct = new HashMap<String, Product>();
		
		System.out.println("Press 1 to buy Products \nPress 2 to add New Promotion to Product");
	    Scanner my_scan = new Scanner(System.in);
	    double int_val = my_scan.nextInt();
	     if(1 == int_val)
	     {
	    	purchaseProduct = addProductToList(purchaseProduct, new Product("A", 3));
	    	purchaseProduct = addProductToList(purchaseProduct, new Product("B", 5));
	    	purchaseProduct = addProductToList(purchaseProduct, new Product("C", 1));
	    	purchaseProduct = addProductToList(purchaseProduct, new Product("D", 1));
	    	System.out.println("Your Total Bill Amount is : "+ promotionImpl.getTotalBillValue(purchaseProduct, promos));
	    	System.out.println("List of Products :");
	    	System.out.println("SKUID	Price");
	    	for (Iterator iterator = products.iterator(); iterator.hasNext();) {
				Product product = (Product) iterator.next();
				System.out.println(product.getSkuId()+"	"+product.getPrice());
			}
	    	
	    	while(true)
	    	{
	    		System.out.print("Press 1 to buy Product \nPress 0 to Exit");
	    		int exitValue = my_scan.nextInt();
	    		
			    if(exitValue != 1)
			    	break;
			    
		    	System.out.println("Enter Product SKUID : ");
		    	String skuId = my_scan.next();
		    	System.out.println("Enter Product Quantity : ");
		    	int quantity = my_scan.nextInt();
		    	
		    	purchaseProduct = addProductToList(purchaseProduct, new Product(skuId, quantity));
		    	
	    	}
	    	
	    	System.out.println("Your Total Bill Amount is : "+ promotionImpl.getTotalBillValue(purchaseProduct, promos));
	     }
	     else 
	     {
	    	System.out.println("Enter Product SKUID : ");
		    String skuId = my_scan.next();
		    System.out.println("Enter % of Promotion : ");
		    int percent = my_scan.nextInt();
		    System.out.println("Enter Product Quantity on which promotion is to be applied : ");
	    	int quantity = my_scan.nextInt();
	    	
		    promotionImpl.createPromotion(promos, new Promotion(skuId.toLowerCase(), 0, percent, quantity));
		    System.out.println("Promotion added Succesfully!!!");
		    System.out.println("List of Products :");
	    	System.out.println("SKUID	Price");
	    	for (Iterator iterator = products.iterator(); iterator.hasNext();) {
				Product product = (Product) iterator.next();
				System.out.println(product.getSkuId()+"	"+product.getPrice());
			}
	    	
	    	purchaseProduct = addProductToList(purchaseProduct, new Product("A", 5));
	    	purchaseProduct = addProductToList(purchaseProduct, new Product("B", 5));
	    	purchaseProduct = addProductToList(purchaseProduct, new Product("C", 1));
	    	purchaseProduct = addProductToList(purchaseProduct, new Product("D", 1));
	    	System.out.println("Your Total Bill Amount is : "+ promotionImpl.getTotalBillValue(purchaseProduct, promos));
	    	
	    	while(true)
	    	{
	    		System.out.print("Press 1 to buy Product \nPress 0 to Exit");
	    		int exitValue = my_scan.nextInt();
	    		
			    if(exitValue != 1)
			    	break;
			    
		    	System.out.println("Enter Product SKUID : ");
		    	skuId = my_scan.next();
		    	System.out.println("Enter Product Quantity : ");
		    	quantity = my_scan.nextInt();
		    	
		    	purchaseProduct = addProductToList(purchaseProduct, new Product(skuId, quantity));
		    	
	    	}
	    	
	    	System.out.println("Your Total Bill Amount is : "+ promotionImpl.getTotalBillValue(purchaseProduct, promos));
		    
	     }
	     my_scan.close();
	}
	
	public static HashMap<String, Product> addProductToList(HashMap<String, Product> purchaseProduct, Product newProduct) {
		
		if(purchaseProduct.isEmpty())
			purchaseProduct.put(newProduct.getSkuId(), newProduct);
		else
		{
			if(purchaseProduct.containsKey(newProduct.getSkuId()))
			{
				Product product = purchaseProduct.get(newProduct.getSkuId());
				int newQuantity = product.getQuantity() + newProduct.getQuantity();
				newProduct.setQuantity(newQuantity);
			}
				
			if((purchaseProduct.containsKey("C") && newProduct.getSkuId().equalsIgnoreCase("D"))
					|| (purchaseProduct.containsKey("D") && newProduct.getSkuId().equalsIgnoreCase("C")))
			{
				String existingProduct = "";
				if(purchaseProduct.containsKey("C"))
					existingProduct = "C";
				else
					existingProduct = "D";
				
				Product product = purchaseProduct.get(existingProduct);
				newProduct.setQuantity(1);
				newProduct.setSkuId("CD");
				
				purchaseProduct.remove(existingProduct);
			}
			
			purchaseProduct.put(newProduct.getSkuId(), newProduct);
			
		}		
		return purchaseProduct;
	}
	

}
