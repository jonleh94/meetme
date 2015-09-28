package de.dhbw.meetme;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import org.hello.R;

public class HelloActivity extends Activity {

    @Override
    public void onCreate(hBundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_layout);
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText("Hello world!");
    }

}
