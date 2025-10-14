package ivan.gcashapp.entity;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    private long id;
    private double amount;
    private String name;
    private long accountId;
    private LocalDateTime date;
    private long transferToId;
    private long transferFromId;
}