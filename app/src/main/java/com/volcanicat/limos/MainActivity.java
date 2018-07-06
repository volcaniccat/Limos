package com.volcanicat.limos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.volcanicat.limos.Common.Common;

import Modelo.User;
import io.paperdb.Paper;

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
        Paper.init(this);
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
        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);
        
        if (user != null && pwd != null){
            if (!user.isEmpty() && !pwd.isEmpty()){
                login(user,pwd);
            }
        }
    }

    private void login(final String phone, final String pwd) {
        /*_____________________________Inicializa la base de datos____________________________________*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("users");

        final ProgressDialog mDialog = new ProgressDialog( MainActivity.this );
        mDialog.setMessage( "Por favor espere...." );
        mDialog.show();

        table_user.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*________________________El siguiente if verifica si existe el usuario______________________*/
                if (dataSnapshot.child(phone).exists()) {
                    mDialog.dismiss();
                    User user = dataSnapshot.child(phone).getValue( User.class );
                    user.setPhone(phone);//Set Phone
                    if (user.getPassword().equals(pwd)) {
                        {
                            Intent homeIntent =new Intent(MainActivity.this,Home.class);
                            Common.currentUser = user;
                            startActivity(homeIntent);
                            finish();
                        }

                    } else {
                        Toast.makeText( MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT ).show();
                    }
                }else {
                    mDialog.dismiss();
                    Toast.makeText( MainActivity.this, "Este usuario no existe", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }
}
