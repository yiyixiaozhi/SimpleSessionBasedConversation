package com.yyxz.main;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.StrKit;

public class ZhiWebSocketHandler extends Handler {

	private Pattern filterUrlRegxPattern;
	
	public ZhiWebSocketHandler(String filterUrlRegx) {
		if (StrKit.isBlank(filterUrlRegx))
			throw new IllegalArgumentException("The para filterUrlRegx can not be blank.");
		filterUrlRegxPattern = Pattern.compile(filterUrlRegx);
	}
	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		// TODO Auto-generated method stub
		if (filterUrlRegxPattern.matcher(target).find())
			return ;
		else
			next.handle(target, request, response, isHandled);
	}

}
