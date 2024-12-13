package io.reactivestax;

import io.reactivestax.service.FileProcessor;
import io.reactivestax.service.FileReader;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import static io.reactivestax.utility.ApplicationPropertiesUtils.readFromApplicationPropertiesStringFormat;

public class FileRunner {
    public static void main(String[] args) throws IOException {
        String filePath = readFromApplicationPropertiesStringFormat("file.path");
        FileReader fileReader = new FileReader();
        fileReader.readFile(filePath);
        ConcurrentHashMap<String, String> ruleValueSet = fileReader.getRuleValueSet();
        new FileProcessor().processFile(ruleValueSet);
    }
}