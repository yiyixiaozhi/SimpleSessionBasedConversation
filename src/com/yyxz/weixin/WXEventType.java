package com.yyxz.weixin;

public class WXEventType {
	public enum MaterialType {
		// / <summary>
		// / 图片（image）: 2MB，支持bmp/png/jpeg/jpg/gif格式
		// / </summary>
		image,
		// / <summary>
		// / 语音（voice）：5MB，播放长度不超过60s，支持mp3/wma/wav/amr格式
		// / </summary>
		voice,
		// / <summary>
		// / 视频（video）：20MB，支持rm/rmvb/wmv/avi/mpg/mpeg/mp4格式
		// / </summary>
		video,
		// / <summary>
		// / 缩略图（thumb）：64KB，支持jpg格式
		// / </summary>
		thumb,
		// / <summary>
		// / 图文
		// / </summary>
		news
	}

	// / <summary>
	// / 消息类型枚举
	// / </summary>
	public enum MsgType {
		/**
		 * 文本类型
		 */
		text,
		/**
		 * 事件类型
		 */
		event,
		image
	}

	// / <summary>
	// / 事件类型枚举
	// / </summary>
	public enum Event {
		/**
		 * 地理位置
		 */
		LOCATION,
		/**
		 * 客户在微信客户端点击的菜单中的CLICK事件
		 */
		CLICK,
		/**
		 * 菜单跳转链接
		 */
		VIEW
	}

	/**
	 * @author bianxiaohui
	 *	微信菜单type为click对应具体事件的key
	 */
	public enum MenuEventKey {
		/**
		 * 注册/绑定
		 */
		register_bind,
		/**
		 * 线上签到
		 */
		checkin_online,
		/**
		 * 我的预约
		 */
		my_order,
		/**
		 * 我的活动
		 */
		my_activities,
		/**
		 * 网上党校App
		 */
		online_party_school_app
	}
	/**
	 * @author zqzx
	 *	自定义菜单类型
	 */
	public enum MenuType {
		// / <summary>
		// / 点击推事件
		// / </summary>
		click,
		// / <summary>
		// / 跳转URL
		// / </summary>
		view,
		// / <summary>
		// / 扫码推事件
		// / </summary>
		scancode_push,
		// / <summary>
		// / 扫码推事件且弹出“消息接收中”提示框
		// / </summary>
		scancode_waitmsg,
		// / <summary>
		// / 弹出系统拍照发图
		// / </summary>
		pic_sysphoto,
		// / <summary>
		// / 弹出拍照或者相册发图
		// / </summary>
		pic_photo_or_album,
		// / <summary>
		// / 弹出微信相册发图器
		// / </summary>
		pic_weixin,
		// / <summary>
		// / 弹出地理位置选择器
		// / </summary>
		location_select,
		// / <summary>
		// / 下发消息（除文本消息）
		// / </summary>
		media_id,
		// / <summary>
		// / 跳转图文消息URL
		// / </summary>
		view_limited
	}
}
