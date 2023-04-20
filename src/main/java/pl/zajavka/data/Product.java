package pl.zajavka.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class Product {

    private int id;
    private String productName;
    private String productCode;
    private BigDecimal productPrice;
    private boolean adultsOnly;
    private String description;
    private int producerID;
}
