package com.yyxz.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.thoughtworks.xstream.XStream;
import com.yyxz.weixin.ImageMessage;
import com.yyxz.weixin.InputMessage;
import com.yyxz.weixin.OutputMessage;
import com.yyxz.weixin.SerializeXmlUtil;
import com.yyxz.weixin.WXEventType;

public class WXController extends Controller {
//	private String Token = "zqzx_bianxiaohui";
//	private String Token = "zqzx_bianxiaohui_test";
	
	/**
	 * 微信回调的接口
	 */
	public void wxCallback() {
		System.out.println("进入chat");
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		String responseStr = "";
		if (isGet) {
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");
			System.out.println(signature);
			System.out.println(timestamp);
			System.out.println(nonce);
			System.out.println(echostr);
//			responseStr = access(request, response);
			responseStr = echostr;
		} else {
			// 进入POST聊天处理
			System.out.println("enter post");
			// 处理接收消息
			try {
				responseStr = acceptMessage(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		renderJson(responseStr);
	}

	private String acceptMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String responseStr = "";
        // 处理接收消息  
        ServletInputStream in = request.getInputStream();  
        // 将POST流转换为XStream对象  
        XStream xs = SerializeXmlUtil.createXstream();  
        xs.processAnnotations(InputMessage.class);  
        xs.processAnnotations(OutputMessage.class);  
        // 将指定节点下的xml节点数据映射为对象  
        xs.alias("xml", InputMessage.class);  
        // 将流转换为字符串  
        StringBuilder xmlMsg = new StringBuilder();  
        byte[] b = new byte[4096];  
        for (int n; (n = in.read(b)) != -1;) {  
            xmlMsg.append(new String(b, 0, n, "UTF-8"));  
        }
        System.out.println("微信发来内容：\n" + xmlMsg.toString());  
        // 将xml内容转换为InputMessage对象  
        InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString());  
  
        String servername = inputMsg.getToUserName();// 服务端  
        String custermname = inputMsg.getFromUserName();// 客户端  
        long createTime = inputMsg.getCreateTime();// 接收时间  
        Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间  
        // 文本消息  
        System.out.println("开发者微信号：" + inputMsg.getToUserName());  
        System.out.println("发送方帐号：" + inputMsg.getFromUserName());  
        System.out.println("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000l));  
        System.out.println("消息内容：" + inputMsg.getContent());  
        System.out.println("消息Id：" + inputMsg.getMsgId());  
        
        String msgType = inputMsg.getMsgType();
        OutputMessage outputMsg = new OutputMessage();
        outputMsg.setFromUserName(servername);
		outputMsg.setToUserName(custermname);
		outputMsg.setCreateTime(returnTime);
		outputMsg.setMsgType(msgType);
		switch (WXEventType.MsgType.valueOf(msgType)) {// 取得消息类型  
		case event:
			switch (WXEventType.Event.valueOf(inputMsg.getEvent())) {
			case CLICK:
				outputMsg.setMsgType(WXEventType.MsgType.text.toString());
				switch (WXEventType.MenuEventKey.valueOf(inputMsg.getEventKey())) {
				case register_bind:
					outputMsg.setContent("注册/绑定");
					break;
				case checkin_online:
					outputMsg.setContent("线上签到");
					break;
				case my_order:
					outputMsg.setContent("观看更多课程，请使用电脑访问<a href=\"www.zqgbzx.cn\">www.zqgbzx.cn</a>");
					break;
				case my_activities:
					outputMsg.setContent("联系客服\n客服：沈建强\nQQ：64391880\n座机：88568252-827\n客服：陶媛媛\nQQ： 252871148\n座机：88568252-813");
					break;
				case online_party_school_app:
					outputMsg.setContent("请使用电脑访问<a href=\"www.zqgbzx.cn\">www.zqgbzx.cn</a>");
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			break;
		case text:
			outputMsg.setContent("你说的是：" + inputMsg.getContent() + " 吗？");
			break;
		case image: // 获取并返回多图片消息
			System.out.println("获取多媒体信息");
			System.out.println("多媒体文件id：" + inputMsg.getMediaId());
			System.out.println("图片链接：" + inputMsg.getPicUrl());
			System.out.println("消息id，64位整型：" + inputMsg.getMsgId());
			ImageMessage images = new ImageMessage();
			images.setMediaId(inputMsg.getMediaId());
			outputMsg.setImage(images);
			break;
		default:
			break;
		}
//		System.out.println("xml转换：/n" + xs.toXML(outputMsg));
		responseStr = xs.toXML(outputMsg);
        System.out.println(responseStr);  
//      response.getWriter().write(xs.toXML(outputMsg));
		return responseStr;  
    }
}
