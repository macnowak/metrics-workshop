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

	private final Logger log = LoggerFactory.getLogger(TrafficMaker.class);

	private final RestTemplate restTemplate;

	private final ScheduledExecutorService scheduledExecutorService;

	TrafficMaker() {
		this.restTemplate = new RestTemplate();
		scheduledExecutorService = new ScheduledThreadPoolExecutor(10);
	}

	@Override
	public void close() throws Exception {
		scheduledExecutorService.shutdown();
	}

	void start() {
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			final String url = "http://localhost:8080/movie/" + createMovieName();
			log.info("Calling {} ", url);
			restTemplate.postForObject(url, null, String.class);
		}, 3000, 100, TimeUnit.MILLISECONDS);

		scheduledExecutorService.scheduleAtFixedRate(() -> {
			final String url = "http://localhost:8080/movie";
			log.info("Calling {} ", url);
			restTemplate.getForEntity(url, null, String.class);
		}, 3000, 500, TimeUnit.MILLISECONDS);
	}

	@Override
	public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
		start();
	}

	private String createMovieName() {
		return "someMovie" + System.currentTimeMillis();
	}

}
