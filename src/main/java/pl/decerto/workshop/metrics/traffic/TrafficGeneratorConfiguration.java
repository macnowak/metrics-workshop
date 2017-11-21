package pl.decerto.workshop.metrics.traffic;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
class TrafficGeneratorConfiguration {

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Component
	private class ServerStartListener implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

		@Autowired
		TrafficScheduler selfTrafficScheduler;

		@Override
		public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
			String prefix = "http://localhost:" + event.getEmbeddedServletContainer().getPort();

			Map<URI, Integer> endpointsToQuery = new HashMap<>();
			endpointsToQuery.put(URI.create(prefix + "/movie"), 5);

			selfTrafficScheduler.start(endpointsToQuery);
		}
	}

}
