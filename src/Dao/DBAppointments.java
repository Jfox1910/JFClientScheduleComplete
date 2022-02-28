package Dao;

import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 Appointment table database access
 */
public final class DBAppointments {

    public static ObservableList<Appointments> getAllAppointments(){
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                int apptIDCol = rs.getInt("Appointment_ID");
                String apptTitleCol = rs.getString("Title");
                String apptDescriptionCol = rs.getString("Description");
                String apptLocationCol = rs.getString("Location");
                int apptContactCol = rs.getInt("Contact_ID");
                String apptTypeCol = rs.getString("Type");
                Timestamp appointmentStartTS = rs.getTimestamp("Start");
                Timestamp appointmentEndTS = rs.getTimestamp("End");
                LocalDateTime apptStartTimeCol = appointmentStartTS.toLocalDateTime();
                LocalDateTime apptEndTimeCol = appointmentEndTS.toLocalDateTime();
                int apptCustomerIDCol = rs.getInt("Customer_ID");
                int apptUserIDCol = rs.getInt("User_ID");
                Appointments appointment = new Appointments(apptIDCol, apptTitleCol, apptDescriptionCol, apptLocationCol, apptContactCol, apptTypeCol, apptStartTimeCol,
                        apptEndTimeCol, apptCustomerIDCol, apptUserIDCol);
                //add appt
                appointments.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointments;
    }
}
