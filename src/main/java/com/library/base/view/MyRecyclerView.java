package com.library.base.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.github.baseclass.BaseDividerListItem;
import com.library.R;

/**
 * Created by Administrator on 2017/12/18.
 */

public class MyRecyclerView extends RecyclerView {
    private static final String TAG="MyRecyclerView";

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (getLayoutManager() == null) {
            setLayoutManager(new LinearLayoutManager(getContext()));
            addItemDecoration(new BaseDividerListItem(getContext(),2, R.color.background_f2));
        }
        super.setAdapter(adapter);
    }
}
