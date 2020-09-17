package com.example.parseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import static android.graphics.BitmapFactory.decodeByteArray;

public class MainActivity3 extends AppCompatActivity {
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        linearLayout = (LinearLayout) findViewById(R.id.linearL);



        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");
        query.whereEqualTo("username", username);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size() > 0){
                        for(ParseObject o : objects){
                            ParseFile file = o.getParseFile("image");
                            try { assert file != null;
                                Bitmap bitmap = decodeByteArray(file.getData(), 0, file.getData().length);
                                ImageView imgView = new ImageView(getApplicationContext());
                                imgView.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                ));
                                imgView.setImageBitmap(bitmap);
                                linearLayout.addView(imgView);
                            } catch (ParseException ex) {
                                ex.printStackTrace();
                            }

                        }


                    }else {
                        e.printStackTrace();
                    }
                }else {
                    e.printStackTrace();
                }
            }
        });

    }

}