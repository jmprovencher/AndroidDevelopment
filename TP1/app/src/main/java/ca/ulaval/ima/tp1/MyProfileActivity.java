package ca.ulaval.ima.tp1;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.text.DateFormat;
import java.util.Locale;

import android.widget.TextView;

public class MyProfileActivity extends AppCompatActivity {

    public Profile myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Profile myProfile = (Profile) getIntent().getSerializableExtra("MyProfile");

        setContentView(R.layout.activity_my_profile);
        TextView firstNameTextView = (TextView) findViewById(R.id.firstNameTextView);
        firstNameTextView.setText("Prénom: " + myProfile.firstName);

        TextView lastNameTextView = (TextView) findViewById(R.id.lastNameTextView);
        lastNameTextView.setText("Nom: " + myProfile.lastName);

        TextView dateTextView = (TextView) findViewById(R.id.dateTextView);
        String birthDayString = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.FRENCH).format(myProfile.birthDate);

        dateTextView.setText("Date de naissance: " + birthDayString);

        TextView IDULTextView = (TextView) findViewById(R.id.IDULTextView);
        IDULTextView.setText("IDUL: " + myProfile.IDUL);
    }
}
