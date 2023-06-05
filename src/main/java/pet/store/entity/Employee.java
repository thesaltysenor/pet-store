package pet.store.entity;


import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String employeeFirstName;
    private String employeeLastName;
    private String employeePhone;
    private String employeeTitle;

    @ManyToOne
    @JoinColumn(name = "pet_store_id")
    private PetStore petStore;


    // Constructors, getters, setters
}
