package io.reactivestax;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
List<String> lines = new ArrayList<>();

    public void readFile(String filePath) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null){
                lines.add(line);
            }
        }
    }


    public void parseInProperFormat(){
        lines.forEach(data -> {
//            data.
        });
    }
}
