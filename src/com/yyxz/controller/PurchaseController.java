package com.yyxz.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.yyxz.model.Purchase;
import com.yyxz.model.Shop;

public class PurchaseController extends Controller {
	
	public void toPurchasePage() {
		Long userId = getParaToLong("userId");
		List<Purchase> purchaseList = Purchase.dao.find("select * from t_purchase where user_id = ?", userId);
		setAttr("purchaseList", purchaseList);
		renderJsp("/WEB-INF/jsp/show_purchase.jsp");
	}
}
