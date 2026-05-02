package fr.istic.tlc.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class PollBusinessTest {

    @Test
    void defaultPoll_shouldHaveGeneratedLinksAndOpenState() {
        Poll poll = new Poll();

        assertNotNull(poll.getSlug());
        assertEquals(24, poll.getSlug().length());

        assertNotNull(poll.getSlugAdmin());
        assertEquals(24, poll.getSlugAdmin().length());

        assertNotNull(poll.getTlkURL());
        assertTrue(poll.getTlkURL().startsWith("https://tlk.io/"));

        assertFalse(poll.isClos());
    }

    @Test
    void addAndRemoveComment_shouldUpdatePollComments() {
        Poll poll = new Poll();
        poll.setPollComments(new ArrayList<>());

        Comment c = new Comment();
        poll.addComment(c);
        assertEquals(1, poll.getPollComments().size());

        poll.removeComment(c);
        assertTrue(poll.getPollComments().isEmpty());
    }

    @Test
    void addAndRemoveMealPreference_shouldUpdatePollMealPreferences() {
        Poll poll = new Poll();
        poll.setPollMealPreferences(new ArrayList<>());

        MealPreference mp = new MealPreference();
        poll.addMealPreference(mp);
        assertEquals(1, poll.getPollMealPreferences().size());

        poll.removeComment(mp);
        assertTrue(poll.getPollMealPreferences().isEmpty());
    }

    @Test
    void addAndRemoveChoice_shouldUpdateChoices() {
        Poll poll = new Poll();
        poll.setPollChoices(new ArrayList<>());

        Choice choice = new Choice();
        poll.addChoice(choice);
        assertEquals(1, poll.getPollChoices().size());

        poll.removeChoice(choice);
        assertTrue(poll.getPollChoices().isEmpty());
    }
}