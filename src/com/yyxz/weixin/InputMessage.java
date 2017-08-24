package com.yyxz.weixin;

import java.io.Serializable;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * POST的XML数据包转换为消息接受对象
 * 
 * <p>
 * 由于POST的是XML数据包，所以不确定为哪种接受消息，<br/>
 * 所以直接将所有字段都进行转换，最后根据<tt>MsgType</tt>字段来判断取何种数据
 * </p>
 * 
 */
@XStreamAlias("xml")
public class InputMessage implements Serializable {

	/**  
     *   
     */
	private static final long serialVersionUID = 1L;
	@XStreamAlias("ToUserName")
	private String ToUserName;
	@XStreamAlias("FromUserName")
	private String FromUserName;
	@XStreamAlias("CreateTime")
	private Long CreateTime;
	@XStreamAlias("MsgType")
	private String MsgType = "text";
	@XStreamAlias("MsgId")
	private Long MsgId;
	// 文本消息
	@XStreamAlias("Content")
	private String Content;
	// 图片消息
	@XStreamAlias("PicUrl")
	private String PicUrl;
	// 位置消息
	@XStreamAlias("Latitude")
	private Double Latitude;
	@XStreamAlias("Longitude")
	private Double Longitude;
	@XStreamAlias("Precision")
	private Double Precision;
	@XStreamAlias("Label")
	private String Label;
	// 链接消息
	@XStreamAlias("Title")
	private String Title;
	@XStreamAlias("Description")
	private String Description;
	@XStreamAlias("Url")
	private String URL;
	// 语音信息
	@XStreamAlias("MediaId")
	private String MediaId;
	@XStreamAlias("Format")
	private String Format;
	@XStreamAlias("Recognition")
	private String Recognition;
	// 事件
	@XStreamAlias("Event")
	private String Event;
	@XStreamAlias("EventKey")
	private String EventKey;
	@XStreamAlias("Ticket")
	private String Ticket;
	@XStreamAlias("MenuId")
	private String MenuId;

	public String getMenuId() {
		return MenuId;
	}

	public void setMenuId(String menuId) {
		MenuId = menuId;
	}

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public Long getMsgId() {
		return MsgId;
	}

	public void setMsgId(Long msgId) {
		MsgId = msgId;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public Double getLatitude() {
		return Latitude;
	}

	public void setLatitude(Double latitude) {
		Latitude = latitude;
	}

	public Double getLongitude() {
		return Longitude;
	}

	public void setLongitude(Double longitude) {
		Longitude = longitude;
	}

	public Double getPrecision() {
		return Precision;
	}

	public void setPrecision(Double precision) {
		Precision = precision;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}
}