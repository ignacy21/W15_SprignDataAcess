package pl.zajavka.data;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Data
@Builder
@RequiredArgsConstructor
public class Product {

    private final int id;
    private final String productName;
    private final String productCode;
    private final BigDecimal productPrice;
    private final boolean adultsOnly;
    private final String description;
    private final int producerID;
}
