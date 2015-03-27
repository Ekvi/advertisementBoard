package com.shribak.board.utils;


import com.shribak.board.model.Advertisement;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

public class TestXmlProvider {
    private FileProvider provider = new XmlProvider();
    private String path;

    @Before
    public void getPath() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("xmlTest.xml");
        File file = new File(url.getPath());
        path = file.getCanonicalPath();
    }


    @Test
    public void read() {
        List<Advertisement> advertisements = provider.read(path);

        assertTrue(!advertisements.isEmpty());
    }

    @Test
    public void write() {
        List<Advertisement> advertisements = provider.read(path);
        advertisements.add(new Advertisement("test", 1427285986884L, "rent", "test title", "test content"));

        provider.write(advertisements);
        List<Advertisement> actual = provider.read(path);

        assertEquals("test title", actual.get(actual.size() - 1).getTitle());
    }
}
