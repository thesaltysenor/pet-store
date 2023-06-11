package com.promineotech.petstore.service;

import com.promineotech.petstore.controller.model.PetStoreData;
import com.promineotech.petstore.dao.PetStoreDao;
import com.promineotech.petstore.entity.PetStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PetStoreService {
    @Autowired
    private PetStoreDao petStoreDao;

    @Autowired
    public PetStoreService(PetStoreDao petStoreDao) {
        this.petStoreDao = petStoreDao;
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


    // Copies the relevant fields from petStoreData to petStore
    private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
        petStore.setPetStoreName(petStoreData.getPetStoreName());
        petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
        petStore.setPetStoreCity(petStoreData.getPetStoreCity());
        petStore.setPetStoreState(petStoreData.getPetStoreState());
        petStore.setPetStoreZip(petStoreData.getPetStoreZip());
        petStore.setPetStorePhone(petStoreData.getPetStorePhone());
    }

}
