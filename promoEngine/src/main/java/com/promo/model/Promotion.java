package com.promo.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Promotion {
	private String sku;
	private double amount = 0;
	private double percent = 0;
	private int quantity;
	
	public Promotion(String sku, double amount, double percent, int quantity)
	{
		this.sku = sku;
		this.amount = amount;
		this.percent = percent;
		this.quantity = quantity;
	}
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
