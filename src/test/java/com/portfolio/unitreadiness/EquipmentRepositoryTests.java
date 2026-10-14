package com.portfolio.unitreadiness;

import com.portfolio.unitreadiness.model.Equipment;
import com.portfolio.unitreadiness.repository.EquipmentRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EquipmentRepositoryTests {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Test
    void shouldSaveEquipment() {

        Equipment equipment = new Equipment();

        equipment.setName("Test Laptop");
        equipment.setEquipmentType("Computer");
        equipment.setStatus("FMC");
        equipment.setAssignedUnit("DEMO-001");

        Equipment savedEquipment = equipmentRepository.save(equipment);

        assertNotNull(savedEquipment.getId());

        Equipment retrievedEquipment = equipmentRepository
                .findById(savedEquipment.getId())
                .orElseThrow();

        assertEquals("Test Laptop", retrievedEquipment.getName());
        assertEquals("FMC", retrievedEquipment.getStatus());
        assertEquals("DEMO-001", retrievedEquipment.getAssignedUnit());
    }
}