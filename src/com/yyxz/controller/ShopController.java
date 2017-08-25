package com.yyxz.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.yyxz.model.Shops;

public class ShopController extends Controller {
	
	public void toShopsPage() {
		Long userId = getParaToLong("userId");
		List<Shops> shopList = Shops.dao.find("select * from t_shops where user_id = ?", userId);
		setAttr("shopList", shopList);
		renderJsp("/WEB-INF/jsp/show_shops.jsp");
	}
}
