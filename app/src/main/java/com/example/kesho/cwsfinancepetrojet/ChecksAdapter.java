package com.example.kesho.cwsfinancepetrojet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * Created by kesho on 3/7/2018.
 */

public class ChecksAdapter extends ArrayAdapter<Document> {


    Activity context;
    List<Document> documentList;
    int recource;
    FragmentTransaction ft;
    FragmentManager fm;
    public static String CHECK_NUMBER_PASSING;


    public ChecksAdapter(@NonNull Activity context, int resource, @NonNull List<Document> objects) {
        super(context, resource, objects);
        this.context=context;
        this.documentList =objects;
        this.recource=resource;
        documentList =objects;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater=context.getLayoutInflater();
        final View view=  inflater.inflate(recource,parent,false);
        Button cancelCheckBtn=view.findViewById(R.id.cancelCheckBtn);

        TextView textView=view.findViewById(R.id.checkamount);
        textView.setText(documentList.get(position).getAmount());
        TextView textView2=view.findViewById(R.id.checkNo);
        textView2.setText(documentList.get(position).getCheck());

        final TextView docStatus=view.findViewById(R.id.docStatus);
        docStatus.setVisibility(View.INVISIBLE);
        final TextView docStatusCode=view.findViewById(R.id.docStatusCode);
        docStatusCode.setVisibility(View.INVISIBLE);
        final TextView backDate=view.findViewById(R.id.backDate);
        backDate.setVisibility(View.INVISIBLE);
        final TextView backDateCode=view.findViewById(R.id.backDateCode);
        backDateCode.setVisibility(View.INVISIBLE);
        textView2.setText(documentList.get(position).getCheck());

        TextView textView3=view.findViewById(R.id.docpo);
        textView3.setText(documentList.get(position).getPo());
        final Button orderCheckBtn=view.findViewById(R.id.orderCheckBtn);
        final Button removePermanently=view.findViewById(R.id.removePermanently);

        final DatabaseReference ordersDatabase7;
        ordersDatabase7= FirebaseDatabase.getInstance().getReference().child("responses");

        final DatabaseReference ordersDatabase2 = FirebaseDatabase.getInstance().getReference().child("orders");


        removePermanently.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (orderCheckBtn.getText().toString().contains("عرض")||orderCheckBtn.getText().toString().contains("تعديل")){
//                new AlertDialog.Builder(context)
//                        .setTitle("تحذير حذف شيك")
//                        .setMessage("يرجي التأكد من استلامكم لهذا الشيك حيث سيتم حذف هذا الشيك من القائمة نهائيا")
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int whichButton) {
//
//                                DatabaseReference vendorsDatabase = FirebaseDatabase.getInstance().getReference().child("vendors");
//
//                                vendorsDatabase.addChildEventListener(new ChildEventListener() {
//                                    @Override
//                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                                        try {
//                                            if (dataSnapshot.child("check").getValue().toString().equals(documentList.get(position).getCheck())){
//
//
//
//                                                dataSnapshot.child("po").getRef().removeValue();
//                                                dataSnapshot.child("check").getRef().removeValue();
//                                                dataSnapshot.child("amount").getRef().removeValue();
//                                                dataSnapshot.child("checkDate").getRef().removeValue();
//                                                dataSnapshot.child("name").getRef().removeValue();
//                                                dataSnapshot.child("vendor").getRef().removeValue();
//                                                dataSnapshot.child("رقم الاذن").getRef().removeValue();
//
//                                                notifyDataSetChanged();
//
//
//                                            }
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//
//                                    @Override
//                                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                                    }
//
//                                    @Override
//                                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//                                      //  notifyDataSetChanged();
//
//
//                                    }
//
//                                    @Override
//                                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//
//                                    }
//
//                                });
//
//                            }
//
//
//                        }).setNegativeButton(android.R.string.no, null).show();
//
//
//            }else {
//
//                    Toast.makeText(getContext(),"لا يمكن حذف الشيك قبل تحديد الادارة موعد لاستلام الشيك",Toast.LENGTH_LONG).show();
//                }
//notifyDataSetChanged();
            }

        });

        ordersDatabase2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final SpotsDialog dialog=new SpotsDialog(context);



                if (dataSnapshot.child("checkNumber").exists()) {


                    try {
                        if (documentList.get(position).getCheck().equals(dataSnapshot.child("checkNumber").getValue().toString())) {

    //




                            orderCheckBtn.setText("تم تنفيذ طلبكم");
                            dialog.dismiss();


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    dialog.dismiss();

                }
                //  notifyDataSetChanged();

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


        DatabaseReference ordersDatabase = FirebaseDatabase.getInstance().getReference().child("responses");



        ordersDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final SpotsDialog dialog=new SpotsDialog(context);




                if (dataSnapshot.child("checkNumberForResposeTree").exists()) {


                    try {
                        if (documentList.get(position).getCheck().equals(dataSnapshot.child("checkNumberForResposeTree").getValue().toString())) {


                            try {
                                if (dataSnapshot.child("checkStatus").getValue().toString().contains("تعديل")){

                                    orderCheckBtn.setText("تم تعديل الرد");
                                    orderCheckBtn.setBackgroundColor(context.getResources().getColor(R.color.responseBeenChangedColor));
                                    backDateCode.setVisibility(View.VISIBLE);
                                    docStatus.setVisibility(View.VISIBLE);
                                    docStatusCode.setVisibility(View.VISIBLE);
                                    backDate.setVisibility(View.VISIBLE);
                                    docStatus.setText("موقف الطلب");
                                    docStatusCode.setText("تم تعديل الموعد");
                                    docStatusCode.setTextColor(context.getResources().getColor(R.color.responseBeenChangedColor));
                                    backDate.setText("الموعد المعدل هو");
                                    backDateCode.setText(dataSnapshot.child("response").getValue().toString());
                                    backDateCode.setTextColor(context.getResources().getColor(R.color.responseBeenChangedColor));


                                }else if (dataSnapshot.child("checkStatus").getValue().toString().equals("تم الرد")) {
                                    orderCheckBtn.setText("تم الرد");
                                    orderCheckBtn.setBackgroundColor(context.getResources().getColor(R.color.green));
                                    backDateCode.setVisibility(View.VISIBLE);
                                    docStatus.setVisibility(View.VISIBLE);
                                    docStatusCode.setVisibility(View.VISIBLE);
                                    backDate.setVisibility(View.VISIBLE);
                                    docStatus.setText("موقف الطلب");
                                    docStatusCode.setText("تم تحديد موعد");
                                    backDate.setText("موعد استلام الشيك");
                                    backDateCode.setText(dataSnapshot.child("response").getValue().toString());
                                    docStatusCode.setTextColor(context.getResources().getColor(R.color.darkGreen));
                                    backDateCode.setTextColor(context.getResources().getColor(R.color.darkGreen));


                                }
                                else if (dataSnapshot.child("checkStatus").getValue().toString().equals("تم تأجيل الرد")) {
                                    orderCheckBtn.setText("طلب استلام الشيك");
                                    orderCheckBtn.setBackgroundColor(context.getResources().getColor(R.color.delayedOrder));
                                    backDateCode.setVisibility(View.VISIBLE);
                                    docStatus.setVisibility(View.VISIBLE);
                                    docStatusCode.setVisibility(View.VISIBLE);
                                    backDate.setVisibility(View.VISIBLE);
                                    docStatus.setText("موقف الطلب");
                                    docStatusCode.setText("تم تأجيل الطلب");
                                    backDate.setText("يرجي اعادة الطلب في");
                                    backDateCode.setText(dataSnapshot.child("delayMSG").getValue().toString());
                                    docStatusCode.setTextColor(context.getResources().getColor(R.color.delayedOrder));
                                    backDateCode.setTextColor(context.getResources().getColor(R.color.delayedOrder));

                                }else if (dataSnapshot.child("checkStatus").getValue().toString().equals("تم الغاء الطلب و في انتظار طلب الشيك مرة اخري")){

                                    orderCheckBtn.setText("طلب استلام الشيك");

                                }
                                    else if (dataSnapshot.child("checkStatus").getValue().toString().equals("استعجال الادارة")){

                                    orderCheckBtn.setText("جاري اصدار هذا الشيك");
                                    orderCheckBtn.setBackgroundColor(context.getResources().getColor(R.color.green));
                                    docStatus.setVisibility(View.VISIBLE);
                                    docStatusCode.setVisibility(View.VISIBLE);
                                    backDate.setVisibility(View.VISIBLE);
                                    docStatus.setText("موقف الطلب");
                                    docStatusCode.setText("جاري اصدار الشيك");
                                    backDate.setText("سيتم اخباركم فور تجهيزالشيك");
                                    docStatusCode.setTextColor(context.getResources().getColor(R.color.darkGreen));

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }else {
                    dialog.dismiss();

                }


            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                notifyDataSetChanged();


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
        cancelCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (orderCheckBtn.getText().toString().equals("طلب استلام الشيك")
                        ||orderCheckBtn.getText().toString().contains("اضغط")||orderCheckBtn.getText().toString().contains("تعديل")
                        ||orderCheckBtn.getText().toString().contains("الرد")||orderCheckBtn.getText().toString().contains("جيل")
                    ||orderCheckBtn.getText().toString().contains("الرد")||orderCheckBtn.getText().toString().equals("جاري اصدار هذا الشيك")){


                }


                else {
                    new AlertDialog.Builder(context)
                            .setTitle("تأكيد الغاءالطلب")
                            .setMessage("هل تريد الغاء طلب استلام هذا الشيك")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    final Dialog dialog2 = new SpotsDialog(context);
                                    dialog2.show();


                                    DatabaseReference ordersDatabase2 = FirebaseDatabase.getInstance().getReference().child("orders");


                                    ordersDatabase2.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                            if (dataSnapshot.child("checkNumber").exists()) {

                                                if (dataSnapshot.child("checkNumber").getValue().toString().equals(documentList.get(position).getCheck())) {

                                                    dataSnapshot.child("checkStatus").getRef().removeValue();
                                                    dataSnapshot.child("name").getRef().removeValue();
                                                    dataSnapshot.child("checkNumber").getRef().removeValue();
                                                    dataSnapshot.child("orderDate").getRef().removeValue();
                                                    dataSnapshot.child("vendor").getRef().removeValue();
                                                    dataSnapshot.child("Amount").getRef().removeValue();
                                                    orderCheckBtn.setText("طلب استلام الشيك");
                                                    Toast.makeText(context, "تم الغاءْ الطلب", Toast.LENGTH_SHORT).show();
                                                    notifyDataSetChanged();

                                                    dialog2.dismiss();

                                                }

                                            }
                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                        notifyDataSetChanged();

                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {


                                            notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();


                }
            }
        });



        orderCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SpotsDialog dialog = new SpotsDialog(context);

                if (orderCheckBtn.getText().toString().contains("تنفيذ")
                      ) {
                    dialog.dismiss();
                }else
                if (orderCheckBtn.getText().toString().contains("اضغط")  ||  orderCheckBtn.getText().toString().contains("تعديل")
                        ||  orderCheckBtn.getText().toString().contains("جاري اصدار هذا الشيك")   ){

//                    ShowResponseFragment fragment = new ShowResponseFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString(CHECK_NUMBER_PASSING, documentList.get(position).getCheck());
//                    fm = context.getFragmentManager();
//                    ft = fm.beginTransaction();
//                    ft.replace(R.id.fragment_container,fragment );
//                    fragment.setArguments(bundle);
//                    ft.addToBackStack(null);
//                    ft.commit();
//                    dialog.dismiss();
                }

                else {


                    dialog.dismiss();
                    new AlertDialog.Builder(context)
                            .setTitle("تأكيد الطلب")
                            .setMessage("هل تريد تأكيد طلب استلام هذا الشيك")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                                public void onClick(DialogInterface dialog, int whichButton) {


                                    orderCheckBtn.setText("تم تنفيذ طلبكم");
                                    Toast.makeText(context, "يتم البدء بأجابة الطلبات خلال 72 ساعة عمل من تاريخ الطلب", Toast.LENGTH_LONG).show();
                                    final Dialog dialog3 = new SpotsDialog(context);
                                    dialog3.show();

                                    Document document = documentList.get(position);
                                    DatabaseReference ordersDatabase = FirebaseDatabase.getInstance().getReference().child("orders");

                                    final DatabaseReference newOrder = ordersDatabase.push();
                                    DateFormat df = new SimpleDateFormat("MMM d, yyyy");
                                    String now = df.format(new Date());
                                    newOrder.child("name").setValue(document.getName());
                                    newOrder.child("checkNumber").setValue(document.getCheck());
                                    newOrder.child("vendor").setValue(document.getVendor());
                                    newOrder.child("Amount").setValue(document.getAmount());
                                    newOrder.child("checkStatus").setValue("مطلوب الرد");
                                    newOrder.child("orderDate").setValue(now);
                                    try {
                                        DatabaseReference ordersDatabase7 = FirebaseDatabase.getInstance().getReference().child("responses");
                                        ordersDatabase7.addChildEventListener(new ChildEventListener() {
                                            @Override
                                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                                try {
                                                    if (dataSnapshot.child("checkNumberForResposeTree").getValue().toString().equals(documentList.
                                                            get(position).getCheck())){
                                                        newOrder.child("checkStatus").setValue("اعادة طلب مؤجل");
                                                        dataSnapshot.child("checkStatus").getRef().setValue("تم تنفيذ الطلب مرة اخري");

                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
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

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    dialog3.dismiss();
                                    notifyDataSetChanged();


                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();

                }

            }


        });



        return view;




    }

}
