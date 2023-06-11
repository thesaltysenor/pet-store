package com.promineotech.petstore.controller.model;

import com.promineotech.petstore.service.PetStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet-store")
@Slf4j
public class PetStoreController {

    @Autowired
    private PetStoreService petStoreService;

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

}
