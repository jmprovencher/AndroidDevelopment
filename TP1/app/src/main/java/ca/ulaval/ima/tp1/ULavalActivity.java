package ca.ulaval.ima.tp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ULavalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulaval);
    }

    public void closeULavalActivity (View view) {
        ULavalActivity.this.finish();
    }

}
