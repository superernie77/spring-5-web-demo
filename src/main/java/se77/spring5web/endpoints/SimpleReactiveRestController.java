package se77.spring5web.endpoints;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.publisher.Mono;

@Controller
public class SimpleReactiveRestController {

	/**
	 * REST-Controller mapping the /just-hello URL and return a simple Mono containing a String
	 * 
	 * @return
	 */
	@GetMapping("/just-hello")
	@ResponseBody
	public Publisher<String> handler() {
		return Mono.just("Hello world new!");
	}
}
