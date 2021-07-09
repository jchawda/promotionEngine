package com.promo.model;


public class Product {
	private String skuId;
	private double price;
	private int quantity;

	public Product(String skuId, int quantity) {
		this.skuId = skuId;
		this.quantity = quantity;

		switch (this.skuId.toLowerCase()) {
		case "a":
			this.price = 50;
			break;

		case "b":
			this.price = 30;
			break;

		case "c":
			this.price = 20;
			break;

		case "d":
			this.price = 15;
			break;

		default:
			this.price = 0;
		}
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public boolean equals(Object obj) {
		
		if((obj instanceof Product) == false)
			return false;
		
		Product p = (Product) obj;
		return p.getSkuId().equalsIgnoreCase(this.getSkuId());
	}
	

	
	
}
