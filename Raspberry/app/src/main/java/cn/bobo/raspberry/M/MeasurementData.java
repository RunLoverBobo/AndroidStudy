package cn.bobo.raspberry.M;

/**
 * 测量数据
 * Created by lionking on 3/23/17.
 */

public class MeasurementData {
    //测量时间
    private String dataTime;
    //温度
    private String temperature;
    //湿度
    private String humidity;

    public String getDataTime() {
        return dataTime;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
