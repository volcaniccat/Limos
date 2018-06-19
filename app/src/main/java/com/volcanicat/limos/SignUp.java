package com.volcanicat.limos;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import Modelo.User;

public class SignUp extends AppCompatActivity {
    MaterialEditText edtPhone,edtName,edtPassword;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );

        edtName = (MaterialEditText)findViewById( R.id.edtName );
        edtPassword = (MaterialEditText)findViewById( R.id.edtPassword );
        edtPhone = (MaterialEditText)findViewById( R.id.edtPhone );

        btnSignUp = (Button)findViewById( R.id.btnSingUp );
    /*_____________________________Inicializa la base de datos____________________________________*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("users");
    /*________________________________Metodo singUp_______________________________________________*/

        btnSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog( SignUp.this );
                mDialog.setMessage( "Por favor espere...." );
                mDialog.show();

                table_user.addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
    /*_______________El siguiente if verifica si existe el numero registrado______________________*/
                        if (dataSnapshot.child( edtPhone.getText().toString() ).exists()) {
                            mDialog.dismiss();
                            Toast.makeText( SignUp.this, "Este numero ya se encuentra registrado", Toast.LENGTH_SHORT ).show();
                        }else{
                           mDialog.dismiss();
                            User user = new User( edtName.getText().toString(),edtPassword.getText().toString(),edtPassword.getText().toString() );
                           table_user.child( edtPhone.getText().toString() ).setValue( user );
                            Toast.makeText( SignUp.this, "Se registrado correctamente", Toast.LENGTH_SHORT ).show();
                            finish();
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
