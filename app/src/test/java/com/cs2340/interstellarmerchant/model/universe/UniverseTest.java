package com.cs2340.interstellarmerchant.model.universe;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("ALL")
public class UniverseTest {
    @Test
    public void serializeTest() {
        String serialization = null;
        try {
             serialization = generateUniverse().serialize();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assertThat("No error while serializing", serialization != null);
        assertThat("Serialization has length", !serialization.isEmpty());

    }

    public static Universe generateUniverse() throws IOException {
        InputStream fileStream  = new FileInputStream(
                new File("src/main/assets/universe/universe.xml"));
        return Universe.generateUniverse(fileStream);
    }
}
