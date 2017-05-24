import javax.print.DocFlavor;
import javax.print.attribute.IntegerSyntax;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by denysh on 24/06/2016.
 */
public class RawDataFilters {

    public static RawData TakeOlderOrNewestItemsInDays(RawData RawDataFile, int days, String operation) {

        RawData OutputRawDataFile = new RawData();

        boolean foundAtLeatOnematch = true;
        int iterator = 0;
        Date currentDate = new Date();
        Date currentItemStartTime;
        String sCurrentItemStartTime;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        long msInOneDay = 86400000;
        ListIterator<LinkedHashMap<String, String>> listIterator = RawDataFile.listOfLinesWithKeyAndValue.listIterator();

        try {
            while (listIterator.hasNext()) {

                sCurrentItemStartTime = RawDataFile.listOfLinesWithKeyAndValue.get(iterator).get("SessionGMTStartTime");

                try {
                    currentItemStartTime = dateFormat.parse(sCurrentItemStartTime);
                    long timeDiffInSec = currentDate.getTime() - currentItemStartTime.getTime();

                    if (operation == "<") {

                        if (timeDiffInSec <= days * msInOneDay && timeDiffInSec > 0) {
                            OutputRawDataFile.listOfLinesWithKeyAndValue.add(RawDataFile.listOfLinesWithKeyAndValue.get(iterator));
                            foundAtLeatOnematch = true;
                        }

                    } else if (operation == ">") {

                        if (timeDiffInSec < 0 && Math.abs(timeDiffInSec) <= days * msInOneDay) {
                            OutputRawDataFile.listOfLinesWithKeyAndValue.add(RawDataFile.listOfLinesWithKeyAndValue.get(iterator));
                            foundAtLeatOnematch = true;
                        }

                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                iterator++;
            }

        } catch (IndexOutOfBoundsException e) {
        }

        if (!foundAtLeatOnematch) {
            System.out.println("\nNo one RawData line matches your criteria!");
        }
        return OutputRawDataFile;
    }

    public static RawData TakeItemsWithDurationInmins(RawData RawDataFile, float durationInMin, String operation) {

        RawData OutputRawDataFile = new RawData();

        boolean foundAtLeatOnematch = true;
        int iterator = 0;
        int addedItems = 0;
        float currentItemDurationInSec;
        ListIterator<LinkedHashMap<String, String>> listIterator = RawDataFile.listOfLinesWithKeyAndValue.listIterator();

        try {
            while (listIterator.hasNext()) {

                currentItemDurationInSec = Integer.parseInt(RawDataFile.listOfLinesWithKeyAndValue.get(iterator).get("SessionDuration"));


                if (operation == "<") {

                    if ((currentItemDurationInSec / 60) < durationInMin) {

                        OutputRawDataFile.listOfLinesWithKeyAndValue.add(RawDataFile.listOfLinesWithKeyAndValue.get(iterator));
                        addedItems++;
                        foundAtLeatOnematch = true;
                    }

                } else if (operation == ">") {

                    if ((currentItemDurationInSec / 60) >= durationInMin) {

                        OutputRawDataFile.listOfLinesWithKeyAndValue.add(RawDataFile.listOfLinesWithKeyAndValue.get(iterator));
                        addedItems++;
                        foundAtLeatOnematch = true;
                    }
                } else if (operation == "=") {

                    if ((currentItemDurationInSec / 60) == durationInMin) {

                        OutputRawDataFile.listOfLinesWithKeyAndValue.add(RawDataFile.listOfLinesWithKeyAndValue.get(iterator));
                        addedItems++;
                        foundAtLeatOnematch = true;
                    }
                } else if (operation == "!=") {

                    if ((currentItemDurationInSec / 60) != durationInMin) {

                        OutputRawDataFile.listOfLinesWithKeyAndValue.add(RawDataFile.listOfLinesWithKeyAndValue.get(iterator));
                        addedItems++;
                        foundAtLeatOnematch = true;
                    }
                }

                iterator++;
            }

        } catch (IndexOutOfBoundsException e) {
        }

//        if (addedItems == 0) {
//            System.out.println("\nNo one RawData line matches your criteria!");
//        }

        if (!foundAtLeatOnematch) {
            System.out.println("\nNo one RawData line matches your criteria!");
        }
        return OutputRawDataFile;
    }

    public static RawData ApplyFilter(RawData RawDataFile, String relevantMemberName, String operation, String valueToCompareWith) {

        RawData OutputRawDataFile = new RawData();

        int iterator = 0;
        boolean foundAtLeatOnematch = false;
        ListIterator<LinkedHashMap<String, String>> listIterator = RawDataFile.listOfLinesWithKeyAndValue.listIterator();

        if (relevantMemberName.matches(".*Time.*") || relevantMemberName.matches(".*Date.*")) {

            OutputRawDataFile = TakeOlderOrNewestItemsInDays(RawDataFile, Integer.parseInt(valueToCompareWith), operation);
            foundAtLeatOnematch = true;

        } else if (relevantMemberName.matches(".*Duration.*")) {

            OutputRawDataFile = TakeItemsWithDurationInmins(RawDataFile, Float.parseFloat(valueToCompareWith), operation);
            foundAtLeatOnematch = true;
        } else {

            try {
                while (listIterator.hasNext()) {

                    try {

                        boolean operationIsRecognized = false;
                        long currentValue = Long.parseLong(RawDataFile.listOfLinesWithKeyAndValue.get(iterator).get(relevantMemberName));
                        long doubleValueToCompareWith = Long.parseLong(valueToCompareWith);

                        switch (operation.charAt(0)) {
                            case '=':
                                if (currentValue == doubleValueToCompareWith) {
                                    OutputRawDataFile.listOfLinesWithKeyAndValue.add(RawDataFile.listOfLinesWithKeyAndValue.get(iterator));
                                    foundAtLeatOnematch = true;
                                }
                                operationIsRecognized = true;
                                break;
                            case '>':
                                if (currentValue > doubleValueToCompareWith) {
                                    OutputRawDataFile.listOfLinesWithKeyAndValue.add(RawDataFile.listOfLinesWithKeyAndValue.get(iterator));
                                    foundAtLeatOnematch = true;
                                }
                                operationIsRecognized = true;
                                break;
                            case '<':
                                if (currentValue < doubleValueToCompareWith) {
                                    OutputRawDataFile.listOfLinesWithKeyAndValue.add(RawDataFile.listOfLinesWithKeyAndValue.get(iterator));
                                    foundAtLeatOnematch = true;
                                }
                                operationIsRecognized = true;
                                break;
                            default:
                                if (!operationIsRecognized) {
                                    String equalString = "!=";

                                    if (operation.equals(equalString)) {

                                        if (currentValue != doubleValueToCompareWith) {

                                            OutputRawDataFile.listOfLinesWithKeyAndValue.add(RawDataFile.listOfLinesWithKeyAndValue.get(iterator));
                                            foundAtLeatOnematch = true;
                                        }

                                    }
                                }
                        }
                    } catch (NumberFormatException e) {
                    }

                    iterator++;
                }

            } catch (IndexOutOfBoundsException e) {
            }
        }

        if (!foundAtLeatOnematch) {
            System.out.println("\nNo one RawData line matches your criteria!");
        }
        return OutputRawDataFile;

    }
}
