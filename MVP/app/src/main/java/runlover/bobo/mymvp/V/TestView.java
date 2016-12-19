package runlover.bobo.mymvp.V;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import runlover.bobo.mymvp.P.ITestPresenter;
import runlover.bobo.mymvp.P.TestPresenter;
import runlover.bobo.mymvp.R;

public class TestView extends AppCompatActivity implements ITestView {
    private Button bShowMessage;
    private EditText etPassword;
    private ITestPresenter iTestPresenter;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniView();
    }

    private void iniView() {
        context = getApplicationContext();
        iTestPresenter = new TestPresenter(this, context);

        final TextView tvPassword=(TextView) findViewById(R.id.tvPassword);
        etPassword=(EditText)findViewById(R.id.etPassword);


        bShowMessage = (Button) findViewById(R.id.bShowMessage);
        bShowMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iTestPresenter.ini();
                //tvPassword.setText(etPassword.getText());
            }
        });



        etPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());

    }

    @Override
    public void showMessage(String iniMsg) {
        Toast.makeText(context,iniMsg,Toast.LENGTH_SHORT).show();
    }

    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }

            public char charAt(int index) {
                return '*'; // This is the important part
            }

            public int length() {
                return mSource.length(); // Return default
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }
}
