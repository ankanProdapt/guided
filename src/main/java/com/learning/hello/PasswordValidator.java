package com.learning.hello;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class PasswordValidator extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PasswordValidator() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String password = request.getParameter("password1");
		
		Path path = Paths.get("/home/ankan2001prodapt/eclipse-workspace/webProject/src/main/java/com/learning/hello/bannedPassword.txt");
		List<String> bannedPasswords = Files.readAllLines(path);
		if(bannedPasswords.contains(password)) {
			out.println("<h1> This password is banned </h1>");
		}
		else {
			out.println("<h1> Password Validated </h1>");
		}
	}

}
