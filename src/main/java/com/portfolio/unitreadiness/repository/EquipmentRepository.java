
package com.portfolio.unitreadiness.repository;

import com.portfolio.unitreadiness.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.portfolio.unitreadiness.model.Equipment;
public interface EquipmentRepository
        extends JpaRepository<Equipment, Long> {

    List<Equipment> findByAssignedUnitIgnoreCase(String assignedUnit);

}