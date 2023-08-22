package com.learning.hello;

import java.io.IOException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.learning.hello.contoller.OdometerController;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/odometer")
public class OdometerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private OdometerController oc;
	private JakartaServletWebApplication application;
	private TemplateEngine templateEngine;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		oc = new OdometerController(5);
		oc.reset();
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
		
		String nxt = req.getParameter("btn");
		if(nxt.equals("Prev")) {
			oc.decrementReading();
		}
		else {
			oc.incrementReading();
		}
		
		ctx.setVariable("reading", oc.getReading());
		templateEngine.process("odometer", ctx, out);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final IWebExchange webExchange = this.application.buildExchange(req, resp);
		final WebContext ctx = new WebContext(webExchange);
		ctx.setVariable("reading", oc.getReading());
		templateEngine.process("odometer", ctx, resp.getWriter());
	}

}
