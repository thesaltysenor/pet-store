package com.promineotech.petstore.service;

import com.promineotech.petstore.controller.model.PetStoreData;
import com.promineotech.petstore.dao.EmployeeDao;
import com.promineotech.petstore.dao.PetStoreDao;
import com.promineotech.petstore.entity.Customer;
import com.promineotech.petstore.entity.Employee;
import com.promineotech.petstore.entity.PetStore;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PetStoreService {
    @Autowired
    private PetStoreDao petStoreDao;
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    public PetStoreService(PetStoreDao petStoreDao) {
        this.petStoreDao = petStoreDao;
    }

    public Employee findEmployeeById(Long petStoreId, Long employeeId) {
        Optional<Employee> employeeOptional = employeeDao.findById(employeeId);
        Employee employee = employeeOptional.orElseThrow(NoSuchElementException::new);
        if (!employee.getPetStore().getPetStoreId().equals(petStoreId)) {
            throw new IllegalArgumentException("Employee does not belong to the specified pet store");
        }
        return employee;
    }
    public Employee findOrCreateEmployee(Long employeeId, Long petStoreId) {
        if (petStoreId == null) {
            return new Employee();
        } else {
            return findEmployeeById(petStoreId, employeeId);
        }
    }
    @Transactional()
    public PetStoreData saveEmployee(Long petStoreId, PetStoreData.PetStoreEmployee petStoreEmployee) {
        PetStore petStore = findPetStoreById(petStoreId);
        Employee employee = findOrCreateEmployee(petStoreEmployee.getEmployeeId(), petStoreId);
        copyEmployeeFields(employee, petStoreEmployee);
        employee.setPetStore(petStore);
        petStore.getEmployees().add(employee);
        employeeDao.save(employee);
        return new PetStoreData(petStore);
    }
    // Saves the pet store data by inserting or modifying the pet store in the database
    public PetStoreData savePetStore(PetStoreData petStoreData) {
        Long petStoreId = petStoreData.getPetStoreId();

        PetStore petStore;
        if (petStoreId == null) {
            petStore = findOrCreatePetStore(null);
        } else {
            petStore = findPetStoreById(petStoreId);
        }

        if (petStore == null) {
            throw new NoSuchElementException("Pet store with ID " + petStoreId + " not found.");
        }

        copyPetStoreFields(petStore, petStoreData);

        PetStore savedPetStore = petStoreDao.save(petStore);

        return new PetStoreData(savedPetStore);
    }
    // Finds or creates a pet store based on the provided ID
    private PetStore findOrCreatePetStore(Long petStoreId) {
        if (petStoreId == null) {
            return new PetStore();
        } else {
            return petStoreDao.findById(petStoreId).orElse(null);
        }
    }

    // Finds a pet store by ID
    public PetStore findPetStoreById(Long petStoreId) {
        return petStoreDao.findById(petStoreId).orElse(null);
    }
    @Transactional(readOnly = false)
    public PetStoreData saveCustomer(Long petStoreId, PetStoreData.PetStoreCustomer petStoreCustomer) {
        PetStore petStore = findPetStoreById(petStoreId);
        Customer customer = findOrCreateCustomer(petStoreCustomer.getCustomerId(), petStoreId);
        copyCustomerFields(customer, petStoreCustomer);
        customer.getPetStores().add(petStore);
        petStore.getCustomers().add(customer);
        petStoreDao.save(petStore);
        return new PetStoreData(petStore);
    }

    private Customer findOrCreateCustomer(Long customerId, Long petStoreId) {
        if (customerId == null) {
            return new Customer();
        } else {
            return findCustomerById(petStoreId, customerId);
        }
    }

    private Customer findCustomerById(Long petStoreId, Long customerId) {
        PetStore petStore = findPetStoreById(petStoreId);
        for (Customer customer : petStore.getCustomers()) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        throw new IllegalArgumentException("Customer does not belong to the specified pet store");
    }
    @Transactional
    public List<PetStoreData> retrieveAllPetStores() {
        List<PetStore> petStores = petStoreDao.findAll();
        List<PetStoreData> petStoreDataList = new ArrayList<>();
        for (PetStore petStore : petStores) {
            PetStoreData petStoreData = new PetStoreData(petStore);
            petStoreData.setCustomers(null);
            petStoreData.setEmployees(null);
            petStoreDataList.add(petStoreData);
        }
        return petStoreDataList;
    }
    @Transactional
    public PetStoreData retrievePetStoreById(Long petStoreId) {
        PetStore petStore = petStoreDao.findById(petStoreId).orElse(null);
        if (petStore == null) {
            throw new NoSuchElementException("Pet store with ID " + petStoreId + " not found.");
        }
        PetStoreData petStoreData = new PetStoreData(petStore);
        return petStoreData;
    }
    @Transactional
    public void deletePetStoreById(Long petStoreId) {
        PetStore petStore = petStoreDao.findById(petStoreId).orElse(null);
        if (petStore == null) {
            throw new NoSuchElementException("Pet store with ID " + petStoreId + " not found.");
        }
        petStoreDao.delete(petStore);
    }


    private void copyCustomerFields(Customer customer, PetStoreData.PetStoreCustomer petStoreCustomer) {
        customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
        customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
        customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
    }

    // Copies the relevant fields from petStoreData to petStore
    private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
        petStore.setPetStoreName(petStoreData.getPetStoreName());
        petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
        petStore.setPetStoreCity(petStoreData.getPetStoreCity());
        petStore.setPetStoreState(petStoreData.getPetStoreState());
        petStore.setPetStoreZip(petStoreData.getPetStoreZip());
        petStore.setPetStorePhone(petStoreData.getPetStorePhone());
    }
    private void copyEmployeeFields(Employee employee, PetStoreData.PetStoreEmployee petStoreEmployee) {
        employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
        employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
        employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
        employee.setEmployeeTitle(petStoreEmployee.getEmployeeTitle());
    }

}
