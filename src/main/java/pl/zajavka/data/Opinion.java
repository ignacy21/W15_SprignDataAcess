package pl.zajavka.data;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
public class Opinion {
    private final int id;
    private final int customerId;
    private final int productId;
    private final int stars;
    private final String comment;
    private final LocalDateTime dateTime;
}
