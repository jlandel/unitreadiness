package com.portfolio.unitreadiness;

import com.portfolio.unitreadiness.controller.EquipmentController;
import com.portfolio.unitreadiness.model.Equipment;
import com.portfolio.unitreadiness.repository.EquipmentRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EquipmentReadinessTests {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentController equipmentController;

    @Test
    void shouldCalculateFiftyPercentReadiness() {

        equipmentRepository.deleteAll();

        Equipment laptop = new Equipment();
        laptop.setName("Tactical Laptop");
        laptop.setEquipmentType("Computer");
        laptop.setStatus("FMC");
        laptop.setAssignedUnit("DEMO-001");

        Equipment radio = new Equipment();
        radio.setName("Radio System");
        radio.setEquipmentType("Communications");
        radio.setStatus("NMC");
        radio.setAssignedUnit("DEMO-001");

        equipmentRepository.save(laptop);
        equipmentRepository.save(radio);

        Map<String, Object> readiness = equipmentController.getReadinessReport();

        assertEquals(2L, ((Number) readiness.get("totalEquipment")).longValue());
        assertEquals(1L, ((Number) readiness.get("fullyMissionCapable")).longValue());
        assertEquals(1L, ((Number) readiness.get("nonMissionCapable")).longValue());
        assertEquals(50.0, ((Number) readiness.get("readinessPercentage")).doubleValue(), 0.001);
    }

    @Test
    void shouldHandleEmptyInventory() {

        equipmentRepository.deleteAll();

        Map<String, Object> readiness =
                equipmentController.getReadinessReport();

        assertEquals(0L,
                ((Number) readiness.get("totalEquipment")).longValue());

        assertEquals(0.0,
                ((Number) readiness.get("readinessPercentage")).doubleValue(),
                0.001);
    }
}