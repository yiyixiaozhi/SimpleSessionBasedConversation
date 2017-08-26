package com.yyxz.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.yyxz.model.Shop;
import com.yyxz.model.Stock;

public class StockController extends Controller {
	
	public void toStockPage() {
		Long userId = getParaToLong("userId");
		List<Stock> stockList = Stock.dao.find("select * from t_stock where user_id = ? order by stock_time desc", userId);
		List<StockInfo> stockInfoList = new ArrayList<StockController.StockInfo>();
		for (Stock s : stockList) {
			Shop sp = Shop.dao.findById(s.getLong("shop_id"));
			stockInfoList.add(new StockInfo(s, sp));
		}
		setAttr("stockInfoList", stockInfoList);
		renderJsp("/WEB-INF/jsp/show_stock.jsp");
		
	}
	public class StockInfo {
		Stock stock;
		Shop shop;
		public StockInfo(Stock stock, Shop shop) {
			this.stock = stock;
			this.shop = shop;
		}
		public Stock getStock() {
			return stock;
		}
		public void setStock(Stock stock) {
			this.stock = stock;
		}
		public Shop getShop() {
			return shop;
		}
		public void setShop(Shop shop) {
			this.shop = shop;
		}
	}
}
