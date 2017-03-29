package cn.bobo.raspberry.V;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

import cn.bobo.raspberry.M.MeasurementData;
import cn.bobo.raspberry.P.IMonitorPresenter;
import cn.bobo.raspberry.P.MonitorPresenter;
import cn.bobo.raspberry.R;


public class MonitorView extends AppCompatActivity implements IMonitorView {
    private IMonitorPresenter iMonitorPresenter;
    public TextView tvData;
    public Button bConnect;
    private LineChart lcTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        iMonitorPresenter = new MonitorPresenter(this);

        lcTemperature = (LineChart) findViewById(R.id.lcTemperature);
        tvData = (TextView) findViewById(R.id.tvData);

        bConnect = (Button) findViewById(R.id.bConnect);
        bConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iMonitorPresenter.getDataFromRaspberryPi();
            }
        });
    }

    /**
     * 显示文字信息
     *
     * @param measurementData 文字信息数组
     */
    @Override
    public void showText(MeasurementData measurementData) {
        tvData.setText("温度：" + measurementData.getTemperature() + "  湿度：" + measurementData.getHumidity());
    }

    /**
     * 绘制曲线
     *
     * @param lineData 曲线数据
     */
    @Override
    public void showCurve(LineData lineData) {
        lcTemperature.setData(lineData);
        lcTemperature.invalidate();
    }
}
