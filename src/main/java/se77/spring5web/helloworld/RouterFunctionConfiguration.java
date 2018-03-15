package se77.spring5web.helloworld;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

/**
 * Examples for Spring 5 Router- and HandlerFunctions.
 * @author superernie77
 *
 */
@Configuration
public class RouterFunctionConfiguration {

	/**
	 * Another hello-world URL, this time created with a RouterFunction.
	 */
	@Bean
	public RouterFunction<ServerResponse> helloRouterFunction() {
		RouterFunction<ServerResponse> routerFunction = RouterFunctions.route(RequestPredicates.path("/hello-again"),
				serverRequest -> ServerResponse.ok().body(Mono.just("Hello Again!"), String.class));

		return routerFunction;
	}

	@Bean
	public RouterFunction<ServerResponse> booksRouterFunction() {
		RouterFunction<ServerResponse> routerFunction = RouterFunctions.route(RequestPredicates.path("/books"),
				serverRequest -> ServerResponse.ok().body(Mono.just("Hello Again!"), String.class));

		return routerFunction;
	}
	
	/**
	 * Example for a RouterFunction with chained RequestPredicate.
	 */
	@Bean
	public RouterFunction<ServerResponse> chainedRequestPredicate(){
		// to make this more readable, create handlerFunction first
		HandlerFunction<ServerResponse> handler = serverRequest -> ServerResponse.ok().body(Mono.just("books"), String.class); 
		
		// now the chained predicate path + method. you could just use GET(path) method which would be a shortcut for this chain.
		RouterFunction<ServerResponse> routerFunction = RouterFunctions.route(RequestPredicates.path("/chained-books").and(RequestPredicates.method(HttpMethod.GET)), handler );

		return routerFunction;
				
	}
}
