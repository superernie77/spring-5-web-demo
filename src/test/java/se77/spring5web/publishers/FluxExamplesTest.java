package se77.spring5web.publishers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import se77.spring5web.domain.User;


/** 
 *  Based on the following tutorials:
 *  http://www.baeldung.com/reactor-core
 *  http://projectreactor.io/docs/core/release/reference/#advanced-broadcast-multiple-subscribers-connectableflux
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FluxExamplesTest {

	@Autowired
	private FluxExamples examplesFactory;

	@Test
	public void connectableFluxTest() throws InterruptedException {

		Flux<Integer> source = Flux.range(1, 3).doOnSubscribe(s -> System.out.println("subscribed to source"));

		ConnectableFlux<Integer> co = source.publish();

		List<Integer> elements = new ArrayList<>();
		List<Integer> elements2 = new ArrayList<>();
		
		co.subscribe(elements::add);
		co.subscribe(elements2::add);

		System.out.println("done subscribing");
		Thread.sleep(500);
		System.out.println("will now connect");

		co.connect();
		
		assertTrue(elements.size() == 3);
		assertTrue(elements2.size() == 3);
		
	}

	@Test
	public void testZip2Fluxes() {

		Flux<String> flux = examplesFactory.fooBarFluxFromList(); // two elements
		Flux<String> flux2 = examplesFactory.fromValues(); // vour elements

		List<String> elements = new ArrayList<>();
		flux.log().zipWith(flux2, (s1, s2) -> String.format("First Flux: %s, Second Flux: %s", s1, s2))
				.subscribe(elements::add);

		// only two elements since the ziped flux ends with the second element of the
		// first flux
		assertTrue(elements.size() == 2);

		List<String> elements2 = new ArrayList<>();
		flux2.log().zipWith(flux, (s1, s2) -> String.format("First Flux: %s, Second Flux: %s", s1, s2))
				.subscribe(elements2::add);

		// still only two elements since the initial flux has only 2 elements
		assertTrue(elements2.size() == 2);

	}

	@Test
	public void testSubscribeToFlux() {

		List<String> elements = new ArrayList<>();
		Flux<String> flux = examplesFactory.fooBarFluxFromList();

		// subscribe to flux and put elements in the result list
		flux.log().subscribe(elements::add);

		// we got the two elements from the flux
		assertTrue(elements.size() == 2);
	}

	@Test
	public void testMapSubsciptionElements() {
		List<User> elements = new ArrayList<>();

		Flux<String> flux = examplesFactory.fromValues();

		flux.log().map(s -> new User(s)).subscribe(elements::add);

		// we got the four Users in the list
		assertTrue(elements.size() == 4);
	}

	@Test
	public void testSubscribeWithBackpreasure() {
		List<String> elements = new ArrayList<>();

		MySubsriber sub = new MySubsriber(elements);

		Flux<String> flux = examplesFactory.fromValues();

		// subscribe to flux and put elements in the subcriber
		flux.log().subscribe(sub);

		// we got the four elements from the flux.
		assertTrue(elements.size() == 4);
	}

	private class MySubsriber implements Subscriber<String> {
		private Subscription s;
		int onNextAmount;
		List<String> elements;

		public MySubsriber(List<String> e) {
			elements = e;
		}

		@Override
		public void onSubscribe(Subscription s) {
			this.s = s;
			s.request(2);
		}

		// In the onNext callback we only get the two requested elements.
		// After these we need to request more elements from the publisher
		@Override
		public void onNext(String integer) {
			elements.add(integer);
			onNextAmount++;
			if (onNextAmount % 2 == 0) {
				s.request(2);
			}
		}

		@Override
		public void onError(Throwable t) {
		}

		@Override
		public void onComplete() {
		}
	}

}
