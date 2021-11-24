package com.gac5206.covidawareness.user;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.gac5206.covidawareness.R;
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
    public String userID;
    Button upload;
    ImageView vac_card;
    Bitmap vacCard;
    Uri imageUri;
    static final int REQUEST_IMAGE_CAPTURE = 100;


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


        final TextView emailTextView = (TextView) view.findViewById(R.id.userEmail);
        final TextView usernameTextView = (TextView) view.findViewById(R.id.userName);
        final TextView addressTextView = (TextView) view.findViewById(R.id.userAddress);
        upload = view.findViewById(R.id.upload_img);
        vac_card = view.findViewById(R.id.vac_card_image);


        if(ContextCompat.checkSelfPermission(requireActivity(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(requireActivity(),new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_IMAGE_CAPTURE);
        }

        upload.setOnClickListener(this:: onClick);


        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener(){


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.mUserName;
                    String email = userProfile.mEmail;
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

    private void onClick(View view) {




        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(intent.resolveActivity(requireActivity().getPackageManager()) != null){
            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
        }else{
            Toast.makeText(requireContext(),"No available app that supports this action", Toast.LENGTH_LONG).show();
        }

        Toast.makeText(getActivity(), "Button Clicked!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            Bundle bundle = data.getExtras();
            vacCard = (Bitmap) bundle.get("data");
            vac_card.setImageBitmap(vacCard);

            Toast.makeText(requireContext(),data.toString(),Toast.LENGTH_LONG).show();



        }

    }


}