package com.link.url;

import com.link.url.model.Url;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUrl
{
  private static CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();

  public static String INVALID = "javascript:void(0);";

  public static String URL_LINK = "www.baidu.com";
  public static String[] keys = { "123" };

  public Map<String, String> map = new HashMap();

  public List<Url> list = new ArrayList();

  static
  {
    detector.add(JChardetFacade.getInstance());
  }

  public static void main(String[] args)
  {
    try
    {
      if (!URL_LINK.startsWith("http://")) {
        URL_LINK = "http://" + URL_LINK;
      }
      String content = catchURLContent(URL_LINK);
      TestUrl tu = new TestUrl();
      tu.patternAHref(content, keys, URL_LINK);
      tu.print(tu.map);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String catchURLContent(String web)
    throws Exception
  {
    String content = "";
    URL url = new URL(web);

    HttpURLConnection uc = (HttpURLConnection)url.openConnection();
    uc.connect();

    String encoding = getContentType(url);

    InputStream is = uc.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(is, encoding));
    String s = "";
    StringBuffer sb = new StringBuffer("");
    while ((s = reader.readLine()) != null) {
      sb.append(s + "\r\n");
    }
    content = sb.toString();
    reader.close();
    return content;
  }

  public static String getContentType(URL url)
  {
    String coding = getFileEncoding(url);
    if (coding == null) {
      coding = "utf-8";
    }
    return coding;
  }

  public void patternAHref(String content, String[] keys, String web)
  {
    Pattern p = null;
    Matcher m = null;
    String link = null;
    content = content.replaceAll("&nbsp;", "");
    for (String key : keys) {
      key = key.trim();
      if (!"".equals(key)) {
        p = Pattern.compile("<a [^>]*>[^<]*?(" + key + ")[^<]*</a>");
        m = p.matcher(content);
        while (m.find()) {
          link = m.group(0);

          catchHref(link, web, key, keys);
        }
      }
    }
  }

  public void catchHref(String link, String web, String key, String[] keys)
  {
    String re = "<a\\s.*?href\\s*=\\s*'?\"?([^(\\s\")]+)\\s*'?\"?[^>]*>(.*?)</a>";
    Pattern p = Pattern.compile(re, 42);
    Matcher m = p.matcher(link);
    String href = "";
    String value = "";
    Url url = null;
    while (m.find()) {
      href = m.group(1);
      value = m.group(2);

      if (INVALID.indexOf(href) == -1) {
        if (href.startsWith("/")) {
          href = web + href;
        }
        if (!this.map.containsKey(href)) {
          for (String tmp : keys) {
            if (!"".equals(tmp.trim())) {
              value = value.replaceAll(tmp, "<font color='red'>" + tmp + "</font>");
            }
          }
          this.map.put(href, value);
          url = new Url();
          url.setSite(web);
          url.setContent(value);
          url.setHref(href);
          url.setKey(key);
          this.list.add(url);
        }
      }
    }
  }

  public static String getFileEncoding(URL url)
  {
    Charset charset = null;
    try {
      charset = detector.detectCodepage(url);
    } catch (Exception e) {
      System.out.println(e.getClass() + "分析" + "编码失败");
    }
    if (charset != null)
      return charset.name();
    return null;
  }
  public void print(Map<String, String> map) {
    int i = 0;
    for (Map.Entry o : map.entrySet())
      System.out.println(i++ + "---URL---:" + (String)o.getKey() + ",-------key:" + (String)o.getValue());
  }
}