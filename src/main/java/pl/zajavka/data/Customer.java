package pl.zajavka.data;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
public class Customer {

    private final int id;
    private final String userName;
    private final String email;
    private final String name;
    private final String surname;
    private final LocalDate dateOfBirth;
    private final String telephoneNumber;

}
