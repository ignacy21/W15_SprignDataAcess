package pl.zajavka;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.data.Opinion;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Exercise_8 {

    private final SimpleDriverDataSource simpleDriverDataSource;

    @Transactional
    public void removeCustomerWhoGiveLEssThan4Stars(List<Opinion> opinionList) {
        String commandOpinion = "DELETE FROM OPINION WHERE CUSTOMER_ID = ?";
        String commandOPurchase = "DELETE FROM PURCHASE WHERE CUSTOMER_ID = ?";
        String commandCustomer = "DELETE FROM CUSTOMER WHERE ID = ?";
        List<Integer> collect = opinionList.stream()
                .filter(opinion -> opinion.getStars() < 4)
                .map(Opinion::getCustomerId)
                .toList();

        JdbcTemplate template = new JdbcTemplate(simpleDriverDataSource);
        collect.forEach(id -> template.update(commandOpinion, id));
        collect.forEach(id -> template.update(commandOPurchase, id));
        collect.forEach(id -> template.update(commandCustomer, id));
    }
}
