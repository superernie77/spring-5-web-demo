package se77.spring5web;


import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;

public class RootServlet extends ServletHttpHandlerAdapter {
	 
 
    RootServlet(HttpHandler httpHandler) {
        super(httpHandler);
    }
 
 
}
