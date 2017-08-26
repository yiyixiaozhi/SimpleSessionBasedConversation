package com.yyxz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.yyxz.main.DemoConfig;
import com.yyxz.model.Purchase;
import com.yyxz.model.Shop;

public class PurchaseController extends Controller {
	public void toPurchasePage() {
		Long userId = getParaToLong("userId");
		Page<Purchase> page = Purchase.dao.getPurchasePage(1, DemoConfig.COMMON_TABLE_PAGESIZE, userId);
		List<PurchaseInfo> purchaseInfoList = packagePurchaseInfoList(page);
		setAttr("userId", userId);
		setAttr("pageNumber", 1);
		setAttr("pageSize", DemoConfig.COMMON_TABLE_PAGESIZE);
		setAttr("purchaseInfoList", purchaseInfoList);
		renderJsp("/WEB-INF/jsp/show_purchase.jsp");
	}
	
	public void toPurchasePageByIndex() {
		Long userId = getParaToLong("userId");
		Integer pageNumber = getParaToInt("pageNumber");
		Integer pageSize = getParaToInt("pageSize");
		Page<Purchase> page = Purchase.dao.getPurchasePage(pageNumber, pageSize, userId);
		List<PurchaseInfo> itemDataList = packagePurchaseInfoList(page);
		Map<String, Object> dataMaps = new HashMap<String, Object>();
		dataMaps.put("itemDataList", itemDataList);
		renderJson(dataMaps);
	}
	
	public List<PurchaseInfo> packagePurchaseInfoList(Page<Purchase> page) {
		List<PurchaseInfo> purchaseInfoList = new ArrayList<PurchaseInfo>();
		for (Purchase p : page.getList()) {
			Shop sp = Shop.dao.findById(p.getLong("shop_id"));
			purchaseInfoList.add(new PurchaseInfo(sp, p));
		}
		return purchaseInfoList;
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
