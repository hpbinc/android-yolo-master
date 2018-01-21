package org.tensorflow.demo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;
import static java.lang.Math.abs;

/**
 * Created by hance on 19/1/18.
 */

public class TTS extends Activity{

    private static TextToSpeech textToSpeech;
    static Context c;

    public static void init(final Context context) {
        c=context;

        if (textToSpeech == null) {
            textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {

                }
            });
        }
    }

    public static void speak(final String text) {




        SharedPreferences prefs = c.getSharedPreferences("hance", MODE_PRIVATE);
        String pitch_value = prefs.getString("pitch","joju");

        String str ="";

        float pitch = Float.parseFloat(pitch_value);

        if(text.equals("Wall"))
            str = str + "Obstacle ahead. Please do make a turn.";
        if(abs(pitch)>0 && abs(pitch)<=5)
            str = str + text + " is at a distance of 5 centimetre ahead.";
        else if(abs(pitch)>5 &&abs(pitch)<=10)
            str = str + text + " is at a distance of 10 centimetre ahead.";
        else if(abs(pitch)>10 &&abs(pitch)<=15)
            str = str + text + " is at a distance of 15 centimetre ahead.";
        else if(abs(pitch)>15 &&abs(pitch)<=20)
            str = str + text + " is at a distance of 20 centimetre ahead.";
        else if(abs(pitch)>20 &&abs(pitch)<=25)
            str = str + text + " is at a distance of 25 centimetre ahead.";
        else if(abs(pitch)>25 &&abs(pitch)<=30)
            str = str + text + " is at a distance of 30 centimetre ahead.";
        else if(abs(pitch)>30 &&abs(pitch)<=35)
            str = str + text + " is at a distance of 35 centimetre ahead.";
        else if(abs(pitch)>35 &&abs(pitch)<=40)
            str = str + text + " is at a distance of 40 centimetre ahead.";
        else if(abs(pitch)>40 &&abs(pitch)<=45)
            str = str + text + " is at a distance of 45 centimetre ahead.";
        else if(abs(pitch)>45 &&abs(pitch)<=50)
            str = str + text + " is at a distance of 50 centimetre ahead.";
        else if(abs(pitch)>50 &&abs(pitch)<=55)
            str = str + text + " is at a distance of 65 centimetre ahead.";
        else if(abs(pitch)>55 &&abs(pitch)<=60)
            str = str + text + " is at a distance of 70 centimetre ahead.";
        else if(abs(pitch)>60 &&abs(pitch)<=65)
            str = str + text + " is at a distance of 80 centimetre ahead.";
        else if(abs(pitch)>65 &&abs(pitch)<=70)
            str = str + text + " is at a distance of 90 centimetre ahead.";
        else if(abs(pitch)>70 &&abs(pitch)<=75)
            str = str + text + " is at a distance of 1 metre ahead.";
        else if(abs(pitch)>75 &&abs(pitch)<=80)
            str = str + text + " is at a distance of 1.1 metre ahead.";
        else if(abs(pitch)>80 &&abs(pitch)<=85)
            str = str + text + " is at a distance of 1.2 metre ahead.";
        else if(abs(pitch)>85 &&abs(pitch)<=90)
            str = str + text + " is at a distance above 1.2 metre.";

        textToSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, null);
        }






}

