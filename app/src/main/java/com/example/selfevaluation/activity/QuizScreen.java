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
    private ExpandableListView exp_list_view_modulesView;
    private LinearLayout layout_Options;
    private RadioGroup optionsRadioGroup;
    private RadioButton radio_a;
    private RadioButton radio_b;
    private RadioButton radio_c;
    private RadioButton radio_d;
    private Button btn_checkAnswer;
    private Button btn_nextQuestion;
    private TextView txt_question;
    private TextView txt_questionNumber;

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
        exp_list_view_modulesView.setAdapter(expandableListAdapter);


        exp_list_view_modulesView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                initiateQuestionNavigationFlow();
                activeGroupPosition = groupPosition;
                activeChildPosition = childPosition;
                loadQuestionAnswerUI();
                return false;
            }
        });

        btn_checkAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isCorrectAnswer = checkAnswer();
                btn_checkAnswer.setEnabled(false);
                btn_nextQuestion.setEnabled(true);
                Toast.makeText(getApplicationContext(), isCorrectAnswer ? "Correct Answer" : "InCorrect Answer", Toast.LENGTH_SHORT).show();
                ViewUtils.setEnabled(optionsRadioGroup, false);
            }
        });

        btn_nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeQuestionIndex++;
                loadQuestionAnswerUI();
                selectedOption = "";
                optionsRadioGroup.clearCheck();
                btn_checkAnswer.setEnabled(false);
            }
        });

        optionsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                btn_checkAnswer.setEnabled(true);
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
        ViewUtils.makeViewGone(layout_Options);
        ViewUtils.makeViewVisible(exp_list_view_modulesView);
    }

    private void displayQuestionAnswerUI() {
        ViewUtils.makeViewGone(exp_list_view_modulesView);
        ViewUtils.makeViewVisible(layout_Options);
    }

    private void initializeUI() {
        exp_list_view_modulesView = findViewById(R.id.expandableListView);
        layout_Options = findViewById(R.id.options_list);
        btn_checkAnswer = findViewById(R.id.checkAmswer);
        btn_nextQuestion = findViewById(R.id.nextQuestion);
        radio_d = findViewById(R.id.d);
        radio_c = findViewById(R.id.c);
        radio_b = findViewById(R.id.b);
        radio_a = findViewById(R.id.a);
        optionsRadioGroup = findViewById(R.id.radio_group_options);
        txt_question = findViewById(R.id.question);
        txt_questionNumber = findViewById(R.id.questionNumber);
    }

    private void loadQuestionAnswerUI() {
        btn_nextQuestion.setEnabled(false);
        ViewUtils.setEnabled(optionsRadioGroup, true);
        try {
            Question question = getData()[activeGroupPosition].getChapters().get(activeChildPosition).getQuestions().get(activeQuestionIndex);
            txt_question.setText(question.getQuestion());
            radio_a.setText(question.getOptions().get(0));
            radio_b.setText(question.getOptions().get(1));
            radio_c.setText(question.getOptions().get(2));
            radio_d.setText(question.getOptions().get(3));
            txt_questionNumber.setText("Question No. " + (activeQuestionIndex + 1));
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
        optionsRadioGroup.clearCheck();
    }

    private void initiateQuestionNavigationFlow() {
        displayQuestionAnswerUI();
        btn_checkAnswer.setEnabled(false);
        activeQuestionIndex = 0;
    }

    @Override
    public void onBackPressed() {
        if (ViewUtils.isViewVisible(exp_list_view_modulesView)) {
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
