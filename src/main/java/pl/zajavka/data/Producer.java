package pl.zajavka.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Producer {

    private int id;
    private String producerName;
    private String address;

}
