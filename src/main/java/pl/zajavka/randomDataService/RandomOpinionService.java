package pl.zajavka.randomDataService;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.data.Opinion;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RandomOpinionService {

    private final SimpleDriverDataSource simpleDriverDataSource;

    @Transactional
    public List<Opinion> createRandomOpinions(int numberOfOpinions, int numberOfCustomers, int numberOfProducts) {
        String COMMAND = "INSERT INTO opinion (customer_id, product_id, stars, comment, date_time) " +
                "VALUES" +
                "(:customerId, :productId, :stars, :comment, :dateTime)";
        List<Opinion> opinionList = new LinkedList<>();
        for (int i = 1; i <= numberOfOpinions; i++) {
            Opinion opinion = Opinion.builder()
                    .customerId(randomCustomerId(numberOfCustomers))
                    .productId(randomProductId(numberOfProducts))
                    .stars(randomStars())
                    .comment(generateRandomComment())
                    .dateTime(randomDateTime())
                    .build();
            opinionList.add(opinion);
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(simpleDriverDataSource);
            BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(opinion);
            template.update(COMMAND, parameterSource);
        }
        return opinionList;
    }

    private int randomCustomerId(int numberOfCustomers) {
        Random random = new Random();
        return random.nextInt(1, numberOfCustomers + 1);
    }

    private int randomProductId(int numberOfProducts) {
        Random random = new Random();
        return random.nextInt(1, numberOfProducts + 1);
    }

    private int randomStars() {
        Random random = new Random();
        return random.nextInt(1, 6);
    }

    private String generateRandomComment() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(40, 70); i++) {
            int i1 = random.nextInt(97, 122);
            char letter = (char) i1;
            sb.append(letter);
            if (i1 > 115) {
                sb.append(" ");
            }
        }
        return sb.toString();
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
