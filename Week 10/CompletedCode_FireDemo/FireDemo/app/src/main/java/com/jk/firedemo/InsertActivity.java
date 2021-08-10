package com.jk.firedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;

import com.jk.firedemo.databinding.ActivityInsertBinding;
import com.jk.firedemo.models.Friend;
import com.jk.firedemo.viewmodels.FriendViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InsertActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignUpActivity";
    ActivityInsertBinding binding;
    private Friend newFriend;
    private FriendViewModel friendViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        this.binding = ActivityInsertBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        this.friendViewModel = FriendViewModel.getInstance(this.getApplication());
        this.newFriend = new Friend();

        this.binding.editBirthdate.setOnClickListener(this);
        this.binding.btnSave.setOnClickListener(this);
        this.binding.editBirthdate.setFocusable(false);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()){
                case R.id.btn_save: {
                    if (this.validateData()) {
                        this.addFriendToDB();
                    }
                    break;}
                case R.id.edit_birthdate:{
                    this.chooseDate();
                    break;
                }
            }
        }
    }

    private Boolean validateData() {
        Boolean isValidData = true;

        if (this.binding.editName.getText().toString().isEmpty()) {
            this.binding.editName.setError("Please enter name");
            isValidData = false;
        }

        return isValidData;
    }

    private void chooseDate() {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker =
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view, final int year, final int month,
                                          final int dayOfMonth) {

                        calendar.set(year, month, dayOfMonth);
                        newFriend.setBirthDate(calendar.getTime());

                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
                        binding.editBirthdate.setText(sdf.format(newFriend.getBirthDate()).toString()); // set the date

                    }
                }, year, month, day); // set date picker to current date

        datePicker.getDatePicker().setMaxDate(new Date().getTime());
        datePicker.show();

        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(final DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }

    private void addFriendToDB(){
        this.newFriend.setName(this.binding.editName.getText().toString());
        this.newFriend.setPhoneNumber(this.binding.editPhone.getText().toString());

        Log.d(TAG, "User : " + this.newFriend.toString());
        //save to DB
        this.friendViewModel.addFriend(newFriend);
        this.finish();
    }
}