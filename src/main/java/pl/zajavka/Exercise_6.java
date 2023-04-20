package pl.zajavka;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Exercise_6 {

    private final SimpleDriverDataSource simpleDriverDataSource;

    @Transactional
    public void putInsertsFromFile() {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of("src/main/resources/sql/w15-project-sql-inserts.sql"));
            lines.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        String sql = "";
        for (String line : lines) {
            if (line.equals("")) {
                sql = "";
                continue;
            }
            String[] split = line.split(" ");
            if (split[0].equals("INSERT")) {
                sql = line;
                continue;
            }
            String command = line.substring(0, line.length() - 1);
            JdbcTemplate template = new JdbcTemplate(simpleDriverDataSource);
            template.update(sql + " " + command);
        }


    }
}
