package pl.zajavka.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private int id;
    private String userName;
    private String email;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String telephoneNumber;

}
