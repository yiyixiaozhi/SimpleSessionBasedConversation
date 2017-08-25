package com.yyxz.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.yyxz.model.Shop;
import com.yyxz.model.Stock;

public class StockController extends Controller {
	
	public void toStockPage() {
		Long userId = getParaToLong("userId");
		List<Stock> stockList = Stock.dao.find("select * from t_stock where user_id = ?", userId);
		setAttr("stockList", stockList);
		renderJsp("/WEB-INF/jsp/show_stock.jsp");
	}
}
