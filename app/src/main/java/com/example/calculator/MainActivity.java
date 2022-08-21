package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity {

    TextView result,sol;
    Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt0,btDot,btMul,btSub,btAdd,btDiv,btEqual,btAc,btC,btBracStart,btBracEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        sol = findViewById(R.id.sol);

        bt0 = findViewById(R.id.zero);
        bt1 = findViewById(R.id.one);
        bt2 = findViewById(R.id.two);
        bt3 = findViewById(R.id.three);
        bt4 = findViewById(R.id.four);
        bt5 = findViewById(R.id.five);
        bt6 = findViewById(R.id.six);
        bt7 = findViewById(R.id.seven);
        bt8 = findViewById(R.id.eight);
        bt9 = findViewById(R.id.nine);
        btC = findViewById(R.id.c);
        btBracStart = findViewById(R.id.start_bracket);
        btBracEnd = findViewById(R.id.end_bracket);
        btDiv = findViewById(R.id.divide);
        btMul = findViewById(R.id.multiply);
        btAdd = findViewById(R.id.add);
        btSub = findViewById(R.id.minus);
        btEqual = findViewById(R.id.equal);
    }


    public void onClick(View v){

        Button b = (Button) v;
        String btnText = b.getText().toString();
        String calculate_val = sol.getText().toString();

        if(btnText.equals("=")){
            sol.setText(result.getText());
            return;
        }
        else if(btnText.equals("AC")){
            sol.setText("");
            result.setText("0");
            return;
        }
        else if(btnText.equals("C")){
            calculate_val = calculate_val.substring(0,calculate_val.length()-1);
        }
        else{
            calculate_val = calculate_val+btnText;
        }
        sol.setText(calculate_val);

        String finalRes = getResult(calculate_val);

        if(!finalRes.equals("err")){
            result.setText(finalRes);
        }
    }

    String getResult(String data){
        try{
            Context c = Context.enter();
            c.setOptimizationLevel(-1);
            Scriptable s = c.initStandardObjects();
            String res = c.evaluateString(s,data,"Javascript",1,null).toString();

            if(res.endsWith(".0")){
                res = res.replace(".0","");
            }
            return res;

        }catch (Exception e){
            return "err";
        }
    }



}