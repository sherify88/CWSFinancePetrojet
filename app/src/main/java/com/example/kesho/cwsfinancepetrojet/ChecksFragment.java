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
public class ChecksFragment extends android.app.Fragment {
    private DatabaseReference mDatabase;
    private DatabaseReference ordersDatabase;
    TextView vendorTitleChecks;
    EditText searchEditTextResponse;
    EditText editSearchByPo;
    ImageView imageView;
    ListView listView;
    List<Document> documents;
    List<Document> searchDocuments;
    ChecksAdapter arrayAdapter;
    Button btnSearchBycheckNumberResponse;
    Button btnSearchByPo;
    Document document;
    FragmentManager fm;
    android.app.FragmentTransaction ft;
   static AlertDialog dialog;

    public ChecksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_checks, container, false);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("vendors");
        ordersDatabase= FirebaseDatabase.getInstance().getReference().child("orders");
        final Bundle bundle=getArguments();
        vendorTitleChecks=view.findViewById(R.id.vendorTitleChecks);
        editSearchByPo=view.findViewById(R.id.editSearchByPo);
        btnSearchByPo=view.findViewById(R.id.btnSearchByPo);
        searchEditTextResponse=view.findViewById(R.id.searchEditTextResponse);
        listView=view.findViewById(R.id.list_view);
        btnSearchBycheckNumberResponse=view.findViewById(R.id.btnSearchBycheckNumberResponse);
        documents=new ArrayList<>();
        searchDocuments=new ArrayList<>();
        dialog = new SpotsDialog(getActivity());
        dialog.show();

       // Toast.makeText(getActivity(),bundle.getString(VendorMainPage.TITLES),Toast.LENGTH_SHORT).show();






        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                dialog.dismiss();
                documents.clear();
                searchDocuments.clear();
                try {
                    arrayAdapter.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){


                    try {
                        if (dataSnapshot1.child("vendor").getValue().toString().equals(bundle.getString(VendorMainPage.TITLES))){

                        Document document =dataSnapshot1.getValue(Document.class);
                       // if (document.getAmount()<80000){

                        documents.add(document);

                            btnSearchBycheckNumberResponse.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final SpotsDialog dialog1=new SpotsDialog(getActivity());
                                    arrayAdapter.clear();
                                    documents.clear();
                                    try {
                                        searchDocuments.clear();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                                        final Document order = dataSnapshot1.getValue(Document.class);
                                        String checknoStr = dataSnapshot1.child("check").getValue().toString();

                                        if (checknoStr.contains(searchEditTextResponse.getText().toString())){

                                            if (dataSnapshot1.child("vendor").getValue().toString().equals(bundle.getString(VendorMainPage.TITLES))){

                                                searchDocuments.add(order);}
                                            arrayAdapter=new ChecksAdapter(getActivity(),R.layout.document_layout, searchDocuments);
                                            listView.setAdapter(arrayAdapter);}
                                    }
                                    dialog1.dismiss();
                                }
                            });

                            btnSearchByPo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final SpotsDialog dialog1=new SpotsDialog(getActivity());
                                    arrayAdapter.clear();
                                    documents.clear();
                                    try {
                                        searchDocuments.clear();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                                        final Document order = dataSnapshot1.getValue(Document.class);
                                        String checknoStr = dataSnapshot1.child("po").getValue().toString();

                                        if (checknoStr.contains(editSearchByPo.getText().toString())){

                                            if (dataSnapshot1.child("vendor").getValue().toString().equals(bundle.getString(VendorMainPage.TITLES))){

                                                searchDocuments.add(order);}
                                            arrayAdapter=new ChecksAdapter(getActivity(),R.layout.document_layout, searchDocuments);
                                            listView.setAdapter(arrayAdapter);}
                                    }
                                    dialog1.dismiss();
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (documents.isEmpty()){

                    vendorTitleChecks.setText("لا يوجد شيكات لكم بالخزينة حاليا");
                }else {
                    try {
                        vendorTitleChecks.setText(documents.get(0).getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
             //   }
                try {
                    arrayAdapter=new ChecksAdapter(getActivity(),R.layout.document_layout,documents);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                listView.setAdapter(arrayAdapter);



//

//


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference vendorsDatabase = FirebaseDatabase.getInstance().getReference().child("vendors");

        vendorsDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

//                ChecksFragment checksFragment=new ChecksFragment();
//                getFragmentManager().beginTransaction().detach(checksFragment).attach(checksFragment).commit();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });



        return view;


    }




}

