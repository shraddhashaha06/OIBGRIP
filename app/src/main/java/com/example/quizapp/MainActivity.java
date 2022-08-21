package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView question, que_attempted;
    Button ans1,ans2,ans3,ans4,submit;

    int score;
    int totQue = QuestionAnswer.questions.length;
    int currentQueIndex = 0;
    int queAttempted = 0;
    String selectedAns = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = findViewById(R.id.que);
        que_attempted = findViewById(R.id.que_attempted);
        ans1 = findViewById(R.id.ans1);
        ans2 = findViewById(R.id.ans2);
        ans3 = findViewById(R.id.ans3);
        ans4 = findViewById(R.id.ans4);
        submit = findViewById(R.id.btn_submit);

        ans1.setOnClickListener(this);
        ans2.setOnClickListener(this);
        ans3.setOnClickListener(this);
        ans4.setOnClickListener(this);
        submit.setOnClickListener(this);

        loadNewQuestion();
    }

    private void loadNewQuestion() {

        que_attempted.setText("Question Attempted : "+queAttempted+"/10");

        if(currentQueIndex==9){
            submit.setText("Submit");
        }
        if(currentQueIndex > 9){
            //finishQuiz();
            showBottomSheet();
            return;
        }
        question.setText(QuestionAnswer.questions[currentQueIndex]);
        ans1.setText(QuestionAnswer.choices[currentQueIndex][0]);
        ans2.setText(QuestionAnswer.choices[currentQueIndex][1]);
        ans3.setText(QuestionAnswer.choices[currentQueIndex][2]);
        ans4.setText(QuestionAnswer.choices[currentQueIndex][3]);
    }

    private void finishQuiz() {
        String passStatus = "";

        if(score > 9*0.60){
            passStatus = "Passed";
        }
        else{
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is "+score+" out of "+totQue)
                .setPositiveButton("Restart",(dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    private void restartQuiz() {
        submit.setText("Submit and Next");
        score=0;
        currentQueIndex=0;
        queAttempted = 0;
        loadNewQuestion();
    }

    private void showBottomSheet(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet, (RelativeLayout) findViewById(R.id.ll_bottomsheet),false);

        TextView s = bottomSheetView.findViewById(R.id.id_score);
        Button restart = bottomSheetView.findViewById(R.id.restart_btn);

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartQuiz();
                bottomSheetDialog.dismiss();
            }
        });

        s.setText("Your Score is :\n"+score+"/10");

        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    @Override
    public void onClick(View view) {


        ans1.setBackgroundColor(Color.rgb(191,149,251));
        ans2.setBackgroundColor(Color.rgb(191,149,251));
        ans3.setBackgroundColor(Color.rgb(191,149,251));
        ans4.setBackgroundColor(Color.rgb(191,149,251));

        Button clickedBtn = (Button) view;


        if(clickedBtn.getId()==R.id.btn_submit) {
            if (selectedAns.equals(QuestionAnswer.Answers[currentQueIndex])) {
                score++;
            }
            currentQueIndex++;

            loadNewQuestion();
        }
        else{
            selectedAns = clickedBtn.getText().toString();
            clickedBtn.setBackgroundColor(Color.BLACK);
            queAttempted++;
        }
    }
}