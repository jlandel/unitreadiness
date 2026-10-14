
package com.portfolio.unitreadiness.controller;

import com.portfolio.unitreadiness.model.Equipment;
import com.portfolio.unitreadiness.repository.EquipmentRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentRepository equipmentRepository;

    public EquipmentController(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @GetMapping
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    
    @PostMapping
    public Equipment createEquipment(@Valid @RequestBody Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

        
    @GetMapping("/{id}")
    public Equipment getEquipmentById(@PathVariable Long id) {
        return equipmentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Equipment not found with ID: " + id
            ));
    }

    
    @PutMapping("/{id}")
    public Equipment updateEquipment(
            @PathVariable Long id,
            @Valid @RequestBody Equipment updatedEquipment) {

        Equipment equipment = equipmentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Equipment not found with ID: " + id
            ));

        equipment.setName(updatedEquipment.getName());
        equipment.setEquipmentType(updatedEquipment.getEquipmentType());
        equipment.setStatus(updatedEquipment.getStatus());
        equipment.setAssignedUnit(updatedEquipment.getAssignedUnit());

        return equipmentRepository.save(equipment);
    }

    @GetMapping("/readiness")
    public Map<String, Object> getReadinessReport() {

        List<Equipment> equipment = equipmentRepository.findAll();

        long total = equipment.size();

        long fmc = equipment.stream()
                .filter(item -> "FMC".equalsIgnoreCase(item.getStatus()))
                .count();

        long nmc = equipment.stream()
                .filter(item -> "NMC".equalsIgnoreCase(item.getStatus()))
                .count();

        double readinessPercentage = total == 0 ? 0.0 : (fmc * 100.0) / total;

        Map<String, Object> report = new LinkedHashMap<>();
        report.put("totalEquipment", total);
        report.put("fullyMissionCapable", fmc);
        report.put("nonMissionCapable", nmc);
        report.put("readinessPercentage", readinessPercentage);

        return report;
    }


    @DeleteMapping("/{id}")
    public void deleteEquipment(@PathVariable Long id) {

        if (!equipmentRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Equipment not found with ID: " + id
            );
        }

        equipmentRepository.deleteById(id);
    }


}
