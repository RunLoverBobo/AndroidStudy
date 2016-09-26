package runlover.bobo.mymvp.M;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import runlover.bobo.mymvp.P.ITestPresenter;

/**
 * Created by lionking on 16-12-6.
 */

public class TestModel implements ITestModel {
    private ITestPresenter iTestPresenter;
    private Context context;

    public TestModel(ITestPresenter iTestPresenter, Context context) {
        this.iTestPresenter = iTestPresenter;
        this.context = context;
    }


    /**
     * ini
     */
    @Override
    public boolean ini() {
        DBHelper dbHelper = new DBHelper(context, "User.db", null, 1);
        SQLiteDatabase sd=dbHelper.getWritableDatabase();
        return true;
    }
}
