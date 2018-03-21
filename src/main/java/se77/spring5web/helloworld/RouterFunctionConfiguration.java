package se77.spring5web.helloworld;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
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
	
	@Bean
	public RouterFunction<ServerResponse> multipleHttpMethodsFunction(){
		
		List<String> result = new ArrayList<>(Arrays.asList("Klaus", "Kurt", "Ernie"));
		
		RouterFunction<ServerResponse> router = RouterFunctions
				.route(GET("/names"), request -> ok().body(Flux.fromIterable(result), String.class) )
				.andRoute(POST("/names"), request -> request.bodyToMono(String.class).doOnNext( s -> result.add(s)).then(ok().build()));
		
		return router;
	}
}
