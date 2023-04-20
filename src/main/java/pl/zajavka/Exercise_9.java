package pl.zajavka;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.data.Purchase;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class Exercise_9 {

    private final SimpleDriverDataSource simpleDriverDataSource;

    @Transactional
    public void clientPurchase(int customerID, int productID, int quantity) {
        String command1 = "INSERT INTO purchase (customer_id, product_id, quantity, date_time) " +
                "VALUES" +
                "(:customerId, :productId, :quantity, :dateTime)";
        Purchase purchase = Purchase.builder()
                .customerId(customerID)
                .productId(productID)
                .quantity(quantity)
                .dateTime(LocalDateTime.now())
                .build();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(purchase);
        namedParameterJdbcTemplate.update(command1, parameterSource);
    }
}
