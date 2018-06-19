package com.example.kesho.cwsfinancepetrojet;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kesho.cwsfinancepetrojet.Model.User;
import com.google.firebase.database.ChildEventListener;
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
public class SignUp extends android.app.Fragment {
    MaterialEditText editVendorNumber,editName,editPassword,signupeditPassCheck;
    Button btnSignUpbtn;
    int counter;
    FragmentTransaction ft1;
    FragmentManager fm1;
    TextView vendorNotInDBTxtView;
    boolean valuechanged=false;
    public SignUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        editPassword=view.findViewById(R.id.signupeditPassword);
        signupeditPassCheck=view.findViewById(R.id.signupeditPassCheck);
        editVendorNumber =view.findViewById(R.id.signUpEditVendorNumber);
        btnSignUpbtn=view.findViewById(R.id.btnSignUp);
        vendorNotInDBTxtView=view.findViewById(R.id.vendorNotInDataBase);
            vendorNotInDBTxtView.setVisibility(View.INVISIBLE);
        final AlertDialog dialog = new SpotsDialog(getActivity());
        counter=0;

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_User=database.getReference("User").child("vendorsUsers");
       final DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference().child("vendosIds");
        btnSignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                counter=0;
                String vendorNumberStr=editVendorNumber.getText().toString();
                String pasStr=editPassword.getText().toString();
                if (TextUtils.isEmpty(vendorNumberStr)||TextUtils.isEmpty(signupeditPassCheck.getText().toString())||pasStr.length()<8) {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "تأكد ان كلمة السر 8 حروف او ارقام علي الاقل و ان جميع الخانات مملوءة", Toast.LENGTH_LONG).show();
                }else if (pasStr.equals("12345678")||pasStr.equals("87654321")){
                    Toast.makeText(getActivity(), "يجب الا تكون جميع الارقام متتابعة", Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                }else if (!signupeditPassCheck.getText().toString().equals(pasStr)){

                    Toast.makeText(getActivity(), "كلمة السر غير متطابقة", Toast.LENGTH_LONG).show();
                    dialog.dismiss();


                }else {
                mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                if (dataSnapshot.child("Vendor").getValue().toString().toString().equals(editVendorNumber.getText().toString()))  {

                counter++;





                                table_User.addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String VendorNumber = editVendorNumber.getText().toString();


                                        SignUp signUp = new SignUp();
                                        try {
                                            if (signUp.getUserVisibleHint()) {


                                                if (!valuechanged) {

                                                    if (dataSnapshot.child(VendorNumber).exists()) {

                                                        dialog.dismiss();
                                                        try {
                                                            Toast.makeText(getActivity(), "الحساب مسجل لدينا بالفعل", Toast.LENGTH_LONG).show();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }

                                                    } else {
                                                        valuechanged = true;
                                                        dialog.dismiss();

                                                        long editpass = Long.parseLong(editPassword.getText().toString());
                                                        User user = new User(editpass);
                                                        table_User.child(editVendorNumber.getText().toString()).setValue(user);
                                                        //         getActivity().getFragmentManager().popBackStack();
                                                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("My Data", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("name", editVendorNumber.getText().toString());
                                                        editor.putString("password", editPassword.getText().toString());
                                                        editor.apply();
                                                        SignIn fragment = new SignIn();
                                                        fm1 = getFragmentManager();
                                                        ft1 = fm1.beginTransaction();
                                                        ft1.replace(R.id.fragment_container, fragment);
                                                        ft1.commit();

                                                    }

                                                }
                                            }
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                        }
                                        dialog.dismiss();

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });



                }
                    else if (counter>0) {
                    dialog.dismiss();
                    vendorNotInDBTxtView.setVisibility(View.INVISIBLE);


                }else {
dialog.dismiss();

                        vendorNotInDBTxtView.setVisibility(View.VISIBLE);



                        vendorNotInDBTxtView.setText( "يرجي التأكد من رقم الحساب الذي تم ادخاله ");}


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
        });}
            }
        });

    return view;
    }

}
