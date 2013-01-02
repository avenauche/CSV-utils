package CSV;

import csvReader.CsvReader;
import java.io.File;

public class CSV {

    public static String read(String fileName) {
        return new CsvReader(fileName).toString();
    }

    public static String read(File inFile) {
        return new CsvReader(inFile).toString();
    }

    public static String read(String fileName, String replaceEmptyWith) {
        return new CsvReader(fileName, replaceEmptyWith).toString();
    }

    public static String read(File inFile, String replaceEmptyWith) {
        return new CsvReader(inFile, replaceEmptyWith).toString();
    }

    public static Object read(String fileName, String replaceEmptyWith, String outputAs) {
        return (new CsvReader(fileName, replaceEmptyWith, outputAs)).output();
    }

    public static Object read(File inFile, String replaceEmptyWith, String outputAs) {
        return new CsvReader(inFile, replaceEmptyWith, outputAs).output();
    }
}
