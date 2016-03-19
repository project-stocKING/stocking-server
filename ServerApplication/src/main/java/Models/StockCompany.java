package Models;

import java.util.Date;

/**
 * Created by Beata on 2016-03-17.
 */
public class StockCompany {

    private String name;
    private Date date;
    private double startValue;
    private double maxValue;
    private double minValue;
    private double endValue;
    private float volume;


    public StockCompany() {

    }
    public StockCompany(String name, Date date, double startValue, double minValue, double maxValue, double endValue, float volume) {
        this.name = name;
        this.date = date;
        this.startValue = startValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.endValue = endValue;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getStartValue() {
        return startValue;
    }

    public void setStartValue(double startValue) {
        this.startValue = startValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getEndValue() {
        return endValue;
    }

    public void setEndValue(double endValue) {
        this.endValue = endValue;
    }

    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", startValue=" + startValue +
                ", maxValue=" + maxValue +
                ", minValue=" + minValue +
                ", endValue=" + endValue +
                ", volume=" + volume +
                '}';
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
