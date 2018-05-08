package com.example.djoha.lathing.Home;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.djoha.lathing.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TambahLelangActivity extends AppCompatActivity implements IPickResult {
    Button btn_ambil_gambar,btn_post;
    EditText et_nama_barang,et_deskripsi,et_harga;

    Bitmap mGallery;
    Uri mUri, dlUri;
    String mPath;
    String i;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = mDatabase.getReference();
    String id_pelelang,nama_pelelang;
    FirebaseStorage mStorage = FirebaseStorage.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_lelang);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                id_pelelang= null;
                nama_pelelang = null;
            } else {
                id_pelelang= extras.getString("id_pelelang");
                nama_pelelang = extras.getString("nama_pelelang");
            }
        }
        btn_ambil_gambar = findViewById(R.id.btn_ambil_gambar);
        btn_post = findViewById(R.id.btn_post);

        et_nama_barang = findViewById(R.id.tv_nama_barang);
        et_deskripsi = findViewById(R.id.tv_deskripsi);
        et_harga = findViewById(R.id.tv_harga_awal);

        btn_ambil_gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog.build(new PickSetup()).show(TambahLelangActivity.this);
            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
            }
        });
    }



    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            mGallery = r.getBitmap();
            mUri = r.getUri();
            mPath = r.getPath();
            btn_ambil_gambar.setText("Foto Sudah didapat");
            Toast.makeText(this, r.getUri() + "  " + r.getPath(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void uploadData(){

       i = mRef.push().getKey();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                uploadGallery(mGallery,i+"");
                Log.e("Messs",i+"");
            }
        }, 1000);
        Log.e("Messs",i+"");
        uploadForda(i);
        finish();
    }

    public void uploadForda(String id){

        Date today = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String awalbid = formatter.format(today);

        String deskripsi = et_deskripsi.getText().toString();
        int harga_awal = Integer.parseInt(et_harga.getText().toString());
        String harga_akhir = "-";
        String nama_barang = et_nama_barang.getText().toString();

        //Toast.makeText(this, awalbid+" "+deskripsi+" "+harga_awal+" "+harga_akhir+" "+nama_barang+" "+id_pelelang+" "+nama_pelelang Toast.LENGTH_SHORT).show();

        Map<String, Object> gallery = new HashMap<String, Object>();
        gallery.put("/lelang/" + id + "/awal_bid", awalbid);
        gallery.put("/lelang/" + id + "/akhir_bid", awalbid);
        gallery.put("/lelang/" + id + "/gambar", "foto_lelang/"+id + ".jpg");
        gallery.put("/lelang/" + id + "/deskripsi", deskripsi);
        gallery.put("/lelang/" + id + "/harga_awal", harga_awal);
        gallery.put("/lelang/" + id + "/harga_akhir", harga_akhir);
        gallery.put("/lelang/" + id + "/id_pelelang", id_pelelang);
        gallery.put("/lelang/" + id + "/nama_pelelang", nama_pelelang);
        gallery.put("/lelang/" + id + "/nama_barang", nama_barang);
        mRef.updateChildren(gallery);
    }

    public void uploadGallery(Bitmap foto, String id){
        StorageReference storageRef = mStorage.getReferenceFromUrl("gs://bidme-6294f.appspot.com");
        StorageReference mountainImagesRef = storageRef.child("foto_lelang/" + id + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                dlUri = downloadUrl;
                Log.v("downloadUrl-->", "" + downloadUrl);
            }
        });
    }
}
