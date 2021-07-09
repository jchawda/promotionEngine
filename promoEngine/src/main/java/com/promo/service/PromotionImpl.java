package com.promo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.promo.model.Product;
import com.promo.model.Promotion;

public class PromotionImpl implements IPromotion {

	@Override
	public HashMap<String, List<Promotion>> createPromotion(HashMap<String, List<Promotion>> promos,
			Promotion promotion) {

		List<Promotion> promotions = new ArrayList();
		if (promos.containsKey(promotion.getSku())) {
			promotions = promos.get(promotion.getSku());
			promotions.add(promotion);
			promos.put(promotion.getSku(), promotions);
		} 
		else
		{
			promotions.add(promotion);
			promos.put(promotion.getSku(), promotions);
		}

		return promos;

	}

	@Override
	public Double getTotalBillValue(HashMap<String, Product> purchaseProduct, HashMap<String, List<Promotion>> promos) {

		double totalValue = 0L;

		List<Promotion> promotions = new ArrayList();
		
		if(purchaseProduct.isEmpty() == false)
		{
			
			for (Iterator iterator = purchaseProduct.keySet().iterator(); iterator.hasNext();) {
				
				Product product = purchaseProduct.get(iterator.next());
				promotions = promos.get(product.getSkuId().toLowerCase());
				
				if (promotions == null || promotions.isEmpty()) {
					totalValue = totalValue + product.getPrice() * product.getQuantity();
				} else if (promotions.size() == 1) {
					Promotion promotion = promotions.get(0);
					if (promotion.getPercent() == 0 && promotion.getAmount() > 0) {
						
						totalValue = totalValue + (product.getQuantity() / promotion.getQuantity()) * promotion.getAmount()
								+ (product.getQuantity() % promotion.getQuantity()) * product.getPrice();
						
					} else {
						
						double disPer = 100 - promotion.getPercent();
						double disPrice = (disPer * product.getPrice()) * 100;
						
						totalValue = totalValue + (product.getQuantity() / promotion.getQuantity()) * disPrice
								+ (product.getQuantity() % promotion.getQuantity()) * product.getPrice();
					}
				}else {
					List<Double> totalValueList = new ArrayList();
					for (Iterator iterator1 = promotions.iterator(); iterator1.hasNext();) {
						Promotion promotion = (Promotion) iterator1.next();
						if (promotion.getPercent() == 0 && promotion.getAmount() > 0) {
							
							totalValueList.add((product.getQuantity() / promotion.getQuantity()) * promotion.getAmount()
									+ (product.getQuantity() % promotion.getQuantity()) * product.getPrice());
							
						} else {
							
							double disPer = 100 - promotion.getPercent();
							double disPrice = (disPer * product.getPrice()) / 100;
							
							totalValueList.add((product.getQuantity() / promotion.getQuantity()) * (product.getQuantity() * disPrice)
									+ (product.getQuantity() % promotion.getQuantity()) * product.getPrice());
						}
					}
					totalValue = totalValue + Collections.max(totalValueList);
				}
			}
			
		}
		return totalValue;
	}

}
