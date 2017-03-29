package cn.bobo.raspberry.P;

import android.os.Handler;
import android.os.Message;

import com.github.mikephil.charting.data.LineData;

import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;

import cn.bobo.raspberry.M.GetDataFromRaspberryThread;
import cn.bobo.raspberry.M.IMonitorModel;
import cn.bobo.raspberry.M.MeasurementData;
import cn.bobo.raspberry.M.MonitorModel;
import cn.bobo.raspberry.V.IMonitorView;

/**
 * 逻辑处理
 * Created by lionking on 3/23/17.
 */

public class MonitorPresenter implements IMonitorPresenter {
    private IMonitorView iMonitorView;
    private IMonitorModel iMonitorModel;


    public MonitorPresenter(IMonitorView iMonitorView) {
        this.iMonitorView = iMonitorView;
        this.iMonitorModel = new MonitorModel(this);
    }

    @Override
    public void getDataFromRaspberryPi() {
        Thread thread = new Thread(new GetDataFromRaspberryThread(mHandler));
        thread.start();

    }

    private MsgHandler mHandler = new MsgHandler(this);



    public static class MsgHandler extends Handler {
        private final WeakReference<MonitorPresenter> mPresenter;

        MsgHandler(MonitorPresenter presenter) {
            mPresenter = new WeakReference<>(presenter);
        }

        public void handleMessage(Message msg) {
            MonitorPresenter presenter = mPresenter.get();
            super.handleMessage(msg);

            String infoStr = msg.obj.toString();

            MeasurementData measurementData = presenter.iMonitorModel.getMeasurementData(new ByteArrayInputStream(infoStr.getBytes()));
            if (!measurementData.getTemperature().equals("err")) {
                presenter.iMonitorView.showText(measurementData);

                LineData lineData = presenter.iMonitorModel.getLineData(measurementData.getTemperature(),
                        measurementData.getHumidity());
                presenter.iMonitorView.showCurve(lineData);
            }
        }


    }
}
