package com.example.inventoryService.service;

import com.example.inventoryService.dao.InventoryDao;
import com.example.inventoryService.dto.InventoryRequest;
import com.example.inventoryService.dto.InventoryResponse;
import com.example.inventoryService.exception.NotFoundException;
import com.example.inventoryService.model.Inventory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InventoryService {
    @Autowired
    private InventoryDao inventoryDao;

    public String createOrder(InventoryRequest inventoryRequest){
        Inventory order = Inventory.builder()
                .skuCode(inventoryRequest.skuCode())
                .quantity(inventoryRequest.quantity())
                .build();

        inventoryDao.save(order);
        log.info("New Inventory is created successfully");
        return "New Inventory is created successfully";
    }

    public List<InventoryResponse> findAll() throws NotFoundException {
        List<InventoryResponse> inventoryResponseList = inventoryDao.findAll().stream().map(
                el->new InventoryResponse(el.getId(),el.getSkuCode(),el.getQuantity())).toList();

        if (inventoryResponseList.isEmpty()) {
            throw new NotFoundException("Inventory list is empty");
        }
        return inventoryResponseList;
    }
    public InventoryResponse findById(long id) throws NotFoundException {
        Inventory inventory = inventoryDao.findById(id)
                .orElseThrow(()-> new NotFoundException("No found such inventory with id "+id));
        return new InventoryResponse(inventory.getId(),inventory.getSkuCode(),inventory.getQuantity());
    }
    public InventoryResponse findBySkuCode(String skuCode) throws NotFoundException {
        Inventory inventory = inventoryDao.findBySkuCode(skuCode)
                .orElseThrow(()-> new NotFoundException("No found such inventory with skuCode " + skuCode));
        return new InventoryResponse(inventory.getId(),inventory.getSkuCode(),inventory.getQuantity());
    }
    public boolean isInStockBySkuCode(String skuCode,int expectQuantity) throws NotFoundException {
        Inventory inventory = inventoryDao.findBySkuCode(skuCode)
                .orElseThrow(()-> new NotFoundException("No found such inventory with skuCode " + skuCode));
        return inventory.getQuantity() >= expectQuantity;
    }

}
