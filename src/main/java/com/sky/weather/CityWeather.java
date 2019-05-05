package com.sky.weather;

public class CityWeather<T> {
    private int dayNum = 1; // 包含最近几天的天气
    private String upTime;
    private String cityName;
    private String citNum;
    private T dayx1;
    private T dayx2;
    private T dayx3;
    private T dayx4;
    private T dayx5;
    private T dayx6;
    private T dayx7;


    public T getDayx1() {
        return dayx1;
    }


    public void setDayx1(T dayx1) {
        this.dayx1 = dayx1;
    }


    public T getDayx2() {
        return dayx2;
    }


    public void setDayx2(T dayx2) {
        this.dayx2 = dayx2;
    }


    public T getDayx3() {
        return dayx3;
    }


    public void setDayx3(T dayx3) {
        this.dayx3 = dayx3;
    }


    public T getDayx4() {
        return dayx4;
    }


    public void setDayx4(T dayx4) {
        this.dayx4 = dayx4;
    }


    public T getDayx5() {
        return dayx5;
    }


    public void setDayx5(T dayx5) {
        this.dayx5 = dayx5;
    }


    public T getDayx6() {
        return dayx6;
    }


    public void setDayx6(T dayx6) {
        this.dayx6 = dayx6;
    }


    public T getDayx7() {
        return dayx7;
    }


    public void setDayx7(T dayx7) {
        this.dayx7 = dayx7;
    }


    public int getDayNum() {
        return dayNum;
    }


    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }


    public void setUpTime(String updateTime) {
        this.upTime = updateTime;

    }


    public String getUpTime() {
        return this.upTime;
    }


    public String getCityName() {
        return cityName;
    }


    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public String getCitNum() {
        return citNum;
    }


    public void setCitNum(String citNum) {
        this.citNum = citNum;
    }

}
