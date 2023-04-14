package pl.zajavka;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(DataSourceConfiguration.class);

        TransactionTemplateExample jdbcTemplateExample = context.getBean(TransactionTemplateExample.class);
        jdbcTemplateExample.someMethod();
    }

}
