package com.example.trackingcorona;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    private ArrayList<StateData> localDataSet;




    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView state,cases;
        private final LinearLayout linear;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            state=(TextView)view.findViewById(R.id.district);
            cases=(TextView)view.findViewById(R.id.cases);
            linear = (LinearLayout) view.findViewById(R.id.linear);

        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public StateAdapter(ArrayList<StateData> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.table_layout, viewGroup, false);

        return new ViewHolder(view);
    }




    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //viewHolder.getTextView().setText(localDataSet[position]);
            viewHolder.state.setText(localDataSet.get(position).getStr_name());
            viewHolder.cases.setText(localDataSet.get(position).getStr_cnf());

            viewHolder.linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),State.class);
                    intent.putExtra("recovery",localDataSet.get(position).getStr_recovery());
                    intent.putExtra("confirm",localDataSet.get(position).getStr_cnf());
                    intent.putExtra("name",localDataSet.get(position).getStr_name());
                    intent.putExtra("death",localDataSet.get(position).getStr_death());
                    intent.putExtra("todaydeath",localDataSet.get(position).getStr_todaydeath());
                    intent.putExtra("todayconfirm",localDataSet.get(position).getStr_todayconfirmed());
                    intent.putExtra("todayrecovery",localDataSet.get(position).getStr_todayrecovered());
                    intent.putExtra("lastupdatedtime",localDataSet.get(position).getStr_lastupdatedtime());

                    v.getContext().startActivity(intent);

                }
            });

        }




    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }




}
