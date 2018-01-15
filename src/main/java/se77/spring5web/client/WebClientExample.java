package se77.spring5web.client;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class WebClientExample {
	  public static void main(String[] args) throws InterruptedException {
	      WebClient webClient = WebClient.create("http://localhost:8080/just-hello");
	      Mono<String> result = webClient.get().retrieve().bodyToMono(String.class);
	      String response = result.block();
	      System.out.println(response);
	  }
	}
