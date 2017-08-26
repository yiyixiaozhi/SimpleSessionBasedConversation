package com.yyxz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.yyxz.main.DemoConfig;
import com.yyxz.model.Sale;
import com.yyxz.model.Shop;

public class SaleController extends Controller {
	public void toSalePage() {
		Long userId = getParaToLong("userId");
		Page<Sale> page = Sale.dao.getSalePage(1, DemoConfig.COMMON_TABLE_PAGESIZE, userId);
		List<SaleInfo> saleInfoList = packageSaleInfoList(page);
		setAttr("userId", userId);
		setAttr("pageNumber", 1);
		setAttr("pageSize", DemoConfig.COMMON_TABLE_PAGESIZE);
		setAttr("saleInfoList", saleInfoList);
		renderJsp("/WEB-INF/jsp/show_sale.jsp");
	}
	
	public void toSalePageByIndex() {
		Long userId = getParaToLong("userId");
		Integer pageNumber = getParaToInt("pageNumber");
		Integer pageSize = getParaToInt("pageSize");
		Page<Sale> page = Sale.dao.getSalePage(pageNumber, pageSize, userId);
		List<SaleInfo> itemDataList = packageSaleInfoList(page);
		Map<String, Object> dataMaps = new HashMap<String, Object>();
		dataMaps.put("itemDataList", itemDataList);
		renderJson(dataMaps);
	}
	
	public List<SaleInfo> packageSaleInfoList(Page<Sale> page) {
		List<SaleInfo> infoList = new ArrayList<SaleInfo>();
		for (Sale p : page.getList()) {
			Shop sp = Shop.dao.findById(p.getLong("shop_id"));
			infoList.add(new SaleInfo(sp, p));
		}
		return infoList;
	}
	
	public class SaleInfo {
		Shop shop;
		Sale sale;
		public SaleInfo(Shop shop, Sale sale) {
			this.shop = shop;
			this.sale = sale;
		}
		public Shop getShop() {
			return shop;
		}
		public void setShop(Shop shop) {
			this.shop = shop;
		}
		public Sale getSale() {
			return sale;
		}
		public void setSale(Sale sale) {
			this.sale = sale;
		}
	}
}
