package com.yyxz.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.yyxz.model.Purchase;
import com.yyxz.model.Shop;

public class PurchaseController extends Controller {
	
	public void toPurchasePage() {
		Long userId = getParaToLong("userId");
		List<Purchase> purchaseList = Purchase.dao.find("select * from t_purchase where user_id = ? order by purchase_time desc", userId);
		List<PurchaseInfo> purchaseInfoList = new ArrayList<PurchaseInfo>();
		for (Purchase p : purchaseList) {
			Shop sp = Shop.dao.findById(p.getLong("shop_id"));
			purchaseInfoList.add(new PurchaseInfo(sp, p));
		}
		setAttr("purchaseInfoList", purchaseInfoList);
		renderJsp("/WEB-INF/jsp/show_purchase.jsp");
	}
	
	public class PurchaseInfo {
		Shop shop;
		Purchase purchase;
		public PurchaseInfo(Shop shop, Purchase purchase) {
			this.shop = shop;
			this.purchase = purchase;
		}
		public Shop getShop() {
			return shop;
		}
		public void setShop(Shop shop) {
			this.shop = shop;
		}
		public Purchase getPurchase() {
			return purchase;
		}
		public void setPurchase(Purchase purchase) {
			this.purchase = purchase;
		}
	}
}
