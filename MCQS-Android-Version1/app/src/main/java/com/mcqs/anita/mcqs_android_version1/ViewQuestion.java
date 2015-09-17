package com.mcqs.anita.mcqs_android_version1;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.graphics.Color;

import java.net.URI;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.view.View.OnClickListener;

public class ViewQuestion extends AppCompatActivity {

    // private static String questionURL = "http://192.168.1.7:2010/api/fullQuestion";
    private ArrayList<Question> questionList = new ArrayList<Question>();
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
    private ScrollView parentScroll;
    private String viewStatus = "question";
    private ProgressBar progressBar;
    private int progressInt=0;
    private int progressMax=0;
    private String myJSONString = "";
    private ImageView questionImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);
        checkFiles();//if there don't copy file
        myJSONString =  readFromFile();

        parseJSONFile(myJSONString);
       // System.out.println("question array: " + questionList.size());
       // for(int i=0; i<questionList.size(); i++){
        //    System.out.println("Question: " + questionList.get(i).getQuestion() + " Image Path " + questionList.get(i).getImagePath());
         //   System.out.println("Correct Answer: " + questionList.get(i).getQuestionOptions()[0].getAnswer());
       // }
        displayQuestions();
        //parseXML();
    }






    private void parseJSONFile(String myJSONString) {
        //questionList - array of questions
        int jsonArraySize;

        JsonParser jsonParser = new JsonParser();
        jsonArraySize = jsonParser.parse(myJSONString).getAsJsonArray().size();
       // System.out.println("length " + jsonArraySize);





        //images
        int count = 0;
        String toPathImages = "/data/data/" + getPackageName() + "/files/images";
        File f = new File(toPathImages);
        File[] files = f.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                count++;
                File file = files[i];
              //  System.out.println("file " + file);
            }
          //  System.out.println("count: " + count);
        } else {
            System.out.println("null images folder????");
        }






        //JSON
        for(int i=0; i<jsonArraySize;i++){

            Question myQuestion = new Question();
            QuestionOptions[] questionOptions = new QuestionOptions[5];
            String background = "";
            String question = "";
            String core = "";
            String explanation = "";

            File file = files[i];

            String path = file.getPath();
          //  System.out.println("file JSON " + path);
            myQuestion.setImagePath(path);



            myQuestion.setIndex(i);

            // System.out.println("Index: " + i +" Length: "+length+ " Json: " + jsonParser.parse(myJSONString).getAsJsonArray().get(i).toString());


            //correct answer
            String correctAnswer = jsonParser.parse(myJSONString)
                    .getAsJsonArray().get(i).getAsJsonObject().getAsJsonArray("options").get(0).getAsJsonObject().getAsJsonArray("correctAnswers").get(0)
                    .getAsJsonObject().getAsJsonArray("correctAnswer").get(0).getAsJsonObject().get("_").getAsString();

            System.out.println("correct: " + correctAnswer);

            QuestionOptions corr = new QuestionOptions(correctAnswer, true);
           // System.out.println("string: " + test);
            questionOptions[0] = corr;

            //incorrect answers
            int length = jsonParser.parse(myJSONString)
                    .getAsJsonArray().get(i).getAsJsonObject().getAsJsonArray("options").get(0).getAsJsonObject().getAsJsonArray("incorrectAnswers").get(0)
                    .getAsJsonObject().getAsJsonArray("incorrectAnswer").size();
           // System.out.println("length: " + length);
            for(int j=0; j<length; j++){


                String myIncorrectOption = jsonParser.parse(myJSONString)
                        .getAsJsonArray().get(i).getAsJsonObject().getAsJsonArray("options").get(0).getAsJsonObject().getAsJsonArray("incorrectAnswers").get(0)
                        .getAsJsonObject().getAsJsonArray("incorrectAnswer").get(j).getAsJsonObject().get("_").getAsString();


                QuestionOptions incorr = new QuestionOptions(myIncorrectOption, false);
                questionOptions[j+1] = incorr;
            }

            myQuestion.setQuestionOptions(questionOptions);

            //explanation
            explanation = jsonParser.parse(myJSONString)
                    .getAsJsonArray().get(i).getAsJsonObject().getAsJsonArray("explanation").get(0).getAsString();
            myQuestion.setExplanation(explanation);

            //core
            core = jsonParser.parse(myJSONString)
                    .getAsJsonArray().get(i).getAsJsonObject().getAsJsonArray("core").get(0).getAsString();
            myQuestion.setCore(core);

            //question
            question = jsonParser.parse(myJSONString)
                    .getAsJsonArray().get(i).getAsJsonObject().getAsJsonArray("question").get(0).getAsString();
            myQuestion.setQuestion(question);
          //  System.out.println("q " + question);

            //background
            background = jsonParser.parse(myJSONString)
                    .getAsJsonArray().get(i).getAsJsonObject().getAsJsonArray("background").get(0).getAsString();
            myQuestion.setBackground(background);

            questionList.add(myQuestion);

        }
    }










    private String readFromFile() {
        String ret = "";
        String toPath = "/data/data/" + getPackageName();
        try {
            InputStream inputStream = openFileInput("myJSON.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }





    private void checkFiles() {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        AssetManager assetMgr = getAssets();
        InputStream in = null;
        OutputStream out = null;
        String toPath = "/data/data/" + getPackageName()+"/files/";
        String toPathImages = "/data/data/" + getPackageName()+"/files/images";
        Boolean fileThere = fileExistance("myJSON.txt");
        if(fileThere==true)
        {
            // System.out.println("not empty");
        }
        else
        {
            // System.out.print("empty folder");
            copyAssetFolder(assetMgr, "json", toPath);
            copyAssetFolder(assetMgr, "myImages", toPathImages);
        }

    }


    private static boolean copyAssetFolder(AssetManager assetManager,
                                           String fromAssetPath, String toPath) {
        try {
            String[] files = assetManager.list(fromAssetPath);
            new File(toPath).mkdirs();
            boolean res = true;
            for (String file : files)
                if (file.contains("."))
                    res &= copyAsset(assetManager,
                            fromAssetPath + "/" + file,
                            toPath + "/" + file);
                else
                    res &= copyAssetFolder(assetManager,
                            fromAssetPath + "/" + file,
                            toPath + "/" + file);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    private boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }












    private static boolean copyAsset(AssetManager assetManager,
                                     String fromAssetPath, String toPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            String[] fileNames = assetManager.list(fromAssetPath);
            in = assetManager.open(fromAssetPath);
            new File(toPath).createNewFile();
            out = new FileOutputStream(toPath);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }





    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }





private void displayQuestions(){

    int questionNumbers = questionList.size();
    int choice = (int) (Math.random() * questionNumbers);//random question
    Question displayQuestion = questionList.get(choice);
    String displayBackgroundString = displayQuestion.getBackground();
    String displayQuestionString = displayQuestion.getQuestion();
    String displayCoreString = displayQuestion.getCore();
    String displayExplanationString = displayQuestion.getExplanation();
    String displayImagePath = displayQuestion.getImagePath();
    QuestionOptions[] questionOptions = displayQuestion.getQuestionOptions();
    myOptions = new ArrayList<QuestionOptions>(Arrays.asList(questionOptions));
    Collections.shuffle(myOptions);//shuffle options

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
    questionImage = (ImageView) findViewById(R.id.imageView);


    //  parentScroll = (ScrollView) findViewById(R.id.scrollViewParent);
    //progressBar = (ProgressBar) findViewById(R.id.progressBar2);

          /*  //scrollbar within a scroll bar
            parentScroll.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event){
                    findViewById(R.id.scrollViewEx).getParent().requestDisallowInterceptTouchEvent(false);
                    findViewById(R.id.scrollView).getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            });
            backgroundScroll.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event){
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
            explainScroll.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event){
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

*/
    File file = new File(displayImagePath);
    //  URI uri = Uri.fromFile(file);
    questionImage.setImageURI(Uri.fromFile(file));
    int height = questionImage.getMaxHeight();
    int width = questionImage.getMaxWidth();
    System.out.println("Image Height " + height + " Width " + width);

    String myQuestion = displayBackgroundString+"\n"+displayQuestionString;
    String myExplanation = displayCoreString+"\n"+displayExplanationString;
    myQuestion.replaceAll("\\s+", "\n");
    myQuestion.replaceAll("\\s+", System.getProperty("line.separator"));
    myExplanation.replaceAll("\\s+", "\n");
    myExplanation.replaceAll("\\s+", System.getProperty("line.separator"));
    backgroundText.setText(displayBackgroundString);
    questionText.setText(myQuestion);
    coreText.setText(displayCoreString);
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
                // progressMax = progressMax+1;
                //  progressBar.setMax(progressMax);

                //   progressInt = progressInt+1;
                //   progressBar.setProgress(progressInt);
            } else {
                optionOne.setBackgroundColor(Color.parseColor("#F44336"));
                //   progressMax = progressMax+1;
                //  progressBar.setMax(progressMax);
                //   progressInt = progressInt-1;
                //   progressBar.setProgress(progressInt);
                showCorrectAnswer(1);
            }
         //   explanationButton.setEnabled(true);
            viewStatus = "explanation";
            explanationButton.setText("Explanation");
            disableOptionButtons();
        }
    });
    optionTwo.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean status = myOptions.get(1).isCorrectAnswer();
            if (status == true) {
                optionTwo.setBackgroundColor(Color.parseColor("#4caf50"));
              //  progressMax = progressMax+1;
              //  progressBar.setMax(progressMax);
              //  progressInt = progressInt+1;
              //  progressBar.setProgress(progressInt);

            } else {
                optionTwo.setBackgroundColor(Color.parseColor("#F44336"));
              //  progressMax = progressMax+1;
              //  progressBar.setMax(progressMax);
                //   progressInt = progressInt-1;
                //   progressBar.setProgress(progressInt);
                showCorrectAnswer(2);
            }
         //   explanationButton.setEnabled(true);
            viewStatus = "explanation";
            explanationButton.setText("Explanation");
            disableOptionButtons();
        }
    });

    optionThree.setOnClickListener(new OnClickListener(){
        @Override
        public void onClick(View view) {
            boolean status = myOptions.get(2).isCorrectAnswer();
            if(status==true){
                optionThree.setBackgroundColor(Color.parseColor("#4caf50"));
               // progressMax = progressMax+1;
             //   progressBar.setMax(progressMax);
              //  progressInt = progressInt+1;
              //  progressBar.setProgress(progressInt);
            }
            else{
                optionThree.setBackgroundColor(Color.parseColor("#F44336"));
               // progressMax = progressMax+1;
              //  progressBar.setMax(progressMax);
                //    progressInt = progressInt-1;
                //    progressBar.setProgress(progressInt);
                showCorrectAnswer(3);
            }
           // explanationButton.setEnabled(true);
            viewStatus = "explanation";
            explanationButton.setText("Explanation");
            disableOptionButtons();
        }
    });

    optionFour.setOnClickListener(new OnClickListener(){
        @Override
        public void onClick(View view) {
            boolean status = myOptions.get(3).isCorrectAnswer();
            if(status==true){
                optionFour.setBackgroundColor(Color.parseColor("#4caf50"));
             //   progressMax = progressMax+1;
              //  progressBar.setMax(progressMax);
             //   progressInt = progressInt+1;
              //  progressBar.setProgress(progressInt);
            }
            else{
                optionFour.setBackgroundColor(Color.parseColor("#F44336"));
             //   progressMax = progressMax+1;
              //  progressBar.setMax(progressMax);
                //   progressInt = progressInt-1;
                //  progressBar.setProgress(progressInt);
                showCorrectAnswer(4);
            }
          //  explanationButton.setEnabled(true);
            viewStatus = "explanation";
            explanationButton.setText("Explanation");
            disableOptionButtons();
        }
    });
    optionFive.setOnClickListener(new OnClickListener(){
        @Override
        public void onClick(View view) {
            boolean status = myOptions.get(4).isCorrectAnswer();
            if(status==true){
                optionFive.setBackgroundColor(Color.parseColor("#4caf50"));
              //  progressMax = progressMax+1;
              //  progressBar.setMax(progressMax);
              //  progressInt = progressInt+1;
              //  progressBar.setProgress(progressInt);
            }
            else{
                optionFive.setBackgroundColor(Color.parseColor("#F44336"));
             //   progressMax = progressMax+1;
            //    progressBar.setMax(progressMax);
                //   progressInt = progressInt-1;
                //  progressBar.setProgress(progressInt);
                showCorrectAnswer(5);
            }
           // explanationButton.setEnabled(true);
            viewStatus = "explanation";
            explanationButton.setText("Explanation");
            disableOptionButtons();
        }
    });

    nextButton.setOnClickListener(new OnClickListener(){
        @Override
        public void onClick(View view) {
            displayQuestions();
          //  parseXML();

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
           questionImage.setVisibility(View.INVISIBLE);
            explanationButton.setText("Image");
            viewStatus = "question";
          //  explainViewStatus=false;
           // imageViewStatus=false;

          //  progressMax = progressMax+1;
           // progressBar.setMax(progressMax);
           // explanationButton.setEnabled(false);
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
            Boolean status= true;

            if(viewStatus=="question"){
                explainText.setVisibility(View.INVISIBLE);
                explainScroll.setVisibility(View.INVISIBLE);
                // questionImage.setVisibility(View.INVISIBLE);
                questionImage.setVisibility(View.VISIBLE);
                //   backgroundText.setVisibility(View.INVISIBLE);
                backgroundScroll.setVisibility(View.INVISIBLE);
                questionText.setVisibility(View.INVISIBLE);
                optionOne.setVisibility(View.INVISIBLE);
                optionTwo.setVisibility(View.INVISIBLE);
                optionThree.setVisibility(View.INVISIBLE);
                optionFour.setVisibility(View.INVISIBLE);
                optionFive.setVisibility(View.INVISIBLE);
                explanationButton.setText("Question");
                viewStatus = "image";
              //  status = false;
            }
            if(status==false){
                explainText.setVisibility(View.INVISIBLE);
                explainScroll.setVisibility(View.INVISIBLE);
              //  questionImage.setVisibility(View.VISIBLE);
                questionImage.setVisibility(View.INVISIBLE);
                //   backgroundText.setVisibility(View.INVISIBLE);
                backgroundScroll.setVisibility(View.VISIBLE);
                questionText.setVisibility(View.VISIBLE);
                optionOne.setVisibility(View.VISIBLE);
                optionTwo.setVisibility(View.VISIBLE);
                optionThree.setVisibility(View.VISIBLE);
                optionFour.setVisibility(View.VISIBLE);
                optionFive.setVisibility(View.VISIBLE);
                explanationButton.setText("Image");
                //viewStatus = "explanation";
            }









        }
    });
}

private void questionView(){

System.out.println("Unanswered Question");
    explainText.setVisibility(View.INVISIBLE);
    explainScroll.setVisibility(View.INVISIBLE);
    //   backgroundText.setVisibility(View.VISIBLE);
    backgroundScroll.setVisibility(View.VISIBLE);
     questionImage.setVisibility(View.INVISIBLE);
    //  questionImage.setVisibility(View.VISIBLE);
    questionText.setVisibility(View.VISIBLE);
    optionOne.setVisibility(View.VISIBLE);
    optionTwo.setVisibility(View.VISIBLE);
    optionThree.setVisibility(View.VISIBLE);
    optionFour.setVisibility(View.VISIBLE);
    optionFive.setVisibility(View.VISIBLE);
    viewStatus = "question";
    explanationButton.setText("Image");
}

    private void questionAnsweredView(){

        System.out.println("Answered Question");
        explainText.setVisibility(View.INVISIBLE);
        explainScroll.setVisibility(View.INVISIBLE);
        //   backgroundText.setVisibility(View.VISIBLE);
        backgroundScroll.setVisibility(View.VISIBLE);
        questionImage.setVisibility(View.INVISIBLE);
        //  questionImage.setVisibility(View.VISIBLE);
        questionText.setVisibility(View.VISIBLE);
        optionOne.setVisibility(View.VISIBLE);
        optionTwo.setVisibility(View.VISIBLE);
        optionThree.setVisibility(View.VISIBLE);
        optionFour.setVisibility(View.VISIBLE);
        optionFive.setVisibility(View.VISIBLE);

    }


/*

    //to delete when JSON sort out!!
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
            //  parentScroll = (ScrollView) findViewById(R.id.scrollViewParent);
            //progressBar = (ProgressBar) findViewById(R.id.progressBar2);

           //scrollbar within a scroll bar
            parentScroll.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event){
                    findViewById(R.id.scrollViewEx).getParent().requestDisallowInterceptTouchEvent(false);
                    findViewById(R.id.scrollView).getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            });
            backgroundScroll.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event){
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
            explainScroll.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event){
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });




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
                        progressMax = progressMax+1;
                        progressBar.setMax(progressMax);

                        progressInt = progressInt+1;
                        progressBar.setProgress(progressInt);
                    } else {
                        optionOne.setBackgroundColor(Color.parseColor("#F44336"));
                        progressMax = progressMax+1;
                        progressBar.setMax(progressMax);
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
                        progressMax = progressMax+1;
                        progressBar.setMax(progressMax);
                        progressInt = progressInt+1;
                        progressBar.setProgress(progressInt);

                    } else {
                        optionTwo.setBackgroundColor(Color.parseColor("#F44336"));
                        progressMax = progressMax+1;
                        progressBar.setMax(progressMax);
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
                        progressMax = progressMax+1;
                        progressBar.setMax(progressMax);
                        progressInt = progressInt+1;
                        progressBar.setProgress(progressInt);
                    }
                    else{
                        optionThree.setBackgroundColor(Color.parseColor("#F44336"));
                        progressMax = progressMax+1;
                        progressBar.setMax(progressMax);
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
                        progressMax = progressMax+1;
                        progressBar.setMax(progressMax);
                        progressInt = progressInt+1;
                        progressBar.setProgress(progressInt);
                    }
                    else{
                        optionFour.setBackgroundColor(Color.parseColor("#F44336"));
                        progressMax = progressMax+1;
                        progressBar.setMax(progressMax);
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
                        progressMax = progressMax+1;
                        progressBar.setMax(progressMax);
                        progressInt = progressInt+1;
                        progressBar.setProgress(progressInt);
                    }
                    else{
                        optionFive.setBackgroundColor(Color.parseColor("#F44336"));
                        progressMax = progressMax+1;
                        progressBar.setMax(progressMax);
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



    */
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


