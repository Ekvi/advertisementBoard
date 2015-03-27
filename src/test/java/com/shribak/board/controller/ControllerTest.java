package com.shribak.board.controller;


import com.shribak.board.model.Advertisement;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ControllerTest {
    private Controller controller;

    @Before
    public void init() {
        controller = new Controller();

        controller.addAdvertisement("Bill", "3", "Bill's title", "Bill's content for advertisement");
        controller.addAdvertisement("Bobby", "2", "Bobby's title", "Bobby's content for advertisement");
        controller.addAdvertisement("Peter", "3", "Peter's title", "Peter's content for advertisement");
        controller.addAdvertisement("Kimmy", "4", "Kimmy's title", "Kimmy's content for advertisement");
        controller.addAdvertisement("Walter", "1", "Walter's title", "Walter's content for advertisement");
    }

    @Test
    public void testGetAdvertisementsByName() {
        List<Advertisement> billAdvertisements = controller.getAdvertisementsByName("Bill");

        assertEquals(1, billAdvertisements.size());
    }

    @Test
    public void testGetAdvertisementsByCategory() {
        List<Advertisement> rentCategory = controller.getAdvertisementsByCategory("3");
        List<Advertisement> emptyCategory = controller.getAdvertisementsByCategory("5");

        assertEquals(2, rentCategory.size());
        assertTrue(emptyCategory.isEmpty());
    }

    @Test
    public void testAddAdvertisement() {
        int oldSize = controller.getAdvertisements().size();
        controller.addAdvertisement("Richard", "5", "Richard's title", "Richard's content for advertisement");

        assertEquals(oldSize + 1, controller.getAdvertisements().size());
    }

    @Test
    public void testFindByTitle() {
        assertNotNull(controller.findByTitle("Kimmy", "Kimmy's title"));
        assertNull(controller.findByTitle("Kimmy", "Fake title"));
    }

    @Test
    public void testRemoveAdvertisement() {
        int oldSize = controller.getAdvertisements().size();
        controller.removeAdvertisement(controller.getAdvertisements().get(3));

        assertEquals(oldSize - 1, controller.getAdvertisements().size());
        assertEquals("Walter", controller.getAdvertisements().get(controller.getAdvertisements().size() - 1).getAuthor());
    }
}
