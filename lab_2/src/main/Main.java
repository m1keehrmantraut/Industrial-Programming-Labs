package main;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));

            String inputString = reader.readLine();
            String delimiters = reader.readLine();
            reader.close();

            StringProcessor stringProcessor = new StringProcessor(inputString, delimiters);

            String result = stringProcessor.process();


            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            writer.write(result);
            writer.close();

        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}