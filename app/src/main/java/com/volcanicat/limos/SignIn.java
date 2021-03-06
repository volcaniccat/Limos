package com.volcanicat.limos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.volcanicat.limos.Common.Common;

import Modelo.User;
import io.paperdb.Paper;

public class SignIn extends AppCompatActivity {
    /*__________________________Declaracion de variables____________________________________________*/
    EditText edtPhone,edtPassword;
    Button btnSignIn;
    CheckBox ckbRecuerdame;
    /*________________________________Metodo onCreate_____________________________________________*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_in );
    /*___________________Asignacion de valores a las variables____________________________________*/
        edtPassword = (MaterialEditText)findViewById( R.id.edtPassword );
        edtPhone = (MaterialEditText)findViewById( R.id.edtPhone );
        btnSignIn = (Button)findViewById( R.id.btnSingIn );
        ckbRecuerdame = findViewById(R.id.ckbRecuerdame);
        Paper.init(this);
    /*_____________________________Inicializa la base de datos____________________________________*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("users");
    /*________________________________Metodo singIn_______________________________________________*/
    btnSignIn.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(ckbRecuerdame.isChecked()){
                Paper.book().write(Common.USER_KEY,edtPhone.getText().toString());
                Paper.book().write(Common.PWD_KEY,edtPassword.getText().toString());
            }
            final ProgressDialog mDialog = new ProgressDialog( SignIn.this );
            mDialog.setMessage( "Por favor espere...." );
            mDialog.show();

            table_user.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
     /*________________________El siguiente if verifica si existe el usuario______________________*/
                    if (dataSnapshot.child( edtPhone.getText().toString() ).exists()) {
                        mDialog.dismiss();
                        User user = dataSnapshot.child( edtPhone.getText().toString() ).getValue( User.class );
                        user.setPhone(edtPhone.getText().toString());//Set Phone
                        if (user.getPassword().equals( edtPassword.getText().toString() )) {
                            {
                                Intent homeIntent =new Intent(SignIn.this,Home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();
                            }

                        } else {
                            Toast.makeText( SignIn.this, "Contraseña incorrecta", Toast.LENGTH_SHORT ).show();
                        }
                    }else {
                        mDialog.dismiss();
                        Toast.makeText( SignIn.this, "Este usuario no existe", Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            } );
        }
    } );

    }
}
