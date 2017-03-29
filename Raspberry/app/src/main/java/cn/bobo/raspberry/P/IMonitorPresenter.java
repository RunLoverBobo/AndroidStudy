package cn.bobo.raspberry.P;

/**
 * 逻辑处理接口
 * Created by lionking on 3/23/17.
 */

public interface IMonitorPresenter {
    /**
     * 开启接收树莓派数据线程
     */
    void getDataFromRaspberryPi();
}
