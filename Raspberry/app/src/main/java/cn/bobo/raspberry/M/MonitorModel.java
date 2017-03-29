package cn.bobo.raspberry.M;

import android.graphics.Color;
import android.util.Xml;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import cn.bobo.raspberry.P.IMonitorPresenter;
import cn.bobo.raspberry.P.MonitorPresenter;

/**
 * Created by lionking on 3/23/17.
 */

public class MonitorModel implements IMonitorModel {
    private IMonitorPresenter iMonitorPresenter;
    private int x;
    private ArrayList<Entry> valTemperature, valHumidity;

    public MonitorModel(IMonitorPresenter iMonitorPresenter) {
        this.iMonitorPresenter=iMonitorPresenter;

        valTemperature = new ArrayList<>();
        valHumidity = new ArrayList<>();

        x = 36000;
        for (int num = 1; num < x; num++) {
            valTemperature.add(new Entry(num, 0));
            valHumidity.add(new Entry(num, 0));
        }
    }

    /**
     * 获取数据
     *
     * @param temperatureStr 温度
     * @param humidityStr    湿度
     * @return 曲线数据
     */
    @Override
    public LineData getLineData(String temperatureStr, String humidityStr) {
        valTemperature.remove(0);
        valHumidity.remove(0);

        x = x + 1;

        valTemperature.add(new Entry(x, Integer.parseInt(temperatureStr)));
        valHumidity.add(new Entry(x, Integer.parseInt(humidityStr)));

        LineDataSet temperatureDataSet, humidityDataSet;
        temperatureDataSet = new LineDataSet(valTemperature, "温度");
        temperatureDataSet.setDrawValues(false);
        temperatureDataSet.setDrawCircles(false);

        humidityDataSet = new LineDataSet(valHumidity, "湿度");
        humidityDataSet.setColor(Color.GREEN);
        humidityDataSet.setDrawValues(false);
        humidityDataSet.setDrawCircles(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(temperatureDataSet);
        dataSets.add(humidityDataSet);

        return new LineData(dataSets);
    }

    /**
     * 解析XML数据
     *
     * @param inputStream xml输入流
     * @return 数据实体
     */
    @Override
    public MeasurementData getMeasurementData(ByteArrayInputStream inputStream) {
        MeasurementData measurementData = null;
        XmlPullParser pullParser = Xml.newPullParser();
        try {
            pullParser.setInput(inputStream, "UTF-8");
            int event = pullParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        measurementData = new MeasurementData();
                        break;
                    case XmlPullParser.START_TAG:
                        if ("time".equals(pullParser.getName())) {
                            if (measurementData != null) {
                                measurementData.setDataTime(pullParser.nextText());
                            }
                        }
                        if ("temperature".equals(pullParser.getName())) {
                            if (measurementData != null) {
                                measurementData.setTemperature(pullParser.nextText());
                            }
                        }
                        if ("humidity".equals(pullParser.getName())) {
                            if (measurementData != null) {
                                measurementData.setHumidity(pullParser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = pullParser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return measurementData;
    }



}
