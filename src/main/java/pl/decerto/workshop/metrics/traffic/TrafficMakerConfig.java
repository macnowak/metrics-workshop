package pl.decerto.workshop.metrics.traffic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class TrafficMakerConfig {

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
