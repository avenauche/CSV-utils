package csvReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

public class CsvReader {

    private static final String DELIMITER = ",";
    private String delimiter = "";
    private File inFile;
    private String replaceEmptyWith = "";
    private String outputAs = "";
    private ArrayList<ArrayList> outputArrayList;
    private HashMap outputMap;
    private ArrayList<String> outputHeader;

    public CsvReader() {
        System.out.println("No Input File.");
        System.exit(0);
    }

    public CsvReader(String fileName) {
        this.inFile = new File(fileName);
        readCSV();
    }

    public CsvReader(String fileName, String replaceEmptyWith) {
        this.inFile = new File(fileName);
        this.replaceEmptyWith = replaceEmptyWith;
        readCSV();
    }

    public CsvReader(String fileName, String replaceEmptyWith, String outputAs) {
        this.inFile = new File(fileName);
        this.replaceEmptyWith = replaceEmptyWith;
        this.outputAs = outputAs;
        readCSV();
    }

    public CsvReader(File inFile) {
        this.inFile = inFile;
        readCSV();
    }

    public CsvReader(File inFile, String replaceEmptyWith) {
        this.inFile = inFile;
        this.replaceEmptyWith = replaceEmptyWith;
        readCSV();
    }

    public CsvReader(File inFile, String replaceEmptyWith, String outputAs) {
        this.inFile = inFile;
        this.replaceEmptyWith = replaceEmptyWith;
        this.outputAs = outputAs;
        readCSV();
    }

    private void readCSV() {
        outputArrayList = new ArrayList<ArrayList>();
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(inFile));
            try {
                while ((line = br.readLine()) != null) {
                    if (delimiter.length() == 0) {
                        delimiter = DELIMITER;
                    }
                    String replacedLine = line.replaceAll(",,", "," + replaceEmptyWith + ",");
                    Pattern pattern = Pattern.compile(",");
                    String[] cols = pattern.split(replacedLine);
                    ArrayList<String> rowsData = new ArrayList<String>();
                    rowsData.addAll(Arrays.asList(cols));
                    outputArrayList.add(rowsData);
                }
            } catch (IOException ex) {
                System.out.println("Cannot Read from CSV file: " + ex.getMessage());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("CSV file not found: " + ex.getMessage());
        }
    }

    public Object output() {
        if (outputAs.equalsIgnoreCase("") || outputAs.equalsIgnoreCase("arraylist")) {
            return (Object) outputArrayList;
        } else if (outputAs.equalsIgnoreCase("map")) {
            return (Object) convArr2Map();
        } else if (outputAs.equalsIgnoreCase("header")) {
        } else {
            System.out.println("invalid output format ");
        }

        // command never reaches here
        return (Object) "";
    }

    private HashMap convArr2Map() {
        int index = 0;
        outputMap = new HashMap();
        try {
            for (Iterator<ArrayList> it = this.outputArrayList.iterator(); it.hasNext();) {
                ArrayList list = it.next();
                outputMap.put(index, list);
                index++;
            }
        } catch (Exception e) {
            System.out.println("cannot return a Map Object. " + e.getMessage());
        }

        return outputMap;
    }

    public ArrayList transpose2() {
        int rowLength = outputArrayList.size();
        int colLength = getMaxColLength(rowLength);

        ArrayList<ArrayList> cols = new ArrayList<ArrayList>();

        // create arraylists 
        for (int listCount = 0; listCount < cols.size(); listCount++) {
            cols.add(listCount, new ArrayList());
        }

        for (int srcListCount = 0; srcListCount < rowLength; srcListCount++) {
            ArrayList srcValues = outputArrayList.get(srcListCount);

            int index = 0;
            for (Iterator it = srcValues.iterator(); it.hasNext(); index++) {
                ((ArrayList) cols.get(index)).add(srcListCount, it.next());
            }


        }
        return cols;
    }

    public void transpose() {
        int rowLength = outputArrayList.size();
        int colLength = getMaxColLength(rowLength);

        ArrayList[] cols = new ArrayList[colLength];

        // create arraylists 
        for (int listCount = 0; listCount < cols.length; listCount++) {
            cols[listCount] = new ArrayList();
        }

        for (int srcListCount = 0; srcListCount < rowLength; srcListCount++) {
            ArrayList srcValues = outputArrayList.get(srcListCount);

            for (int i = 0; i < srcValues.size(); i++) {
                cols[i].add(srcListCount, srcValues.get(i));
            }

        }

        for (int i = 0; i < cols.length; i++) {
            System.out.println(cols[i]);
        }
        
    }

    private int getMaxColLength(int rowLength) {
        ArrayList<Integer> colSizes = new ArrayList();

        for (int rowIndex = 0; rowIndex < rowLength; rowIndex++) {
            colSizes.add(rowIndex, outputArrayList.get(rowIndex).size());
        }

        return Integer.parseInt(Collections.max(colSizes).toString());
    }

    @Override
    public String toString() {
        return outputArrayList.toString();
    }
}
