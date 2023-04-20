package pl.zajavka.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Opinion implements Comparable<Opinion> {
    private final int id;
    private int customerId;
    private int productId;
    private int stars;
    private String comment;
    private LocalDateTime dateTime;

    @Override
    public int compareTo(Opinion o) {
        return o.getCustomerId() - customerId;
    }
}
