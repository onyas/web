package com.search.notify;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.remoting.exchange.ExchangeChannel;
import com.alibaba.dubbo.remoting.exchange.support.Replier;
import com.search.request.Dispatcher;

public class SearchNotifyHandler implements Replier<Object> {
	private static final Logger log = LoggerFactory
			.getLogger(SearchNotifyHandler.class);

	private HashMap<String, Dispatcher> requestMap;

	public void setRequestMap(HashMap<String, Dispatcher> requestMap) {
		this.requestMap = requestMap;
	}

	@Override
	public Object reply(ExchangeChannel channel, Object state)
			throws RemotingException {
		try {
			log.info("object class :: {}", state.getClass().getCanonicalName());

			Dispatcher dispatcher = requestMap.get(state.getClass()
					.getCanonicalName());
			if (dispatcher != null) {
				return dispatcher.process(state);
			} else {
				log.error(
						"doesnot register the dispatcher service for the message :: {}",
						state.getClass().getCanonicalName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	public void destroy() {
		requestMap.clear();
	}
}
