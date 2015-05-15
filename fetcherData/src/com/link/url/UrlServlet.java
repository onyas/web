package com.link.url;

import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlServlet extends HttpServlet
{
  private static final long serialVersionUID = -5915165461841239902L;

  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
  {
    processRequestGet(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
  {
    processRequestPost(request, response);
  }

  public void processRequestGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    request.getRequestDispatcher("/index.jsp").forward(request, response);
  }

  public void processRequestPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    response.setContentType("text/html;charset=utf-8");
    String website = new String(request.getParameter("website").getBytes("ISO8859-1"), "UTF-8");
    String key = new String(request.getParameter("key").getBytes("ISO8859-1"), "UTF-8");
    request.setAttribute("website", website);
    request.setAttribute("key", key);
    TestUrl tu = new TestUrl();
    String[] webs = website.split(",");
    for (String web : webs) {
      web = web.trim();
      if (!"".equals(web)) {
        if (!web.startsWith("http://"))
          web = "http://" + web;
        try
        {
          String content = TestUrl.catchURLContent(web);
          String[] keys = key.split("\\ ");
          tu.patternAHref(content, keys, web);
        }
        catch (Exception e) {
          System.out.println(web + "网址错误或连接不上");
          request.setAttribute("error", Boolean.valueOf(true));
          request.setAttribute("errorMess", web + "网址错误或连接不上");
        }

      }

    }

    request.setAttribute("list", tu.list);
    request.getRequestDispatcher("/index.jsp").forward(request, response);
  }
}