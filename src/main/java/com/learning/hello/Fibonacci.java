package com.learning.hello;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Fibonacci extends HttpServlet{
  
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      Map<String, String[]> parameterMap = req.getParameterMap();
      parameterMap.entrySet().stream().forEach(entry -> {
        System.out.print("key: " + entry.getKey());
        System.out.print(" values: " + Arrays.toString(entry.getValue()) + "\n");
      });
      int n = Integer.valueOf(parameterMap.get("n")[0]);
      System.out.println(n);
      PrintWriter out = resp.getWriter();
      out.println(String.format("<h1> Fibonacci Sequence:161263 %s <h1>", fibSeq(n).toString()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    int n = Integer.valueOf(request.getParameter("formLimit"));
    out.println(String.format("<p>%s</p>", fibSeq(n).toString()));
  }
  
  
  public static List<Integer> fibSeq(int n){
	  List<Integer> seq = new ArrayList<>();
	  seq.add(0);
	  seq.add(1);
	  for(int i = 0; i < n - 2; i++) {
		  seq.add(seq.get(i) + seq.get(i + 1));
	  }
	  
	  return seq;
  }

}