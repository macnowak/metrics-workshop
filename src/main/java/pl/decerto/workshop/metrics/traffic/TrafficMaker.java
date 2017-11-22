package pl.decerto.workshop.metrics.traffic;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class TrafficMaker implements AutoCloseable, ApplicationListener<EmbeddedServletContainerInitializedEvent> {

	private final RestTemplate restTemplate;

	private final ScheduledExecutorService scheduledExecutorService;

	TrafficMaker(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		scheduledExecutorService = new ScheduledThreadPoolExecutor(10);
	}

	@Override
	public void close() throws Exception {
		scheduledExecutorService.shutdown();
		scheduledExecutorService.awaitTermination(1, TimeUnit.SECONDS);
	}

	public void start() {
		scheduledExecutorService.scheduleAtFixedRate(new EndpointCaller(restTemplate), 3000, 100, TimeUnit.MILLISECONDS);
	}

	@Override
	public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
		start();
	}

	private class EndpointCaller implements Runnable {

		private final Logger logger = LoggerFactory.getLogger(EndpointCaller.class);

		private final RestTemplate restTemplate;

		private EndpointCaller(RestTemplate restTemplate) {
			this.restTemplate = restTemplate;
		}

		@Override
		public void run() {
			try {
				doCall();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void doCall() {
			logger.info("Calling endpoint");
			restTemplate.postForObject("http://localhost:8080/movie/" + getMovieName(), null, String.class);
		}

		private String getMovieName() {
			return "someMovie" + System.currentTimeMillis();
		}
	}
}
