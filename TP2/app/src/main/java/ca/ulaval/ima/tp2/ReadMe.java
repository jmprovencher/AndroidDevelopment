package ca.ulaval.ima.tp2;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ReadMe extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savecInstance) {
        myView = inflater.inflate(R.layout.readme_layout, container, false);
        return myView;
    }
}
