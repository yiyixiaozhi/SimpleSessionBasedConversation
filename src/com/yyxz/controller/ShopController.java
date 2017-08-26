package com.yyxz.controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.yyxz.main.DemoConfig;
import com.yyxz.model.Shop;

public class ShopController extends Controller {
	public void toShopPage() {
		Long userId = getParaToLong("userId");
		Page<Shop> shopPage = Shop.dao.getShopPage(1, DemoConfig.COMMON_TABLE_PAGESIZE, userId);
		setAttr("userId", userId);
		setAttr("pageNumber", 1);
		setAttr("pageSize", DemoConfig.COMMON_TABLE_PAGESIZE);
		setAttr("shopList", shopPage.getList());
		renderJsp("/WEB-INF/jsp/show_shop.jsp");
	}
	
	public void toShopPageByIndex() {
		Long userId = getParaToLong("userId");
		Integer pageNumber = getParaToInt("pageNumber");
		Integer pageSize = getParaToInt("pageSize");
		Page<Shop> shopPage = Shop.dao.getShopPage(pageNumber, pageSize, userId);
		Map<String, Object> dataMaps = new HashMap<String, Object>();
		dataMaps.put("itemDataList", shopPage.getList());
		renderJson(dataMaps);
	}
}
