package ivan.gcashapp.entity;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Balance {

    private long id;
    private double amount;
    private long userId;
}