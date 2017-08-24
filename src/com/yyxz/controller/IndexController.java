package com.yyxz.controller;

import com.jfinal.core.Controller;

/**
 * Created by bianxiaohui 2017-8-24
 */
public class IndexController extends Controller {

    public void index(){
//        renderText("欢迎进入JFinal的世界！！！");
    	renderJsp("/index.jsp");
    }
}
