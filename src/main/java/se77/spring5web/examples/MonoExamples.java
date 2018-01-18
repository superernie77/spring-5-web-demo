package se77.spring5web.examples;

import reactor.core.publisher.Mono;

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
