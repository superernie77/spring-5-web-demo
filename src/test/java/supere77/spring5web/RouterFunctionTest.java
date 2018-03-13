package supere77.spring5web;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
public class RouterFunctionTest {
	
	@Test
	public void testHelloWorldFunction() {
		WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
		
		client.get().uri("/books").exchange().expectStatus().isOk();
	}
	
	@Test
	public void testHelloWorldFunction2() {
		WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
		
		client.get().uri("/hello-again").exchange().expectStatus().isOk();
	}
	
	@Test
	public void testHelloWorldFunction3() {
		WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
		
		client.get().uri("/just-hello").exchange().expectStatus().isOk();
	}
}
