/**
 * Created by denysh on 24/06/2016.
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

public class RawData {

    private String[] schemaMembers;
    protected LinkedList<LinkedHashMap<String, String>> listOfLinesWithKeyAndValue = new LinkedList<LinkedHashMap<String, String>>();
    private List<String[]> listOfLinesInInputFile = new LinkedList<String[]>();

    RawData() {
    }

    RawData(String pathToXMLSchema, String pathToCSVInputFile) {

        GenerateRawDataFile(pathToXMLSchema, pathToCSVInputFile);

    }

    private void GenerateRawDataFile(String pathToXMLSchema, String pathToCSVInputFile) {

        LinkedHashMap<String, String> line;

        schemaMembers = ParseXMLToSchemaMembers(pathToXMLSchema);
        listOfLinesInInputFile = ParseCSVToListOfLines(pathToCSVInputFile);

        int iterator = 0;

        ListIterator<String[]> listIterator = listOfLinesInInputFile.listIterator();

        try {
            while (listIterator.hasNext()) {

                int arrayLenght = listOfLinesInInputFile.get(iterator).length;
                line = new LinkedHashMap<String, String>();
                for (int i = 0; i < arrayLenght; i++) {

                    line.put(schemaMembers[i], listOfLinesInInputFile.get(iterator)[i]);
                }

                iterator++;
                //currentLineMembers = null;

                // Print all keys and related values
//        for (Map.Entry entry : line.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }

                listOfLinesWithKeyAndValue.add(line);
            }
        } catch (IndexOutOfBoundsException e) {
        }

    }

    private String[] ParseXMLToSchemaMembers(String pathToXMLSchema) {

        String[] currentSchema = new String[0];

        try {

            File fXmlFile = new File(pathToXMLSchema);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("member");
            currentSchema = new String[nList.getLength()];

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                //System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    currentSchema[temp] = eElement.getAttribute("name");
                    //System.out.println(schema[temp]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentSchema;
    }

    private List<String[]> ParseCSVToListOfLines(String pathToCSVFile) {

        BufferedReader br = null;
        String line = "";
        String csvSplitBy = "\\|";

        List<String[]> listOfLinesInInputFile = new LinkedList<String[]>();

        try {

            br = new BufferedReader(new FileReader(pathToCSVFile));
            while ((line = br.readLine()) != null) {

                // use pipe as separator
                String[] currentLine = line.split(csvSplitBy);
                listOfLinesInInputFile.add(currentLine);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return listOfLinesInInputFile;
    }

    public void PrintCountOfLinesInRawData() {
        System.out.println("\nRawData consists from " + listOfLinesWithKeyAndValue.size() + " lines.");
    }

    public void PrintAllLinesFromRawData() {

        System.out.println();
        for (int iterator = 0; iterator < listOfLinesWithKeyAndValue.size(); iterator++) {
            System.out.println(listOfLinesWithKeyAndValue.get(iterator));
        }

    }

    public void PrintSpecificLineFromRawData(int numberOfLine) {

        int number = Math.abs(numberOfLine);

        if (number > 0 && number <= listOfLinesWithKeyAndValue.size())
            System.out.println("\n" + listOfLinesWithKeyAndValue.get(number - 1));

    }

    public void PrintAllSchemaMembers() {

        System.out.println("\nSchema consists from the following members (in the same order):");
        for (int iterator = 0; iterator < schemaMembers.length; iterator++) {

            System.out.println(iterator + 1 + " --> " + schemaMembers[iterator]);

        }
    }

    public String GetSchemaMember(int memberNumber) {

        int number = Math.abs(memberNumber);

        if (number > 0 && number <= schemaMembers.length) {
            return schemaMembers[number - 1];
        } else return null;
    }

    public void ChangeSpecificMemberValue(int line, String relevantMemberName, String valueByWhichToReplace) {

        if (line <= 0 || line > listOfLinesWithKeyAndValue.size()) {
            System.out.println("Line " + line + " doesn't exist! RawData has --> " + listOfLinesWithKeyAndValue.size() + " lines");

        } else {

            if (listOfLinesWithKeyAndValue.get(Math.abs(line - 1)).get(relevantMemberName) != null) {

                System.out.print("\nThe following value --> '" + listOfLinesWithKeyAndValue.get(Math.abs(line - 1)).get(relevantMemberName) + "' in line " + line);

                listOfLinesWithKeyAndValue.get(Math.abs(line - 1)).put(relevantMemberName, valueByWhichToReplace);

                System.out.println(" was updated with a new one --> '" + listOfLinesWithKeyAndValue.get(Math.abs(line - 1)).get(relevantMemberName) + "'");

            } else

                System.out.println("Nothing was updated as you put the wrong key!");
        }
    }

}
