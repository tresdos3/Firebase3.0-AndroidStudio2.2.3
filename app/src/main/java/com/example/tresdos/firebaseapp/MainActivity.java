package com.example.tresdos.firebaseapp;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button mAgregarDato;
    private Button mAddDate;
    private Button mVentana;
    private Button mImagen;
    private Button mVeNImagen;
    private EditText mTxTCamp;
    private TextView mText;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 2;
        private ProgressDialog cargando;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAgregarDato = (Button) findViewById(R.id.AgregarNuevo);
        mAddDate = (Button) findViewById(R.id.AddDate);
        mTxTCamp = (EditText) findViewById(R.id.txtCamp);
        mText = (TextView) findViewById(R.id.VerTexto);
        mVentana = (Button) findViewById(R.id.btnVentana);
        mVeNImagen = (Button) findViewById(R.id.VerImagen);
        mImagen = (Button) findViewById(R.id.btnImagen);
        cargando = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();

        mAgregarDato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("Nombre").setValue("Luis Juarez");
            }
        });
        mAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CajaTexto = mTxTCamp.getText().toString();
                mDatabase.child("Nombre").setValue(CajaTexto);
            }
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                recibetodo en formato json
//                mDatabase.child("Nombre").getDatabase();
//                String Recibido = dataSnapshot.getValue().toString();
//                mText.setText(Recibido);
                Map<String, String> map = (Map) dataSnapshot.getValue();
//                Map<String,String> map = (Map<String,String>)dataSnapshot.getValue(Map.class);
                String Nombre = map.get("Nombre");
                mText.setText(Nombre);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
        mVentana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
        mVeNImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            cargando.setMessage("Cargando Imagen");
            cargando.show();

            Uri uri = data.getData();
            StorageReference DireccionArchivo = mStorage.child("Fotos").child(uri.getLastPathSegment());
//            Toast.makeText(MainActivity.this, uri.toString(), Toast.LENGTH_LONG).show();
            DireccionArchivo.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(MainActivity.this, "Imagen Recibida", Toast.LENGTH_LONG).show();
                   cargando.dismiss();
                }
            });

        }
    }

}
