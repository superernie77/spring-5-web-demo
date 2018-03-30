package se77.spring5web.publishers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Flux;
import se77.spring5web.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FluxExamplesTest {
	
	
	@Autowired
	private FluxExamples examplesFactory;
	
	@Test
	public void testSubscribeToFlux() {
		
		List<String> elements = new ArrayList<>();
		Flux<String> flux =	examplesFactory.fooBarFluxFromList();
		
		// subscribe to flux and put elements in the result list
		flux.log().subscribe(elements::add);
		
		// we got the two elements from the flux
		assertTrue(elements.size() == 2);
	}
	
	@Test
	public void testMapSubsciptionElements() {
		List<User> elements = new ArrayList<>();
		
		Flux<String> flux =	examplesFactory.fromValues();
		
		flux.log().map( s -> new User(s)).subscribe(elements::add);
		
		// we got the four Users in the list
		assertTrue(elements.size() == 4);
	}
	
	@Test
	public void testSubscribeWithBackpreasure() {
		List<String> elements = new ArrayList<>();
		
		MySubsriber sub = new MySubsriber(elements);
		
		Flux<String> flux =	examplesFactory.fromValues();
		
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
	    public void onError(Throwable t) {}
	 
	    @Override
	    public void onComplete() {}
	}

}
