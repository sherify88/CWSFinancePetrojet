package com.example.kesho.cwsfinancepetrojet;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kesho on 3/30/2018.
 */

public class UnderReviewAdapter  extends ArrayAdapter<DocUnderReview> {


    Activity context;
    List<DocUnderReview> documentList;
    int recource;
    FragmentTransaction ft;
    FragmentManager fm;
    public static String CHECK_NUMBER_PASSING;


    public UnderReviewAdapter(@NonNull Activity context, int resource, @NonNull List<DocUnderReview> objects) {
        super(context, resource, objects);
        this.context=context;
        this.documentList =objects;
        this.recource=resource;
        documentList =objects;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = context.getLayoutInflater();
        final View view = inflater.inflate(recource, parent, false);
        TextView textView5 = view.findViewById(R.id.checkNoTextTitle);

        TextView textView = view.findViewById(R.id.checkamount);
        textView.setText(documentList.get(position).getAmount());

        TextView textView3 = view.findViewById(R.id.docpo);
        textView3.setText(documentList.get(position).getPo());
        final Button orderCheckBtn = view.findViewById(R.id.orderCheckBtn);
        orderCheckBtn.setVisibility(View.INVISIBLE);
        orderCheckBtn.setVisibility(View.GONE);
        final Button cancelCheckBtn = view.findViewById(R.id.cancelCheckBtn);
        cancelCheckBtn.setVisibility(View.INVISIBLE);
        cancelCheckBtn.setVisibility(View.GONE);
        TextView docStatus=view.findViewById(R.id.docStatus);
        TextView docStatusCode=view.findViewById(R.id.docStatusCode);
        docStatusCode.setText(documentList.get(position).docStatus);
        TextView backDate=view.findViewById(R.id.backDate);
        TextView backDateCode=view.findViewById(R.id.backDateCode);
        backDateCode.setText(documentList.get(position).backDate);

        TextView textView2=view.findViewById(R.id.checkNo);
        textView2.setText(documentList.get(position).getCheck());


        {
        }
   return view; }
}