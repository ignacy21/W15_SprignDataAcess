package pl.zajavka;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.zajavka.data.*;
import pl.zajavka.randomDataService.*;

import java.util.List;

@Data
@Repository
@RequiredArgsConstructor
public class DataBaseService {

    private final SimpleDriverDataSource simpleDriverDataSource;

    private List<Customer> randomCustomers;
    private List<Producer> randomProducers;
    private List<Product> randomProducts;
    private List<Purchase> randomPurchases;
    private List<Opinion> randomOpinions;

    public void createData(
            ApplicationContext context,
            int numberOfCustomers, int numberOfProducers, int numberOfPurchases, int numberOfOpinions) {

        RandomCustomerService randomCustomerService = context.getBean(RandomCustomerService.class);
        randomCustomers = randomCustomerService.createRandomCustomers(numberOfCustomers);
        print(randomCustomers);

        RandomProducerService randomProducerService = context.getBean(RandomProducerService.class);
        randomProducers = randomProducerService.createRandomProducers(numberOfProducers);
        print(randomProducers);

        RandomProductService randomProductService = context.getBean(RandomProductService.class);
        randomProducts = randomProductService.createRandomProducts();
        print(randomProducts);

        RandomPurchaseService randomPurchaseService = context.getBean(RandomPurchaseService.class);
        randomPurchases = randomPurchaseService.createRandomPurchases(
                numberOfPurchases, numberOfCustomers, randomProducts.size());
        print(randomPurchases);

        RandomOpinionService randomOpinionService = context.getBean(RandomOpinionService.class);
        randomOpinions = randomOpinionService.createRandomOpinions(
                numberOfOpinions, numberOfCustomers, randomProducts.size());
        print(randomOpinions);
    }

    public void removeAll() {
        JdbcTemplate template = new JdbcTemplate(simpleDriverDataSource);
        String command1 = "delete from opinion  where 1=1";
        String command2 = "delete from purchase where 1=1";
        String command3 = "delete from product  where 1=1";
        String command4 = "delete from producer where 1=1";
        String command5 = "delete from customer where 1=1";

        template.update(command1);
        template.update(command2);
        template.update(command3);
        template.update(command4);
        template.update(command5);

    }
    private static void print(List list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }
}
