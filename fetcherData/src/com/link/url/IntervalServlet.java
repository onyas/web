package com.link.url;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IntervalServlet extends HttpServlet
{
  private static final long serialVersionUID = -6226234072439841121L;

  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
  {
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
  {
    setIntervaltime(request, response);
  }

  public void setIntervaltime(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    String time = request.getParameter("intervaltime");
    String flag = request.getParameter("flag");
    String txtpath = request.getSession().getServletContext().getRealPath("/");
    String fullpath = txtpath + "/interval.txt";
    File file = new File(fullpath);
    if (!file.exists()) {
      file.createNewFile();
    }

    if ("S".equals(flag)) {
      writeTxtFile(file, time);
    }

    time = readTxtFile(file);
    PrintWriter out = response.getWriter();
    out.print(time);
    out.flush();
    out.close();
  }

  public static String readTxtFile(File file)
  {
    BufferedReader bufread = null;
    String readStr = "";

    FileReader fileread = null;
    try {
      fileread = new FileReader(file);
      bufread = new BufferedReader(fileread);
      try
      {
        String read;
        while ((read = bufread.readLine()) != null)
        {
          readStr = readStr + read + "\r\n";
        }
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    try {
      bufread.close();
      fileread.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return readStr;
  }

  public static void writeTxtFile(File file, String newStr)
    throws IOException
  {
    FileWriter fw = new FileWriter(file);
    fw.write(newStr);
    fw.close();
  }
}