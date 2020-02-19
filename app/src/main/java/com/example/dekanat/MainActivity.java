package com.example.dekanat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import java.io.IOException;
//import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    TextView textField;
    TextView out;
    ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textField = findViewById(R.id.textField);
        out = findViewById(R.id.out);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Привіт, скажіт шось");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                assert result != null;
                assistant(result.get(0));
                //textField.setText(result.get(0));
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void assistant(String command) {
        String text = command.toLowerCase();
        textField.setText(text);

        try {
            JSONArray jTeacher = new JSONArray("[{\"couple_id\":4,\"aud\":320},{\"couple_id\":5,\"aud\":320},{\"couple_id\":6,\"aud\":320}]");
            JSONArray jCouple = new JSONArray("[{\"aud\":307}]");
            JSONArray jAud = new JSONArray("[{\"aud\":207},{\"aud\":230},{\"aud\":232},{\"aud\":234},{\"aud\":301},{\"aud\":302},{\"aud\":303},{\"aud\":306},{\"aud\":307},{\"aud\":309},{\"aud\":310},{\"aud\":312},{\"aud\":313},{\"aud\":316},{\"aud\":317},{\"aud\":318},{\"aud\":320},{\"aud\":322},{\"aud\":324},{\"aud\":325},{\"aud\":402},{\"aud\":403}]");

            if(command.contains("де")){
                if(command.contains("пара")){
                    textField.setText("");
                    String result = jCouple.getJSONObject(0).getString("aud");
                    out.setText("У аудиторії " + result);
                }
                if(command.contains("вільна аудиторія")){
                    textField.setText("");
                    out.setText("Вільні аудиторії:");
                    for(int i = 0; i < jAud.length(); i++){
                        String result = jAud.getJSONObject(i).getString("aud");

                        out.setText(result + ", ");
                    }
                }
            }

            if(command.contains("в") || command.contains("у")){
                if(command.contains("кого")){
                    textField.setText("");
                    out.setText("у викладача Козич О.В.");
                }
            }

            if(command.contains("Козич")){
                textField.setText("");
                out.setText("Козич сьогді є\n");
                for(int i = 0; i < jTeacher.length(); i++){
                    JSONObject result = jTeacher.getJSONObject(i);
                    out.setText("у аудиторії " + result.getString("aud")
                            + " на парі " + result.getString("couple"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public String readJSONFromAsset(String jsonName) {
//        String json = null;
//        try {
//            InputStream is = getAssets().open(jsonName);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }
}
