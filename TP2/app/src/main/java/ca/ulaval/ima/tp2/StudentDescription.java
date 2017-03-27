package ca.ulaval.ima.tp2;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class StudentDescription extends Fragment {

    View studentDescriptionView;
    TextView firstName;
    TextView lastName;
    TextView date;
    TextView department;
    TextView sex;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        studentDescriptionView = inflater.inflate(R.layout.student_description_layout, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Student student = (Student) bundle.getParcelable("student");
            if (student != null) {
                firstName = (TextView) studentDescriptionView.findViewById(R.id.firstName);
                firstName.setText(student.getFirstName());
                lastName = (TextView) studentDescriptionView.findViewById(R.id.lastname);
                lastName.setText(student.getLastName());
                date = (TextView) studentDescriptionView.findViewById(R.id.date);
                date.setText(student.getBirthday());
                department = (TextView) studentDescriptionView.findViewById(R.id.department);
                department.setText(student.getDepartment());
                sex = (TextView) studentDescriptionView.findViewById(R.id.sex);
                sex.setText(student.getSex());
            }
        }


        return studentDescriptionView;

    }


}

