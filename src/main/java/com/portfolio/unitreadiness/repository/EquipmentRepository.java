
package com.portfolio.unitreadiness.repository;

import com.portfolio.unitreadiness.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository
        extends JpaRepository<Equipment, Long> {

}
