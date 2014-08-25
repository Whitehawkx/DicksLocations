
package com.jonathansteadman.dickslocations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DicksDetailsFragment extends Fragment implements Constants {

    private Dicks dicks;

    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null) {
            id = getActivity().getIntent().getIntExtra(EXTRA_DICKS_ID, 0);
        } else {
            id = getArguments().getInt(EXTRA_DICKS_ID);
        }

        dicks = DicksSet.getInstance().getLocation(id);
    }

    public static DicksDetailsFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_DICKS_ID, id);
        DicksDetailsFragment fragment = new DicksDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dicks_details, container, false);
        initDicksTitle(view);
        initDicksDesc(view);
        initDicksImage(view);
        mapButton(view, id);
        return view;
    }

    private void initDicksTitle(View view) {
        TextView titleView = (TextView)view.findViewById(R.id.textview_dickstitle);
        titleView.setText(dicks.getTitle());
    }

    private void initDicksDesc(View view) {
        TextView descView = (TextView)view.findViewById(R.id.textview_dicksdesc);
        descView.setText(dicks.getDesc());
    }

    private void initDicksImage(View view) {
        ImageView imgView = (ImageView)view.findViewById(R.id.imageview_dicksimg);
        imgView.setImageResource(dicks.getImage());
    }

    private void mapButton(View view, final int position) {
        ImageView button = (ImageView)view.findViewById(R.id.map_button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MappingActivity.class);
                intent.putExtra(EXTRA_DICKS_ID, position);
                startActivity(intent);
            }
        });

    }

}
