package edu.dosw.rideci.application.events;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEvent {
    private Long id; //Viene del microservicio de user?
    private String name; //Viene del microservicio de user?
    private String phoneNumber;
    private String profileType;
    

}
