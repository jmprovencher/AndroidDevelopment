package ca.ulaval.ima.tp2;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StudentDescription extends Fragment {

    View studentDescriptionView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        studentDescriptionView = inflater.inflate(R.layout.student_description_layout, container, false);
        return studentDescriptionView;
    }
}
