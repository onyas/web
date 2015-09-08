package com.search.notify;

import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.remoting.exchange.ExchangeServer;

public class SearchNettyServer {

	private final String host;
	private final int port;
	private ExchangeServer server;

	public SearchNettyServer(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void initServer() throws RemotingException {
		server = SearchNotifyServerFactory.newServer(host, port,
				new SearchNotifyHandler());
	}

	public void destroy() {
		server.close();
	}

}
