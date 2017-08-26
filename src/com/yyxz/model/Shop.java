package com.yyxz.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 商品表
 * 
 * @author bian.xh
 * @date 2017年8月4日13:32:43
 */
public class Shop extends Model<Shop> {

	private static final long serialVersionUID = 3510114169664396189L;
	public static final Shop dao = new Shop();

	public Page<Shop> getShopPage(Integer pageNumber, Integer pageSize,
			Long useId) {
		String select = "select * ";
		String sqlExceptSelect = " from t_shop where user_id = " + useId
				+ " order by update_time desc";
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
}
