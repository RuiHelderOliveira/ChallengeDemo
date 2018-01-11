package me.demo.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;

import me.demo.R;
import me.demo.classes.User;
import me.demo.utils.Util;

/**
 * Created by Rui Oliveira on 10/01/18.
 */

public class DetailActivity extends AppCompatActivity {

    private User mUser;

    private ImageView mPicture;
    private TextView mUsername;
    private TextView mEmail;
    private TextView mDateOfBirth;
    private TextView mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mUser = getIntent().getParcelableExtra(Util.USER);

        mPicture = (ImageView) findViewById(R.id.picture);
        mUsername = (TextView) findViewById(R.id.user_name);
        mEmail = (TextView) findViewById(R.id.email);
        mDateOfBirth = (TextView) findViewById(R.id.date_of_birth);
        mAddress = (TextView) findViewById(R.id.address);

        if (Util.isNetworkConnected(this))
            Picasso.with(this).load(mUser.getPicture().getLarge()).into(mPicture);
        else
            Toast.makeText(this, "Without connection", Toast.LENGTH_LONG).show();

        mUsername.setText(String.format("%s %s %s(%s)", mUser.getName().getTitle(), mUser.getName().getFirst(), mUser.getName().getLast(), mUser.getNat()));
        mEmail.setText(mUser.getEmail());

        try {
            Log.d("Date-before", mUser.getDob());
            String d = Util.convertDateFormat(mUser.getDob());
            Log.d("Date-after", d);

            mDateOfBirth.setText(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mAddress.setText(String.format("%s %s %s %s", mUser.getLocation().getStreet(), mUser.getLocation().getCity(), mUser.getLocation().getState(), mUser.getLocation().getPostcode()));

        mPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 100);
                }
            }
        });

        mEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mUser.getEmail()});
                startActivity(Intent.createChooser(intent, ""));
            }
        });

        mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address = "https://www.google.com/maps/search/?api=1&query=" + mUser.getLocation().getStreet() + "+" + mUser.getLocation().getCity() + "+" + mUser.getLocation().getState();

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(DetailActivity.this, Manifest.permission.CAMERA)) {
                    //Show an explanation to the user *asynchronously*
                    ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                } else {
                    //Never ask again and handle your app without permission.
                }
            }
        }
    }
}
