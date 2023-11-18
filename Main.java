package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;




public class Main {


    public static void main(String[] args) {
           if (args.length != 2) {
            System.out.println("Usage: java FileConverter <source.xxx> <destination.yyy>");
            return;
        }
         
        String sourceFileName = args[0];
        String destinationFileName = args[1];

        try {
            //This determines the source and destination of the file formats
            String sourceFormat = getFileFormat(sourceFileName);
            String destinationFormat = getFileFormat(destinationFileName);

            //Cheks if its csv or txt
            if (!isValidFormat(sourceFormat) || !isValidFormat(destinationFormat)) {
                System.out.println("Please use formats csv or txt.");
                return;
            }

            //Reads content from the files
            String fileContent = readFileContent(sourceFileName);

            //Write the content to the file
            writeFileContent(destinationFileName, fileContent);

            System.out.println("File conversion completed.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    
    
    private static String getFileFormat(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    private static boolean isValidFormat(String format) {
        return format.equals("csv") || format.equals("txt");
    }

    private static String readFileContent(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private static void writeFileContent(String fileName, String content) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        }
    }
}

