package com.learning.hello;

import java.io.IOException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.learning.hello.contoller.TennisController;
import com.learning.hello.contoller.TennisDB;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tennis")
public class TennisServlet extends HttpServlet {
	private static final long serialVersionUID = 956140707118987401L;
	private TennisController TC;
	private JakartaServletWebApplication application;
	private TemplateEngine templateEngine;
	private TennisDB DB;
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		TC = new TennisController();
		DB = new TennisDB();
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
		final IWebExchange webExchange = this.application.buildExchange(req, resp);
		WebContext ctx = new WebContext(webExchange);
		String btn = req.getParameter("btn");
		ctx = TC.performAction(btn, req, resp, ctx, DB);
		ctx.setVariable("matches", DB.getMatchIds());
		var out = resp.getWriter();
		templateEngine.process("tennis", ctx, out);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final IWebExchange webExchange = this.application.buildExchange(req, resp);
		WebContext ctx = new WebContext(webExchange);
		Cookie[] c = req.getCookies();
		if(c != null) {
			String matchId = c[0].getValue();
			ctx = TC.performAction("Match " + matchId, req, resp, ctx, DB);
		}
		ctx.setVariable("matches", DB.getMatchIds());
		templateEngine.process("tennis", ctx, resp.getWriter());
	}

}