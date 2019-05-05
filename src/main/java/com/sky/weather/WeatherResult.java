package com.sky.weather;

import java.util.HashMap;
import java.util.Map;


public class WeatherResult<T> {
    private String upTime;
    private Map<String, T> cityWeathers = new HashMap<String, T>();


    public Map<String, T> getCityWeathers() {
        return cityWeathers;
    }


    public void setCityWeathers(Map<String, T> cityWeathers) {
        this.cityWeathers = cityWeathers;
    }


    public void addCityWather(String key, T weather) {
        this.cityWeathers.put(key, weather);
    }


    public String getUpTime() {
        return upTime;
    }


    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

}
