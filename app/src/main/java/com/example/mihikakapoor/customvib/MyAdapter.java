package com.example.mihikakapoor.customvib;

/**
 * Created by mihikakapoor on 7/6/15.
 */
import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by mihikakapoor on 6/19/15.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private int mCount;
    private ArrayList<Long> al;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SeekBar mSeekBar;
        public SeekBar mSeekBar2;
        public EditText mTextView1;
        public EditText mTextView2;
        public ViewHolder(View v) {
            super(v);
            mSeekBar = (SeekBar) v.findViewById(R.id.seekBar1);
            mSeekBar2 = (SeekBar) v.findViewById(R.id.seekBar2);
            mTextView1 = (EditText) v.findViewById(R.id.textv1);
            mTextView2 = (EditText) v.findViewById(R.id.textv2);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(int count, Context context) {
        mContext = context;
        mCount = count;
        al = new ArrayList<>(2*count + 1);

        // vibration array takes in a long for silence duration first, so set first value
        // of array to 0 permanently
        al.add(Long.valueOf(0));

        // add initial seekbar values to vibration array list
        al.add(Long.valueOf(0));
        al.add(Long.valueOf(0));
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycled_seekbars, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        // set contents of seekbars and edittexts
        holder.mSeekBar.setProgress((toIntArrayVal(vibratePositionInArray(position)))/10);
        holder.mTextView1.setText(String.valueOf(toIntArrayVal(vibratePositionInArray(position))));
        holder.mSeekBar2.setProgress(toIntArrayVal(silencePositionInArray(position)) / 10);
        holder.mTextView2.setText(String.valueOf(toIntArrayVal(silencePositionInArray(position))));

        // vibrate seekbar listener
        holder.mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // modify arraylist (only if seekbar touched - not if progress changed by
                // a change in corresponding edittext)
                al.set(vibratePositionInArray(position), Long.valueOf((holder.mSeekBar.getProgress()) * 10));

                notifyDataSetChanged();
            }
        });

        // vibrate edittext listener
        holder.mTextView1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.mTextView1.getText().toString().matches("")) {
                    holder.mSeekBar.setProgress(0);
                    al.set(vibratePositionInArray(holder.getAdapterPosition()), Long.valueOf(0));
                }

                else {
                    // read in from edittext
                    int prog = Integer.parseInt(holder.mTextView1.getText().toString());

                    // update corresponding seekbar
                    int prog1 = prog / 10;
                    holder.mSeekBar.setProgress(prog1);

                    // modify arraylist
                    al.set(vibratePositionInArray(holder.getAdapterPosition()), Long.valueOf(prog));

                    // exceptions
                    if (Integer.parseInt(holder.mTextView1.getText().toString()) > 1000) {
                        Toast.makeText(mContext, "Not Valid Duration", Toast.LENGTH_SHORT).show();
                        al.set(vibratePositionInArray(holder.getAdapterPosition()), Long.valueOf(1000));
                    }

                    else if (Integer.parseInt(holder.mTextView1.getText().toString()) < 0) {
                        Toast.makeText(mContext, "Not Valid Duration", Toast.LENGTH_SHORT).show();
                        holder.mSeekBar.setProgress(0);
                        al.set(vibratePositionInArray(holder.getAdapterPosition()), Long.valueOf(0));
                    }
                }


            }
        });

        // silence seekbar listener
        holder.mSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // modify arraylist (only if seekbar touched - not if progress changed by
                // a change in corresponding edittext)
                al.set(silencePositionInArray(position), Long.valueOf((holder.mSeekBar2.getProgress()) * 10));

                notifyDataSetChanged();
            }
        });

        // silence edittext listener
        holder.mTextView2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.mTextView2.getText().toString().matches("")) {
                    holder.mSeekBar2.setProgress(0);
                    al.set(silencePositionInArray(holder.getAdapterPosition()), Long.valueOf(0));
                }

                else {
                    // read in from edittext
                    int prog = Integer.parseInt(holder.mTextView2.getText().toString());

                    // update corresponding seekbar
                    int prog1 = prog / 10;
                    holder.mSeekBar2.setProgress(prog1);

                    // modify arraylist
                    al.set(silencePositionInArray(holder.getAdapterPosition()), Long.valueOf(prog));

                    // exceptions
                    if (Integer.parseInt(holder.mTextView2.getText().toString()) > 1000) {
                        Toast.makeText(mContext, "Not Valid Duration", Toast.LENGTH_SHORT).show();
                        al.set(silencePositionInArray(holder.getAdapterPosition()), Long.valueOf(1000));
                    }

                    else if (Integer.parseInt(holder.mTextView2.getText().toString()) < 0) {
                        Toast.makeText(mContext, "Not Valid Duration", Toast.LENGTH_SHORT).show();
                        holder.mSeekBar2.setProgress(0);
                        al.set(silencePositionInArray(holder.getAdapterPosition()), Long.valueOf(0));
                    }
                }

            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mCount;
    }

    public void add()
    {
        mCount++;

        // fill in vibrate and silence values in arraylist for two new seekbars

        if (mCount == 1) {
            al.add(Long.valueOf(0));
            al.add(Long.valueOf(0));
        }

        else {
            al.add(al.get(((mCount - 1) * 2) - 1));
            al.add(al.get((mCount - 1) * 2));
        }

        this.notifyDataSetChanged();
    }

    public void subtract()
    {
        mCount--;

        // remove most recently added items
        al.remove(al.size() - 1);
        al.remove(al.size() - 1);

        this.notifyDataSetChanged();
    }

    public long[] vibrate()
    {
        // Start without a delay
        // Each element then alternates between vibrate, sleep, vibrate, sleep...
        long[] pattern = new long[al.size()];

        // feed arraylist into array
        for (int i = 0; i < al.size(); i++) { pattern[i] = (long)  al.get(i); }

        return pattern;
    }

    // vibrate position in arraylist
    private int vibratePositionInArray(int viewPosition) {
        // accounts for empty first slot in arraylist
        return (viewPosition * 2) + 1;
    }

    // silence position in arraylist
    private int silencePositionInArray(int viewPosition) {
        // accounts for empty first slot in arraylist
        return (viewPosition + 1) * 2;
    }

    // finds int value in arraylist, given position in arraylist
    private int toIntArrayVal(int position) {
        return al.get(position).intValue();
    }

    //class MyViewHolder extends RecycleView.adapter<MyViewHolder> {}
}
