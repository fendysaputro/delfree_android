package com.delfree.delfree_android.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.delfree.delfree_android.Adapter.ProgressRouteAdapter;
import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.R;

import java.util.ArrayList;

public class ProgressRoute extends Activity {

    AppDelfree appDelfree;
    private ListView progressList;
//    ArrayList<WorkOrders> list = null;
    String[] itemProgress = new String[] {"Slipi - Grogol", "Kuningan - Tomang"};
    ProgressRouteAdapter adapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_route_activity);

        appDelfree = (AppDelfree) getApplication();
        progressList=(ListView) findViewById(R.id.list);
//        list = new ArrayList<WorkOrders>();
        adapter = new ProgressRouteAdapter(this.getApplication(), R.layout.custom_item_progress_adapter, itemProgress);
        progressList.setAdapter(adapter);

    }
}
