package com.example.selfevaluation.utils;

import android.content.Context;
import android.view.View;
import android.widget.RadioGroup;

import androidx.core.content.ContextCompat;

import com.example.selfevaluation.R;
import com.example.selfevaluation.application.MyApplication;

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

    public static void setEnabled(RadioGroup radioGroup, boolean status) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(status);
        }
    }

    public static int getCorrect() {
        return ContextCompat.getColor(MyApplication.getAppContext(), R.color.colorPrimary);
    }

    public static int getInCorrect() {
        return ContextCompat.getColor(MyApplication.getAppContext(), R.color.colorAccent);
    }
}
