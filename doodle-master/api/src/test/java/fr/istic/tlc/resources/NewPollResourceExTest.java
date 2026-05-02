package fr.istic.tlc.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.istic.tlc.dao.ChoiceRepository;
import fr.istic.tlc.dao.CommentRepository;
import fr.istic.tlc.dao.MealPreferenceRepository;
import fr.istic.tlc.dao.PollRepository;
import fr.istic.tlc.dao.UserRepository;
import fr.istic.tlc.domain.Choice;
import fr.istic.tlc.domain.Comment;
import fr.istic.tlc.domain.Poll;
import fr.istic.tlc.domain.User;
import fr.istic.tlc.dto.ChoiceUser;
import fr.istic.tlc.services.SendEmail;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;

@ExtendWith(MockitoExtension.class)
class NewPollResourceExTest {

    @Mock PollRepository pollRep;
    @Mock UserRepository userRep;
    @Mock ChoiceRepository choiceRep;
    @Mock MealPreferenceRepository mealprefRep;
    @Mock CommentRepository commentRep;
    @Mock SendEmail sendmail;

    @Mock PanacheQuery<User> userQuery;
    @Mock PanacheQuery<Poll> pollQuery;
    @Mock EntityManager choiceEntityManager;
    @Mock EntityManager pollEntityManager;

    private NewPollResourceEx resource;

    @BeforeEach
    void setUp() {
        resource = new NewPollResourceEx();
        resource.pollRep = pollRep;
        resource.userRep = userRep;
        resource.choiceRep = choiceRep;
        resource.mealprefRep = mealprefRep;
        resource.commentRep = commentRep;
        resource.sendmail = sendmail;
    }

    @Test
    void getPollBySlug_shouldClearCommentsAndHideAdminSlug() {
        Poll poll = new Poll();
        poll.setSlugAdmin("secret");
        poll.setPollComments(new ArrayList<>(List.of(new Comment())));

        when(pollRep.findBySlug("slug")).thenReturn(poll);

        Poll result = resource.getPollBySlug("slug");

        assertEquals("", result.getSlugAdmin());
        assertTrue(result.getPollComments().isEmpty());
    }

    @Test
    void createComment4Poll_shouldPersistCommentAndAttachItToPoll() {
        Comment comment = new Comment();
        Poll poll = new Poll();
        poll.setPollComments(new ArrayList<>());

        when(pollRep.findBySlug("slug")).thenReturn(poll);

        Comment result = resource.createComment4Poll("slug", comment);

        assertSame(comment, result);
        assertEquals(1, poll.getPollComments().size());
        verify(commentRep).persist(comment);
        verify(pollRep).persistAndFlush(poll);
    }

    @Test
    void addChoiceUser_shouldCreateUserAndVotesAndMealPreference() {
        ChoiceUser payload = new ChoiceUser();
        payload.setUsername("Alice");
        payload.setMail("alice@mail.test");
        payload.setIcs("http://ics.test/alice");
        payload.setPref("Vegan");
        payload.setChoices(List.of(1L, 2L));

        Choice c1 = new Choice();
        Choice c2 = new Choice();

        when(userRep.find("mail", "alice@mail.test")).thenReturn(userQuery);
        when(userQuery.firstResult()).thenReturn(null);
        when(choiceRep.findById(1L)).thenReturn(c1);
        when(choiceRep.findById(2L)).thenReturn(c2);

        User result = resource.addChoiceUser(payload);

        assertNotNull(result);
        assertEquals("alice@mail.test", result.getMail());
        verify(userRep).persist(any(User.class));
        verify(mealprefRep).persist(any(fr.istic.tlc.domain.MealPreference.class));
        verify(choiceRep).persistAndFlush(c1);
        verify(choiceRep).persistAndFlush(c2);
    }

    @Test
    void closePoll_shouldMarkClosedPersistAndSendEmail() {
        Choice choice = new Choice();
        Poll poll = new Poll();

        when(choiceRep.findById(10L)).thenReturn(choice);
        when(pollRep.find(anyString(), any(Parameters.class))).thenReturn(pollQuery);
        when(pollQuery.firstResult()).thenReturn(poll);

        resource.closePoll("10");

        assertTrue(poll.isClos());
        assertEquals(choice, poll.getSelectedChoice());
        verify(pollRep).persist(poll);
        verify(sendmail).sendASimpleEmail(poll);
    }

    @Test
    void updatePoll_shouldRemoveMissingChoicesAndResetSelectedChoiceIfNeeded() {
        Choice removed = new Choice();
        Choice kept = new Choice();

        Poll existing = new Poll();
        existing.setId(1L);
        existing.setPollChoices(new ArrayList<>(List.of(removed, kept)));
        existing.setSelectedChoice(removed);
        existing.setClos(true);

        Poll incoming = new Poll();
        incoming.setId(1L);
        incoming.setPollChoices(new ArrayList<>(List.of(kept)));
        incoming.setSelectedChoice(removed);
        incoming.setClos(true);

        when(pollRep.findById(1L)).thenReturn(existing);
        when(choiceRep.getEntityManager()).thenReturn(choiceEntityManager);
        when(pollRep.getEntityManager()).thenReturn(pollEntityManager);
        when(pollEntityManager.merge(incoming)).thenReturn(incoming);

        Poll result = resource.updatePoll(incoming);

        assertNotNull(result);
        assertNull(incoming.getSelectedChoice());
        assertFalse(incoming.isClos());
        verify(choiceRep).delete(removed);
    }
}