package com.sky.weather;

public class CityMapping {
    private String cityNum;
    private String cityName;


    public String getCityNum() {
        return cityNum;
    }


    public void setCityNum(String cityNum) {
        this.cityNum = cityNum;
    }


    public String getCityName() {
        return cityName;
    }


    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public CityMapping() {
    }


    public CityMapping(String cityNum, String cityName) {
        super();
        this.cityNum = cityNum;
        this.cityName = cityName;
    }

}
