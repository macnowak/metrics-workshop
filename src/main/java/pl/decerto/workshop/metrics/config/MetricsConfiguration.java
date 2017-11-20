package pl.decerto.workshop.metrics.config;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.jvm.FileDescriptorRatioGauge;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableMetrics
class MetricsConfiguration {

	@Autowired
	private MetricRegistry metricRegistry;

	@PostConstruct
	public void startConsoleReporter() throws IOException {

		final Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
			.outputTo(LoggerFactory.getLogger("metrics"))
			.convertDurationsTo(TimeUnit.MILLISECONDS)
			.build();

		reporter.start(10, TimeUnit.SECONDS);

	}

	@PostConstruct
	public void registerMetrics() {
		registerAll("jvm", new MemoryUsageGaugeSet(), metricRegistry);
		registerAll("gc", new GarbageCollectorMetricSet(), metricRegistry);
		registerAll("thread", new ThreadStatesGaugeSet(), metricRegistry);
	}

	private void registerAll(String prefix, MetricSet metricSet, MetricRegistry metricRegistry) {

		for (Map.Entry<String, Metric> stringMetricEntry : metricSet.getMetrics().entrySet()) {
			if (stringMetricEntry.getValue() instanceof MetricSet) {
				registerAll(prefix + "." + stringMetricEntry.getKey(), (MetricSet) stringMetricEntry.getValue(), metricRegistry);
			} else {
				metricRegistry.register(prefix + "." + stringMetricEntry.getKey(), stringMetricEntry.getValue());
			}

		}

	}

}
