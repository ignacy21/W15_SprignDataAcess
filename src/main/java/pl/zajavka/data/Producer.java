package pl.zajavka.data;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class Producer {

    private final int id;
    private final String producerName;
    private final String address;

}
