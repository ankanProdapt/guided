package com.learning.hello;

import java.io.IOException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.learning.hello.contoller.MangataController;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/mangata")
public class MangataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private MangataController mc = new MangataController();
	private JakartaServletWebApplication application;
	private TemplateEngine templateEngine;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		application = JakartaServletWebApplication.buildApplication(getServletContext());
		final WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var out = resp.getWriter();
		final IWebExchange webExchange = this.application.buildExchange(req, resp);
		final WebContext ctx = new WebContext(webExchange);
		
		String btn = req.getParameter("btn");
		
		if(btn.equals("Add To Game")) {
			String playerName = req.getParameter("playerName");
			int bet = Integer.valueOf(req.getParameter("bet"));
			String abbr = req.getParameter("rank") + req.getParameter("suit");
			String position = req.getParameter("position");
			mc.addPlayer(playerName, bet, abbr, position);
		}
		
		else if(btn.equals("Start Game")) {
			ctx.setVariable("In", "IN");
			ctx.setVariable("Out", "OUT");
		}
		
		else if(btn.equals("next")) {
			mc.drawCard();
			ctx.setVariable("In", mc.getInPileTop().toString());
			ctx.setVariable("Out", mc.getOutPileTop().toString());
			
			ctx.setVariable("winner", mc.getResult());
		}
		
		
		//ctx.setVariable("reading", mc.getReading());
		ctx.setVariable("players", mc.getPlayers());
		templateEngine.process("mangata", ctx, out);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final IWebExchange webExchange = this.application.buildExchange(req, resp);
		final WebContext ctx = new WebContext(webExchange);
		templateEngine.process("mangata", ctx, resp.getWriter());
	}

}