package com.example.kesho.cwsfinancepetrojet;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainScreen extends android.app.Fragment {
    FragmentTransaction ft;
    FragmentManager fm;
    Button signIn,signUp;

    TextView txtSlogan;

    public MainScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main_screen, container, false);

        txtSlogan=view.findViewById(R.id.txtSlogan);
        Typeface face=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Nabila.ttf");
        txtSlogan.setTypeface(face);

        signIn=view.findViewById(R.id.btnSignInMainScrn);
        signUp=view.findViewById(R.id.btnSignUpMainScrn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp fragment = new SignUp();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,fragment );
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn fragment = new SignIn();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,fragment );
                ft.addToBackStack(null);
                ft.commit();

            }
        });


    return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }
}
