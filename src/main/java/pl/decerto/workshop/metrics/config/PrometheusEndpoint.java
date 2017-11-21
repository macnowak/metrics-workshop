package pl.decerto.workshop.metrics.config;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import java.io.IOException;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

@Component
class PrometheusEndpoint extends AbstractEndpoint<String> {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

	private final CollectorRegistry collectorRegistry;

	public PrometheusEndpoint(CollectorRegistry collectorRegistry) {
		super("prometheus", false, true);
		this.collectorRegistry = collectorRegistry;
	}

	@Override
	public String invoke() {
		try {
			StringWriter stringWriter = new StringWriter();
			TextFormat.write004(stringWriter, collectorRegistry.metricFamilySamples());
			return stringWriter.toString();
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			return "Error occurred: " + e.getMessage();
		}

	}
}
