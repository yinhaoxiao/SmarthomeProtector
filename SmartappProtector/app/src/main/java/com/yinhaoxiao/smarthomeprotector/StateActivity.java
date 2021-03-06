package com.yinhaoxiao.smarthomeprotector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class StateActivity extends Activity {
    private Button mRunBtn;
    private TextView mRunTextView;
    private Intent mMonitorServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_state);

        mMonitorServiceIntent = new Intent(this, MonitorService.class);
        startService(mMonitorServiceIntent);

        mRunTextView= (TextView) findViewById(R.id.runningstateTV);
        mRunBtn = (Button) findViewById(R.id.runBtn);
        mRunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.RunningState) {
                    mRunBtn.setText("Run");
                    mRunTextView.setText("Inactive");
                    MainActivity.RunningState = false;
                    stopService(mMonitorServiceIntent);
                    try {
                        MonitorService.HttpNotifiRunner.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    mRunBtn.setText("Stop");
                    mRunTextView.setText("Running");
                    MainActivity.RunningState = true;
                    startService(mMonitorServiceIntent);
                }
            }
        });
    }

}
