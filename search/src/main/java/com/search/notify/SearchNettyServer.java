package com.search.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.remoting.exchange.ExchangeServer;

public class SearchNettyServer {
	private static final Logger log = LoggerFactory
			.getLogger(SearchNettyServer.class);
	private final String host;
	private final int port;
	private ExchangeServer server;
	
	private SearchNotifyHandler notifyHandler;

	public SearchNettyServer(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void initServer() throws RemotingException {
		server = SearchNotifyServerFactory.newServer(host, port,
				notifyHandler);
		log.info("search Server start on host::{} port::{}", host, port);
	}

	public void destroy() {
		server.close();
	}

	public void setNotifyHandler(SearchNotifyHandler notifyHandler) {
		this.notifyHandler = notifyHandler;
	}
	

}
