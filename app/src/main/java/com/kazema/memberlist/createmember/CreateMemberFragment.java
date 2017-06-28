package com.kazema.memberlist.createmember;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kazema.memberlist.R;

/**
 * A fragment to create member.
 * Use the {@link CreateMemberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateMemberFragment extends Fragment {

    public CreateMemberFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment CreateMemberFragment.
     */
    public static CreateMemberFragment newInstance() {
        CreateMemberFragment fragment = new CreateMemberFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_member, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
