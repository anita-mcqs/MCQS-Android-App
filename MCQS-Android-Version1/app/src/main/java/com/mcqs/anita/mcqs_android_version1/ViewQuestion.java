package com.mcqs.anita.mcqs_android_version1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewQuestion extends AppCompatActivity {

    private static String questionURL = "http://192.168.1.7:2010/api/fullQuestion";
    private JSONObject myJSON;
    private Question myQuestion;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);

        try{
            myQuestion = new DownloadQuestion().execute(questionURL).get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }


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


    private class DownloadQuestion extends AsyncTask<String, Integer, Question>{

        @Override
        protected Question doInBackground(String... urls){
            JSONParser jsonParser = new JSONParser();
            JSONObject myJSON = jsonParser.getJSONFromUrl(questionURL);
            if(myJSON==null){
                System.out.println("json null");
            }
            Question myQuestion = new Question();
            try{

                JSONArray jsonArr = myJSON.getJSONArray("optionsArray");
                String[] optionsArray = new String[jsonArr.length()];//should be 5
                for(int i=0; i<jsonArr.length(); i++){
                    optionsArray[i] = (String) jsonArr.get(i);
                }

                int correctAnswer = myJSON.getInt("correctAnswer");
                String correctNode = myJSON.getString("correctNode");
                String background = myJSON.getString("background");
                String question = myJSON.getString("question");
                String core = myJSON.getString("core");
                String explanation = myJSON.getString("explanation");
                int attemptId = myJSON.getInt("attemptId");
                String questionId = myJSON.getString("questionId");

                myQuestion.setOptionsArray(optionsArray);
                myQuestion.setCorrectAnswer(correctAnswer);
                myQuestion.setCorrectNode(correctNode);
                myQuestion.setBackground(background);
                myQuestion.setQuestion(question);
                myQuestion.setCore(core);
                myQuestion.setExplanation(explanation);
                myQuestion.setAttemptId(attemptId);
                myQuestion.setQuestionId(questionId);
                System.out.print("json: " + background);
            }

            catch(JSONException ex){
                Log.v(TAG, "JSON Exception");
                ex.printStackTrace();
            }


            return myQuestion;
        }

        protected void onPostExecute(Question result)
        {
            myQuestion = result;
        }


    }


}


