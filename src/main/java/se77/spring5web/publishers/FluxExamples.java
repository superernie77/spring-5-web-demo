package se77.spring5web.publishers;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

/**
 * Creates some Fluxes with the standard reactor core API.
 * @author superernie77
 *
 */
@Component
public class FluxExamples {

	public Flux<String> fooBarFluxFromList() {
		return Flux.fromIterable(Arrays.asList("foo", "bar"));
	}
	
	public Flux<String> emtyFlux() {
		return Flux.empty();
	}
	
	public Flux<String> fromValues(){
		return Flux.just("foo","bar","foo","bar");
	}
	
	public Flux<String> error(){
		return Flux.error(new IllegalArgumentException());
	}
	
	public Flux<Long> counter10Values() {
		return Flux.interval(Duration.ofMillis(100l)).take(10);
	}
}
