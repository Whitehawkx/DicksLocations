
package com.jonathansteadman.dickslocations;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodFragment extends Fragment implements Constants {
    private Food food;

    private View view;

    private int id;

    boolean shouldPause = false;

    public static final int REQUEST_PHOTO = 1;

    public static FoodFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_FOOD_ID, id);
        FoodFragment dFrag = new FoodFragment();
        dFrag.setArguments(args);

        return dFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getActivity().getIntent().getIntExtra(EXTRA_FOOD_ID, 0);

        if (id == -1) {
            food = new Food();
        } else {
            id = getArguments().getInt(EXTRA_FOOD_ID);
            food = FoodSet.newInstance(getActivity()).getFood().get(id);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.food_fragment, container, false);
        initTitle(view);
        initDesc(view);
        initPictureView(view);
        initCameraButton(view);
        return view;
    }

    private void initTitle(View view) {
        TextView textView = (TextView)view.findViewById(R.id.edittext_food_title);
        textView.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        textView.setText(food.getTitle());
    }

    public void initDesc(View view) {
        EditText editText = (EditText)view.findViewById(R.id.edittext_food_desc);
        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText.setText(food.getDescription());
    }

    public void initPictureView(View view) {
        ImageView photoView = (ImageView)view.findViewById(R.id.imageview_photo);
        String photoFileName = "";

        if (food.getImage() != null) {
            photoFileName = food.getImage();
        } else {
            // do nothing
        }

        BitmapDrawable bitmap = null;

        if (getActivity().getFileStreamPath(photoFileName) == null)
            return;

        String path = getActivity().getFileStreamPath(photoFileName).getAbsolutePath();
        if (path == null)
            return;

        bitmap = PictureUtils.getScaledDrawable(getActivity(), path);
        if (bitmap == null)
            return;

        photoView.setImageDrawable(bitmap);
    }

    public void initCameraButton(View view) {
        ImageView button = (ImageView)view.findViewById(R.id.button_show_camera);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivityForResult(intent, REQUEST_PHOTO);
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == REQUEST_PHOTO) {
            food.setImage(CameraFragment.PHOTO_FILENAME);
            initPictureView(view);
        }
        // allow onPause() to run
        shouldPause = true;
    }

    @Override
    public void onPause() {
        super.onPause();

        EditText title = (EditText)view.findViewById(R.id.edittext_food_title);
        EditText desc = (EditText)view.findViewById(R.id.edittext_food_desc);

        // Only save list item if the title has something in it and a picture
        // has been taken. This will prevent any blank entries.
        if (title.getText().toString().trim().length() > 0 && shouldPause == true) {

            food.setTitle(title.getText().toString());
            food.setDesc(desc.getText().toString());

            if (id == -1) {
                FoodSet.addFood(food);
            }

            FoodSet.newInstance(getActivity()).saveFood();
        }
    }
}
