package se77.spring5web.helloworld;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.publisher.Mono;

@Controller
public class HelloWorldController {

	/**
	 * REST-Controller mapping the /just-hello URL
	 * @return
	 */
  @GetMapping("/just-hello")
  @ResponseBody
  public Publisher<String> handler() {
      return Mono.just("Hello world!");
  }
}
