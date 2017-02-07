package ca.ulaval.ima.tp1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    public Profile profile = new Profile("Jean-Michel", "Provencher", new Date(93, 10, 23), "JMPRO18");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToGitHub(View view) {
        goToUrl("http://github.com/");
    }

    public void openULavalActivity(View view) {
        Intent myIntent = new Intent(MainActivity.this, ULavalActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void openMyProfileActivity(View view) {
        Intent myIntent = new Intent(MainActivity.this, MyProfileActivity.class);
        myIntent.putExtra("MyProfile", profile);

        MainActivity.this.startActivity(myIntent);
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}
