package pl.zajavka;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.data.Customer;
import pl.zajavka.data.Opinion;
import pl.zajavka.data.Purchase;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class Exercise_4 {

    private final SimpleDriverDataSource simpleDriverDataSource;

    @Transactional
    public void removeClientIfHasHistory(
            List<Purchase> purchaseList, List<Opinion> opinionList, List<Customer> customerList
    ) {
        int grater;
        if (purchaseList.size() - opinionList.size() > 0) {
            grater = purchaseList.size();;
        } else {
            grater = opinionList.size();
        }
        String COMMAND = "DELETE FROM CUSTOMER WHERE ID = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        Set<Integer> customersIdsToRemove = new HashSet<>();
        for (int i = 0; i < grater; i++) {
            int purchaseCustomerId = purchaseList.get(i).getCustomerId();
            int opinionCustomerId = opinionList.get(i).getCustomerId();
            customersIdsToRemove.add(purchaseCustomerId);
            customersIdsToRemove.add(opinionCustomerId);
        }
        for (Integer id : customersIdsToRemove) {
            if (is40YO(id, customerList)) {
                throw new RuntimeException("Customer is 40 or more years old");
            }
            jdbcTemplate.update(COMMAND, id);
        }
//        customersIdsToRemove.stream()
//                .filter(id-> !(is40YO(id, customerList)))
//                .forEach(id -> jdbcTemplate.update(COMMAND, id));
    }

    private boolean is40YO(int id, List<Customer> customerList) {
        int index = id - 1;
        LocalDate dateOfBirth = customerList.get(index).getDateOfBirth();
        LocalDate dateAfter40years = dateOfBirth.plus(4, ChronoUnit.YEARS);
        return dateAfter40years.isAfter(LocalDate.now());
    }

}
