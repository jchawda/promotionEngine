package com.promo.service;

import java.util.HashMap;
import java.util.List;

import com.promo.model.Product;
import com.promo.model.Promotion;

public interface IPromotion {
	
	HashMap<String, List<Promotion>> createPromotion(HashMap<String, List<Promotion>> promos, Promotion promotion);
	Double getTotalBillValue(HashMap<String, Product> product, HashMap<String, List<Promotion>> promos);
		
}
