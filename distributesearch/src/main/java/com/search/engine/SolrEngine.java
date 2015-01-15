package com.search.engine;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Vector;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

public class SolrEngine {

	//主机名+应用名
	public static final String host = "http://127.0.0.1:8083/distributesearch/";
	
	/***
	 * 测试核的状态，可以查看核是否存在，核中文档数等还有其它一些配置信息
	 * 
	 * @param coreName 核的名字
	 */
	public String coreStatus(String coreName){
		//构造请求url
		StringBuffer requestUrl = new StringBuffer();
		requestUrl.append(host);
		requestUrl.append("/admin/cores?action=STATUS&wt=json&core=");
		requestUrl.append(coreName);
		
		HttpResponse response = new HttpResponse();
		try {
			response = send(requestUrl.toString(), "GET");
		} catch (IOException e) {
			e.printStackTrace();
		}//方法名必须为大写
		System.out.println(response);
		System.out.println(response.getContent());
		JSONObject jsonObject = new JSONObject();
		jsonObject=JSONObject.fromObject(response.getContent());
		jsonObject=JSONObject.fromObject(jsonObject.get("status"));
		jsonObject=JSONObject.fromObject(jsonObject.get(coreName));
		if(jsonObject.toString().equals("{}")){
			System.out.println("核不存在");
		}else{
			System.out.println("核已经存在");
		}
		return jsonObject.toString();
	}

	/***
	 * 建核
	 *  
	 * @param coreName 核的名字
	 * @param param 建核的一些参数,比如numShards,replicationFactor,collection.configName等
	 */
	public void createCore(String coreName,HashMap<String, String> param){
		//这种方式只有建一个核名字为core_test      collection.configName视情况加不加
		//String url = "http://10.142.51.183:5002/admin/cores?action=CREATE&name="+core+"&numShards="+1+"&replicationFactor="+1+"&collection.configName=search_server_conf";
		//这是solrCloud的方式，核的名字为core_test1_shard1_replica1
		//构造请求url
		StringBuffer requestUrl = new StringBuffer();
		requestUrl.append(host);
		requestUrl.append("admin/collections?action=CREATE&name=");
		requestUrl.append(coreName);
		
		//"&numShards="+1+"&replicationFactor="+1+"&collection.configName=search_server_conf";
		if(param!=null&&param.size()>0){
			if(StringUtils.isNotBlank(param.get("numShards"))){
				requestUrl.append("&numShards="+param.get("numShards"));
			}
			if(StringUtils.isNotBlank(param.get("replicationFactor"))){
				requestUrl.append("&replicationFactor="+param.get("replicationFactor"));
			}
			if(StringUtils.isNotBlank(param.get("collection.configName"))){
				requestUrl.append("&collection.configName="+param.get("collection.configName"));
			}
		}
		HttpResponse response=new HttpResponse();
		try {
			response = send(requestUrl.toString(), "GET");
		} catch (IOException e) {
			e.printStackTrace();
		}//方法名必须为大写
		String rStr = response.getContent();
		if(rStr!=null){
			if("".equals(rStr) || rStr.indexOf("false")>-1){
				System.out.println("建核失败");
			}else{
				System.out.println("建核成功");
			}
		}else{
			System.out.println("建核失败");
		}
	}
	
	/**
	 * 删除指定核
	 * @param coreName 核的名字
	 */
	public void deleteCore(String coreName){
		//构造请求url
		StringBuffer requestUrl = new StringBuffer();
		requestUrl.append(host);
		requestUrl.append("admin/collections?action=DELETE&name=");
		requestUrl.append(coreName);
		
		HttpResponse response=new HttpResponse();
		try {
			response = send(requestUrl.toString(), "GET");
		} catch (IOException e) {
			e.printStackTrace();
		}//方法名必须为大写
		String rStr = response.getContent();
		if("".equals(rStr) || rStr.indexOf("false")>-1){
			System.out.println("删除核失败");
		}else{
			System.out.println("删除核成功");
		}
		//TODO 删除索引文件
	}
	
	/**
	 * 对指定核更新索引(包括 新增，更新，删除索引) 
	 * 
	 * @param coreName 核的名字
	 * @param data 数据 	
	 * <br/>
	 * 新增/更新索引:<br/>
	 * "{\"add\":{\"doc\":{\"id_key\":\"asdf\",\"title_text\":\"test1\",\"content_text\":\"test1\"}}}"
	 * <br/>
	 * 删除索引:"{\"delete\":{\"id\":\"addf\"}}"
	 * @return responseCode 200为成功，其它为失败
	 */
	public int updateIndex(String coreName,String data){
		//构造请求url
		StringBuffer requestUrl = new StringBuffer();
		requestUrl.append(host);
		requestUrl.append(coreName);
		requestUrl.append("/update/json?commit=true");
		
		StringBuilder indexData = new StringBuilder();
		indexData.append(data);
		StringBuilder sbResult = new StringBuilder();
		int responseCode = 0;
		try {
			responseCode = SolrEngine.httpRequest(requestUrl.toString(), indexData, sbResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("responseCode==" + responseCode);
		System.out.println("sbResult==" + sbResult);
		return responseCode;
	}
	
	/***
	 * 搜索
	 * @param coreName 核的名字
	 * @param param 搜索参数
	 * @return 返回值可以是json或xml格式的数据，根据查询时指定的wt选项来定
	 */
	public String search(String coreName,String param){
		//构造请求url
		StringBuffer requestUrl = new StringBuffer();
		requestUrl.append(host);
		requestUrl.append(coreName);
		requestUrl.append("/select?");
		requestUrl.append(param);
		
		HttpResponse response = new HttpResponse();
		try {
			response = send(requestUrl.toString(), "GET");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("responseCode==" + response.getCode());
		System.out.println("sbResult==" + response.getContent());
		return  response.getContent();
	}
	
	
	/***
	 * 
	 * @param urls 请求的地址
	 * @param indexData post数据
	 * @param sbResult 用于保存返回的数据
	 * @return
	 * @throws IOException
	 */
	public static int httpRequest(String urls, StringBuilder indexData,
			StringBuilder sbResult) throws IOException {
		URL url = new URL(urls);
		HttpURLConnection httpCon = null;
		try {
			httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setConnectTimeout(6000);
			httpCon.setReadTimeout(6000);
			httpCon.setDoOutput(true);
			httpCon.setDoInput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setUseCaches(false);
			httpCon.setAllowUserInteraction(false);
			httpCon.setRequestProperty("Content-Type",
					"text/json; charset=utf-8");
			httpCon.connect();

			BufferedWriter out = null;
			OutputStream httpOutStream = null;
			try {
				httpOutStream = httpCon.getOutputStream();
				out = new BufferedWriter(new OutputStreamWriter(httpOutStream,
						"utf-8"));
				out.write(indexData.toString());
				out.flush();
			} finally {
				if (httpOutStream != null) {
					httpOutStream.close();
				}
				if (out != null)
					out.close();
			}

			int rscode = httpCon.getResponseCode();
			if (rscode == 200) {
				BufferedReader in = null;
				InputStream httpConStream = null;
				try {
					httpConStream = (InputStream) httpCon.getInputStream();
					in = new BufferedReader(new InputStreamReader(
							httpConStream, "UTF-8"));
					String line = null;
					while ((line = in.readLine()) != null) {
						sbResult.append(line);
					}
				} finally {
					if (httpConStream != null)
						httpConStream.close();

					if (in != null)
						in.close();
				}
			} else {
				String uds_errorInfo = httpCon.getHeaderField("uds_errorInfo");
				System.err.println(uds_errorInfo);
				sbResult.append(uds_errorInfo);
			}
			return rscode;
		} finally {
			if (httpCon != null)
				httpCon.disconnect();
		}
	}

	private HttpResponse send(String urlString, String method)throws IOException {
		HttpURLConnection urlConnection = null;
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
		urlConnection.setConnectTimeout(3000);
		
		HttpResponse httpResponser = new HttpResponse();
		try {
			InputStream in = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(in));
			httpResponser.setContentCollection(new Vector<String>());
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				httpResponser.getContentCollection().add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
			String ecod = urlConnection.getContentEncoding();
			if (ecod == null)
				ecod = Charset.defaultCharset().name();
			httpResponser.setUrlString(urlString);
			httpResponser.setDefaultPort(urlConnection.getURL().getDefaultPort());
			httpResponser.setFile(urlConnection.getURL().getFile());
			httpResponser.setHost(urlConnection.getURL().getHost());
			httpResponser.setPath(urlConnection.getURL().getPath());
			httpResponser.setPort(urlConnection.getURL().getPort());
			httpResponser.setProtocol(urlConnection.getURL().getProtocol());
			httpResponser.setQuery(urlConnection.getURL().getQuery());
			httpResponser.setRef(urlConnection.getURL().getRef());
			httpResponser.setUserInfo(urlConnection.getURL().getUserInfo());
			httpResponser.setContent(new String(temp.toString().getBytes(), ecod));
			httpResponser.setContentEncoding(ecod);
			httpResponser.setCode(urlConnection.getResponseCode());
			httpResponser.setMessage(urlConnection.getResponseMessage());
			httpResponser.setContentType(urlConnection.getContentType());
			httpResponser.setMethod(urlConnection.getRequestMethod());
			httpResponser.setConnectTimeout(urlConnection.getConnectTimeout());
			httpResponser.setReadTimeout(urlConnection.getReadTimeout());
			return httpResponser;
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
	}
	
	
	 /**
	  * HTTP响应对象
	  */
	  class HttpResponse {

	 	private String urlString;
	 	private int defaultPort;
	 	private String file;
	 	private String host;
	 	private String path;
	 	private int port;
	 	private String protocol;
	 	private String query;
	 	private String ref;
	 	private String userInfo;
	 	private String contentEncoding;
	 	private String content;
	 	private String contentType;
	 	private int code;
	 	private String message;
	 	private String method;
	 	private int connectTimeout;
	 	private int readTimeout;
	 	private Vector<String> contentCollection;

	 	public String getContent() {
	 		return content;
	 	}

	 	public String getContentType() {
	 		return contentType;
	 	}

	 	public int getCode() {
	 		return code;
	 	}

	 	public String getMessage() {
	 		return message;
	 	}

	 	public Vector<String> getContentCollection() {
	 		return contentCollection;
	 	}

	 	public String getContentEncoding() {
	 		return contentEncoding;
	 	}

	 	public String getMethod() {
	 		return method;
	 	}

	 	public int getConnectTimeout() {
	 		return connectTimeout;
	 	}

	 	public int getReadTimeout() {
	 		return readTimeout;
	 	}

	 	public String getUrlString() {
	 		return urlString;
	 	}

	 	public int getDefaultPort() {
	 		return defaultPort;
	 	}

	 	public String getFile() {
	 		return file;
	 	}

	 	public String getHost() {
	 		return host;
	 	}

	 	public String getPath() {
	 		return path;
	 	}

	 	public int getPort() {
	 		return port;
	 	}

	 	public String getProtocol() {
	 		return protocol;
	 	}

	 	public String getQuery() {
	 		return query;
	 	}

	 	public String getRef() {
	 		return ref;
	 	}

	 	public String getUserInfo() {
	 		return userInfo;
	 	}

	 	public void setUrlString(String urlString) {
	 		this.urlString = urlString;
	 	}

	 	public void setDefaultPort(int defaultPort) {
	 		this.defaultPort = defaultPort;
	 	}

	 	public void setFile(String file) {
	 		this.file = file;
	 	}

	 	public void setHost(String host) {
	 		this.host = host;
	 	}

	 	public void setPath(String path) {
	 		this.path = path;
	 	}

	 	public void setPort(int port) {
	 		this.port = port;
	 	}

	 	public void setProtocol(String protocol) {
	 		this.protocol = protocol;
	 	}

	 	public void setQuery(String query) {
	 		this.query = query;
	 	}

	 	public void setRef(String ref) {
	 		this.ref = ref;
	 	}

	 	public void setUserInfo(String userInfo) {
	 		this.userInfo = userInfo;
	 	}

	 	public void setContentEncoding(String contentEncoding) {
	 		this.contentEncoding = contentEncoding;
	 	}

	 	public void setContent(String content) {
	 		this.content = content;
	 	}

	 	public void setContentType(String contentType) {
	 		this.contentType = contentType;
	 	}

	 	public void setCode(int code) {
	 		this.code = code;
	 	}

	 	public void setMessage(String message) {
	 		this.message = message;
	 	}

	 	public void setMethod(String method) {
	 		this.method = method;
	 	}

	 	public void setConnectTimeout(int connectTimeout) {
	 		this.connectTimeout = connectTimeout;
	 	}

	 	public void setReadTimeout(int readTimeout) {
	 		this.readTimeout = readTimeout;
	 	}

	 	public void setContentCollection(Vector<String> contentCollection) {
	 		this.contentCollection = contentCollection;
	 	}

	 	@Override
	 	public String toString() {
	 		return "HttpRespons [urlString=" + urlString + ", defaultPort="
	 				+ defaultPort + ", file=" + file + ", host=" + host + ", path="
	 				+ path + ", port=" + port + ", protocol=" + protocol
	 				+ ", query=" + query + ", ref=" + ref + ", userInfo="
	 				+ userInfo + ", contentEncoding=" + contentEncoding
	 				+ ", content=" + content + ", contentType=" + contentType
	 				+ ", code=" + code + ", message=" + message + ", method="
	 				+ method + ", connectTimeout=" + connectTimeout
	 				+ ", readTimeout=" + readTimeout + ", contentCollection="
	 				+ contentCollection + "]";
	 	}
	}
	  
	  
	  

}
