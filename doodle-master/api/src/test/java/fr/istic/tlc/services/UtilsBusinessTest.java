package fr.istic.tlc.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

class UtilsBusinessTest {

    @Test
    void generateSlug_shouldRespectLengthAndCharset() {
        String slug = Utils.getInstance().generateSlug(24);

        assertNotNull(slug);
        assertEquals(24, slug.length());
        assertTrue(slug.matches("[abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890]+"));
    }

    @Test
    void intersect_shouldReturnFalseWhenAnyDateIsNull() {
        Date now = new Date();
        assertFalse(Utils.getInstance().intersect(null, now, now, now));
        assertFalse(Utils.getInstance().intersect(now, null, now, now));
        assertFalse(Utils.getInstance().intersect(now, now, null, now));
        assertFalse(Utils.getInstance().intersect(now, now, now, null));
    }

    @Test
    void intersect_shouldReturnTrueWhenIntervalsOverlap() {
        Calendar cal = Calendar.getInstance();
        Date s1 = cal.getTime();
        cal.add(Calendar.HOUR, 2);
        Date e1 = cal.getTime();

        cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 1);
        Date s2 = cal.getTime();
        cal.add(Calendar.HOUR, 2);
        Date e2 = cal.getTime();

        assertTrue(Utils.getInstance().intersect(s1, e1, s2, e2));
    }

    @Test
    void intersect_shouldReturnFalseWhenIntervalsOnlyTouch() {
        Calendar cal = Calendar.getInstance();
        Date s1 = cal.getTime();
        cal.add(Calendar.HOUR, 1);
        Date e1 = cal.getTime();

        Date s2 = e1;
        cal.add(Calendar.HOUR, 1);
        Date e2 = cal.getTime();

        assertFalse(Utils.getInstance().intersect(s1, e1, s2, e2));
    }
}