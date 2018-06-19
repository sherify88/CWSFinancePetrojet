package com.example.kesho.cwsfinancepetrojet;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import dmax.dialog.SpotsDialog;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignIn extends android.app.Fragment {
    EditText editPhone,editPassword;
    TextView gotoSignUpTextView;
    Button btnSignIn;
    FragmentTransaction ft;
    FragmentManager fm;
    public static String TITLES;
    TextView newSlogan;
    ValueEventListener valueEventListener;
    DatabaseReference table_User;
    public SignIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);
        editPassword=(MaterialEditText)view.findViewById(R.id.signineditPassword);
        editPhone=(MaterialEditText)view.findViewById(R.id.signineditPhone);
        btnSignIn=view.findViewById(R.id.btnSignIn);
        newSlogan=view.findViewById(R.id.newSlogan);
        gotoSignUpTextView=view.findViewById(R.id.gotoSignUpTextViewId);
        Typeface face=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Nabila.ttf");
        newSlogan.setTypeface(face);
           // Toast.makeText(getActivity(),"i'm here",Toast.LENGTH_SHORT).show();
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        table_User=database.getReference("User").child("vendorsUsers");

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("My Data",MODE_PRIVATE);
        String name=  sharedPreferences.getString("name","");
        String password=  sharedPreferences.getString("password","");
        editPhone.setText(name);
        editPassword.setText(password);

        SignIn signIn=new SignIn();
        if (signIn.getUserVisibleHint()) {
      //      if (!editPassword.getText().toString().isEmpty())
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog dialog = new SpotsDialog(getActivity());
                    dialog.show();

                    valueEventListener = table_User.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            dialog.dismiss();

                            if (dataSnapshot.child(editPhone.getText().toString()).exists()) {


                                try {
                                    if (dataSnapshot.child(editPhone.getText().toString()).child("password").getValue().toString()
                                            .equals(editPassword.getText().toString())) {

                                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("My Data", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("name", editPhone.getText().toString());
                                        editor.putString("password", editPassword.getText().toString());
                                        editor.apply();
                                        VendorMainPage fragment = new VendorMainPage();
                                        Bundle bundle = new Bundle();
                                        bundle.putString(TITLES, editPhone.getText().toString());
                                        fm = getFragmentManager();
                                        try {
                                            ft = fm.beginTransaction();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        ft.replace(R.id.fragment_container, fragment);
                                        fragment.setArguments(bundle);
                                        ft.addToBackStack(null);
                                        ft.commit();



                                    } else {
                                        try {
                                            Toast.makeText(getActivity(), "كلمة السر خطأ", Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(getActivity(), "لم يتم انشاء حساب لهذا المستخدم..", Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
        }

        gotoSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPassword.setText("");
                editPhone.setText("");
                SignUp fragment = new SignUp();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,fragment );
                ft.addToBackStack(null);
                ft.commit();
            }
        });


    return view;
    }



}
