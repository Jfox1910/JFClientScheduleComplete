
package utils;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Utility class that holds various utilities and methods.
 */
public class Utils {
    private User user;


    public static int getIdFromComboString(String string) {
        Matcher m = Pattern.compile("^(\\d+)\\s.*").matcher(string);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return 0;
    }


    private static final LocalTime hourStart = LocalTime.of(8,0);
    private static final LocalTime hourEnd = LocalTime.of(22,0);

    private static final ObservableList<LocalTime> startTimeList = FXCollections.observableArrayList();
    private static final ObservableList<LocalTime> endTimeList = FXCollections.observableArrayList();

    private static void generateStartEndTimes(){
        ZonedDateTime estStartZDT = ZonedDateTime.of(LocalDate.now(),hourStart, ZoneId.of("America/New_York"));
        ZonedDateTime estEndZDT = ZonedDateTime.of(LocalDate.now(), hourEnd, ZoneId.of("America/New_York"));
        LocalTime localStart = estStartZDT.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();
        LocalTime localEnd = estEndZDT.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();

        if(startTimeList.size() == 0 || endTimeList.size() == 0){
            while (localStart.isBefore(localEnd)){
                startTimeList.add(localStart);
                localStart = localStart.plusMinutes(15);
                endTimeList.add(localStart);
            }
        }
    }

    public static ObservableList<LocalTime> getStartTimeList() {
        generateStartEndTimes();
        return startTimeList;
    }

    public static ObservableList<LocalTime> getEndTimeList() {
        generateStartEndTimes();
        return endTimeList;
    }
}





