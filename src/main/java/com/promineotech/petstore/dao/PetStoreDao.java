package com.promineotech.petstore.dao;

import com.promineotech.petstore.entity.PetStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetStoreDao extends JpaRepository<PetStore, Long> {
}
