package com.example.mihikakapoor.customvib;

import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    LinearLayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    MyAdapter mAdapter;
    int count;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        count = 1;


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager/
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(count, getActivity());
        mRecyclerView.setAdapter(mAdapter);


        Button buttonMinus = (Button) rootView.findViewById(R.id.minus);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count != 0) {
                    count--;
                    mAdapter.subtract();
                }
            }
        });

        Button buttonPlus = (Button) rootView.findViewById(R.id.plus);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;

                // specify an adapter (see also next example)
                mAdapter.add();
            }
        });

        Button buttonTest = (Button) rootView.findViewById(R.id.test);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get instance of Vibrator from current Context
                Vibrator vib = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);

                // Start without a delay
                // Each element then alternates between vibrate, sleep, vibrate, sleep...
                long[] pattern = mAdapter.vibrate();

                // The '-1' here means to vibrate once, as '-1' is out of bounds in the pattern array
                vib.vibrate(pattern, -1);
            }
        });


        return rootView;
    }
}
