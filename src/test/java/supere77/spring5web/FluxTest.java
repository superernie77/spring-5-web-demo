package supere77.spring5web;

import org.junit.Assert;
import org.junit.Test;

import reactor.core.publisher.Flux;
import se77.spring5web.helloworld.RouterFunctionConfiguration;

public class FluxTest {
	
	@Test
	public void testFlux() {
		RouterFunctionConfiguration router = new RouterFunctionConfiguration();
		
		Flux<String> f = router.fooBarFluxFromValues();
		
		Assert.assertTrue("foo".equals(f.next().block()));
		
		Assert.assertTrue("bar".equals(f.next().block()));
		
		
	}

}
