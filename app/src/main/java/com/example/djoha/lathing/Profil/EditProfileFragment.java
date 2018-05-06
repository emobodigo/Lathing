package com.example.djoha.lathing.Profil;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.djoha.lathing.R;
import com.example.djoha.lathing.Utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

public class EditProfileFragment extends Fragment {
    private ImageView img;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        img = (ImageView) view.findViewById(R.id.profilephoto);

        setImageProfile();

        ImageView imageView = (ImageView) view.findViewById(R.id.backbuttonprofile);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getActivity());
        ImageLoader.getInstance().init(universalImageLoader.loaderConfiguration());
    }

    private void setImageProfile(){
        String imgURL = "https://i1.wp.com/biodatanet.com/wp-content/uploads/2017/07/Pevits2.jpg";
        UniversalImageLoader.setImage(imgURL, img, null, "");
    }
}
