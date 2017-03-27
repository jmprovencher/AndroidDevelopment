package ca.ulaval.ima.tp2;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.support.v4.app.ActionBarDrawerToggle;


import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class StudentForm extends Fragment {

    View studentFormView;
    Button button;
    TextView editFirstName;
    TextView editLastName;
    EditText editDate;
    DatePickerDialog datePicker;
    Spinner deparment;
    RadioButton sex;
    private RadioGroup radioGroup;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        studentFormView = inflater.inflate(R.layout.student_form_layout, container, false);
        addListenerOnButton();
        editFirstName = (TextView) studentFormView.findViewById(R.id.editFirstName);
        editFirstName.setText("Jean-Michel");
        editLastName = (TextView) studentFormView.findViewById(R.id.editLastName);
        editLastName.setText("Provencher");
        editDate = (EditText) studentFormView.findViewById(R.id.editDate);
        editDate.setText("23 novembre 1993");
        deparment = (Spinner) studentFormView.findViewById(R.id.department);
        radioGroup = (RadioGroup) studentFormView.findViewById(R.id.radioSex);


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            final Calendar myCalendar = Calendar.getInstance(Locale.FRENCH);

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd MMMM yyyy"; // your format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA_FRENCH);

                editDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        datePicker = new DatePickerDialog(getActivity(), date, 1993, 10, 23);

        editDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                datePicker.show();
                return false;
            }
        });


        return studentFormView;
    }

    public void addListenerOnButton() {

        button = (Button) studentFormView.findViewById(R.id.submit_button);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                insertNestedFragment();

            }

        });
    }


    // Embeds the child fragment dynamically
    private void insertNestedFragment() {
        StudentDescription studentDescriptionFragment = new StudentDescription();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        sex = (RadioButton) studentFormView.findViewById(selectedId);
        Bundle bundle = new Bundle();
        Student student = new Student(editFirstName.getText().toString(), editLastName.getText().toString(), editDate.getText().toString(), deparment.getSelectedItem().toString(), sex.getText().toString());
        bundle.putParcelable("student", student);
        studentDescriptionFragment.setArguments(bundle);


        ((MainActivity) getActivity()).getDataFromFragment(student);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, studentDescriptionFragment).addToBackStack("tag").commit();

    }
}



