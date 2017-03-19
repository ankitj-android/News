package com.ajasuja.codepath.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.ajasuja.codepath.news.R;
import com.ajasuja.codepath.news.model.Settings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;
import static com.ajasuja.codepath.news.model.Settings.SortOrder.NEWEST;
import static com.ajasuja.codepath.news.model.Settings.SortOrder.OLDEST;
import static com.ajasuja.codepath.news.model.Settings.SortOrder.fromValue;

/**
 * Created by ajasuja on 3/17/17.
 */


public class SettingsDialogFragment extends DialogFragment implements DatePickerFragment.DatePickerDialogListener {


    @BindView(R.id.editTextBeginDate) EditText editTextBeginDate;
    @BindView(R.id.imageButtonCalendar) ImageButton imageButtonCalendar;

    @BindView(R.id.spinnerSortOrder) Spinner spinnerSortOrder;

    @BindView(R.id.checkBoxArts) CheckBox checkBoxArts;
    @BindView(R.id.checkBoxFashionStyle) CheckBox checkBoxFashionStyle;
    @BindView(R.id.checkBoxSports) CheckBox checkBoxSports;

    @BindView(R.id.buttonSave) Button buttonSave;
    @BindView(R.id.buttonCancel) Button buttonCancel;

    private Settings settings;
    private Calendar calendar = Calendar.getInstance(Locale.US);

    @Override
    public void onDateSelected(int year, int month, int day) {
        System.out.println("Selected Begin Date : " + year + month + day);
        calendar.set(year, month, day);
//        editTextBeginDate.setTag(Long.valueOf(calendar.getTimeInMillis()));
        editTextBeginDate.setText(DateUtils.formatDateTime(getContext(), calendar.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE));
    }


    public interface SettingsListener {
        void onSettingsSave(Settings settings);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container);
        settings = Settings.getInstance();
        calendar.setTimeInMillis(settings.getBeginDateInMillis());
        bind(this, view);
        updateSettingsViews();
        imageButtonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setTargetFragment(SettingsDialogFragment.this, 300);
                datePickerFragment.show(fragmentManager, "datePickerFragment");
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("debug", "settings changes saved");
                SettingsListener settingsListener = (SettingsListener) getActivity();
                updateSettingsData();
                settingsListener.onSettingsSave(settings);
                dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("debug", "settings changes cancelled");
                dismiss();
            }
        });
        return view;
    }

    private void updateSettingsViews() {
        editTextBeginDate.setText(DateUtils.formatDateTime(getContext(), settings.getBeginDateInMillis(), DateUtils.FORMAT_SHOW_DATE));
        updateSortOrderSpinner();
        updateNewsDeskCheckboxes();
    }

    private void updateNewsDeskCheckboxes() {
        checkBoxArts.setChecked(settings.isNewsDeskArtsSelected());
        checkBoxFashionStyle.setChecked(settings.isNewsDeskFashionAndStyleSelected());
        checkBoxSports.setChecked(settings.isNewsDeskSportsSelected());
    }

    private void updateSortOrderSpinner() {
        List<String> sortOrders = new ArrayList<>();
        sortOrders.add(NEWEST.getSortOrder());
        sortOrders.add(OLDEST.getSortOrder());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sortOrders);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortOrder.setAdapter(dataAdapter);
        if (settings != null
                && settings.getSortOrder() != null) {
            spinnerSortOrder.setSelection(dataAdapter.getPosition(settings.getSortOrder().name()));
        }
    }

    private void updateSettingsData() {
        settings.setBeginDateInMillis(calendar.getTimeInMillis());
        settings.setSortOrder(fromValue((String) spinnerSortOrder.getSelectedItem()));
        settings.setNewsDeskArtsSelected(checkBoxArts.isChecked());
        settings.setNewsDeskFashionAndStyleSelected(checkBoxFashionStyle.isChecked());
        settings.setNewsDeskSportsSelected(checkBoxSports.isChecked());
    }
}
