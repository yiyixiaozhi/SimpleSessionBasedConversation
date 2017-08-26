package com.yyxz.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 进货表
 * @author bian.xh
 * @date 2017年8月4日13:32:43
 */
public class Sale extends Model<Sale> {
	private static final long serialVersionUID = 1767723520024068324L;
	public static final Sale dao = new Sale();

	public Page<Sale> getSalePage(Integer pageNumber,  Integer pageSize, Long useId) {
		String select = "select * ";
		String sqlExceptSelect = " from t_sale where user_id = " + useId + " order by sale_time desc"; 
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
}
