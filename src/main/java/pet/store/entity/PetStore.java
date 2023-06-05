package pet.store.entity;



import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class PetStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petStoreId;

    private String petStoreName;
    private String petStoreAddress;
    private String petStoreCity;
    private String petStoreState;
    private String petStoreZip;
    private String petStorePhone;

    @OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "customer_pet_store",
            joinColumns = @JoinColumn(name = "pet_store_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> customers = new HashSet<>();

    // Constructors, getters, setters
}
