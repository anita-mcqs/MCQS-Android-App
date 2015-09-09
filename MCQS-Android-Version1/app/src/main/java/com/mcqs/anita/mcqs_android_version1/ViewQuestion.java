package com.mcqs.anita.mcqs_android_version1;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.View;
import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import java.io.InputStream;
import java.util.Collections;
import android.view.View.OnClickListener;

public class ViewQuestion extends AppCompatActivity {

   // private static String questionURL = "http://192.168.1.7:2010/api/fullQuestion";
   // private Question myQuestion;
    private TextView backgroundText;
    private TextView questionText;
    private Button optionOne;
    private Button optionTwo;
    private Button optionThree;
    private Button optionFour;
    private Button optionFive;
    private ArrayList<QuestionOptions> myOptions;
    private Button nextButton;
    private Button explanationButton;
    private TextView coreText;
    private TextView explainText;
    private ScrollView explainScroll;
    private ScrollView backgroundScroll;
    private boolean explainViewStatus = false;
    private ProgressBar progressBar;
    private int progressInt=0;
    private int progressMax=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);
        parseXML();
    }

    private void parseXML(){
        AssetManager assetManager = getBaseContext().getAssets();
        try{

            Field[] fields = R.raw.class.getFields();

            int choice= (int) (Math.random() * fields.length);//select random question
            int resourceID = fields[choice].getInt(fields[choice]);
            InputStream is=getResources().openRawResource(resourceID);

            //myList.get(choice)
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            QuestionXMLHandler myXMLHandler = new QuestionXMLHandler();
            xr.setContentHandler(myXMLHandler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);
            String background = myXMLHandler.getBackground();
            String question = myXMLHandler.getQuestion();
            String core = myXMLHandler.getCore();
            String explanation = myXMLHandler.getExplanation();
            myOptions = myXMLHandler.getMyOptions();
            Collections.shuffle(myOptions);//shuffle options
            QuestionOptions firstOption = myOptions.get(0);//if boolean true/false

            backgroundText = (TextView) findViewById(R.id.textViewBackground);
            questionText = (TextView) findViewById(R.id.textViewQuestion);
            optionOne = (Button) findViewById(R.id.buttonOption1);
            optionTwo = (Button) findViewById(R.id.buttonOption2);
            optionThree = (Button) findViewById(R.id.buttonOption3);
            optionFour = (Button) findViewById(R.id.buttonOption4);
            optionFive = (Button) findViewById(R.id.buttonOption5);
            nextButton = (Button) findViewById(R.id.buttonNext);
            explanationButton = (Button) findViewById(R.id.buttonExplanation);
            coreText = (TextView) findViewById(R.id.textViewCore);
            explainText = (TextView) findViewById(R.id.textViewExplanation);
            explainScroll = (ScrollView) findViewById(R.id.scrollViewEx);
            backgroundScroll = (ScrollView) findViewById(R.id.scrollView);
            progressBar = (ProgressBar) findViewById(R.id.progressBar2);

            String myQuestion = background+"\n"+question;
            String myExplanation = core+"\n"+explanation;
            myQuestion.replaceAll("\\s+", "\n");
            myQuestion.replaceAll("\\s+", System.getProperty("line.separator"));
            myExplanation.replaceAll("\\s+", "\n");
            myExplanation.replaceAll("\\s+", System.getProperty("line.separator"));
            backgroundText.setText(background);
            questionText.setText(myQuestion);


            coreText.setText(core);
            explainText.setText(myExplanation);
            optionOne.setText(myOptions.get(0).getAnswer());
            optionTwo.setText(myOptions.get(1).getAnswer());
            optionThree.setText(myOptions.get(2).getAnswer());
            optionFour.setText(myOptions.get(3).getAnswer());
            optionFive.setText(myOptions.get(4).getAnswer());

            optionOne.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean status = myOptions.get(0).isCorrectAnswer();
                    if (status == true) {
                        optionOne.setBackgroundColor(Color.parseColor("#4caf50"));
                      //  progressMax = progressMax+1;
                       // progressBar.setMax(progressMax);

                        progressInt = progressInt+1;
                        progressBar.setProgress(progressInt);
                    } else {
                        optionOne.setBackgroundColor(Color.parseColor("#F44336"));
                      //  progressMax = progressMax+1;
                      //  progressBar.setMax(progressMax);
                     //   progressInt = progressInt-1;
                     //   progressBar.setProgress(progressInt);
                        showCorrectAnswer(1);
                    }
                    explanationButton.setEnabled(true);
                    disableOptionButtons();
                }
            });
            optionTwo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean status = myOptions.get(1).isCorrectAnswer();
                    if (status == true) {
                        optionTwo.setBackgroundColor(Color.parseColor("#4caf50"));
                    //    progressMax = progressMax+1;
                     //   progressBar.setMax(progressMax);
                        progressInt = progressInt+1;
                        progressBar.setProgress(progressInt);

                    } else {
                        optionTwo.setBackgroundColor(Color.parseColor("#F44336"));
                      //  progressMax = progressMax+1;
                      //  progressBar.setMax(progressMax);
                     //   progressInt = progressInt-1;
                     //   progressBar.setProgress(progressInt);
                        showCorrectAnswer(2);
                    }
                    explanationButton.setEnabled(true);
                    disableOptionButtons();
                }
            });
            optionThree.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View view) {
                    boolean status = myOptions.get(2).isCorrectAnswer();
                    if(status==true){
                        optionThree.setBackgroundColor(Color.parseColor("#4caf50"));
                     //   progressMax = progressMax+1;
                      //  progressBar.setMax(progressMax);
                        progressInt = progressInt+1;
                        progressBar.setProgress(progressInt);
                    }
                    else{
                        optionThree.setBackgroundColor(Color.parseColor("#F44336"));
                     //   progressMax = progressMax+1;
                     //   progressBar.setMax(progressMax);
                    //    progressInt = progressInt-1;
                    //    progressBar.setProgress(progressInt);
                        showCorrectAnswer(3);
                    }
                    explanationButton.setEnabled(true);
                    disableOptionButtons();
                }
            });
            optionFour.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View view) {
                    boolean status = myOptions.get(3).isCorrectAnswer();
                    if(status==true){
                        optionFour.setBackgroundColor(Color.parseColor("#4caf50"));
                      //  progressMax = progressMax+1;
                      //  progressBar.setMax(progressMax);
                        progressInt = progressInt+1;
                        progressBar.setProgress(progressInt);
                    }
                    else{
                        optionFour.setBackgroundColor(Color.parseColor("#F44336"));
                      //  progressMax = progressMax+1;
                      //  progressBar.setMax(progressMax);
                     //   progressInt = progressInt-1;
                      //  progressBar.setProgress(progressInt);
                        showCorrectAnswer(4);
                    }
                    explanationButton.setEnabled(true);
                    disableOptionButtons();
                }
            });
            optionFive.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View view) {
                    boolean status = myOptions.get(4).isCorrectAnswer();
                    if(status==true){
                        optionFive.setBackgroundColor(Color.parseColor("#4caf50"));
                    //    progressMax = progressMax+1;
                    //    progressBar.setMax(progressMax);
                        progressInt = progressInt+1;
                        progressBar.setProgress(progressInt);
                    }
                    else{
                        optionFive.setBackgroundColor(Color.parseColor("#F44336"));
                     //   progressMax = progressMax+1;
                      //  progressBar.setMax(progressMax);
                     //   progressInt = progressInt-1;
                      //  progressBar.setProgress(progressInt);
                        showCorrectAnswer(5);
                    }
                    explanationButton.setEnabled(true);
                    disableOptionButtons();
                }
            });
            nextButton.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View view) {
                    parseXML();
                      progressMax = progressMax+1;
                     progressBar.setMax(progressMax);
                    explanationButton.setEnabled(false);
                    optionOne.setBackgroundColor(Color.parseColor("#D8D8D8"));
                    optionTwo.setBackgroundColor(Color.parseColor("#D8D8D8"));
                    optionThree.setBackgroundColor(Color.parseColor("#D8D8D8"));
                    optionFour.setBackgroundColor(Color.parseColor("#D8D8D8"));
                    optionFive.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            });

            explanationButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(explainViewStatus==false) {
                       // coreText.setVisibility(View.VISIBLE);
                        explainText.setVisibility(View.VISIBLE);
                        explainScroll.setVisibility(View.VISIBLE);
                     //   backgroundText.setVisibility(View.INVISIBLE);
                        backgroundScroll.setVisibility(View.INVISIBLE);
                        questionText.setVisibility(View.INVISIBLE);
                        optionOne.setVisibility(View.INVISIBLE);
                        optionTwo.setVisibility(View.INVISIBLE);
                        optionThree.setVisibility(View.INVISIBLE);
                        optionFour.setVisibility(View.INVISIBLE);
                        optionFive.setVisibility(View.INVISIBLE);
                        explanationButton.setText("Question");
                        explainViewStatus=true;
                    }
                    else if(explainViewStatus==true){
                      //  coreText.setVisibility(View.INVISIBLE);
                        explainText.setVisibility(View.INVISIBLE);
                        explainScroll.setVisibility(View.INVISIBLE);
                     //   backgroundText.setVisibility(View.VISIBLE);
                        backgroundScroll.setVisibility(View.VISIBLE);
                        questionText.setVisibility(View.VISIBLE);
                        optionOne.setVisibility(View.VISIBLE);
                        optionTwo.setVisibility(View.VISIBLE);
                        optionThree.setVisibility(View.VISIBLE);
                        optionFour.setVisibility(View.VISIBLE);
                        optionFive.setVisibility(View.VISIBLE);
                        explanationButton.setText("Explanation");
                        explainViewStatus=false;
                    }

                }
            });


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showCorrectAnswer(int sel){
        for (int i = 0; i < myOptions.size(); i++) {
            boolean stat = myOptions.get(i).isCorrectAnswer();
            if (stat == true) {
                int buttonID = i;
                if(sel==1){
                    if (i == 1) {
                        optionTwo.setBackgroundColor(Color.parseColor("#4caf50"));
                    } else if (i == 2) {
                        optionThree.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                    else if (i == 3) {
                        optionFour.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                    else if (i == 4) {
                        optionFive.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                }
                else if(sel==2){
                    if (i == 0) {
                        optionOne.setBackgroundColor(Color.parseColor("#4caf50"));
                    } else if (i == 2) {
                        optionThree.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                    else if (i == 3) {
                        optionFour.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                    else if (i == 4) {
                        optionFive.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                }
                else if(sel==3){
                    if (i == 0) {
                        optionOne.setBackgroundColor(Color.parseColor("#4caf50"));
                    } else if (i == 1) {
                        optionTwo.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                    else if (i == 3) {
                        optionFour.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                    else if (i == 4) {
                        optionFive.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                }
                else if(sel==4){
                    if (i == 0) {
                        optionOne.setBackgroundColor(Color.parseColor("#4caf50"));
                    } else if (i == 1) {
                        optionTwo.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                    else if (i == 2) {
                        optionThree.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                    else if (i == 4) {
                        optionFive.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                }

                else if(sel==5){
                    if (i == 0) {
                        optionOne.setBackgroundColor(Color.parseColor("#4caf50"));
                    } else if (i == 1) {
                        optionTwo.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                    else if (i == 2) {
                        optionThree.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                    else if (i == 3) {
                        optionFour.setBackgroundColor(Color.parseColor("#4caf50"));
                    }
                }
            }
        }
    }
private void disableOptionButtons(){
    optionOne.setClickable(false);
    optionTwo.setClickable(false);
    optionThree.setClickable(false);
    optionFour.setClickable(false);
    optionFive.setClickable(false);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}


