package com.example.kesho.cwsfinancepetrojet;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import dmax.dialog.SpotsDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class VendorMainPage extends android.app.Fragment {

    Button viewChecks,viewvendors,title;
    FragmentTransaction ft;

    FragmentManager fm;
    public static String TITLES;
    public String pass;
    private RecyclerView mFoodRecycle;
    DatabaseReference mDatabase;


    public VendorMainPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_vendor_main_page, container, false);
        viewChecks=view.findViewById(R.id.viewChecksId);
        viewvendors=view.findViewById(R.id.viewVendorId);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("vendosIds");
      final   TextView nametxtview=view.findViewById(R.id.mainVendorTitle);

        Bundle bundle=getArguments();
        final String string=bundle.getString(SignIn.TITLES);
        Query orderQuery= mDatabase.orderByChild("Vendor").limitToLast(1)
.equalTo(bundle.getString(SignIn.TITLES));
        final AlertDialog dialog = new SpotsDialog(getActivity());
        dialog.show();
        orderQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String key=dataSnapshot.child("Name").getValue().toString();

                nametxtview.setText(key);
                dialog.dismiss();

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

        viewChecks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        ChecksFragment fragment = new ChecksFragment();
                Bundle bundle = new Bundle();
                bundle.putString(TITLES, string);
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_container,fragment );
                fragment.setArguments(bundle);
                ft.addToBackStack(null);
                        ft.commit();
            }
        });
        viewvendors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        underReview fragment = new underReview();
                Bundle bundle = new Bundle();
                bundle.putString(TITLES, string);
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_container,fragment );
                fragment.setArguments(bundle);
                ft.addToBackStack(null);
                        ft.commit();
            }
        });



    return view;
    }


}
