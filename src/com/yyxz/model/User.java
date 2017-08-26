package com.yyxz.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 用户表
 * 
 * @author bian.xh
 * @date 2017年8月4日13:32:43
 */
public class User extends Model<User> {

	private static final long serialVersionUID = -1738023793407902037L;
	public static final User dao = new User();

	/**
	 * 检查系统是否存在此用户微信openId，如果没有，返回用户(包含状态)
	 * 
	 * @param openId
	 * @return
	 */
	public User checkUserByOpenId(String openId) {
		String sql = "select id, operation_id from t_user where openid = ?";
		User user = dao.findFirst(sql, openId);
		if (user == null) {
			user = new User().set("openid", openId);
			user.save();
		}
		return user;
	}
}
