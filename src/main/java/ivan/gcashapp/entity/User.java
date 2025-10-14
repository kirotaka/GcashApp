package ivan.gcashapp.entity;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private long id;
    private String name;
    private String email;
    private long number;
    private String pin;
}