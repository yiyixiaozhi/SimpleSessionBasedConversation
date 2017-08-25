package com.yyxz.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.core.Controller;
import com.thoughtworks.xstream.XStream;
import com.yyxz.model.Operation;
import com.yyxz.model.Shops;
import com.yyxz.model.User;
import com.yyxz.weixin.ImageMessage;
import com.yyxz.weixin.InputMessage;
import com.yyxz.weixin.OutputMessage;
import com.yyxz.weixin.SerializeXmlUtil;
import com.yyxz.weixin.WXEventType;

public class WXController extends Controller {
	private static final int QUERY_STATUS = 1;	// 查询状态码
	private static final int NEW_SHOP = 2;	// 新建商品
	private static final int MODIFY_SHOP = 3;	// 修改商品
	private static final int QUERY_SHOP = 4;	// 查询商品
	private static final int DELETE_SHOP = 5; // 删除商品
	private static final String URL_HEAD = "http://bxh7425014.vicp.cc/yiyixiaozhi";
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
		case text:
			User user = User.dao.checkUserByOpenId(inputMsg.getFromUserName());
			String msg = inputMsg.getContent();
			Integer status = null;
			try {
				status = Integer.valueOf(msg);
			}catch (NumberFormatException e) {
				//e.printStackTrace();
			}
			String help = "";
			System.out.println("用户发送状态码：" + status);
			if (status == null) {	// 文本消息
				Integer operationId = user.getInt("operation_id");	// 从数据库获取当前状态码，根据状态码解析文本消息
				help += "当前状态：" + operationId + "\n";
				System.out.println("查询到用户：" + user);
				Operation op = Operation.dao.findById(operationId);
				String[] arrayStr;
				Shops shop;
				Long shopId;
				String shopName;
				Long shopOwner;
				switch (operationId) {
				case NEW_SHOP:
					shopName = msg;
					if (shopName.length() > 50) {
						help += "名称必须50字以内";
					} else {
						shop = new Shops().set("user_id", user.get("id")).set("name", shopName);
						shop.save();
						help += "新增商品成功。" + "\n名称：" + shop.getStr("name") + "\n编号：" + shop.getLong("id") + "\n";
					}
					break;
				case MODIFY_SHOP:
					arrayStr = msg.split("\n");
					try {
						shopId = Long.valueOf(arrayStr[0]); // 商品编号
						String sName = arrayStr[1];	// 商品名称
						Shops sp = Shops.dao.findById(shopId);
						shopOwner = sp.getLong("user_id");
						if (user.getLong("id") == shopOwner) {
							sp.set("name", sName).update();
							help += "更新商品成功。" + "\n名称：" + sp.getStr("name") + "\n编号：" + sp.getLong("id") + "\n";
						} else {
							help += "只有创建者才能管理此商品";
						}
					} catch (Exception e) {
						help += "输入格式不正确";
					}
					break;
				case DELETE_SHOP:
					arrayStr = msg.split("\n");
					if (arrayStr[0].equals("d")) {
						try {
							shopId = Long.valueOf(arrayStr[1]); // 商品编号
							shop = Shops.dao.findById(shopId);
							shopOwner = shop.getLong("user_id");
							if (user.getLong("id") == shopOwner) {
								shop.delete();
								help += "删除商品成功。" + "\n名称：" + shop.getStr("name") + "\n编号：" + shop.getLong("id") + "\n";
							} else {
								help += "只有创建者才能管理此商品";
							}
						} catch (Exception e) {
							help += "输入格式不正确";
						}
					} else {
						help += "输入格式不正确";
					}
					break;
				default:
					help += op.getStr("operation_help");
					break;
				}
			} else {	// 数字/状态码消息
				Operation op = Operation.dao.findById(status);
				if (op != null) {	// 数据库有用户输入的这个状态码
					help += "切换到状态：" + status + "\n";
					user.set("operation_id", status).update();
					help += op.getStr("operation_help").replace("\\n", "\n");	// 反馈给用户状态码提示语
					switch (status) {
					case QUERY_STATUS:
						break;
					case MODIFY_SHOP:
						
						break;
					case QUERY_SHOP:
						help += "\n<a href=\"" + URL_HEAD + "/api/shops/toShopsPage?userId=" + user.get("id") + "\">点击查询商品明细</a>" ;  
						break;
					default:
						break;
					}
				} else {
					help = "无此状态码：" + status + "\n输入数字1查询状态码";
				}
			}
			outputMsg.setContent(help);
//			outputMsg.setContent("你留言：" + inputMsg.getContent() + " 吗？");
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
