package com.jambo.example.toolbartitleanimation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by JAMBO on 7/14/15.
 */

public class ContentFragment extends Fragment {
    private static final String TITLE = "title";

    private String mTitle;

    public static ContentFragment newInstance(String title) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    public ContentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_content, container, false);
        TextView title_text = (TextView) root.findViewById(R.id.title_text);
        title_text.setText(mTitle);

        return root;
    }


}
