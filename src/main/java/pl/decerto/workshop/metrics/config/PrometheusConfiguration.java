package pl.decerto.workshop.metrics.config;

import com.codahale.metrics.MetricRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PrometheusConfiguration {

	@Bean
	public CollectorRegistry collectorRegistry(MetricRegistry metricRegistry) {
		CollectorRegistry collectorRegistry = new CollectorRegistry();
		collectorRegistry.register(new DropwizardExports(metricRegistry));

		return collectorRegistry;
	}

}
