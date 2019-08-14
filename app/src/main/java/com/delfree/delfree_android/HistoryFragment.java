package com.delfree.delfree_android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.delfree.delfree_android.Adapter.HistoryAdapter;
import com.delfree.delfree_android.Adapter.HomeAdapter;

import static com.delfree.delfree_android.MainActivity.ShowFragment;


/**
 * Created by phephen on 6/8/19.
 */

public class HistoryFragment extends Fragment {

    private ListView listJobsHistory;
    String[] itemHistory = new String[] {"WO No. 124/7A/VI/2019", "WO No. 124/7A/VI/2019", "WO No. 124/7A/VI/2019"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Back");
        toolbar.setTitleTextColor(getResources().getColor(R.color.chooseNav));
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        listJobsHistory=(ListView) view.findViewById(R.id.list);

        HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(), R.layout.custom_item, itemHistory);
        listJobsHistory.setAdapter(historyAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
}
