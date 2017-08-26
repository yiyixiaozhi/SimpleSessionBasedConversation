package com.yyxz.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 存货表
 * 
 * @author bian.xh
 * @date 2017年8月4日13:32:43
 */
public class Stock extends Model<Stock> {
	private static final long serialVersionUID = -4744265500107019836L;
	public static final Stock dao = new Stock();

	public Page<Stock> getStockPage(Integer pageNumber, Integer pageSize,
			Long useId) {
		String select = "select * ";
		String sqlExceptSelect = " from t_stock where user_id = " + useId
				+ " order by stock_time desc";
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
}
