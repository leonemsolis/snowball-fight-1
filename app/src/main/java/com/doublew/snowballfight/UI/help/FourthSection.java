package com.doublew.snowballfight.UI.help;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.doublew.snowballfight.R;

public class FourthSection extends Fragment {
    public FourthSection() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.help_fourth_section,
                container, false);
        Button write = (Button) view.findViewById(R.id.write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "leonemsolis@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject?");
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_TEXT, "Enter something.");
                startActivity(intent);
            }
        });

        return view;

    }
}
