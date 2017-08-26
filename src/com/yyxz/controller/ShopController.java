package com.yyxz.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.yyxz.model.Shop;

public class ShopController extends Controller {
	
	public void toShopPage() {
		Long userId = getParaToLong("userId");
		List<Shop> shopList = Shop.dao.find("select * from t_shop where user_id = ? order by update_time desc", userId);
		setAttr("shopList", shopList);
		renderJsp("/WEB-INF/jsp/show_shop.jsp");
	}
}
