package com.promineotech.petstore.controller.model;

import com.promineotech.petstore.entity.Customer;
import com.promineotech.petstore.entity.Employee;
import com.promineotech.petstore.entity.PetStore;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class PetStoreData {
    private Long petStoreId;
    private String petStoreName;
    private String petStoreAddress;
    private String petStoreCity;
    private String petStoreState;
    private String petStoreZip;
    private String petStorePhone;
    private Set<PetStoreEmployee> employees = new HashSet<>();
    private Set<PetStoreCustomer> customers = new HashSet<>();

    // Constructor that initializes the PetStoreData object based on a PetStore entity
    public PetStoreData(PetStore petStore) {
        // Set the properties based on the PetStore entity
      this.petStoreId = petStore.getPetStoreId();
      this.petStoreName = petStore.getPetStoreName();
      this.petStoreAddress = petStore.getPetStoreAddress();
      this.petStoreCity = petStore.getPetStoreCity();
      this.petStoreState = petStore.getPetStoreState();
      this.petStoreZip = petStore.getPetStoreZip();
      this.petStorePhone = petStore.getPetStorePhone();

      // Convert and populate the set of employees from the PetStore entity
      Set<PetStoreEmployee> employeeSet = new HashSet<>();
        for (Employee employee : petStore.getEmployees()) {
            PetStoreEmployee petStoreEmployee = new PetStoreEmployee();
            petStoreEmployee.setEmployeeId(employee.getEmployeeId());
            petStoreEmployee.setEmployeeFirstName(employee.getEmployeeFirstName());
            petStoreEmployee.setEmployeeLastName(employee.getEmployeeLastName());
            petStoreEmployee.setEmployeePhone(employee.getEmployeePhone());
            petStoreEmployee.setEmployeeTitle(employee.getEmployeeTitle());

            employeeSet.add(petStoreEmployee);
        }
        this.employees = employeeSet;

        // Convert and populate the set of customers from the PetStore entity
        Set<PetStoreCustomer> customerSet = new HashSet<>();
        for (Customer customer : petStore.getCustomers()) {
            PetStoreCustomer petStoreCustomer = new PetStoreCustomer();
            petStoreCustomer.setCustomerId(customer.getCustomerId());
            petStoreCustomer.setCustomerFirstName(customer.getCustomerFirstName());
            petStoreCustomer.setCustomerLastName(customer.getCustomerLastName());
            petStoreCustomer.setCustomerEmail(customer.getCustomerEmail());

            customerSet.add(petStoreCustomer);
        }
        this.customers = customerSet;

    }

    // Inner class representing a customer associated with the pet store
    @Data
    @NoArgsConstructor
    public static class PetStoreCustomer {
        private Long customerId;
        private String customerFirstName;
        private String customerLastName;
        private String customerEmail;
        private Set<PetStore> petStores = new HashSet<>();

        // Constructor that initializes the PetStoreCustomer object based on a Customer entity
        public PetStoreCustomer(Customer customer) {
            // Set the properties based on the Customer entity
            this.customerId = customer.getCustomerId();
            this.customerFirstName = customer.getCustomerFirstName();
            this.customerLastName = customer.getCustomerLastName();
            this.customerEmail = customer.getCustomerEmail();
        }

    }
    // Inner class representing an employee working at the pet store
    @Data
    @NoArgsConstructor
    public static class PetStoreEmployee {
        private Long employeeId;
        private String employeeFirstName;
        private String employeeLastName;
        private String employeePhone;
        private String employeeTitle;
        private PetStore petStore;

        // Constructor that initializes the PetStoreEmployee object based on an Employee entity
        public PetStoreEmployee(Employee employee) {
            this.employeeId = employee.getEmployeeId();
            this.employeeFirstName = employee.getEmployeeFirstName();
            this.employeeLastName = employee.getEmployeeLastName();
            this.employeePhone = employee.getEmployeePhone();
            this.employeeTitle = employee.getEmployeeTitle();
            this.petStore = employee.getPetStore();
        }
    }
}
