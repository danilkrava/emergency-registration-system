package model;

import java.sql.Time;
import java.time.LocalTime;

/**
 * Created by Oleksandr on 28.11.2015.
 */
public class Date {
    private int day,month,year;

    public Date (int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year > 1900 && year <= 2015)
            this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        if (day > 0 && day < 32)
            this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        if (month > 0 && month < 13)
            this.month = month;
    }

    public Date getDate() {
        return new Date(day, month, year);
    }
}
