/**
 * @author hwei
 * @date 2015年7月2日 下午4:40:29 
 */
package com.search.notify;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.remoting.exchange.ExchangeChannel;
import com.alibaba.dubbo.remoting.exchange.ExchangeServer;
import com.alibaba.dubbo.remoting.exchange.Exchangers;
import com.alibaba.dubbo.remoting.exchange.support.Replier;

/**
 * @author hwei
 *
 */
public class SearchNotifyServerFactory {

	public static ExchangeServer newServer(String host, int port,
			Replier<?> receiver) throws RemotingException {
		return Exchangers.bind(URL.valueOf(String.format(
				"exchange://%s:%s?server=netty", host, port)), receiver);
	}

	public static ExchangeChannel newClient(String host, int port, int heartbeat)
			throws RemotingException {
		String exchangeURL = String.format("exchange://%s:%s?client=netty",
				host, port);
		return Exchangers.connect(URL.valueOf(exchangeURL));
	}

}
