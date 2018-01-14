package se77.spring5web;

import static org.springframework.web.reactive.function.server.RouterFunctions.*;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

@SpringBootApplication
public class Spring5WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring5WebApplication.class, args);

		HttpHandler httpHandler = RouterFunctions.toHttpHandler(routingFunction());
		
	}
	
	
	public static RouterFunction<ServerResponse> routingFunction(){
		
		RequestPredicate pred = request -> request.path().equals("/hello-world");
		
		HandlerFunction<ServerResponse> handler = request ->ok().build();
 
		
		RouterFunction<ServerResponse> helloWorldRoute = RouterFunctions.route(pred,handler);
		
		return helloWorldRoute;
	}
	
	 @Bean
	    public ServletRegistrationBean<Servlet> servletRegistrationBean() throws Exception {
	        HttpHandler httpHandler = WebHttpHandlerBuilder.webHandler((WebHandler) toHttpHandler(routingFunction())).build();
	        ServletRegistrationBean registrationBean = new ServletRegistrationBean<>(new RootServlet(httpHandler), "/");
	        registrationBean.setLoadOnStartup(1);
	        registrationBean.setAsyncSupported(true);
	        return registrationBean;
	    }

}
