package pl.zajavka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import pl.zajavka.data.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RandomProductService {
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

    public List<Product> createRandomProducts() {
        String COMMENT = "INSERT INTO product (id, product_name, product_code, product_price, adults_only, description, producer_id) " +
                "VALUES" +
                "(:id, :productName, :productCode, :productPrice, :adultsOnly, :description, :producerID)";
        List<String> lines = fileToStringList("src/main/resources/productsNames.txt");
        List<Product> productList = new LinkedList<>();
        for (int i = 0; i < lines.size(); i++) {
            Product product = Product.builder()
                    .id(i + 1)
                    .productName(lines.get(i))
                    .productCode(randomProductCode(i))
                    .productPrice(randomProductPrice())
                    .adultsOnly(isAdultsOnly(lines.get(i)))
                    .description(generateRandomDescription())
                    .producerID(randomProducerId(20))
                    .build();
            productList.add(product);

            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(simpleDriverDataSource);
            BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
            template.update(COMMENT, parameterSource);
        }
        return productList;
    }

    private String generateRandomDescription() {
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

    private int randomProducerId(int howManyProducers) {
        Random random = new Random();
        return random.nextInt(1, howManyProducers + 1);
    }

    private boolean isAdultsOnly(String product) {
        switch (product) {
            case "wino", "piwo", "wódka", "papierosy", "cydr" -> {return true;}
        }
        return false;
    }

    private String randomProductCode(int id) {
        Random random = new Random();
        int i = random.nextInt(11111111, 99999999);
        return id + "" + i;
    }

    private BigDecimal randomProductPrice() {
        Random random = new Random();
        double v = random.nextDouble(0.10, 1000.00);
        BigDecimal bd = new BigDecimal(v);
        return bd.setScale(2, RoundingMode.DOWN);
    }
}
