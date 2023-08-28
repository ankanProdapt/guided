package com.learning.hello;

 

import java.io.IOException;

 

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.learning.hello.contoller.Game2584Controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

 
@WebServlet("/game2584")
public class Game2584Servlet extends HttpServlet {

    private static final long serialVersionUID = 956140707118987401L;

    private Game2584Controller GC;
    private JakartaServletWebApplication application;
     private TemplateEngine templateEngine;

     @Override
     public void init(ServletConfig config) throws ServletException {
         super.init(config);
         GC = new Game2584Controller();
         application = JakartaServletWebApplication.buildApplication(getServletContext());

         application = JakartaServletWebApplication.buildApplication(getServletContext());
         final WebApplicationTemplateResolver templateResolver = 
                new WebApplicationTemplateResolver(application);
         templateResolver.setTemplateMode(TemplateMode.HTML);
         templateResolver.setPrefix("/WEB-INF/templates/");
         templateResolver.setSuffix(".html");
         templateEngine = new TemplateEngine();
         templateEngine.setTemplateResolver(templateResolver);
     }


     @Override
     protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
     {
         final IWebExchange webExchange = this.application.buildExchange(req, resp);
         final WebContext ctx = new WebContext(webExchange);
         String buttonID = req.getParameter("btn");

         if(buttonID != null) {
             if(buttonID.equals("▲")) { 
                 if(GC.moveUp())
                	 GC.generateRandomTile();
             }
             if(buttonID.equals("▼")) {
                 if(GC.moveDown())
                	 GC.generateRandomTile();
             }
             if(buttonID.equals("◀")) { 
                 if(GC.moveLeft())
                	 GC.generateRandomTile();
             }
             if(buttonID.equals("▶")) {
                 if(GC.moveRight())
                	 GC.generateRandomTile();
             } 
             if(buttonID.equals("↺")) {
            	 GC.reset();
             }
         }
         
         if(GC.checkWin()) {
        	 ctx.setVariable("msg", "YOU WON");
         }
         else if(GC.isGameOver()) {
             ctx.setVariable("msg", "GAME OVER");
         }
         
         ctx.setVariable("score", GC.getScore());
         
         for(int i = 0; i < 4; i++) {
        	 for(int j = 0; j < 4; j++) {
        		 if(GC.getCellValue(i, j) != 0) {
        			 ctx.setVariable("cell" + String.valueOf(i * 4 + j), GC.getCellValue(i, j));
        		 }
        	 }
         }

         var out = resp.getWriter();
         templateEngine.process("game2584", ctx, out);
     }


     @Override
      protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         final IWebExchange webExchange = this.application.buildExchange(req, resp);
         final WebContext ctx = new WebContext(webExchange);
         
         for(int i = 0; i < 4; i++) {
        	 for(int j = 0; j < 4; j++) {
        		 if(GC.getCellValue(i, j) != 0) {
        			 ctx.setVariable("cell" + String.valueOf(i * 4 + j), GC.getCellValue(i, j));
        		 }
        	 }
         }
         
         //ctx.setVariable("reading", GC.getReading());
         templateEngine.process("game2584", ctx, resp.getWriter());
      }

 

}