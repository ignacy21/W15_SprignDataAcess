package pl.zajavka;

import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@ComponentScan(basePackages = "pl.zajavka")
@EnableTransactionManagement
public class DataSourceConfiguration {

    @Bean
    public SimpleDriverDataSource databaseDataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new Driver());
        dataSource.setUrl("jdbc:postgresql://localhost:5432/projekt-w-15");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgresql");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(databaseDataSource());
    }
}
