package ca.ulaval.ima.tp2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        studentFormView = inflater.inflate(R.layout.student_form_layout, container, false);
        final TextView editDate = (TextView) studentFormView.findViewById(R.id.editDate);
        addListenerOnButton();


        final Calendar myCalendar = Calendar.getInstance(Locale.FRENCH);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
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
        new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();


        return studentFormView;
    }

    public void addListenerOnButton() {

        button = (Button) studentFormView.findViewById(R.id.submit_button);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.studentDescriptionFrame, new StudentDescription()).commit();
            }

        });

    }


}
