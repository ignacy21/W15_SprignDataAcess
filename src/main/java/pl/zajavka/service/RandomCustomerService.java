package pl.zajavka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import pl.zajavka.data.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RandomCustomerService {
    private static final List<String> NAMES_LIST = new ArrayList<>();
    private static final List<String> SURNAMES_LIST = new ArrayList<>();

    private final SimpleDriverDataSource simpleDriverDataSource;


    static {
        try {
            List<String> strings = Files.readAllLines(Path.of("src/main/resources/names.txt"));
            for (String line : strings) {
                String[] s = line.split(" ");
                NAMES_LIST.addAll(Arrays.stream(s)
                        .filter(x -> !(x.equals("")))
                        .toList());
            }
        } catch (IOException e) {
            System.err.println("Reading file went wrong");
        }
    }

    static {
        try {
            List<String> strings = Files.readAllLines(Path.of("src/main/resources/surnames.txt"));
            for (String line : strings) {
                String[] s = line.split(" ");
                SURNAMES_LIST.addAll(Arrays.stream(s)
                        .filter(x -> !(x.equals("")))
                        .toList());
            }
        } catch (IOException e) {
            System.err.println("Reading file went wrong");
        }
    }

    public List<Customer> createRandomCustomers(int numberOfCustomers) {
        String COMMENT = "INSERT INTO customer (id, user_name, email, name, surname, date_of_birth, telephone_number) " +
                "VALUES" +
                "(:id, :userName, :email, :name, :surname, :dateOfBirth, :telephoneNumber)";
        List<Customer> customers = new ArrayList<>();
        for (int i = 1; i <= numberOfCustomers; i++) {
            String randomName = randomName();
            String randomSurname = randomSurname();
            String randomEmail = randomEmail(randomName, randomSurname, i);
            String randomUserName = randomUserName(randomName, randomSurname, i);
            LocalDate randomDate = randomDate();
            String randomTelephone = randomTelephone();
            Customer customer = Customer.builder()
                    .id(i)
                    .name(randomName)
                    .surname(randomSurname)
                    .email(randomEmail)
                    .userName(randomUserName)
                    .dateOfBirth(randomDate)
                    .telephoneNumber(randomTelephone)
                    .build();
            customers.add(customer);
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(simpleDriverDataSource);
            BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(customer);
            template.update(COMMENT, parameterSource);

        }
        return customers;
    }

    private String randomName() {
        Random random = new Random();
        int i = random.nextInt(0, NAMES_LIST.size());
        return NAMES_LIST.get(i);
    }

    private String randomSurname() {
        Random random = new Random();
        int i = random.nextInt(0, SURNAMES_LIST.size());
        return SURNAMES_LIST.get(i);
    }

    private String randomEmail(String name, String surname, int id) {
        name = name.toLowerCase();
        surname = surname.toLowerCase();
        return String.format("%s.%s%s@gmail.com", name, surname, id);
    }

    private LocalDate randomDate() {
        Random random = new Random();
        int year = random.nextInt(2000, 2020);
        int month = random.nextInt(1, 13);
        int day = 1;
        switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> day = random.nextInt(1, 32);
            case 4, 6, 9, 11 -> day = random.nextInt(1, 31);
            case 2 -> day = random.nextInt(1, 29);
        }
        return LocalDate.of(year, month, day);
    }

    private String randomUserName(String name, String surname, int id) {
        return name.charAt(0) + "_" + surname.toLowerCase() + id;
    }

    private String randomTelephone() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                int l = random.nextInt(0, 10);
                sb.append(l);
            }
            if (j != 2) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
