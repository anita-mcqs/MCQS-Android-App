package com.mcqs.anita.mcqs_android_version1;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import java.io.InputStream;
import java.util.List;

public class ViewQuestion extends AppCompatActivity {

   // private static String questionURL = "http://192.168.1.7:2010/api/fullQuestion";
   // private Question myQuestion;
    private TextView backgroundText;
    private List<Integer> myList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);
      // myList= listQuestions();//list of xml files
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
            ArrayList<QuestionOptions> myOptions = myXMLHandler.getMyOptions();
            QuestionOptions firstOption = myOptions.get(0);//if boolean true/false
            String myTest = "my Question: "+ background+ firstOption.getAnswer();

            backgroundText = (TextView) findViewById(R.id.textViewBackground);
            backgroundText.setText(background + fields.length);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //list of xml files
    private List<Integer> listQuestions(){
        List myList = new ArrayList<Integer>();
        myList.add(R.raw.q1);
        myList.add(R.raw.q2);
        myList.add(R.raw.q3);
        myList.add(R.raw.q4);
        myList.add(R.raw.q5);
        myList.add(R.raw.q6);
        myList.add(R.raw.q7);
        myList.add(R.raw.q8);
        myList.add(R.raw.q9);
        myList.add(R.raw.q10);
        return myList;
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


