package com.sky.weather;

public class OneDayWeather {
    private WeatherInfo before;// 前12小时
   
    private WeatherInfo after; // 后12小时
    public WeatherInfo getBefore() {
        return before;
    }
    public void setBefore(WeatherInfo before) {
        this.before = before;
    }
    public OneDayWeather() {}
    public OneDayWeather(WeatherInfo before, WeatherInfo after) {
        super();
        this.before = before;
        this.after = after;
    }
    

}
