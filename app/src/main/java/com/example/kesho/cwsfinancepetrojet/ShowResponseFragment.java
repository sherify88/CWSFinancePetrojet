package com.example.kesho.cwsfinancepetrojet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowResponseFragment extends android.app.Fragment {

    TextView responseViewer;
    TextView responsetxtView;
    TextView titleResponseFrag;
    TextView msgOnDelayCases;
    TextView msgOnResponseCases;
    TextView monthDayViewId;
    TextView monthDayViewIdOnDelay;
    TextView txtDate;
    ChildEventListener childEventListener;
MainActivity mainActivity;
        static boolean dataChanged;

    public ShowResponseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_show_response, container, false);

        monthDayViewIdOnDelay=view.findViewById(R.id.monthDayViewIdOnDelay);
        monthDayViewId=view.findViewById(R.id.monthDayViewId);
        responseViewer=view.findViewById(R.id.showResponseText);
        responsetxtView=view.findViewById(R.id.responsetxtView);
        titleResponseFrag=view.findViewById(R.id.titleResponseFrag);
        msgOnDelayCases=view.findViewById(R.id.msgOnDelayCases);
        msgOnDelayCases.setVisibility(View.INVISIBLE);
        msgOnDelayCases.setVisibility(View.GONE);
        msgOnResponseCases=view.findViewById(R.id.msgOnResponseCases);
        msgOnResponseCases.setVisibility(View.INVISIBLE);
        txtDate=view.findViewById(R.id.txtDate);
        dataChanged=true;
        DatabaseReference ordersDatabase = FirebaseDatabase.getInstance().getReference().child("responses");


        childEventListener=ordersDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
              //  final SpotsDialog dialog=new SpotsDialog(getActivity());



                    Bundle bundle=getArguments();

                if (dataSnapshot.child("checkNumberForResposeTree").exists()) {


                    if (bundle.getString(ChecksAdapter.CHECK_NUMBER_PASSING).equals
                            (dataSnapshot.child("checkNumberForResposeTree").getValue().toString())) {

                        try {
                            if (dataSnapshot.child("response").exists()){

                            responseViewer.setText(dataSnapshot.child("response").getValue().toString());
                                txtDate.setText(dataSnapshot.child("responseDate").getValue().toString());
                                titleResponseFrag.setText(" عرض الرد علي طلب استلام شيك رقم : "+ bundle.getString(ChecksAdapter.CHECK_NUMBER_PASSING) );
                                msgOnResponseCases.setVisibility(View.VISIBLE);
                                monthDayViewIdOnDelay.setVisibility(View.INVISIBLE);
                //            dialog.dismiss();
                                }
                            else
                                try {
                                    responseViewer.setText(dataSnapshot.child("delayMSG").getValue().toString());
                                    msgOnDelayCases.setVisibility(View.VISIBLE);
                                    titleResponseFrag.setText(" عرض الرد علي طلب استلام شيك رقم : "+ bundle.getString(ChecksAdapter.CHECK_NUMBER_PASSING) );
                                    responsetxtView.setText("يرجي اعادة الطلب في تاريخ  : ");
                                    monthDayViewId.setVisibility(View.INVISIBLE);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            try {
                                txtDate.setText(dataSnapshot.child("delayDate").getValue().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }else {
                    //dialog.dismiss();

                }


            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Bundle bundle=getArguments();

                if (dataSnapshot.child("checkNumberForResposeTree").exists()) {


                    if (bundle.getString(ChecksAdapter.CHECK_NUMBER_PASSING).equals
                            (dataSnapshot.child("checkNumberForResposeTree").getValue().toString())) {


                        try {
                            responseViewer.setText(dataSnapshot.child("response").getValue().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        dataChanged=true;



                    }
                }else {

                }

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




   return view; }




}
