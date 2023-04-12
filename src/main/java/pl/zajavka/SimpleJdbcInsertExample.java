package pl.zajavka;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SimpleJdbcInsertExample {

    private final SimpleDriverDataSource simpleDriverDataSource;

    public void simpleDriverDataSource() {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource);
        simpleJdbcInsert.setTableName("PERSON");

        Map<String, Object> params = new HashMap<>();
        params.put("ID", 12);
        params.put("NAME", "Karol");
        params.put("AGE", 19);

        int result1 = simpleJdbcInsert.execute(params);
        System.out.println(result1);

        // better way ->
        Person person = Person.builder()
                .id(39L)
                .name("Stefan")
                .age(44)
                .build();
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(person);

        int result2 = simpleJdbcInsert.execute(parameterSource);
        System.out.println(result2);
    }
}
