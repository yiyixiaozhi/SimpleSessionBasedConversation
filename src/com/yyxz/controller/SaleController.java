package com.yyxz.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.yyxz.model.Sale;
import com.yyxz.model.Shop;

public class SaleController extends Controller {
	
	public void toSalePage() {
		Long userId = getParaToLong("userId");
		List<Sale> saleList = Sale.dao.find("select * from t_sale where user_id = ? order by sale_time desc", userId);
		List<SaleInfo> saleInfoList = new ArrayList<SaleInfo>();
		for (Sale s : saleList) {
			Shop sp = Shop.dao.findById(s.getLong("shop_id"));
			saleInfoList.add(new SaleInfo(sp, s));
		}
		setAttr("saleInfoList", saleInfoList);
		renderJsp("/WEB-INF/jsp/show_sale.jsp");
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
