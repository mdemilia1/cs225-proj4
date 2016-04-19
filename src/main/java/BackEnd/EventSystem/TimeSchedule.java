package BackEnd.EventSystem;

import auth.AuthorizationException;
import auth.Operation;
import auth.Permissions;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.lang.IllegalArgumentException;

/**
 *
 * @author Ketty Lezama
 */

public class TimeSchedule {
    private Calendar startDateTime, endDateTime;
    
    public TimeSchedule() {
        startDateTime = new GregorianCalendar();
        endDateTime = new GregorianCalendar();
    }
    
    public TimeSchedule(TimeSchedule timeSchedule){
        try(Permissions.SystemTransaction ignored = Permissions.get().beginSystemTransaction()) {
            startDateTime = timeSchedule.getStartDateTimeCalendar();
            endDateTime = timeSchedule.getEndDateTimeCalendar();
        }
        catch(AuthorizationException ignored){}
    }
    
    public void setStartDateTime(int year, int month, int day, int hour, int minute) throws IllegalArgumentException, AuthorizationException {
        Permissions.get().checkPermission("SUBEVENTS","STARTDATE", Operation.MODIFY);
        if (year >= 2013 && year <= 9999)
            startDateTime.set(Calendar.YEAR, year);
        else
            throw new IllegalArgumentException("Invalid year entered.");
        
        if (month >= 0 && month < 12)
            startDateTime.set(Calendar.MONTH, month - 1);
        else
            throw new IllegalArgumentException("Invalid numerical month entered.");
        
        if (day > 0 && day <= startDateTime.getMaximum(Calendar.DAY_OF_MONTH))
            startDateTime.set(Calendar.DAY_OF_MONTH, day);
        else
            throw new IllegalArgumentException("Invalid numerical day entered.");
        
        if (hour >= 0 && hour < 24)
            startDateTime.set(Calendar.HOUR_OF_DAY, hour);
        else
            throw new IllegalArgumentException("Invalid hour entered.");
        
        if (minute >= 0 && minute < 60)
            startDateTime.set(Calendar.MINUTE, minute);
        else
            throw new IllegalArgumentException("Invalid minute entered.");
    }
    
    public void setStartDateTime(Timestamp startDateTime) throws AuthorizationException{
        Permissions.get().checkPermission("SUBEVENTS","STARTDATE", Operation.MODIFY);
        this.startDateTime.setTimeInMillis(startDateTime.getTime());
    }
    
    public Calendar getStartDateTimeCalendar() throws AuthorizationException {
        Permissions.get().checkPermission("SUBEVENTS","STARTDATE", Operation.VIEW);
        return startDateTime;
    }
    
    public Timestamp getStartDateTimeTimestamp() throws AuthorizationException {
        Permissions.get().checkPermission("SUBEVENTS","STARTDATE", Operation.VIEW);
        return new Timestamp(startDateTime.getTimeInMillis());
    }
    
    public void setEndDateTime(int year, int month, int day, int hour, int minute) throws IllegalArgumentException, AuthorizationException {
        Permissions.get().checkPermission("SUBEVENTS","ENDDATE", Operation.MODIFY);
        if (year >= 2013 && year <= 9999)
            endDateTime.set(Calendar.YEAR, year);
        else
            throw new IllegalArgumentException("Invalid year entered.");
        
        if (month > 0 && month <= 12)
            endDateTime.set(Calendar.MONTH, month - 1);
        else
            throw new IllegalArgumentException("Invalid numerical month entered.");
        
        if (day > 0 && day <= endDateTime.getMaximum(Calendar.DAY_OF_MONTH))
            endDateTime.set(Calendar.DAY_OF_MONTH, day);
        else
            throw new IllegalArgumentException("Invalid numerical day entered.");
        
        if (hour >= 0 && hour < 24)
            endDateTime.set(Calendar.HOUR_OF_DAY, hour);
        else
            throw new IllegalArgumentException("Invalid hour entered.");
        
        if (minute >= 0 && minute < 60)
            endDateTime.set(Calendar.MINUTE, minute);
        else
            throw new IllegalArgumentException("Invalid minute entered.");
    }
    
    public void setEndDateTime(Timestamp endDateTime) throws AuthorizationException {
        Permissions.get().checkPermission("SUBEVENTS","ENDDATE", Operation.MODIFY);
        this.endDateTime.setTimeInMillis(endDateTime.getTime());
    }
    
    public Calendar getEndDateTimeCalendar() throws AuthorizationException {
        Permissions.get().checkPermission("SUBEVENTS","ENDDATE", Operation.VIEW);
        return endDateTime;
    }
    
    public Timestamp getEndDateTimeTimestamp() throws AuthorizationException {
        Permissions.get().checkPermission("SUBEVENTS","ENDDATE", Operation.VIEW);
        return new Timestamp(endDateTime.getTimeInMillis());
    }
    
    public boolean equals(TimeSchedule timeSchedule) throws AuthorizationException {
        if (this.getStartDateTimeCalendar().equals(timeSchedule.getStartDateTimeCalendar()) 
                && this.getEndDateTimeCalendar().equals(timeSchedule.getEndDateTimeCalendar()))
            return true;
        else
            return false;
    }
    
    public String toString() {
        try {
            return "Start Date & Time: " + getStartDateTimeCalendar().get(Calendar.MONTH) + "/" + getStartDateTimeCalendar().get(Calendar.DAY_OF_MONTH) +
                    "/" + getStartDateTimeCalendar().get(Calendar.YEAR) + " " + String.format("%02d", getStartDateTimeCalendar().get(Calendar.HOUR_OF_DAY)) + ":" +
                    String.format("%02d", getStartDateTimeCalendar().get(Calendar.MINUTE)) + "\nEnd Date & Time: " + getEndDateTimeCalendar().get(Calendar.MONTH) +
                    "/" + getEndDateTimeCalendar().get(Calendar.DAY_OF_MONTH) + "/" + getEndDateTimeCalendar().get(Calendar.YEAR) + " " +
                    String.format("%02d", getEndDateTimeCalendar().get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", getEndDateTimeCalendar().get(Calendar.MINUTE));
        }
        catch (AuthorizationException check){
            return "Unauthorized Access";
        }
    }
}