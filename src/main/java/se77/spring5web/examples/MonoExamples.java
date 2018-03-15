package se77.spring5web.examples;

import reactor.core.publisher.Mono;

/**
 * Creates some Monos with the standard reactor core API.
 * @author superernie77
 *
 */
public class MonoExamples {
	
	public Mono<String> emptyMono() {
		return Mono.empty();
	}

	
	Mono<String> fooMono() {
		return Mono.just("foo");
	}

	Mono<String> errorMono() {
		return Mono.error(new IllegalStateException());
	}

}
