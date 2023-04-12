package pl.zajavka;

import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
@ComponentScan(basePackages = "pl.zajavka")
public class DataSourceConfiguration {

    @Bean
    public SimpleDriverDataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new Driver());
        dataSource.setUrl("jdbc:postgresql://localhost:5432/zajavka");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgresql");
        return dataSource;
    }
}
