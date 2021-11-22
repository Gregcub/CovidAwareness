package com.gac5206.covidawareness;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInfoFragment} factory method to
 * create an instance of this fragment.
 */
public class UserInfoFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference ref;
    private TextView editProfile;
    public String userID;



    public UserInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        editProfile = (TextView) view.findViewById(R.id.edit_profile);
        final TextView emailTextView = (TextView) view.findViewById(R.id.userEmail);
        final TextView usernameTextView = (TextView) view.findViewById(R.id.userName);
        final TextView addressTextView = (TextView) view.findViewById(R.id.userAddress);





        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener(){


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.mUserName;
                    String email = userProfile.mEmail;
                    String country = userProfile.mCountry;
                    String city = userProfile.mCity;
                    String state = userProfile.mState;

                    emailTextView.setText(email);
                    usernameTextView.setText(fullName);
                    addressTextView.setText(state + ", " + city);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();

            }
        });


        return view;



    }



}