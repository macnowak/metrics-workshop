package pl.decerto.workshop.metrics.config;

import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "params.datasource")
class DatasourceConfiguration extends HikariConfig {

	@Bean
	public DataSource dataSource(MetricRegistry registry) {
		HikariDataSource dataSource = new HikariDataSource(this);
		dataSource.setMetricRegistry(registry);
		return dataSource;
	}

}
