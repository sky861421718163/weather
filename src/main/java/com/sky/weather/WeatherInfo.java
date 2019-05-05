package com.sky.weather;


public class WeatherInfo {
    private String startTime;// 起报时间
    private String weather;// 天气现象
    private String temperature;// 温度
    private String windDirection;// 风向
    private String windForce; // 风力
    private String img;// 图标 (天气预报图标数据)


    public WeatherInfo() {
    }


    public WeatherInfo(String startTime, String weather, String temperature, String windDirection,
            String windForce, String img) {
        super();
        this.startTime = startTime;
        this.weather = weather;
        this.temperature = temperature;
        this.windDirection = windDirection;
        this.windForce = windForce;
        this.img = img;
    }


    public String getStartTime() {
        return startTime;
    }


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public String getWeather() {
        return weather;
    }


    public void setWeather(String weather) {
        this.weather = weather;
    }


    public String getTemperature() {
        return temperature;
    }


    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }


    public String getWindDirection() {
        return windDirection;
    }


    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }


    public String getWindForce() {
        return windForce;
    }


    public void setWindForce(String windForce) {
        this.windForce = windForce;
    }


    public String getImg() {
        return img;
    }


    public void setImg(String img) {
        this.img = img;
    }

}
