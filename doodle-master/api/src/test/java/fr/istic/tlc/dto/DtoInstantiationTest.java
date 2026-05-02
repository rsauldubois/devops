package fr.istic.tlc.dto;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class DtoInstantiationTest {

    @Test
    void shouldInstantiateChoiceUser() {
        assertDoesNotThrow(() -> ChoiceUser.class.getDeclaredConstructor().newInstance());
    }

    @Test
    void shouldInstantiateEventDTO() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredConstructor().newInstance());
    }

    @Test
    void shouldInstantiateEventDTOAndSelectedChoice() {
        assertDoesNotThrow(() -> EventDTOAndSelectedChoice.class.getDeclaredConstructor().newInstance());
    }
}