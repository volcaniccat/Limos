package com.volcanicat.limos;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    /*__________________________Declaracion de variables____________________________________________*/
    Button btnSignIn, btnSignUp;
    TextView txtSlogan;
    /*________________________________Metodo onCreate_____________________________________________*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
    /*___________________Asignacion de valores a las variables____________________________________*/
        btnSignIn = (Button)findViewById( R.id.btnSingIn );
        btnSignUp = (Button)findViewById( R.id.btnSingUp );

        txtSlogan = (TextView)findViewById( R.id.txtSlogan );
    /*__________________________Declaracion de fuente tipografica_________________________________*/
        Typeface face =Typeface.createFromAsset( getAssets(),"font/Righteous-Regular.ttf" );
        txtSlogan.setTypeface( face );
    /*________________________________Metodo singIn_____________________________________________*/
        btnSignIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(MainActivity.this,SignIn.class );
                startActivity( signIn );
            }
        } );
    /*________________________________Metodo singUp_____________________________________________*/
        btnSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(MainActivity.this,SignUp.class );
                startActivity( signUp );
            }
        } );
    }
}
