package com.example.selfevaluation.activity;

import android.os.Bundle;

import com.example.selfevaluation.R;
import com.example.selfevaluation.data.Data;
import com.example.selfevaluation.model.Module;
import com.google.gson.Gson;

public class QuizScreen extends BaseActivity {
    public static final String LOG = "QuizScreen";
    Module[] userArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getUserData();

    }

    private void getUserData() {
        Gson gson = new Gson();
        userArray = gson.fromJson(Data.JSON_DATA, Module[].class);
    }


}

