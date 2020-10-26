package com.example.matrimony_demo.activity;

import androidx.annotation.Nullable;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.matrimony_demo.R;
import com.example.matrimony_demo.adapter.CityAdapter;
import com.example.matrimony_demo.adapter.LanguageAdapter;
import com.example.matrimony_demo.database.TableMasterCity;
import com.example.matrimony_demo.database.TableMasterLanguage;
import com.example.matrimony_demo.database.TableUser;
import com.example.matrimony_demo.model.CityModel;
import com.example.matrimony_demo.model.LanguageModel;
import com.example.matrimony_demo.model.UserModel;
import com.example.matrimony_demo.util.Constant;
import com.example.matrimony_demo.util.Utils;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends BaseActivity {

    @BindView(R.id.etFirstName)
    TextInputEditText etFirstName;
    @BindView(R.id.etMiddleName)
    TextInputEditText etMiddleName;
    @BindView(R.id.etLastName)
    TextInputEditText etLastName;
    @BindView(R.id.rbMale)
    MaterialRadioButton rbMale;
    @BindView(R.id.rbFeMale)
    MaterialRadioButton rbFeMale;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.etDateOfBirth)
    TextInputEditText etDateOfBirth;
    @BindView(R.id.etEmailAddress)
    TextInputEditText etEmailAddress;
    @BindView(R.id.spCity)
    Spinner spCity;
    @BindView(R.id.spLanguage)
    Spinner spLanguage;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.etMobileNumber)
    TextInputEditText etMobileNumber;

    String startingDate = "1995-03-14";

    CityAdapter cityAdapter;
    LanguageAdapter languageAdapter;


    ArrayList<CityModel> cityList = new ArrayList<>();
    ArrayList<LanguageModel> languageList = new ArrayList<>();
    @BindView(R.id.screen_Layout)
    FrameLayout screenLayout;

    UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        setUpActionBar(getString(R.string.lbl_add_user), true);
        setDataToView();
        getDataForUpdate();
    }

    void getDataForUpdate() {
        if (getIntent().hasExtra(Constant.USER_OBJECT)) {
            userModel = (UserModel) getIntent().getSerializableExtra(Constant.USER_OBJECT);
            getSupportActionBar().setTitle(R.string.lbl_edit_user);
            etFirstName.setText(userModel.getFirstName());
            etMiddleName.setText(userModel.getMiddleName());
            etLastName.setText(userModel.getLastName());

            etMobileNumber.setText(userModel.getMobileNumber());
            etDateOfBirth.setText(userModel.getDateOfBirth());
            if (userModel.getGender() == Constant.MALE) {
                rbMale.setChecked(true);
            } else {
                rbFeMale.setChecked(true);
            }
            etEmailAddress.setText(userModel.getEmail());
            spCity.setSelection(getSelectedPositionFromCityId(userModel.getCityID()));
            spLanguage.setSelection(getSelectedPositionFromLanguageId(userModel.getLanguageID()));
        }
    }

    int getSelectedPositionFromCityId(int cityId) {
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).getCityID() == cityId) {
                return i;
            }
        }
        return 0;
    }

    int getSelectedPositionFromLanguageId(int languageId) {
        for (int i = 0; i < languageList.size(); i++) {
            if (languageList.get(i).getLanguageID() == languageId) {
                return i;
            }
        }
        return 0;
    }

    void setSpinnerAdapter() {
        cityList.addAll(new TableMasterCity(this).getCityList());

        languageList.addAll(new TableMasterLanguage(this).getLanguages());

        cityAdapter = new CityAdapter(this, cityList);
        languageAdapter = new LanguageAdapter(this, languageList);

        spCity.setAdapter(cityAdapter);
        spLanguage.setAdapter(languageAdapter);
    }

    void setDataToView() {
        final Calendar newCalendar = Calendar.getInstance();
        etDateOfBirth.setText(
                String.format("%02d", newCalendar.get(Calendar.DAY_OF_MONTH)) + "/" +
                        String.format("%02d", newCalendar.get(Calendar.MONTH) + 1) + "/" +
                        String.format("%02d", newCalendar.get(Calendar.YEAR)));
        setSpinnerAdapter();
    }

    @OnClick(R.id.etDateOfBirth)
    public void onEtDobClicked() {
        final Calendar newCalendar = Calendar.getInstance();
        Date date = Utils.getDateFromString(startingDate);
        newCalendar.setTimeInMillis(date.getTime());
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                RegistrationActivity.this, (datePicker, year, month, day) -> etDateOfBirth.setText(String.format("%02d", day) + "/" + String.format("%02d", (month + 1)) + "/" + year),
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @OnClick(R.id.btnSubmit)
    public void onBtnSubmitClicked() {
        if (isValidUser()) {
            if (userModel == null) {

                int languagePosition = spLanguage.getSelectedItemPosition();
                int cityPosition = spCity.getSelectedItemPosition();

                long lastInsertedID = new TableUser(getApplicationContext()).insertUser(etFirstName.getText().toString(),
                        etMiddleName.getText().toString(), etLastName.getText().toString(), rbMale.isChecked() ? Constant.MALE : Constant.FEMALE,
                        "", Utils.getFormattedDateToInsert(etDateOfBirth.getText().toString()),
                        etMobileNumber.getText().toString(),
                        etEmailAddress.getText().toString(),
                        languageList.get(languagePosition).getLanguageID(),
                        cityList.get(cityPosition).getCityID(), 0);
                showToast(lastInsertedID > 0 ? "User Inserted Successfully" : "Something went wrong");
            } else {
                long lastInsertedID = new TableUser(getApplicationContext()).updateUserById(etFirstName.getText().toString(),
                        etMiddleName.getText().toString(), etLastName.getText().toString(), rbMale.isChecked() ? Constant.MALE : Constant.FEMALE,
                        "", Utils.getFormattedDateToInsert(etDateOfBirth.getText().toString()),
                        etMobileNumber.getText().toString(),
                        etEmailAddress.getText().toString(),
                        languageList.get(spLanguage.getSelectedItemPosition()).getLanguageID(),
                        cityList.get(spCity.getSelectedItemPosition()).getCityID(), userModel.getIsFavorite(), userModel.getUserId());
                showToast(lastInsertedID > 0 ? "User Updated Successfully" : "Something went wrong");
            }
            Intent intent = new Intent(RegistrationActivity.this, ActivityUserListByGender.class);
            startActivity(intent);
            finish();
        }
    }

    boolean isValidUser() {
        boolean isValid = true;
        if (TextUtils.isEmpty(etFirstName.getText().toString())) {
            isValid = false;
            etFirstName.setError(getString(R.string.error_first_name));
        }

        if (TextUtils.isEmpty(etMiddleName.getText().toString())) {
            isValid = false;
            etMiddleName.setError(getString(R.string.error_middle_name));
        }

        if (TextUtils.isEmpty(etLastName.getText().toString())) {
            isValid = false;
            etLastName.setError(getString(R.string.error_Last_name));
        }

        if (TextUtils.isEmpty(etMobileNumber
                .getText().toString())) {
            isValid = false;
            etMobileNumber.setError(getString(R.string.error_enter_mobile_number));
        } else if (etMobileNumber.getText().toString().length() < 10) {
            isValid = false;
            etMobileNumber.setError(getString(R.string.error_valid_mobile_number));
        }

        if (TextUtils.isEmpty(etEmailAddress.getText().toString())) {
            isValid = false;
            etEmailAddress.setError(getString(R.string.error_enter_email));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmailAddress.getText().toString()).matches()) {
            isValid = false;
            etEmailAddress.setError(getString(R.string.error_valid_email));
        }

        if (spCity.getSelectedItemPosition() == 0) {
            isValid = false;
            showToast(getString(R.string.error_city_select));
        }

        if (spLanguage.getSelectedItemPosition() == 0) {
            isValid = false;
            showToast(getString(R.string.error_language_select));
        }
        return isValid;
    }

}