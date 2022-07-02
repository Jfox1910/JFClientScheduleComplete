
package utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Utility class that holds various utilities and methods.
 */
public class Utils {


    /**
     * Parses out the index from comboboxes. Used to get the id more easily and consistantly. I had a lot of
     * issues along the way and this was an easy and quick fix I found.
     * @param string
     * @return 0
     */
    public static int getIdFromComboString(String string) {
        Matcher m = Pattern.compile("^(\\d+)\\s.*").matcher(string);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }return 0;
    }


    /**
     * The following method is used to create and store times for the appointment time combo boxes.
     * They set to EST to be in sync with the office hours.
     * Only business hours are available to use and are added in 15 minute increments.
     */
    private static final ObservableList<LocalTime> startTimeList = FXCollections.observableArrayList();
    private static final ObservableList<LocalTime> endTimeList = FXCollections.observableArrayList();

    private static void generateStartEndTimes(){
        LocalTime hourStart = LocalTime.of(8,0);
        LocalTime hourEnd = LocalTime.of(22,0);
        ZonedDateTime startEST = ZonedDateTime.of(LocalDate.now(),hourStart, ZoneId.of("America/New_York"));
        ZonedDateTime endEST = ZonedDateTime.of(LocalDate.now(), hourEnd, ZoneId.of("America/New_York"));
        LocalTime localStart = startEST.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();
        LocalTime localEnd = endEST.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();

        if(startTimeList.size() == 0 || endTimeList.size() == 0){
            while (localStart.isBefore(localEnd)){
                startTimeList.add(localStart);
                localStart = localStart.plusMinutes(15);
                endTimeList.add(localStart);
            }
        }
    }


    /**
     * stores the start times for retrieval
     * @return startTimeList
     */
    public static ObservableList<LocalTime> getStartTimeList() {
        generateStartEndTimes();
        return startTimeList;
    }


    /**
     * Stores the end times for retrieval
     * @return endTimeList
     */
    public static ObservableList<LocalTime> getEndTimeList() {
        generateStartEndTimes();
        return endTimeList;
    }
}





