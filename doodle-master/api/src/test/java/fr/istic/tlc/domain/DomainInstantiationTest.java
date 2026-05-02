package fr.istic.tlc.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class DomainInstantiationTest {

    @Test
    void shouldInstantiateChoice() {
        assertDoesNotThrow(() -> Choice.class.getDeclaredConstructor().newInstance());
    }

    @Test
    void shouldInstantiateComment() {
        assertDoesNotThrow(() -> Comment.class.getDeclaredConstructor().newInstance());
    }

    @Test
    void shouldInstantiateMealPreference() {
        assertDoesNotThrow(() -> MealPreference.class.getDeclaredConstructor().newInstance());
    }

    @Test
    void shouldInstantiatePoll() {
        assertDoesNotThrow(() -> Poll.class.getDeclaredConstructor().newInstance());
    }

    @Test
    void shouldInstantiateUser() {
        assertDoesNotThrow(() -> User.class.getDeclaredConstructor().newInstance());
    }
}