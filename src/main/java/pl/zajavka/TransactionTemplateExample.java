package pl.zajavka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class TransactionTemplateExample {

    private static final String SQL = "INSERT INTO PERSON (NAME, AGE) VALUES (:name, :age)";

    private SimpleDriverDataSource simpleDriverDataSource;

    private TransactionTemplate transactionTemplate;

    // tak w praktyce raczej nie piszemy :(
    public void example1() {
        Integer result = transactionTemplate.execute(status -> {
            Integer integer = someMethod();
            if (true) {
                status.setRollbackOnly();
                log.error("Transaction rollback");
            }
            return integer;
        });
        System.out.println("Rows: " + result);

    }

    // tak w praktyce raczej nie piszemy :(
    public void transactionCallbackWithoutResultUse() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                someVoidMethod();
                if (true) {
                    status.setRollbackOnly();
                    log.error("Transaction rollback");
                }
            }
        });
    }

    private Integer someMethod() {
        int result = 0;
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);

        Person person1 = Person.builder().name("Roman").age(55).build();
        Person person2 = Person.builder().name("Tomasz").age(25).build();
        Person person3 = Person.builder().name("Roman2").age(21).build();

        result += jdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(person1));
        result += jdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(person2));
        result += jdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(person3));
        return result;
    }
    private void someVoidMethod() {
        int result = 0;
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);

        Person person1 = Person.builder().name("Roman").age(55).build();
        Person person2 = Person.builder().name("Tomasz").age(25).build();
        Person person3 = Person.builder().name("Roman2").age(21).build();

        jdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(person1));
        jdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(person2));
        jdbcTemplate.update(SQL, new BeanPropertySqlParameterSource(person3));
    }

}
