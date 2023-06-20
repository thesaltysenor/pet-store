package com.promineotech.petstore.controller.model;

import com.promineotech.petstore.service.PetStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pet-store")
@Slf4j
public class PetStoreController {

    @Autowired
    private PetStoreService petStoreService;

    @Autowired
    public PetStoreController(PetStoreService petStoreService) {
        this.petStoreService = petStoreService;
    }

    // Creates a new pet store with the provided data
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) {
       log.info("Received a request to create a pet store.");
        return petStoreService.savePetStore(petStoreData);

    }
    // Updates an existing pet store with the provided data
    @PutMapping("/pet-store/{petStoreId}")
    public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
        petStoreData.setPetStoreId(petStoreId); // Set the pet store ID from the request URI
        log.info("Updating pet store with ID: {}", petStoreId);
        return petStoreService.savePetStore(petStoreData);
    }
    @PostMapping("/pet-store/{petStoreId}/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreData addEmployeeToPetStore(
            @PathVariable Long petStoreId, @RequestBody PetStoreData.PetStoreEmployee employee) {
        System.out.println("Adding employee to pet store. Pet Store ID: " + petStoreId + ", Employee: " + employee);

        return petStoreService.saveEmployee(petStoreId, employee);
    }

    @PostMapping("/pet-store/{petStoreId}/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreData addCustomerToPetStore(
            @PathVariable Long petStoreId, @RequestBody PetStoreData.PetStoreCustomer customer) {
        System.out.println("Adding customer to pet store. Pet Store ID: " + petStoreId + ", Customer: " + customer);

        return petStoreService.saveCustomer(petStoreId, customer);
    }
    @GetMapping("/pet_store")
    public List<PetStoreData> retrieveAllPetStores() {
        return petStoreService.retrieveAllPetStores();
    }
    @GetMapping("/pet_store/{petStoreId}")
    public PetStoreData retrievePetStoreById(@PathVariable Long petStoreId) {
        return petStoreService.retrievePetStoreById(petStoreId);
    }
    @DeleteMapping("/pet_store/{petStoreId}")
    public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
        log.info("Received a request to delete pet store with ID: {}", petStoreId);
        petStoreService.deletePetStoreById(petStoreId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Deletion successful");
        return response;
    }


}
