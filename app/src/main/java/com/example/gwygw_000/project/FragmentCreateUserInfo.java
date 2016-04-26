package com.example.gwygw_000.project;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentCreateUserInfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCreateUserInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateUserInfo extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static int output_X = 480;
    private static int output_Y = 480;


    // TODO: Rename and change types of parameters
    private String userEmail;
    private String mParam2;

    EditText etUsername;
    EditText etWhazup;
    Button btTakePhoto;
    Button btChooseLib;
    Button btSubmit;
    CircleImageView cImage;
    Bitmap image;

    private OnFragmentInteractionListener mListener;

    public FragmentCreateUserInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCreateUserInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCreateUserInfo newInstance(String userEmail) {
        FragmentCreateUserInfo fragment = new FragmentCreateUserInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, userEmail);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userEmail = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_user_info, container, false);
        etUsername = (EditText) view.findViewById(R.id.et_nickname);
        etWhazup = (EditText) view.findViewById(R.id.et_whatzup);
        btTakePhoto = (Button) view.findViewById(R.id.bt_takephoto);
        btChooseLib = (Button) view.findViewById(R.id.bt_choosephotolib);
        btSubmit = (Button) view.findViewById(R.id.bt_submitInfo);
        cImage = (CircleImageView) view.findViewById(R.id.cv_headimage);

        btTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (hasSdcard()) {
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                            .fromFile(new File(Environment
                                    .getExternalStorageDirectory(), "temp_header.jpg")));
                }

                startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);

            }
        });

        btChooseLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFromGallery = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //intentFromGallery.setType("image/*");
                //intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase("https://luminous-heat-2520.firebaseio.com/");
                ref = ref.child("UserData").child(etUsername.getText().toString());
                ref.child("Email").setValue(userEmail);
                ref.child("description").setValue(etWhazup.getText().toString());
                if (image != null) {
                    ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
                    image.recycle();
                    byte[] byteArray = bYtE.toByteArray();
                    String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    ref.child("headimage").setValue(imageFile);

                    byte[] decodedString = Base64.decode(imageFile, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    cImage.setImageBitmap(decodedByte);


                } else {
                    ref.child("headimage").setValue("null");
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == getActivity().RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            "temp_header.jpg");
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getActivity(), "No SDCard", Toast.LENGTH_LONG)
                            .show();
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            image = photo;
            cImage.setImageBitmap(image);
        }
    }

    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");

        // aspectX , aspectY
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
