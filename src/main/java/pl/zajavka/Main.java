package pl.zajavka;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(DataSourceConfiguration.class);


        JdbcTempleExample example = context.getBean(JdbcTempleExample.class);
//        example.insert();
//        example.update();
        example.delete();
        example.select();

    }

}
