/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.drivit.androidsdk_sample;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.drivit.core.trips.DrivitStatusActivity;
import com.drivit.core.trips.DrivitStatusType;

import java.util.List;

/**
 * teste2
 * Provide views to RecyclerView with data from mDataSet.
 */
public class DrivitStatusAdapter extends RecyclerView.Adapter<DrivitStatusAdapter.ViewHolder> {
    private static final String TAG = "TripAdapter";

    private List<DrivitStatusType> mDataSet;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title, subtitle, action;
        ImageView icon, state;
        public ProgressBar progressBar;


        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.

            title = (TextView) v.findViewById(R.id.textView_settings_title);
            subtitle = (TextView) v.findViewById(R.id.textView_settings_subtitle);
            action = v.findViewById(R.id.textView_info);
            icon = v.findViewById(R.id.imageView_settings);
            state = v.findViewById(R.id.imageView_state);
            progressBar=v.findViewById(R.id.progressBar_setting);


        }

    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public DrivitStatusAdapter(List<DrivitStatusType> dataSet) {
        mDataSet = dataSet;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_status, viewGroup, false);


        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)1

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        Context ctx = viewHolder.itemView.getContext();
        DrivitStatusType status = mDataSet.get(position);
        viewHolder.title.setText(status.getTitle(ctx));
        viewHolder.subtitle.setText(status.getSubtitle(ctx));
        viewHolder.action.setText(status.rightHandSideText);
        viewHolder.icon.setImageDrawable(ContextCompat.getDrawable(ctx,status.settingIconId));
        viewHolder.state.setImageDrawable(ContextCompat.getDrawable(ctx,status.stateImageId));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                status.resolve((DrivitStatusActivity) ctx,() -> {
                    viewHolder.progressBar.setVisibility(View.GONE);
                    notifyDataSetChanged();
                });
            }
        });
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
