package cn.bobo.raspberry;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    public TextView tvData;
    public Button bConnect;
    private LineChart lcTemperature;
    private ArrayList<Entry> valTemperature, valHumidity;
    private int x;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        tvData = (TextView) findViewById(R.id.tvData);

        bConnect = (Button) findViewById(R.id.bConnect);
        bConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new GetDataFromRaspberryThread(mHandler));
                thread.start();
            }
        });

        lcTemperature = (LineChart) findViewById(R.id.lcTemperature);

        valTemperature = new ArrayList<>();
        valHumidity = new ArrayList<>();

        x = 36000;
        for (int num = 1; num < x; num++) {
            valTemperature.add(new Entry(num, 0));
            valHumidity.add(new Entry(num, 0));
        }
    }

    private LineData getLineData(String temperatureStr, String humidityStr) {


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
        LineData data = new LineData(dataSets);


        return data;

    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String infoStr = msg.obj.toString();
            String[] info = infoStr.split("-");
            if (!info[0].equals("err")) {
                tvData.setText("温度：" + info[0] + "  湿度：" + info[1]);


                LineData lineData = getLineData(info[0], info[1]);

                lcTemperature.setData(lineData);
                lcTemperature.invalidate();
            }
        }
    };
}
