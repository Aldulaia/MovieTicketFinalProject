package ca.sheridancollege.database;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class DataBaseConfig {

	// used by our DatabaseAccess class to submit our JDBC Query Strings!

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean // will return the result to the namedParameterJdbcTemplate()
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:testdb");
		dataSource.setUsername("sa");
		dataSource.setPassword("");

		return dataSource;

	}

	@Bean // will return the result to the namedParameterJdbcTemplate()
	public DataSource loadSchema() { // to attach the schema.sql in the RESOURCES
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("classpath:MySql.sql").build();
	}

}
