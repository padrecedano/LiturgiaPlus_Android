package org.deiverbum.app.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.deiverbum.app.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class BreviarioMasActivityFragment extends Fragment {

    public BreviarioMasActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_breviario_mas, container, false);
    }
}
