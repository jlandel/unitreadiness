
package com.portfolio.unitreadiness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.portfolio.unitreadiness.model.Equipment;
import com.portfolio.unitreadiness.repository.EquipmentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class EquipmentApiTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Test
    void shouldRejectInvalidEquipment() {

        String invalidEquipment = """
                {
                    "name": "",
                    "equipmentType": "Computer",
                    "status": "POTATO",
                    "assignedUnit": "DEMO-001"
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>(invalidEquipment, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/api/equipment",
                        request,
                        String.class
                );

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void shouldReturn404ForMissingEquipment() {

        ResponseEntity<String> response =
                restTemplate.getForEntity(
                        "/api/equipment/999999999",
                        String.class
                );

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void shouldGetExistingEquipment() {

        // Arrange: create and save equipment
        Equipment equipment = new Equipment(
                "Test Radio",
                "Communications",
                "FMC",
                "DEMO-001"
        );

        Equipment savedEquipment = equipmentRepository.save(equipment);

        // Act: retrieve equipment through the API
        ResponseEntity<Equipment> response =
                restTemplate.getForEntity(
                        "/api/equipment/" + savedEquipment.getId(),
                        Equipment.class
                );

        // Assert: verify successful retrieval
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Test Radio", response.getBody().getName());
    }

    @Test
    void shouldDeleteEquipment() {

        // Arrange: create equipment to delete
        Equipment equipment = new Equipment(
                "Test Laptop",
                "Computer",
                "FMC",
                "DEMO-001"
        );

        Equipment savedEquipment = equipmentRepository.save(equipment);
        Long id = savedEquipment.getId();

        // Act: delete equipment through the API
        restTemplate.delete("/api/equipment/" + id);

        // Assert: verify equipment no longer exists
        assertFalse(equipmentRepository.existsById(id));
    }
}
