package fr.istic.tlc.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import fr.istic.tlc.dao.PollRepository;
import fr.istic.tlc.domain.Poll;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import net.gjerull.etherpad.client.EPLiteClient;

@ExtendWith(MockitoExtension.class)
class PollResourceExTest {

    @Mock
    PollRepository pollRepository;

    @Mock
    PanacheQuery<Poll> pollQuery;

    @Mock
    EntityManager entityManager;

    @Mock
    EPLiteClient client;

    private PollResourceEx resource;

    @BeforeEach
    void setUp() {
        resource = new PollResourceEx();
        resource.pollRepository = pollRepository;
        resource.client = client;
        resource.usePad = false;
        resource.externalPadUrl = "http://ext/";
        resource.padUrl = "http://etherpad:9001/";
        resource.apikey = "key";
    }

    @Test
    void retrieveAllpolls_shouldReturnSortedList() {
        List<Poll> polls = new ArrayList<>();
        polls.add(new Poll());

        when(pollRepository.findAll(any(Sort.class))).thenReturn(pollQuery);
        when(pollQuery.list()).thenReturn(polls);

        ResponseEntity<List<Poll>> response = resource.retrieveAllpolls();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void retrievePoll_shouldReturnNotFoundWhenMissing() {
        when(pollRepository.findBySlug("unknown")).thenReturn(null);

        ResponseEntity<Poll> response = resource.retrievePoll("unknown", null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void retrievePoll_shouldReturnUnauthorizedWhenTokenInvalid() {
        Poll poll = new Poll();
        poll.setSlugAdmin("admin-token");
        when(pollRepository.findBySlug("slug")).thenReturn(poll);

        ResponseEntity<Poll> response = resource.retrievePoll("slug", "bad-token");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void retrievePoll_shouldHideAdminSlugWhenAuthorized() {
        Poll poll = new Poll();
        poll.setSlugAdmin("admin-token");
        when(pollRepository.findBySlug("slug")).thenReturn(poll);

        ResponseEntity<Poll> response = resource.retrievePoll("slug", "admin-token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("", response.getBody().getSlugAdmin());
    }

    @Test
    void createPoll_shouldPersistAndReturnCreated() {
        Poll poll = new Poll();

        ResponseEntity<Poll> response = resource.createPoll(poll);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(pollRepository).persist(poll);
    }

    @Test
    void deletePoll_shouldReturnUnauthorizedIfTokenInvalid() {
        Poll poll = new Poll();
        poll.setSlug("slug");
        poll.setSlugAdmin("good");
        when(pollRepository.findBySlug("slug")).thenReturn(poll);

        ResponseEntity<Poll> response = resource.deletePoll("slug", "bad");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(pollRepository, never()).deleteById(anyLong());
    }

    @Test
    void deletePoll_shouldDeletePadAndPollWhenAuthorized() {
        Poll poll = new Poll();
        poll.setId(7L);
        poll.setSlug("slug");
        poll.setSlugAdmin("good");
        poll.setPadURL("http://etherpad.diverse-team.fr/p/abc123");

        when(pollRepository.findBySlug("slug")).thenReturn(poll);

        ResponseEntity<Poll> response = resource.deletePoll("slug", "good");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(client).deletePad("abc123");
        verify(pollRepository).deleteById(7L);
    }

    @Test
    void updatePoll_shouldUpdateBusinessFieldsAndPadContent() {
        Poll ancient = new Poll();
        ancient.setSlug("slug");
        ancient.setSlugAdmin("token");
        ancient.setTitle("OldTitle");
        ancient.setLocation("OldLocation");
        ancient.setDescription("OldDescription");
        ancient.setPadURL("http://etherpad.diverse-team.fr/p/pad42");

        Poll incoming = new Poll();
        incoming.setTitle("NewTitle");
        incoming.setLocation("NewLocation");
        incoming.setDescription("NewDescription");
        incoming.setHas_meal(true);

        Map<String, Object> textMap = new HashMap<>();
        textMap.put("text", "OldTitle\nLocalisation : OldLocation\nDescription : OldDescription\n");

        when(pollRepository.findBySlug("slug")).thenReturn(ancient);
        when(client.getText("pad42")).thenReturn(textMap);
        when(pollRepository.getEntityManager()).thenReturn(entityManager);
        when(entityManager.merge(any(Poll.class))).thenAnswer(inv -> inv.getArgument(0));

        ResponseEntity<Object> response = resource.updatePoll(incoming, "slug", "token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Poll updated = (Poll) response.getBody();
        assertEquals("NewTitle", updated.getTitle());
        assertEquals("NewLocation", updated.getLocation());
        assertEquals("NewDescription", updated.getDescription());
        assertTrue(updated.isHas_meal());

        verify(client).setText(eq("pad42"), contains("NewTitle"));
    }
}