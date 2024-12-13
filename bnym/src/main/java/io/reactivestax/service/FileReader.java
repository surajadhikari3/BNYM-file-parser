package io.reactivestax.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class FileReader {

    public void readFile(String filePath) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null){
                parseInProperFormat(line.trim());
            }
        }
    }

    public void parseInProperFormat(String line){
        ConcurrentHashMap<String, String> ruleValueSet = new ConcurrentHashMap<>();
            String key = line.substring(0, 2);
            String value = line.substring(2);
            ruleValueSet.put(key, value);
            System.out.println("key--value " + key + " ," +  value );
    }
}
