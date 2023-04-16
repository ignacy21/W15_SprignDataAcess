package pl.zajavka;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.zajavka.data.*;
import pl.zajavka.service.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfiguration.class);

        RandomCustomerService randomCustomerService = context.getBean(RandomCustomerService.class);
        List<Customer> randomCustomers = randomCustomerService.createRandomCustomers(150);
        print(randomCustomers);

        RandomProducerService randomProducerService = context.getBean(RandomProducerService.class);
        List<Producer> randomProducers = randomProducerService.createRandomProducers(20);
        print(randomProducers);

        RandomProductService randomProductService = context.getBean(RandomProductService.class);
        List<Product> randomProducts = randomProductService.createRandomProducts();
        print(randomProducts);

        RandomPurchaseService randomPurchaseService = context.getBean(RandomPurchaseService.class);
        List<Purchase> randomPurchases = randomPurchaseService.createRandomPurchases(500, 150, 64);
        print(randomPurchases);

        RandomOpinionService randomOpinionService = context.getBean(RandomOpinionService.class);
        List<Opinion> randomOpinions = randomOpinionService.createRandomOpinions(50, 150, 64);
        print(randomOpinions);

    }
    private static void print(List list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }
}
