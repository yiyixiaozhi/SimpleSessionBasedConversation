package com.yyxz.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.core.Controller;
import com.thoughtworks.xstream.XStream;
import com.yyxz.model.Operation;
import com.yyxz.model.Purchase;
import com.yyxz.model.Sale;
import com.yyxz.model.Shop;
import com.yyxz.model.Stock;
import com.yyxz.model.User;
import com.yyxz.weixin.ImageMessage;
import com.yyxz.weixin.InputMessage;
import com.yyxz.weixin.OutputMessage;
import com.yyxz.weixin.SerializeXmlUtil;
import com.yyxz.weixin.WXEventType;

public class WXController extends Controller {
	private static final int QUERY_STATUS = 0;	// 查询状态码
	private static final int NEW_SHOP = 1;	// 新建商品
	private static final int DELETE_SHOP = 2; // 删除商品
	private static final int MODIFY_SHOP = 3;	// 修改商品
//	private static final int QUERY_SHOP = 4;	// 查询商品
	private static final int CREATE_PURCHASE = 4; // 新增进货记录
	private static final int DELETE_PURCHASE = 5; // 删除进货记录
	private static final int MODIFY_PURCHASE = 6; // 修改进货记录
//	private static final int QUERY_PURCHASE = 9; // 查询进货记录
	private static final int CREATE_SALE = 7; // 新增销售记录
	private static final int DELETE_SALE = 8; // 删除销售记录
	private static final int MODIFY_SALE = 9; // 修改销售记录
//	private static final int QUERY_SALE = 13; // 查询销售记录
//	private static final int QUERY_STOCK = 14; // 查询库存记录
	
	private static final String URL_HEAD = "http://bxh7425014.vicp.cc/yiyixiaozhi";
	
	private static final String HELP = "状态码查询：\n" +
			"1：新增商品\n" +
			"2：删除商品\n" +
			"3：修改商品\n" +
			"4：新增进货记录\n" +
			"5：删除进货记录\n" +
			"6：修改进货记录\n" +
			"7：新增销售记录\n" +
			"8：删除销售记录\n" +
			"9：修改销售记录";
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
			Long userId = user.getLong("id");
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
				String[] arrayStr = msg.split("\n");
				Shop shop;
				Long shopId;
				String shopName;
				Long shopOwner;
				switch (operationId) {
				case NEW_SHOP:
					shopName = msg;
					if (shopName.length() > 50) {
						help += "名称必须50字以内";
					} else {
						shop = new Shop().set("user_id", user.get("id")).set("name", shopName)
								.set("update_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						shop.save();
						help += "新增商品成功。" + "\n名称：" + shop.getStr("name") + "\n编号：" + shop.getLong("id") + "\n";
					}
					break;
				case MODIFY_SHOP:
					try {
						shopId = Long.valueOf(arrayStr[0]); // 商品编号
						String sName = arrayStr[1];	// 商品名称
						Shop sp = Shop.dao.findById(shopId);
						shopOwner = sp.getLong("user_id");
						if (user.getLong("id") == shopOwner) {
							sp.set("name", sName).set("update_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).update();
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
							shop = Shop.dao.findById(shopId);
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
				case CREATE_PURCHASE:
					// 商品编号+进货总价+进货数量+客户名称
					try {
						Long createPurchaseShopId = Long.valueOf(arrayStr[0]);
						Double createPurchasePrice = Double.valueOf(arrayStr[1]);
						Integer createPurchaseNum = Integer.valueOf(arrayStr[2]);
						String createPurchaseClientName = arrayStr[3];
						if (createPurchaseClientName.length() > 20) {
							help += "客户名称必须20字以内";
						} else {
							Purchase createPurchase = new Purchase().set("shop_id",createPurchaseShopId)
									.set("purchase_price", createPurchasePrice)
									.set("purchase_num", createPurchaseNum)
									.set("client_name", createPurchaseClientName)
									.set("purchase_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
									.set("user_id", user.get("id"));
							createPurchase.save();
							help += "新增进货成功:" + createPurchase;
							updateStock(createPurchaseShopId, userId);
							help += "\n库存已更新";
						}
					} catch (Exception e) {
						help += "输入格式不正确";
					}
					break;
				case MODIFY_PURCHASE:
					// 进货编号+进货总价+进货数量+客户名称
					try {
						Long modifyPurchaseId = Long.valueOf(arrayStr[0]);
						Integer modifyPurchasePrice = Integer.valueOf(arrayStr[1]);
						Double modifyPurchaseNum = Double.valueOf(arrayStr[2]);
						String modifyPurchaseClientName = arrayStr[3];
						if (modifyPurchaseClientName.length() > 20) {
							help += "客户名称必须20字以内";
						} else {
							Purchase modifyPurchase = Purchase.dao.findById(modifyPurchaseId);
							Long modifyPurchaseOwner = modifyPurchase.getLong("user_id");
							if (user.getLong("id") == modifyPurchaseOwner) {
								modifyPurchase.set("purchase_price", modifyPurchasePrice)
								.set("purchase_num", modifyPurchaseNum)
								.set("client_name", modifyPurchaseClientName)
								.set("purchase_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
								.update();
								help += "更新进货成功:" + modifyPurchase;
								updateStock(modifyPurchase.getLong("shop_id"), userId);
								help += "\n库存已更新";
							} else {
								help += "只有创建者才能管理此商品";
							}
						}
					} catch (Exception e) {
						help += "输入格式不正确";
					}
					break;
				case DELETE_PURCHASE:
					arrayStr = msg.split("\n");
					if (arrayStr[0].equals("d")) {
						try {
							Long deletePurchaseId = Long.valueOf(arrayStr[1]); // 进货编号
							Purchase deletePurchase = Purchase.dao.findById(deletePurchaseId);
							Long deletePurchaseOwner = deletePurchase.getLong("user_id");
							if (user.getLong("id") == deletePurchaseOwner) {
								deletePurchase.delete();
								help += "删除商品成功。\n" + deletePurchase;
								updateStock(deletePurchase.getLong("shop_id"), userId);
								help += "\n库存已更新";
							} else {
								help += "只有创建者才能管理此进货";
							}
						} catch (Exception e) {
							help += "输入格式不正确";
						}
					} else {
						help += "输入格式不正确";
					}
					break;
				case CREATE_SALE:
					// 商品编号+销售总价+销售数量+客户名称
					try {
						Long createSaleShopId = Long.valueOf(arrayStr[0]);
						Double createSalePrice = Double.valueOf(arrayStr[1]);
						Integer createSaleNum = Integer.valueOf(arrayStr[2]);
						String createSaleClientName = arrayStr[3];
						if (createSaleClientName.length() > 20) {
							help += "客户名称必须20字以内";
						} else {
							Sale createSale = new Sale().set("shop_id",createSaleShopId)
									.set("sale_price", createSalePrice)
									.set("sale_num", createSaleNum)
									.set("client_name", createSaleClientName)
									.set("sale_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
									.set("user_id", user.get("id"));
							createSale.save();
							help += "新增销售成功:" + createSale;
							updateStock(createSaleShopId, userId);
							help += "\n库存已更新";
						}
					} catch (Exception e) {
						help += "输入格式不正确";
					}
					break;
				case MODIFY_SALE:
					// 销售编号+销售总价+销售数量+客户名称
					try {
						Long modifySaleId = Long.valueOf(arrayStr[0]);
						Double modifySalePrice = Double.valueOf(arrayStr[1]);
						Integer modifySaleNum = Integer.valueOf(arrayStr[2]);
						String modifySaleClientName = arrayStr[3];
						if (modifySaleClientName.length() > 20) {
							help += "客户名称必须20字以内";
						} else {
							Sale modifySale = Sale.dao.findById(modifySaleId);
							Long modifySaleOwner = modifySale.getLong("user_id");
							if (user.getLong("id") == modifySaleOwner) {
								modifySale.set("sale_price", modifySalePrice)
								.set("sale_num", modifySaleNum)
								.set("client_name", modifySaleClientName)
								.set("sale_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
								.update();
								help += "更新销售成功:" + modifySale;
								updateStock(modifySale.getLong("shop_id"), userId);
								help += "\n库存已更新";
							} else {
								help += "只有创建者才能管理此销售记录";
							}
						}
					} catch (Exception e) {
						help += "输入格式不正确";
					}
					break;
				case DELETE_SALE:
					arrayStr = msg.split("\n");
					if (arrayStr[0].equals("d")) {
						try {
							Long deleteSaleId = Long.valueOf(arrayStr[1]); // 销售编号
							Sale deleteSale = Sale.dao.findById(deleteSaleId);
							Long deleteSaleOwner = deleteSale.getLong("user_id");
							if (user.getLong("id") == deleteSaleOwner) {
								deleteSale.delete();
								help += "删除销售记录成功。\n" + deleteSale;
								updateStock(deleteSale.getLong("shop_id"), userId);
								help += "\n库存已更新";
							} else {
								help += "只有创建者才能管理此销售记录";
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
				if (status == QUERY_STATUS) {
					help += HELP;
				} else {
					Operation op = Operation.dao.findById(status);
					if (op != null) {	// 数据库有用户输入的这个状态码
						help += "切换到状态：" + status + "\n";
						user.set("operation_id", status).update();
						help += op.getStr("operation_help").replace("\\n", "\n");	// 反馈给用户状态码提示语
					} else {
						help = "无此状态码：" + status + "\n输入数字1查询状态码";
					}
				}
			}
			help += "\n-----\n快捷入口：";
			help += "\n<a href=\"" + URL_HEAD + "/api/purchase/toPurchasePage?userId=" + user.get("id") + "\">点击查询进货明细</a>" ;
			help += "\n<a href=\"" + URL_HEAD + "/api/sale/toSalePage?userId=" + user.get("id") + "\">点击查询销售明细</a>" ;
			help += "\n<a href=\"" + URL_HEAD + "/api/stock/toStockPage?userId=" + user.get("id") + "\">点击查询存货明细</a>" ;
			help += "\n<a href=\"" + URL_HEAD + "/api/shop/toShopPage?userId=" + user.get("id") + "\">点击查询商品明细</a>" ;  
			outputMsg.setContent(help);
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
	
	/**
	 * 根据商品编号更新库存信息
	 * @param shopId 商品编号
	 */
	private void updateStock(Long shopId, Long userId) {
		// 查询是否有销售记录
		Sale saleRecord = Sale.dao.findFirst("select * from t_sale where shop_id = ?", shopId);
		Purchase purchaseRecord = Purchase.dao.findFirst("select * from t_purchase where shop_id = ?", shopId);
		Purchase tmpStock = null;
		if (saleRecord == null) {	// 无销售记录，以进货记录为准
			tmpStock = Purchase.dao.findFirst("select sum(purchase_price) as purchase_price, sum(purchase_num) as purchase_num from t_purchase where shop_id = ?", shopId);
		} else {
			if (purchaseRecord == null) { //有销售记录，但是无进货记录，直接返回
				return;
			} else {	// 有销售和进货记录，库存采用进货-销售记录的方式
				tmpStock = Purchase.dao.findFirst("select (a.price-b.price) as purchase_price, (a.num-b.num) as purchase_num from (select sum(purchase_price) as price, sum(purchase_num) as num from t_purchase where shop_id = ?) a ,(select sum(sale_price) as price, sum(sale_num) as num from t_sale where shop_id = ?)  b", shopId,shopId);
			}
		}
		BigDecimal stockPrice = tmpStock.getBigDecimal("purchase_price");
		BigDecimal stockNum = tmpStock.getBigDecimal("purchase_num");
		String curDatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();
		System.out.println("tmpStock:" + tmpStock);
		Stock stock = Stock.dao.findFirst("select * from t_stock where shop_id = ?", shopId);
		if (stock == null) {
			new Stock().set("stock_price", stockPrice)
			.set("stock_num", stockNum)
			.set("stock_time", curDatetime)
			.set("shop_id", shopId)
			.set("user_id", userId).save();
		} else {
			stock.set("stock_price", stockPrice)
			.set("stock_num", stockNum)
			.set("stock_time", curDatetime)
			.set("shop_id", shopId)
			.set("user_id", userId).update();
		}
	}
}
