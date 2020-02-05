package com.example.myruns3;

public class Entry {


    public String input_type;
    public String activity_type;

    public int duration;
    public int distance;
    public int calories;
    public int heart_rate;
    public String comment;

    public String date;
    public String time;

    // The entries entered into the database
    public Entry(String input_type, String activity_type, int duration, int distance, int calories, int heart_rate, String comment, int[] date, int[] time) {

        this.input_type = input_type;
        this.activity_type = activity_type;

        this.duration = duration;
        this.distance = distance;
        this.calories = calories;
        this.heart_rate = heart_rate;
        this.comment = comment;

        this.date = "" + date[0] + "/" + date[1] + "/" + date[2];
        this.time = "" + time[0] + ":" + time[1];
    }


    // The entries returned from the database
    public Entry(String input_type, String activity_type, int duration, int distance, int calories, int heart_rate, String comment, String date, String time) {

        this.input_type = input_type;
        this.activity_type = activity_type;

        this.duration = duration;
        this.distance = distance;
        this.calories = calories;
        this.heart_rate = heart_rate;
        this.comment = comment;

        this.date = date;
        this.time = time;
    }



    public String toString() {

        return  input_type + ": " + activity_type + ", " + time + " " + date + "\n"
                + "calories burned: " + calories + ", " + "avg. heart rate: " + heart_rate + "\n"
                + distance + " Miles, " + time + " secs";
    }
}
