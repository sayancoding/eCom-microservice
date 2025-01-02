package com.example.inventoryService.dao;

import com.example.inventoryService.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryDao extends JpaRepository<Inventory,Long> {
    Optional<Inventory> findBySkuCode(String skuCode);
}
