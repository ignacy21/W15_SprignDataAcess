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
public class Exercise_7 {

    private final SimpleDriverDataSource simpleDriverDataSource;

    @Transactional
    public void removeOpinionsRatedUnder4(List<Opinion> opinionList) {
        String command = "DELETE FROM OPINION WHERE ID = ?";
        List<Integer> collect = opinionList.stream()
                .filter(opinion -> opinion.getStars() < 4)
                .map(Opinion::getId)
                .toList();

        JdbcTemplate template = new JdbcTemplate(simpleDriverDataSource);
        collect.forEach(id -> template.update(command, id));
    }
}
