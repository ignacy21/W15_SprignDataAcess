package pl.zajavka.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Purchase implements Comparable<Purchase> {

    private int id;
    private int customerId;
    private int productId;
    private int quantity;
    private LocalDateTime dateTime;

    @Override
    public int compareTo(Purchase p) {
        return p.getCustomerId() - customerId;
    }
}
