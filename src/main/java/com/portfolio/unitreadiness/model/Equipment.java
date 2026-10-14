
package com.portfolio.unitreadiness.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Equipment name is required")
    private String name;
    @NotBlank(message = "Equipment type is required")
    private String equipmentType;
    @NotBlank(message = "Status is required")
    @Pattern(
        regexp = "(?i)FMC|NMC",
        message = "Status must be FMC or NMC"
    )
    private String status;
    @NotBlank(message = "Assigned unit is required")
    private String assignedUnit;

    public Equipment() {
    }

    public Equipment(String name, String equipmentType,
                     String status, String assignedUnit) {
        this.name = name;
        this.equipmentType = equipmentType;
        this.status = status;
        this.assignedUnit = assignedUnit;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedUnit() {
        return assignedUnit;
    }

    public void setAssignedUnit(String assignedUnit) {
        this.assignedUnit = assignedUnit;
    }
}
