/**
 * Created by denysh on 24/06/2016.
 */
public class main {

    public static void main(String[] args) {

        RawData SprintSessionIVRRawData = new RawData("C:/Users/denysh/IdeaProjects/rawdata-analyzer_2/InputFiles/SprintSessionIVRRawData.xml", "C:/Users/denysh/IdeaProjects/rawdata-analyzer_2/InputFiles/SprintSessionIVRRawData_TINY_10.csv");

        RawData SprintSessionIVRRawData_Filtered = new RawData();

        SprintSessionIVRRawData.PrintAllLinesFromRawData();

        SprintSessionIVRRawData.ChangeSpecificMemberValue(7, "ChannelTypeID", "2");
//
//        SprintSessionIVRRawData.PrintAllLinesFromRawData();
//
//        System.out.println(SprintSessionIVRRawData.GetSchemaMember(1));
//
//        SprintSessionIVRRawData.PrintAllSchemaMembers();
//
//        SprintSessionIVRRawData.PrintSpecificLineFromRawData(3);

        SprintSessionIVRRawData_Filtered = RawDataFilters.ApplyFilter(SprintSessionIVRRawData,"ChannelTypeID","!=","14");


        SprintSessionIVRRawData_Filtered.PrintAllLinesFromRawData();

//        SprintSessionIVRRawData.


        //RawData SprintSessionIVRRawData_Filtered = new RawData("C:/Users/denysh/IdeaProjects/rawdata-analyzer_2/InputFiles/SprintSessionIVRRawData.xml", "C:/Users/denysh/IdeaProjects/rawdata-analyzer_2/InputFiles/SprintSessionIVRRawData_TINY_10.csv");
//        SprintSessionIVRRawData_Filtered = RawDataFilters.TakeItemsOlderThanXDays(SprintSessionIVRRawData, 2);
//
//        SprintSessionIVRRawData_Filtered.PrintAllLinesFromRawData();

//        SprintSessionIVRRawData.PrintAllSchemaMembers();
//
//        System.out.println();
//        System.out.println(SprintSessionIVRRawData.GetSchemaMember(1));
//
//        SprintSessionIVRRawData.PrintSpecificLineFromRawData(274);
//
//        SprintSessionIVRRawData_Filtered
//
//        SprintSessionIVRRawData_Filtered.ChangeSpecificMemberValue(1, "SystemID", "111");


//        SprintSessionIVRRawData_Filtered.PrintAllLinesFromRawData();
//        System.out.println();
//        SprintSessionIVRRawData_Filtered.ChangeSpecificMemberValue(3, "ChannelTypeID", "2");
//        System.out.println();
//        SprintSessionIVRRawData_Filtered.PrintAllLinesFromRawData();


//        SprintSessionIVRRawData_Filtered = RawDataFilters.TakeItemsWithDurationMoreThanXmins(SprintSessionIVRRawData_Filtered, 3);
//        SprintSessionIVRRawData_Filtered.PrintAllLinesFromRawData();

//        SprintSessionIVRRawData.PrintCountOfLinesInRawData();
//        SprintSessionIVRRawData = RawDataFilters.TakeItemsWithDurationMoreThanXmins(SprintSessionIVRRawData, 3);
//
//
//
//        SprintSessionIVRRawData.PrintCountOfLinesInRawData();
//        SprintSessionIVRRawData.PrintAllLinesFromRawData();

        //SprintSessionIVRRawData.PrintAllLinesFromRawData();


//        SprintSessionIVRRawData_Filtered.PrintAllLinesFromRawData();
//        SprintSessionIVRRawData_Filtered.ChangeSpecificMemberValue(1,"SystemID","111");
//        SprintSessionIVRRawData_Filtered.PrintAllLinesFromRawData();
//
//        SprintSessionIVRRawData_Filtered = RawDataFilters.ApplyFilter(SprintSessionIVRRawData_Filtered,"SessionGMTStartTime", ">","2");
//        SprintSessionIVRRawData_Filtered.PrintAllLinesFromRawData();


    }
}
