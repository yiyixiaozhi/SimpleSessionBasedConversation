package com.yyxz.interceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

public class AuthInterceptor implements Interceptor {
	private Set<String> authSet = new HashSet<String>(Arrays.asList(PropKit.get("authUrl").split(",")));
	private static String targetUri = null;
	@Override
	public void intercept(Invocation invoc) {
		Controller contro = invoc.getController();
		String contextPath = contro.getRequest().getContextPath();
		String uri = invoc.getActionKey(); // 默认就是ActionKey
		String openId = contro.getSessionAttr("openId");
		//需要权限
		if(authSet.contains(uri)){
			targetUri = uri;
			if(openId == null){
				//获取openId
				contro.forwardAction("/api/user/toOauth");
				return;
			}else{
//				if(Student.dao.findByOpenId(openId) == null && !"/api/user/toRegisterPage".equals(uri) && !"/api/user/toBindingPage".equals(uri)){
//					//去绑定
//					contro.forwardAction("/api/user/toBindingPage");
//					return;
//				}
			}
		}
		if(openId != null && contro.getSessionAttr("loginStudent") == null){
//			contro.setSessionAttr("loginStudent", Student.dao.findByOpenId(openId));
		}
		invoc.invoke();
	}
	public static String getTargetUri() {
		return targetUri;
	}
	public static void setTargetUri(String targetUri) {
		AuthInterceptor.targetUri = targetUri;
	}
	
}
