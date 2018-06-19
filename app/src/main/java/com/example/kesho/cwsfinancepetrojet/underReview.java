package com.example.kesho.cwsfinancepetrojet;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class underReview extends android.app.Fragment {
    private DatabaseReference mDatabase;
    private DatabaseReference ordersDatabase;
    TextView vendorTitleChecks;
    TextView textResult;
    TextView noDocForReview;
    ImageView imageView;
    ListView listView;
    List<DocUnderReview> documents;
    List<DocUnderReview> searchDocuments;
    EditText searchEditReview;
    Button searchBtn;
    UnderReviewAdapter arrayAdapter;
    DocUnderReview document;
    FragmentManager fm;
    android.app.FragmentTransaction ft;
    static AlertDialog dialog;

    public underReview() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_under_review, container, false);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("review");
        ordersDatabase= FirebaseDatabase.getInstance().getReference().child("orders");
        final Bundle bundle=getArguments();
        vendorTitleChecks=view.findViewById(R.id.vendorTitleReview);
        noDocForReview=view.findViewById(R.id.noDocForReview);
        textResult=view.findViewById(R.id.noResults);
        listView=view.findViewById(R.id.usnderReviewListViewId);
        documents=new ArrayList<>();
        searchDocuments=new ArrayList<>();
        int editpass = Integer.parseInt(bundle.getString(SignIn.TITLES));
        searchBtn=view.findViewById(R.id.btnSearchByPo);
        searchEditReview=view.findViewById(R.id.searchEditTextReview);
        dialog = new SpotsDialog(getActivity());
        dialog.show();

        Query orderQuery1= mDatabase.orderByChild("vendor").limitToLast(1)
                .equalTo(bundle.getString(SignIn.TITLES));

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.child("vendor").getValue().toString().equals(bundle.getString(SignIn.TITLES))){

                String key=dataSnapshot.child("name").getValue().toString();
                noDocForReview.setVisibility(View.INVISIBLE);
                vendorTitleChecks.setText(key);}else {
                    dialog.dismiss();

                }

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

        int editpass1 = Integer.parseInt(bundle.getString(VendorMainPage.TITLES));

        Query orderQuery= mDatabase.orderByChild("vendor").equalTo(bundle.getString(SignIn.TITLES));

        orderQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                dialog.dismiss();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    dialog.dismiss();


                    final DocUnderReview document =dataSnapshot1.getValue(DocUnderReview.class);




                        searchBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                arrayAdapter.clear();
                                textResult.setVisibility(View.INVISIBLE);

                                // Toast.makeText(getActivity(),"لا يوجد نتائج",Toast.LENGTH_SHORT).show();

                                final SpotsDialog dialog1=new SpotsDialog(getActivity());
                                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {


                                    final DocUnderReview document = dataSnapshot1.getValue(DocUnderReview.class);
                                    String checknoStr = dataSnapshot1.child("po").getValue().toString();

                                    if (checknoStr.contains(searchEditReview.getText().toString())) {

                                        if (searchDocuments.size() != 0) {
                                            searchDocuments.clear();
                                            searchDocuments.add(document);

                                        } else {

                                            searchDocuments.add(document);
                                        }
                                        arrayAdapter = new UnderReviewAdapter(getActivity(), R.layout.document_layout, searchDocuments);
                                        listView.setAdapter(arrayAdapter);
                                    }
                                    if (searchEditReview.getText().toString().isEmpty()) {
                                        arrayAdapter.clear();
                                    }

                                }
                                if (searchDocuments.size()==0||arrayAdapter.isEmpty()){
                                    textResult.setVisibility(View.VISIBLE);
                                    textResult.setText("لا يوجد نتائج لهذا البحث");

                                }
                                dialog1.dismiss();
                            }
                        });

                    arrayAdapter=new UnderReviewAdapter(getActivity(),R.layout.document_layout, searchDocuments);
                    listView.setAdapter(arrayAdapter);


                    }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;


    }




}
