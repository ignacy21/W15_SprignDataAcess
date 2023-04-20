package pl.zajavka.randomDataService;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.data.Purchase;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class RandomPurchaseService {

    private final SimpleDriverDataSource simpleDriverDataSource;

    @Transactional
    public List<Purchase> createRandomPurchases(int numberOfPurchases, int numberOfCustomers, int numberOfProducts) {
        String COMMAND = "INSERT INTO purchase (customer_id, product_id, quantity, date_time) " +
                "VALUES" +
                "(:customerId, :productId, :quantity, :dateTime)";
        List<Purchase> purchaseList = new LinkedList<>();
        for (int i = 1; i <= numberOfPurchases; i++) {
            Purchase purchase = Purchase.builder()
                    .customerId(randomCustomerId(numberOfCustomers))
                    .productId(randomProductId(numberOfProducts))
                    .quantity(randomQuantity())
                    .dateTime(randomDateTime())
                    .build();
            purchaseList.add(purchase);
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(simpleDriverDataSource);
            BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(purchase);
            template.update(COMMAND, parameterSource);
        }
        return purchaseList;
    }
    private int randomCustomerId(int numberOfCustomers) {
        Random random = new Random();
        return random.nextInt(1, numberOfCustomers + 1);
    }

    private int randomQuantity() {
        Random random = new Random();
        return random.nextInt(1, 10);
    }
    private int randomProductId(int numberOfProducts) {
        Random random = new Random();
        return random.nextInt(1, numberOfProducts + 1);
    }

    private LocalDateTime randomDateTime() {
        Random random = new Random();
        int year = random.nextInt(2020, 2024);
        int month = random.nextInt(1, 13);
        int day = 1;
        int hour = random.nextInt(1, 24);
        int minutes = random.nextInt(0, 60);
        int seconds = random.nextInt(0, 60);
        switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> day = random.nextInt(1, 32);
            case 4, 6, 9, 11 -> day = random.nextInt(1, 31);
            case 2 -> day = random.nextInt(1, 29);
        }
        return LocalDateTime.of(year, month, day, hour, minutes, seconds);
    }
}
