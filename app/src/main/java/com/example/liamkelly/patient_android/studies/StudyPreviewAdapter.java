package com.example.liamkelly.patient_android.studies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liamkelly.patient_android.R;

import java.util.List;

public class StudyPreviewAdapter extends RecyclerView.Adapter<StudyPreviewAdapter.ViewHolder> {

    private List<Study> mStudies;
    private Context mContext;

    public StudyPreviewAdapter(List<Study> studies, Context context){
        mStudies = studies;
        mContext = context;
    }

    @Override
    public StudyPreviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View studyView = inflater.inflate(R.layout.study_reyclerview_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(studyView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(StudyPreviewAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Study study = mStudies.get(position);

        // Set item views based on your views and data model
        TextView name = viewHolder.mStudyNameField;
        name.setText(study.getName());

        TextView researcher = viewHolder.mResearcherField;
        researcher.setText(study.getResearcher());

        TextView institution = viewHolder.mInstitutionField;
        institution.setText(study.getInstitution());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mStudies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mStudyNameField;
        private TextView mInstitutionField;
        private TextView mResearcherField;

        public ViewHolder(View view) {
            super(view);

            mStudyNameField = (TextView) view.findViewById(R.id.name);
            mInstitutionField = (TextView) view.findViewById(R.id.institution);
            mResearcherField = (TextView) view.findViewById(R.id.researcher);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Study study = mStudies.get(getAdapterPosition());
            Intent i = new Intent(mContext, FullStudyActivity.class);
            i.putExtra("study", study);
            mContext.startActivity(i);
        }
    }
}
