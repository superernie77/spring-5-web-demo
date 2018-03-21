package se77.spring5web;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import se77.spring5web.helloworld.RouterFunctionConfiguration;

/**
 * Calls the reactive URLs defined in the RouterFunctionConfiguration.
 * 
 * @author superernie77
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RouterFunctionTest {

	// Definition of the RouterFunctions
	@Autowired
	private RouterFunctionConfiguration context;

	@Test
	public void testHelloWorldFunction() {
		WebTestClient client = WebTestClient.bindToRouterFunction(context.booksRouterFunction()).build();
		client.get().uri("/books")
			.exchange().expectStatus().isOk();
	}

	@Test
	public void testHelloWorldFunction2() {
		WebTestClient client = WebTestClient.bindToRouterFunction(context.helloRouterFunction()).build();
		
		client.get().uri("/hello-again")
			.exchange().expectStatus().isOk();
	}

	@Test
	public void testChainedPredicate() {
		WebTestClient client = WebTestClient.bindToRouterFunction(context.chainedRequestPredicate()).build();

		// this should work because url and method is correct
		client.get().uri("/chained-books")
			.exchange().expectStatus().isOk();

		// this is not mapped
		client.post().uri("/chained-books").exchange()
			.expectStatus().isNotFound();
	}
	
	@Test
	public void testMultipleHttpMethods() {
		WebTestClient client = WebTestClient.bindToRouterFunction(context.multipleHttpMethodsFunction()).build();
		
		client.get().uri("/names")
		.exchange()
		.expectStatus().isOk()
		.expectBody(String.class).consumeWith(System.out::println);
		
		
		client.post()
			.uri("/names")
			.syncBody("Felix")
			.exchange()
			.expectStatus().isOk();
		
		client.get().uri("/names")
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(String.class).consumeWith(System.out::println);
	}
}
