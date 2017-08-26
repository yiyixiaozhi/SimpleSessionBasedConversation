package com.yyxz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.yyxz.main.DemoConfig;
import com.yyxz.model.Shop;
import com.yyxz.model.Stock;

public class StockController extends Controller {
	public void toStockPage() {
		Long userId = getParaToLong("userId");
		Page<Stock> page = Stock.dao.getStockPage(1, DemoConfig.COMMON_TABLE_PAGESIZE, userId);
		List<StockInfo> stockInfoList = packageStockInfoList(page);
		setAttr("userId", userId);
		setAttr("pageNumber", 1);
		setAttr("pageSize", DemoConfig.COMMON_TABLE_PAGESIZE);
		setAttr("stockInfoList", stockInfoList);
		renderJsp("/WEB-INF/jsp/show_stock.jsp");
	}
	
	public void toStockPageByIndex() {
		Long userId = getParaToLong("userId");
		Integer pageNumber = getParaToInt("pageNumber");
		Integer pageSize = getParaToInt("pageSize");
		Page<Stock> page = Stock.dao.getStockPage(pageNumber, pageSize, userId);
		List<StockInfo> itemDataList = packageStockInfoList(page);
		Map<String, Object> dataMaps = new HashMap<String, Object>();
		dataMaps.put("itemDataList", itemDataList);
		renderJson(dataMaps);
	}
	
	public List<StockInfo> packageStockInfoList(Page<Stock> page) {
		List<StockInfo> infoList = new ArrayList<StockInfo>();
		for (Stock p : page.getList()) {
			Shop sp = Shop.dao.findById(p.getLong("shop_id"));
			infoList.add(new StockInfo(sp, p));
		}
		return infoList;
	}
	public class StockInfo {
		Stock stock;
		Shop shop;
		public StockInfo(Shop shop, Stock stock) {
			this.shop = shop;
			this.stock = stock;
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
