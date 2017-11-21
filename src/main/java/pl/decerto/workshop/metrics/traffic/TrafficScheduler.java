package pl.decerto.workshop.metrics.traffic;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TrafficScheduler implements AutoCloseable {

	private final RestTemplate restTemplate;

	private final ScheduledExecutorService scheduler;

	public TrafficScheduler(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.scheduler = Executors.newScheduledThreadPool(30);
	}

	void start(Map<URI, Integer> endpointsToQuery) {
		endpointsToQuery.forEach((uri, frequency) ->
			scheduler.scheduleAtFixedRate(new TrafficGenerator(uri, restTemplate), 100, 1000 / frequency, TimeUnit.MILLISECONDS)
		);
	}

	@Override
	public void close() throws Exception {
		scheduler.shutdown();
		scheduler.awaitTermination(1, TimeUnit.SECONDS);
	}

	private static class TrafficGenerator implements Runnable {

		private static final Logger logger = LoggerFactory.getLogger("traffic-generator");

		private final URI endpoint;

		private final RestTemplate restTemplate;

		TrafficGenerator(URI endpoint, RestTemplate restTemplate) {
			this.endpoint = endpoint;
			this.restTemplate = restTemplate;
		}

		private void callEndpoint() {
			logger.trace("Calling endpoint : " + endpoint);
			restTemplate.getForObject(endpoint, String.class);
		}

		@Override
		public void run() {
			try {
				callEndpoint();
			} catch (Exception e) {
				logger.error("Unable to finalize HTTP request", e);
			}
		}
	}

}
