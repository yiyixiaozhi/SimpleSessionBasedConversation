package com.yyxz.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.yyxz.model.Sale;

public class SaleController extends Controller {
	
	public void toSalePage() {
		Long userId = getParaToLong("userId");
		List<Sale> saleList = Sale.dao.find("select * from t_sale where user_id = ?", userId);
		setAttr("saleList", saleList);
		renderJsp("/WEB-INF/jsp/show_sale.jsp");
	}
}
