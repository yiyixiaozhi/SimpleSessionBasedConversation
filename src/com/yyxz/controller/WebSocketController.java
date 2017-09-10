package com.yyxz.controller;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class WebSocketController {
	public WebSocketController() {
		System.out.println(" WebSocket init~~~");
	}

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("onOpen");
	}

	@OnClose

	public void onClose(Session session) {
		System.out.println("onClose");
	}
	
//	@OnError
//	public void onError(Session session) {
//		System.out.println("onError");
//	}

	@OnMessage
	public void onMessage(String requestJson, Session session)
			throws IOException {
		requestJson = "一一小知回复：" + requestJson; 
		System.out.println(requestJson);
		session.getBasicRemote().sendText(requestJson);
	}
}