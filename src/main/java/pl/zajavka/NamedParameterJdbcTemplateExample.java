package pl.zajavka;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NamedParameterJdbcTemplateExample {

    private final SimpleDriverDataSource simpleDriverDataSource;


    void example() {
        NamedParameterJdbcTemplate template =
                new NamedParameterJdbcTemplate(simpleDriverDataSource);

        String sql = "INSERT INTO PERSON (NAME, AGE) VALUES (:name, :age)";

        // 1-st way
        Map<String, Object> param1 = new HashMap<>();
        param1.put("name", "Kamil");
        param1.put("age", 12);
        template.update(sql, param1);

        // 2-nd. way
        MapSqlParameterSource param2 = new MapSqlParameterSource();
        param2.addValue("name", "Zdun");
        param2.addValue("age", 12);
        template.update(sql, param2);

        // 3-rd. way
        Person person = Person.builder()
                .name("Agnieszka")
                .age(25)
                .build();

        BeanPropertySqlParameterSource param3 = new BeanPropertySqlParameterSource(person);
        template.update(sql, param3);
    }

}
