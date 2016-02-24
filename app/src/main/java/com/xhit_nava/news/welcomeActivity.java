package com.xhit_nava.news;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class welcomeActivity extends Activity
{
    private ImageView wel_Image;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
 //       requestWindowFeature(Window);
        setContentView(R.layout.activity_welcome);
        wel_Image = (ImageView) findViewById(R.id.welcome_image);
        wel_Image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.welcome_pic));
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();

    }
}
