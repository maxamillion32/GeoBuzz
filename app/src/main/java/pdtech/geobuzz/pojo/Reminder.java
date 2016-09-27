package pdtech.geobuzz.pojo;

/**
 * Created by parth on Sep 20, 2016.
 */

public class Reminder {
    String reminderName;
    String latlng;

    public Reminder() {
    }

    public Reminder(String reminderName, String latlng) {
        this.reminderName = reminderName;
        this.latlng = latlng;
    }

    public String getReminderName() {
        return reminderName;
    }

    public String getLatlng() {
        return latlng;
    }
}

