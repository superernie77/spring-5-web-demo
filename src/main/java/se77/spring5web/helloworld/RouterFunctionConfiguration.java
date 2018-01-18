package se77.spring5web.helloworld;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class RouterFunctionConfiguration {

	/**
	 * Another hello-world URL, this time created with a RouterFunction.
	 */
	@Bean
	RouterFunction<ServerResponse> helloRouterFunction() {
		RouterFunction<ServerResponse> routerFunction = RouterFunctions.route(RequestPredicates.path("/hello-again"),
				serverRequest -> ServerResponse.ok().body(Mono.just("Hello Again!"), String.class));

		return routerFunction;
	}

	@Bean
	RouterFunction<ServerResponse> secondRouterFunction() {
		RouterFunction<ServerResponse> routerFunction = RouterFunctions.route(RequestPredicates.path("/books"),
				serverRequest -> ServerResponse.ok().body(Mono.just("Hello Again!"), String.class));

		return routerFunction;
	}

	public Flux<String> fooBarFluxFromValues() {
		return Flux.fromIterable(Arrays.asList("foo", "bar"));
	}
	
	Flux<Long> counter() {
		return Flux.interval(Duration.ofSeconds(10l));
	}

}
