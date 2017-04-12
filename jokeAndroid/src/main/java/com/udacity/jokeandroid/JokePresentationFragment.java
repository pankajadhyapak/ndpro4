package com.udacity.jokeandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JokePresentationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_joke_presentation, container, false);
        ((TextView) root.findViewById(R.id.txtJoke)).setText(getActivity().getIntent().getStringExtra("joke"));
        return root;
    }
}
