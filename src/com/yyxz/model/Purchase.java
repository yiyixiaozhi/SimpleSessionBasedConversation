package com.yyxz.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 销售表
 * 
 * @author bian.xh
 * @date 2017年8月4日13:32:43
 */
public class Purchase extends Model<Purchase> {
	private static final long serialVersionUID = -1635094020684773206L;
	public static final Purchase dao = new Purchase();

	public Page<Purchase> getPurchasePage(Integer pageNumber, Integer pageSize,
			Long useId) {
		String select = "select * ";
		String sqlExceptSelect = " from t_purchase where user_id = " + useId
				+ " order by purchase_time desc";
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
}
