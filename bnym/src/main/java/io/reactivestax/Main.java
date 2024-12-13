package io.reactivestax;

import io.reactivestax.service.FileReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "/Users/Suraj.Adhikari/sources/student-mode-programs/BNYM-file-parser/bnym/src/main/resources/bony_cbna_test_ruleset.data";
        new FileReader().readFile(filePath);
        System.out.println("Hello, World!");
    }
}