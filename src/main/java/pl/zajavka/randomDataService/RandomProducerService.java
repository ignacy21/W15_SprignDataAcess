package pl.zajavka.randomDataService;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.data.Producer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RandomProducerService {

    private final SimpleDriverDataSource simpleDriverDataSource;

    private static List<String> fileToStringList(String string) {
        try {
            List<String> strings = Files.readAllLines(Path.of(string));
            return strings.stream()
                    .filter(x -> !(x.equals("")))
                    .toList();

        } catch (IOException e) {
            System.err.println("Reading file went wrong: " + e.getMessage());
        }
        throw new RuntimeException("POSSIBLENT!!!???");
    }

    @Transactional
    public List<Producer> createRandomProducers(int numberOfProducers) {
        List<Producer> producers= new LinkedList<>();
        String COMMAND = "INSERT INTO producer (id, producer_name, address) " +
                "VALUES" +
                "(:id, :producerName, :address)";

        for (int i = 1; i <= numberOfProducers; i++) {
            Producer producer = Producer.builder()
                    .id(i)
                    .producerName(randomProducerName(i))
                    .address(randomAddress())
                    .build();
            producers.add(producer);
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(simpleDriverDataSource);
            BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(producer);
            template.update(COMMAND, parameterSource);
        }
        return producers;
    }

    private String randomAddress() {
        List<String> CITIES = fileToStringList("src/main/resources/exercise_3_files/cities.txt");
        List<String> STREETS = fileToStringList("src/main/resources/exercise_3_files/streets.txt");
        Random random = new Random();
        String city = CITIES.get(random.nextInt(0, CITIES.size()));
        String street = STREETS.get(random.nextInt(0, STREETS.size()));
        return String.format("%s ul. %s %s", city, street, random.nextInt(1, 57));
    }

    private String randomProducerName(int id) {
        Random random = new Random();
        List<String> SURNAMES = fileToStringList("src/main/resources/exercise_3_files/surnames.txt");
        List<String> PRODUCER_PROFESSION = fileToStringList("src/main/resources/exercise_3_files/producerProfession.txt");
        String surname = SURNAMES.get(random.nextInt(0, SURNAMES.size()));
        String profession = PRODUCER_PROFESSION.get(random.nextInt(0, PRODUCER_PROFESSION.size()));
        return surname + profession + id;
    }

}
