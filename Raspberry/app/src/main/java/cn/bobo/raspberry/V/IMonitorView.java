package cn.bobo.raspberry.V;

import com.github.mikephil.charting.data.LineData;

import cn.bobo.raspberry.M.MeasurementData;

/**
 * 显示接口
 * Created by lionking on 3/23/17.
 */

public interface IMonitorView {
    /**
     * 显示文字信息
     * @param measurementData 文字信息数组
     */
    void showText(MeasurementData measurementData);

    /**
     * 绘制曲线
     * @param lineData 曲线数据
     */
    void showCurve(LineData lineData);
}
