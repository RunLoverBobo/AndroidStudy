package runlover.bobo.mymvp.P;

import android.content.Context;

import runlover.bobo.mymvp.M.ITestModel;
import runlover.bobo.mymvp.M.TestModel;
import runlover.bobo.mymvp.V.ITestView;

/**
 * Created by lionking on 16-12-6.
 */

public class TestPresenter implements ITestPresenter {
    private ITestModel itestModel;
    private ITestView iTestView;


    public TestPresenter(ITestView iTestView,Context context){
        this.iTestView=iTestView;
        this.itestModel=new TestModel(this,context);
    }


    /**
     * ini
     */
    @Override
    public void ini() {
        if(itestModel.ini())
            iTestView.showMessage("ini");
    }
}
