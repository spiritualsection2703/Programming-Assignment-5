package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter conversion direction (csv to txt or txt to csv, or 'quit' to exit): ");
        String conversionDirection = scanner.nextLine().toLowerCase();

        if (conversionDirection.equals("quit")) {
            System.out.println("Thank you for using our program!");
            return;
        }

        System.out.print("Enter source file name: ");
        String sourceFileName = scanner.nextLine();

        System.out.print("Enter destination file name: ");
        String destinationFileName = scanner.nextLine();

        try {

            // This determines the source and destination of the file formats
            String sourceFormat = getFileFormat(sourceFileName);
            String destinationFormat = getDestinationFormat(sourceFormat, conversionDirection);

            // Checks if it's csv or txt
            if (!isValidFormat(sourceFormat) || !isValidFormat(destinationFormat)) {
                System.out.println("Please use formats csv or txt.");
                return;
            }

            //This reads content from the source file
            String fileContent = readFileContent(sourceFileName);

            //This writes the content to the destination
            writeFileContent(destinationFileName, fileContent);

            System.out.println("File conversion completed.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
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
                String[] cells = line.split(",");
                for (int i = 0; i < cells.length; i++) {
                    //This checks if the cell has a string
                    if (cells[i].length() > 13) {
                        // Normalize the string value
                        cells[i] = cells[i].substring(0, 10) + "...";
                    }
                }
                //This replaces empty cells with "N/A"
                line = String.join(",", cells).replaceAll(",(?=,)|,$", ",N/A");
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

    private static String getDestinationFormat(String sourceFormat, String conversionDirection) {
        if (conversionDirection.equals("csv to txt")) {
            return "txt";
        } else if (conversionDirection.equals("txt to csv")) {
            return "csv";
        } else {
            System.out.println("Invalid conversion direction. Please use 'csv to txt' or 'txt to csv'.");
            System.exit(1);
            return "";
        }
    }
}
