package org.example.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader extends Properties {

    public PropertiesLoader(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            this.load(reader);
        }
    }
}
