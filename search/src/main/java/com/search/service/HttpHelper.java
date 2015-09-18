package com.search.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

public class HttpHelper {
	private static final Logger log = LoggerFactory.getLogger(HttpHelper.class);
	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	static final JsonFactory JSON_FACTORY = new JacksonFactory();

	static HttpRequestFactory requestFactory = HTTP_TRANSPORT
			.createRequestFactory(new HttpRequestInitializer() {
				public void initialize(HttpRequest request) {
					request.setParser(new JsonObjectParser(JSON_FACTORY));
				}
			});

	public static InputStream requestGetInputStream(GenericUrl genericURL,
			JSONObject data, long timeout) throws IOException {

		HttpRequest httpRequest = requestFactory.buildPostRequest(genericURL,
				new JsonHttpContent(JSON_FACTORY, data));

		Future<HttpResponse> future = httpRequest.executeAsync();
		try {
			HttpResponse response = future.get(timeout, TimeUnit.MILLISECONDS);
			String result = response.parseAsString();
			JSONObject jsonResult = JSON.parseObject(result);
			log.info("get message result ::{}", jsonResult.toJSONString());
			if (0 == jsonResult.getIntValue("errorcode")) {
				return response.getContent();
			} else {
				log.error("get message result ::{}", result);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject requestGetJSON(GenericUrl genericURL, long timeout)
			throws IOException {

		HttpRequest httpRequest = requestFactory.buildGetRequest(genericURL);
		Future<HttpResponse> future = httpRequest.executeAsync();
		try {
			HttpResponse response = future.get(timeout, TimeUnit.MILLISECONDS);
			String result = response.parseAsString();
			JSONObject jsonResult = JSON.parseObject(result);
			log.info("get message result ::{}", jsonResult.toJSONString());
			if (0 == jsonResult.getIntValue("errorcode")) {
				return jsonResult;
			} else {
				log.error("get message result ::{}", result);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject requestPostJSON(GenericUrl genericURL,
			JSONObject data, long timeout) throws IOException {

		HttpRequest httpRequest = requestFactory.buildPostRequest(genericURL,
				new JsonHttpContent(JSON_FACTORY, data));
		Future<HttpResponse> future = httpRequest.executeAsync();
		try {
			HttpResponse response = future.get(timeout, TimeUnit.MILLISECONDS);
			String result = response.parseAsString();
			JSONObject jsonResult = JSON.parseObject(result);
			log.info("get message result::{}", jsonResult.toJSONString());
			return jsonResult;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return null;
	}

}
