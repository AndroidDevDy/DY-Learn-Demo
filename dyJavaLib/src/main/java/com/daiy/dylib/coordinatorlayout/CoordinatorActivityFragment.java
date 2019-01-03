package com.daiy.dylib.coordinatorlayout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daiy.dylib.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CoordinatorActivityFragment extends Fragment {

    public CoordinatorActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coordinator, container, false);
    }
}
