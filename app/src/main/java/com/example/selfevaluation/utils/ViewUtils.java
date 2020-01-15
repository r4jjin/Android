package com.example.selfevaluation.utils;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public class ViewUtils {
    Context context;
    private ViewUtils viewUtils;

    ViewUtils getInstance() {
        if (viewUtils != null) {
            return viewUtils;
        } else {
            viewUtils = new ViewUtils();
        }
        return viewUtils;
    }

    public static void makeViewGone(View view) {
        view.setVisibility(View.GONE);
    }

    public static void makeViewInvisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public static void makeViewVisible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public static boolean isViewVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }
}