package com.ekosp.realtimedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ekosp.realtimedatabase.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DatabaseActivity extends AppCompatActivity {

    private static final String TAG = DatabaseActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputName, inputEmail, inputUsername, inputLong, inputLat ;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        // Displaying toolbar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        txtDetails = (TextView) findViewById(R.id.txt_user);
        inputName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputUsername = (EditText) findViewById(R.id.username);
        inputLong = (EditText) findViewById(R.id.longitude);
        inputLat = (EditText) findViewById(R.id.latitude);
       // btnSave = (Button) findViewById(R.id.btn_save);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");
        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");
                String appTitle = dataSnapshot.getValue(String.class);
                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        // Save / update the user
/*        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                // Check for already existed userId
                if (TextUtils.isEmpty(userId)) {
                    createUser(name, email);
                } else {
                    updateUser(name, email);
                }
            }
        });
        toggleButton();*/

      /* if (savedInstanceState == null) {
            android.support.v4.app.Fragment fragment = new MapViewFragment();
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .commit();
        }*/
    }

    // Changing button text
    private void toggleButton() {
        if (TextUtils.isEmpty(userId)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    }

    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String email) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        User user = new User(name, email, null, null );

        mFirebaseDatabase.child(userId).setValue(user);

        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }
                Log.e(TAG, "User data is changed!" + user.name + ", " + user.email);
                // Display newly updated name and email
                txtDetails.setText(user.name + ", " + user.email);
                // clear edit text
                inputEmail.setText("");
                inputName.setText("");
                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(String name, String email) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userId).child("name").setValue(name);
        if (!TextUtils.isEmpty(email))
            mFirebaseDatabase.child(userId).child("email").setValue(email);
    }



    public void btn_add_new(View v) {
        Log.i(TAG, "tambah baru custom user");

       /* final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("");
        DatabaseReference usersRef = ref.child("users");

        Map<String, User> users = new HashMap<String, User>();
        users.put("ekosp", new User("June 23, 1912", "Alan Turing", "adadad", "adasdad"));
        usersRef.push().setValue(users);*/

       if (inputUsername.getText() == null) {
           Toast.makeText(this, "username harus diisi", Toast.LENGTH_SHORT).show();
       } else {

           final FirebaseDatabase database = FirebaseDatabase.getInstance();
           DatabaseReference ref = database.getReference("users");

           String usr = inputUsername.getText().toString();

           Map<String, Object> nicknames = new HashMap<String, Object>();
           nicknames.put(usr + "/" + User.stringEmail(), "hello@555555.com");
           nicknames.put(usr + "/" + User.stringName(), "Eko Setyo 555555");
           nicknames.put(usr + "/" + User.stringLongitude(), "-10");
           nicknames.put(usr + "/" + User.stringLatitude(), "106");
           ref.updateChildren(nicknames);
       }
    }

    public void btn_update(View v) {
        Log.i(TAG, "update custom user");
        if (inputUsername.getText() == null) {
            Toast.makeText(this, "username harus diisi", Toast.LENGTH_SHORT).show();
        } else {

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("users");

            String usr = inputUsername.getText().toString();

            Map<String, Object> nicknames = new HashMap<String, Object>();
            nicknames.put(usr + "/" + User.stringEmail(), "hello@555555.com");
            nicknames.put(usr + "/" + User.stringName(), "Eko Setyo 555555");
            nicknames.put(usr + "/" + User.stringLongitude(), "-10");
            nicknames.put(usr + "/" + User.stringLatitude(), "106");
            ref.updateChildren(nicknames);
        }
    }

    public void btn_update_lokasi (View v) {
        Log.i(TAG, "update lokasi user");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        String usr = inputUsername.getText().toString();
        String longi = usr+"/"+User.stringLongitude();
        String lati = usr+"/"+User.stringLatitude();

        Log.i(TAG, "nilai lati : "+lati);
        Log.i(TAG, "nilai longi : "+longi);

        Map<String, Object> nicknames = new HashMap<String, Object>();
        nicknames.put(longi, inputLong.getText().toString());
        nicknames.put(lati, inputLat.getText().toString());
        ref.updateChildren(nicknames);


    }

    public void btn_get_all (View v) {
        Log.i(TAG, "get all data custom user");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        Query queryRef = ref.orderByChild("email");

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User facts = snapshot.getValue(User.class);
                System.out.println(snapshot.getKey() + " was " + facts.email + " meters tall");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }

    }