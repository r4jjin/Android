package com.example.selfevaluation.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.selfevaluation.R;
import com.example.selfevaluation.model.Question;
import com.example.selfevaluation.utils.ViewUtils;
import com.example.selfevaluation.views.CustomExpandableListAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.example.selfevaluation.data.Data.getData;
import static com.example.selfevaluation.data.Data.getViewData;

public class QuizScreen extends BaseActivity {
    /*Constants*/
    public static final String LOG = "QuizScreen";
    public static final String APP_EXIT_MESSAGE = "Are you sure you want to exit?";
    public static final String TEST_EXIT_MESSAGE = "Are you sure you want to exit test?";

    /*UI variables*/
    private ExpandableListView expandableListView;
    private LinearLayout optionsLayout;
    private RadioGroup optionsRadioGroup;
    private RadioButton radio_a;
    private RadioButton radio_b;
    private RadioButton radio_c;
    private RadioButton radio_d;
    private Button checkAnswerButton;
    private Button nextQuestionButton;
    private TextView txt_question;

    /*Intitializer variables*/
    private int activeQuestionIndex = 0;
    private int activeGroupPosition = 0;
    private int activeChildPosition = 0;
    private String selectedOption = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initializeUI();
        displayModuleChaptersListUI();


        final LinkedHashMap<String, List<String>> expandableListDetail;
        expandableListDetail = getViewData();
        final List<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        ExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                initiateQuestionNavigationFlow();
                activeGroupPosition = groupPosition;
                activeChildPosition = childPosition;
                loadQuestionAnwerUI();
//                Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) + " -> " + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        checkAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isCorrectAnswer = checkAnswer();
                Toast.makeText(getApplicationContext(), isCorrectAnswer ? "Correct" : "InCorrect", Toast.LENGTH_SHORT).show();

            }
        });

        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadQuestionAnwerUI();
                selectedOption = "";
                activeQuestionIndex++;
                optionsRadioGroup.clearCheck();
                checkAnswerButton.setEnabled(false);
            }
        });

        optionsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                checkAnswerButton.setEnabled(true);
                if (checkedId == radio_a.getId()) {
                    selectedOption = "a";
                } else if (checkedId == radio_b.getId()) {
                    selectedOption = "b";
                } else if (checkedId == radio_c.getId()) {
                    selectedOption = "c";
                } else if (checkedId == radio_d.getId()) {
                    selectedOption = "d";
                } else selectedOption = "";

            }
        });
    }

    private void displayModuleChaptersListUI() {
        ViewUtils.makeViewGone(optionsLayout);
        ViewUtils.makeViewVisible(expandableListView);
    }

    private void displayQuestionAnswerUI() {
        ViewUtils.makeViewGone(expandableListView);
        ViewUtils.makeViewVisible(optionsLayout);
    }

    private void initializeUI() {
        expandableListView = findViewById(R.id.expandableListView);
        optionsLayout = findViewById(R.id.options_list);
        checkAnswerButton = findViewById(R.id.checkAmswer);
        nextQuestionButton = findViewById(R.id.nextQuestion);
        radio_d = findViewById(R.id.d);
        radio_c = findViewById(R.id.c);
        radio_b = findViewById(R.id.b);
        radio_a = findViewById(R.id.a);
        optionsRadioGroup = findViewById(R.id.radio_group_options);
        txt_question = findViewById(R.id.question);
    }

    private void loadQuestionAnwerUI() {
        try {
            Question question = getData()[activeGroupPosition].getChapters().get(activeChildPosition).getQuestions().get(activeQuestionIndex);
            txt_question.setText(question.getQuestion());
            radio_a.setText(question.getOptions().get(0));
            radio_b.setText(question.getOptions().get(1));
            radio_c.setText(question.getOptions().get(2));
            radio_d.setText(question.getOptions().get(3));
        } catch (Exception e) {
            displayModuleChaptersListUI();
            reset();
        }


    }

    private boolean checkAnswer() {
        String answer = getData()[activeGroupPosition].getChapters().get(activeChildPosition).getQuestions().get(activeQuestionIndex).getAnswer();
        return answer.equalsIgnoreCase(selectedOption);
    }

    private void reset() {
        activeQuestionIndex = 0;
        activeGroupPosition = 0;
        activeChildPosition = 0;
        selectedOption = "";
    }

    private void initiateQuestionNavigationFlow() {
        displayQuestionAnswerUI();
        checkAnswerButton.setEnabled(false);
        activeQuestionIndex = 0;
    }

    @Override
    public void onBackPressed() {
        if (ViewUtils.isViewVisible(expandableListView)) {
            displayDIalog(APP_EXIT_MESSAGE, true);
        } else {
            displayDIalog(TEST_EXIT_MESSAGE, false);
        }
    }

    void displayDIalog(String message, final boolean appExitStatus) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (appExitStatus) {
                            QuizScreen.this.finish();
                        } else {
                            reset();
                            displayModuleChaptersListUI();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
