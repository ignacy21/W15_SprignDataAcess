package pl.zajavka.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Purchase {

    private final int id;
    private final int customerId;
    private final int productId;
    private final int quantity;
    private final LocalDateTime dateTime;
}
