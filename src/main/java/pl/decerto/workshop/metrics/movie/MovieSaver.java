package pl.decerto.workshop.metrics.movie;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import org.jctools.queues.MpscArrayQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class MovieSaver {

	private final static Logger LOG = LoggerFactory.getLogger(MovieSaver.class);

	private final MpscArrayQueue<Movie> queue;

	private final MovieRepository repository;

	MovieSaver(MovieRepository repository, MetricRegistry metricRegistry) {
		this.repository = repository;
		this.queue = new MpscArrayQueue<>(100);

		metricRegistry.register("movieSaver.queue.utilization", (Gauge<Double>) () -> calculate(queue));

	}

	private double calculate(MpscArrayQueue<Movie> queue) {
		return queue.size() / queue.capacity();
	}

	void save(Movie movie) {
		queue.add(movie);
	}

	@Scheduled(fixedDelay = 1000)
	public void doSave() {
		LOG.info("Saving start");
		queue.drain(repository::save);
		LOG.info("Saving end");
	}
}
