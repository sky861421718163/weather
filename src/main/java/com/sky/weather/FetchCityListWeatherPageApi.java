package com.sky.weather;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;



public class FetchCityListWeatherPageApi {
    public static final String CITY_LIST_URL = "http://www.weather.com.cn/static/html/weather_list.shtml";
    private static Date publishTime = new Date();


    public static List<CityWeather<OneDayWeather>> getAllWeather() throws Exception {
        List<CityWeather<OneDayWeather>> result = new ArrayList<CityWeather<OneDayWeather>>();

        List<CityMapping> citys = readCityListFromUrl();
        Gson gson = new Gson();

        for (CityMapping cityMapping : citys) {
            String cityNo = cityMapping.getCityNum();
            String cityName = cityMapping.getCityName();
            CityWeather<OneDayWeather> cityWeather = parseCity(cityNo, cityName);
            result.add(cityWeather);
            System.out.println("城市[" + cityName + "]天气信息解析完毕 :\n" + gson.toJson(cityWeather));
        }

        return result;
    }


    private static OneDayWeather parsetodayWeatherInfo(Element infos, int upTime) {
        OneDayWeather result = null;
        if (infos != null) {
            org.jsoup.select.Elements e2 = infos.getElementsByAttribute("data-dn");
            String weatherIcon0 = "";
            String weatherIcon1 = "";
            String weatherZhuangkuang0 = "";
            String weatherWendu0 = "";
            String weatherFengxiang0 = "";
            String weatherFengli0 = "";
            String weatherZhuangkuang1 = "";
            String weatherWendu1 = "";
            String weatherFengxiang1 = "";
            String weatherFengli1 = "";
            String publishStr0 = "";
            String publishStr1 = "";
            // 拼装 最近12 以及24小时的日期格式.
            if (upTime > 0) {

                publishStr0 = calculateTime(upTime, 0, 0);
                publishStr1 = calculateTime(upTime, 1, 0);
            }
            else {
                System.out.println("Time Error ");
                return result;
            }
            if (e2 != null && e2.size() == 2) {
                // 图标
                weatherIcon0 = parserIcons(e2.get(0).getElementsByTag("big").first());
                // 天气
                Elements weatherZhuangkuangTmp = e2.get(0).getElementsByAttributeValue("class", "wea");
                if (weatherZhuangkuangTmp != null) {
                    weatherZhuangkuang0 = weatherZhuangkuangTmp.text();
                }
                else {
                    System.out.println("today  weatherZhuangkuang0 Error ");
                    return result;
                }
                // 温度
                weatherWendu0 =
                        e2.get(0).getElementsByAttributeValue("class", "tem").get(0).getElementsByTag("span")
                            .text();
                Elements wins =
                        e2.get(0).getElementsByAttributeValue("class", "win").get(0).getElementsByTag("span");
                if (wins != null && wins.size() > 0) {
                    Element win = wins.get(0);
                    weatherFengxiang0 = win.attr("title");
                    weatherFengli0 = win.text();
                }

                weatherIcon1 = parserIcons(e2.get(1).getElementsByTag("big").first());
                Elements weatherZhuangkuangTmp2 = e2.get(1).getElementsByAttributeValue("class", "wea");
                if (weatherZhuangkuangTmp2 != null) {
                    weatherZhuangkuang1 = weatherZhuangkuangTmp2.text();
                }
                else {
                    System.out.println("today  weatherZhuangkuang1 Error ");
                    return result;
                }
                // 温度
                weatherWendu1 =
                        e2.get(1).getElementsByAttributeValue("class", "tem").get(0).getElementsByTag("span")
                            .text();
                Elements wins2 =
                        e2.get(1).getElementsByAttributeValue("class", "win").get(0).getElementsByTag("span");
                if (wins != null && wins2.size() > 0) {
                    Element win = wins2.get(0);
                    weatherFengxiang1 = win.attr("title");
                    weatherFengli1 = win.text();
                }

            }
            else {
                System.out.println("today Error ");
                return result;
            }

            WeatherInfo info0 =
                    new WeatherInfo(publishStr0, weatherZhuangkuang0, weatherWendu0, weatherFengxiang0,
                        weatherFengli0, weatherIcon0);
            WeatherInfo info1 =
                    new WeatherInfo(publishStr1, weatherZhuangkuang1, weatherWendu1, weatherFengxiang1,
                        weatherFengli1, weatherIcon1);

            result = new OneDayWeather(info0, info1);
            return result;
        }
        else {
            System.out.println("today Error ");
            return result;
        }

    }


    /**
     * 
     * @param infos
     * @param upTime
     * @param dayoffset
     *            1== tomorrow ;
     * @return
     */
    private static OneDayWeather parseto23WeatherInfo(Element infos, int upTime, int dayoffset) {
        if (infos != null) {
            Element tommorow = infos.getElementsByAttributeValue("data-dn", "23d" + (dayoffset + 1)).get(0);// 23d2
            /**
             * <pre>
             * Element tommorow2 = infos.getElementsByAttributeValue(&quot;data-dn&quot;, &quot;23d&quot; + (dayoffset + 2)).get(0);// 23d3
             * </pre>
             */
            return parserWeatherInfo(tommorow, upTime, dayoffset);

        }
        else {
            System.out.println(" Error dayoffset=" + 1);
        }
        return null;
    }


    private static OneDayWeather parserWeatherInfo(Element infos, int upTime, int dayoffset) {
        OneDayWeather result = null;
        if (infos != null) {
            org.jsoup.select.Elements e2 = infos.getElementsByTag("ul").get(0).getElementsByTag("li");
            String weatherIcon0 = "";
            String weatherIcon1 = "";
            String weatherZhuangkuang0 = "";
            String weatherWendu0 = "";
            String weatherFengxiang0 = "";
            String weatherFengli0 = "";
            String weatherZhuangkuang1 = "";
            String weatherWendu1 = "";
            String weatherFengxiang1 = "";
            String weatherFengli1 = "";
            String publishStr0 = "";
            String publishStr1 = "";
            // 拼装 最近12 以及24小时的日期格式.
            if (upTime > 0) {

                publishStr0 = calculateTime(upTime, 0, dayoffset);
                publishStr1 = calculateTime(upTime, 1, dayoffset);
            }
            else {
                System.out.println("Time Error ");
                return result;
            }
            if (e2 != null && e2.size() == 2) {
                for (Element day : e2) {
                    String danight = day.getElementsByTag("h1").first().text();
                    if (danight.trim().equals("白天")) {
                        // 图标
                        weatherIcon0 = parserIcons(day.getElementsByTag("big").first());
                        // 天气
                        Elements weatherZhuangkuangTmp = day.getElementsByAttributeValue("class", "wea");
                        if (weatherZhuangkuangTmp != null) {
                            weatherZhuangkuang0 = weatherZhuangkuangTmp.text();
                        }
                        else {
                            System.out.println("today  weatherZhuangkuang0 Error ");
                            return result;
                        }
                        // 温度
                        weatherWendu0 =
                                day.getElementsByAttributeValue("class", "tem").get(0)
                                    .getElementsByTag("span").text();
                        Elements wins =
                                day.getElementsByAttributeValue("class", "win").get(0)
                                    .getElementsByTag("span");
                        if (wins != null && wins.size() > 0) {
                            Element win = wins.get(0);
                            weatherFengxiang0 = win.attr("title");
                            weatherFengli0 = win.text();
                        }

                    }
                    else if (danight.trim().equals("夜间")) {
                        weatherIcon1 = parserIcons(day.getElementsByTag("big").first());
                        Elements weatherZhuangkuangTmp = day.getElementsByAttributeValue("class", "wea");
                        if (weatherZhuangkuangTmp != null) {
                            weatherZhuangkuang1 = weatherZhuangkuangTmp.text();
                        }
                        else {
                            System.out.println("today  weatherZhuangkuang1 Error ");
                            return result;
                        }
                        // 温度
                        weatherWendu1 =
                                day.getElementsByAttributeValue("class", "tem").get(0)
                                    .getElementsByTag("span").text();
                        Elements wins =
                                day.getElementsByAttributeValue("class", "win").get(0)
                                    .getElementsByTag("span");
                        if (wins != null && wins.size() > 0) {
                            Element win = wins.get(0);
                            weatherFengxiang1 = win.attr("title");
                            weatherFengli1 = win.text();
                        }
                    }
                    else {
                        System.out.println("today  data-dn Error ");
                        return result;
                    }
                }

            }
            else {
                System.out.println("today Error ");
                return result;
            }

            // result =
            //
            // "<预报 起报时间=\"" + publishStr0 + "\">" + "\n" + "<天气现象>" +
            // weatherZhuangkuang0 + "</天气现象>"
            // + "\n" + "<温度>" + weatherWendu0 + "</温度>" + "\n" + "<风向>" +
            // weatherFengxiang0
            // + "</风向>" + "\n" + "<风力>" + weatherFengli0 + "</风力>" + "\n" +
            // "<图标>"
            // + weatherIcon0 + "</图标>" + "\n" + "</预报>" + "\n" + "<预报 起报时间=\""
            // + publishStr1
            // + "\">" + "\n" + "<天气现象>" + weatherZhuangkuang1 + "</天气现象>" +
            // "\n" + "<温度>"
            // + weatherWendu1 + "</温度>" + "\n" + "<风向>" + weatherFengxiang1 +
            // "</风向>" + "\n"
            // + "<风力>" + weatherFengli1 + "</风力>" + "\n" + "<图标>" +
            // weatherIcon1 + "</图标>"
            // + "\n" + "</预报>" + "\n";
            WeatherInfo info0 =
                    new WeatherInfo(publishStr0, weatherZhuangkuang0, weatherWendu0, weatherFengxiang0,
                        weatherFengli0, weatherIcon0);
            WeatherInfo info1 =
                    new WeatherInfo(publishStr1, weatherZhuangkuang1, weatherWendu1, weatherFengxiang1,
                        weatherFengli1, weatherIcon1);

            result = new OneDayWeather(info0, info1);

            return result;
        }
        else {
            System.out.println("today Error ");
            return result;
        }

    }


    private static String parserIcons(Element big) {
        String classStr = big.attr("class");
        if (classStr != null && classStr.length() > 0 && (classStr.contains("d") || classStr.contains("n"))) {
            String[] classes = classStr.split(" ");
            if (classes.length == 2) {
                String tmpIcons = classes[1];

                if (tmpIcons.contains("d")) {
                    return tmpIcons.trim();
                }
                else if (tmpIcons.contains("n")) {
                    return tmpIcons.trim();
                }
                else {
                    System.out.println("Icons is not D or N  Error  ");
                    return null;
                }
            }
            else {
                System.out.println("Icons class Size Error  ");
                return null;
            }

        }
        else {
            System.out.println("Icons class is Empty Error  ");
            return null;
        }

    }


    public static CityWeather<OneDayWeather> parseCity(String cityNo, String cityName) {
        String todayCityUrl = "http://www.weather.com.cn/weather1d/" + cityNo + ".shtml";
        String tommorrowCityUrl = "http://www.weather.com.cn/weather2d/" + cityNo + ".shtml";
        CityWeather<OneDayWeather> result = null;
        Document docToday = null;
        Document docTomorrow = null;
        try {
            docToday = Jsoup.connect(todayCityUrl).get();
            docTomorrow = Jsoup.connect(tommorrowCityUrl).get();
            String updateTimeStr = "";
            if (docToday != null && docTomorrow != null && docToday.getElementById("today") != null
                    && docTomorrow.getElementById("2_3d") != null) {
                Elements upEs = docToday.getElementsByAttributeValue("class", "updataTime clearfix");
                if (upEs != null && upEs.size() > 0) {

                    Elements tmpUpTime = upEs.select(" p > em");// ("style",
                                                                // "float:right;");
                    updateTimeStr = tmpUpTime.text();
                }
                else {
                    upEs = docToday.getElementsByAttributeValue("class", "updataTime");
                    updateTimeStr = upEs.get(0).text();
                }

                String updateTime = updateTimeStr.substring(0, updateTimeStr.indexOf("更新"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                result = new CityWeather<OneDayWeather>();
                Date date = dateFormat.parse(updateTime);
                result.setUpTime(updateTime);
                Calendar canlandar = Calendar.getInstance();
                canlandar.setTime(date);
                int upHour = 0;
                upHour = canlandar.get(Calendar.HOUR_OF_DAY);
                result.setDayNum(3);
                result.setDayx1(parsetodayWeatherInfo(docToday.getElementById("today"), upHour));
                result.setDayx2(parseto23WeatherInfo(docTomorrow.getElementById("2_3d"), upHour, 1));
                result.setDayx3(parseto23WeatherInfo(docTomorrow.getElementById("2_3d"), upHour, 2));

                return result;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("==============" + result);
        return result;
    }


    private static String calculateTime(int fchh, int offset, int dayOffset) {
        String result = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String dateStr = dateFormat.format(date);

            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (fchh < 12) {
                publishTime = timeFormat.parse(dateStr + " 08:00:00");
            }
            else {
                publishTime = timeFormat.parse(dateStr + " 20:00:00");
            }
            Calendar canlandar = Calendar.getInstance();
            canlandar.setTime(publishTime);

            canlandar.add(Calendar.DATE, dayOffset);
            canlandar.add(Calendar.HOUR, offset * 12);

            result = timeFormat.format(canlandar.getTime());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static List<CityMapping> readCityListFromFile() throws Exception {
        List<CityMapping> result = new ArrayList<CityMapping>();
        String citys = FileUtils.readFileToString(new File("weather_cityList.properties"), "gbk");
        String[] city = citys.split("\r\n");
        for (int i = 0; i < city.length; i++) {
            String[] str = city[i].split("=");
            String cityNo = str[0];
            String cityName = str[1];
            System.out.println(cityNo + "," + cityName);
            result.add(new CityMapping(cityNo, cityName));

        }
        return result;
    }


    public static List<CityMapping> readCityListFromUrl() throws Exception {
        List<CityMapping> result = new ArrayList<CityMapping>();
        Document citys = null;
        citys = Jsoup.connect(CITY_LIST_URL).get();
        Elements cs = citys.getElementsByClass("tmapin_citylist");
        for (Element e : cs) {
            if (e != null) {
                Elements lis = e.getElementsByTag("li");
                for (Element li : lis) {
                    if (li != null) {
                        Elements as = li.getElementsByTag("a");
                        if (as != null && as.get(0) != null) {
                            // <a
                            // href="http://www.weather.com.cn/weather/101221701.shtml"
                            // target="_blank">池州</a>
                            String url = as.get(0).attr("href");
                            if (url != null) {
                                String cityNum =
                                        url.substring("http://www.weather.com.cn/weather/".length(),
                                            url.length() - ".shtml".length());
                                String name = as.get(0).text();
                                if (cityNum != null && name != null) {
                                    CityMapping cm = new CityMapping();
                                    cm.setCityName(name);
                                    cm.setCityNum(cityNum);
                                    result.add(cm);
                                }
                            }

                        }
                    }
                }
            }
        }

        return result;
    }


    public static void main(String[] args) throws Exception {
        getAllWeather();
    }
}
