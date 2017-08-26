package com.yyxz.main;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.yyxz.controller.IndexController;
import com.yyxz.controller.PurchaseController;
import com.yyxz.controller.SaleController;
import com.yyxz.controller.ShopController;
import com.yyxz.controller.StockController;
import com.yyxz.controller.WXController;
import com.yyxz.interceptor.AuthInterceptor;
import com.yyxz.model.Operation;
import com.yyxz.model.Purchase;
import com.yyxz.model.Sale;
import com.yyxz.model.Shop;
import com.yyxz.model.Stock;
import com.yyxz.model.User;

/**
 * Created by Czl on 2015/7/21.
 */
public class DemoConfig extends JFinalConfig {
	public static int COMMON_TABLE_PAGESIZE = 30;	// 默认使用30个
	
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("main/resources/a_little_config.txt");
		me.setDevMode(true);
	}

	public void configRoute(Routes me) {
		me.add("/", IndexController.class);
		me.add("/api/weixin", WXController.class);
		me.add("/api/shop", ShopController.class);
		me.add("/api/purchase", PurchaseController.class);
		me.add("/api/sale", SaleController.class);
		me.add("/api/stock", StockController.class);
	}

	public void configPlugin(Plugins me) {
		DruidPlugin aDruidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim(),
				PropKit.get("driverClass"));
		aDruidPlugin.start();
//		aDruidPlugin.setInitialSize(50);
		aDruidPlugin.set(10, 50, 100);
		aDruidPlugin.addFilter(new StatFilter());
		WallFilter wall = new WallFilter();
		wall.setDbType("mysql");
		aDruidPlugin.addFilter(wall);
		me.add(aDruidPlugin);
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(aDruidPlugin);
		arp.setDialect(new MysqlDialect());
		me.add(arp);
		arp.addMapping("t_user", User.class);
		arp.addMapping("t_operation", Operation.class);
		arp.addMapping("t_purchase", Purchase.class);
		arp.addMapping("t_sale", Sale.class);
		arp.addMapping("t_shop", Shop.class);
		arp.addMapping("t_stock", Stock.class);
		try {
			COMMON_TABLE_PAGESIZE = Integer.valueOf(PropKit.get("COMMON_TABLE_PAGESIZE"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void configInterceptor(Interceptors me) {
// 		me.add(new ExceptionAndLogInterceptor());
//		me.add(new ParamPkgInterceptor());
		me.add(new AuthInterceptor());
//		me.add(new ApiInterceptor());
	}

	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("ctx"));
	}

	@Override
	public void configEngine(Engine arg0) {
		// TODO Auto-generated method stub
		
	}
}
