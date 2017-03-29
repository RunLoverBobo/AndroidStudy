package cn.bobo.raspberry.M;

import com.github.mikephil.charting.data.LineData;

import java.io.ByteArrayInputStream;

/**
 *
 * Created by lionking on 3/23/17.
 */

public interface IMonitorModel {
    /**
     * 获取数据
     * @param s 温度
     * @param s1 湿度
     * @return 曲线数据
     */
    LineData getLineData(String s, String s1);

    /**
     * 解析XML数据
     * @param inputStream xml输入流
     * @return 数据实体
     */
    MeasurementData getMeasurementData(ByteArrayInputStream inputStream);

}
