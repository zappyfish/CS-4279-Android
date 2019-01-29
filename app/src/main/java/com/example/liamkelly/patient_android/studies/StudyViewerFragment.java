package com.example.liamkelly.patient_android.studies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liamkelly.patient_android.R;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudyViewerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudyViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyViewerFragment extends Fragment {

    private final List<StudyPreview> mStudyPreviews;
    private int currentButtonInd;
    private StudyPreview mInView;
    private RecyclerView mPreviewScroller;

    private OnFragmentInteractionListener mListener;

    public StudyViewerFragment() {
        // Required empty public constructor
        mStudyPreviews = new LinkedList<>();
        currentButtonInd = 0;
    }

    public void addStudies(List<Study> studies) {
        // mStudyPreviews.add(preview);
        StudyPreviewAdapter adapter = new StudyPreviewAdapter(studies, getContext());
        mPreviewScroller.setAdapter(adapter);
        TextView numStudies = (TextView)getView().findViewById(R.id.num_studies);
        numStudies.setText("There are " + studies.size() + " studies available.");
    }

    private void setPreview(StudyPreview preview) {
        ViewGroup parent =(ViewGroup)mInView.getParent();
        int index = parent.indexOfChild(mInView);
        parent.removeView(mInView);
        parent.addView(preview, index);
        mInView = preview;
        mInView.refresh();
    }

    private void initButtons() {
        final StudyViewerFragment frg = this;
        initButton(R.id.left, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frg.currentButtonInd > 0) {
                    setPreview(frg.mStudyPreviews.get(--frg.currentButtonInd));
                }
            }
        });

        initButton(R.id.right, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frg.currentButtonInd < mStudyPreviews.size() - 1) {
                    setPreview(frg.mStudyPreviews.get(++frg.currentButtonInd));
                }
            }
        });
    }

    private void initButton(int btnID, View.OnClickListener listener) {
        Button btn = (Button)getView().findViewById(btnID);
        btn.setOnClickListener(listener);
    }

    public static StudyViewerFragment newInstance() {
        StudyViewerFragment fragment = new StudyViewerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_study_viewer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // initButtons();
        mPreviewScroller = (RecyclerView) getView().findViewById(R.id.study_scroller);
        mPreviewScroller.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        TextView numStudies = (TextView)getView().findViewById(R.id.num_studies);
        numStudies.setText("There are " + mStudyPreviews.size() + " studies available.");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
