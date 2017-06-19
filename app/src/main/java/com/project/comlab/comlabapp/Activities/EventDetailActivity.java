package com.project.comlab.comlabapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codesgood.views.JustifiedTextView;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.project.comlab.comlabapp.Adapters.RecyclerCommentsAdapter;
import com.project.comlab.comlabapp.POJO.CommentsModel;
import com.project.comlab.comlabapp.POJO.EventsModel;
import com.project.comlab.comlabapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class EventDetailActivity extends AppCompatActivity {

    TextView tv_title, tv_place, tv_place_two, tv_place_three, tv_date, tv_date_two, tv_date_three, tv_time, tv_time_two, tv_time_three, tv_members, tv_members_two, tv_members_three;
    JustifiedTextView tv_description;
    ImageView iv_image;
    Button btn_comment;
    Button event_one, event_two, event_three;
    TextInputEditText et_comment;
    ImageView qr;
    Button qr_read;

    String key = "";
    RecyclerView rv_comments;
    RecyclerCommentsAdapter adapter;
    List<CommentsModel> commentsList;

    CardView one, two, three;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private DatabaseReference refereceMembers;
    private FirebaseAuth mAuth;

    String values;

    int members, members_two, members_three;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("comments").child("events");
        mAuth = FirebaseAuth.getInstance();

        tv_title = (TextView) findViewById(R.id.event_title_detail);
        tv_place = (TextView) findViewById(R.id.event_place_detail);
        tv_place_two = (TextView) findViewById(R.id.event_place_two_detail);
        tv_place_three = (TextView) findViewById(R.id.event_place_three_detail);
        tv_date = (TextView) findViewById(R.id.event_date_detail);
        tv_date_two = (TextView) findViewById(R.id.event_date_two_detail);
        tv_date_three = (TextView) findViewById(R.id.event_date_three_detail);
        tv_time = (TextView) findViewById(R.id.event_time_detail);
        tv_time_two = (TextView) findViewById(R.id.event_time_two_detail);
        tv_time_three = (TextView) findViewById(R.id.event_time_three_detail);
        tv_description = (JustifiedTextView) findViewById(R.id.event_description_detail);
        iv_image = (ImageView) findViewById(R.id.event_image_detail);
        rv_comments = (RecyclerView) findViewById(R.id.event_rv_comments_detail);
        et_comment = (TextInputEditText) findViewById(R.id.event_et_comment_detail);
        btn_comment = (Button) findViewById(R.id.event_btn_comment_detail);
        tv_members = (TextView) findViewById(R.id.event_members_detail);
        tv_members_two = (TextView) findViewById(R.id.event_members_two_detail);
        tv_members_three = (TextView) findViewById(R.id.event_members_three_detail);

        qr = (ImageView) findViewById(R.id.event_qr_detail);
        qr_read = (Button) findViewById(R.id.event_qr_read_detail);

        event_one = (Button) findViewById(R.id.event_one_btn_detail);
        event_two = (Button) findViewById(R.id.event_two_btn_detail);
        event_three = (Button) findViewById(R.id.event_three_btn_detail);

        one = (CardView) findViewById(R.id.card_detail_one);
        two = (CardView) findViewById(R.id.card_detail_two);
        three = (CardView) findViewById(R.id.card_detail_three);



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();



        rv_comments.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        commentsList = new ArrayList<>();
        adapter = new RecyclerCommentsAdapter(EventDetailActivity.this, getApplicationContext(),commentsList);
        rv_comments.setAdapter(adapter);


        if(extras != null){
            key = extras.getString("key");
            String title = extras.getString("title");
            String place = extras.getString("place");
            String place_two = extras.getString("place_two");
            String place_three = extras.getString("place_three");
            String date = extras.getString("date");
            String date_two = extras.getString("date_two");
            String date_three = extras.getString("date_three");
            String time = extras.getString("time");
            String time_two = extras.getString("time_two");
            String time_three = extras.getString("time_three");
            String description = extras.getString("description");
            String image = extras.getString("image");
            members = extras.getInt("members");
            members_two = extras.getInt("members_two");
            members_three = extras.getInt("members_three");
            values = title + " " + place + " " + date;

            if(place != null  && place_two == null && place_three == null){
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
            }

            else if(place != null && place_two != null && place_three == null){
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.VISIBLE);
                three.setVisibility(View.GONE);
            }

            else if(place != null && place_two != null && place_three != null){
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.VISIBLE);
                three.setVisibility(View.VISIBLE);
            }

            else{
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
            }


            tv_title.setText(title);
            tv_place.setText(place);
            tv_place_two.setText(place_two);
            tv_place_three.setText(place_three);
            tv_description.setText(description);
            tv_date.setText(date);
            tv_date_two.setText(date_two);
            tv_date_three.setText(date_three);
            tv_time.setText(time);
            tv_time_two.setText(time_two);
            tv_time_three.setText(time_three);
            tv_members.setText("" + members);
            tv_members_two.setText("" + members_two);
            tv_members_three.setText("" + members_three);
            Picasso.with(getApplicationContext()).load(image).into(iv_image);
        }

         btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = et_comment.getText().toString();

                if(text.equals("")){
                    Toast.makeText(getApplicationContext(), "Campo de comentario vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser user = mAuth.getCurrentUser();
                String eOwner = user.getEmail();
                CommentsModel comment = new CommentsModel(text, eOwner);
                reference.child(key).push().setValue(comment);

            }
        });

        event_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQR();
            }
        });

        event_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        event_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        qr_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readQR();
            }
        });


         reference.child(key).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 commentsList.removeAll(commentsList);
                 for (DataSnapshot snap: dataSnapshot.getChildren()) {
                     CommentsModel comment = snap.getValue(CommentsModel.class);
                     commentsList.add(comment);
                 }

                 adapter.notifyDataSetChanged();
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });




    }

    private void generateQR(){

        refereceMembers = database.getReference("events").child(key).child("member_one");
        refereceMembers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                members = dataSnapshot.getValue(Integer.class);
                tv_members.setText("" + members);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(members <= 0){
            Toast.makeText(getApplicationContext(), "Ya no hay lugares disponibles", Toast.LENGTH_SHORT).show();
            return;
        }
        members = members - 1;
        refereceMembers.setValue(members);


        MultiFormatWriter mfw = new MultiFormatWriter();

        try{
            BitMatrix bitMatrix = mfw.encode(values, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(bitMatrix);
            qr.setImageBitmap(bitmap);
        }catch(WriterException e){
            e.printStackTrace();
        }
    }

    private void readQR(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("escanear");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){

            if(result.getContents() == null){
                Toast.makeText(getApplicationContext(), "Cancelaste el escaneo", Toast.LENGTH_SHORT).show();
            }else{
                showAlert(result.getContents());
            }

        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void showAlert(String value){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Escaneo de QR");
        View view = this.getLayoutInflater().inflate(R.layout.item_dialog_qr, null);

        final TextView tv = (TextView) view.findViewById(R.id.tv_qr);
        tv.setText(value);

        alert.setCancelable(true);

        AlertDialog dialog = alert.create();
        dialog.setView(view);

        dialog.show();
    }
}
