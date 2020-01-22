package com.example.onlineshop.view.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentSettingBinding;
import com.example.onlineshop.event.NotificationEvent;
import com.example.onlineshop.prefs.QueryPreferences;
import com.example.onlineshop.viewmodel.SplashViewModel;
import com.google.android.material.radiobutton.MaterialRadioButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends VisibleFragment {

    private MaterialRadioButton[] mRbList;
    private FragmentSettingBinding mSettingBinding;
    private SplashViewModel mSplashViewModel;
    private ArrayAdapter<Integer> mArrayAdapter;
    private List<Integer> mListStatusSpinnerItem;


    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSplashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mSettingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);

        initCheckedRadioButtons();
        initSpinnerHour();

        mSettingBinding.rbDisableNotifySetting.setOnClickListener(getListener(0));
        mSettingBinding.rbSetting15minute.setOnClickListener(getListener(15));
        mSettingBinding.rbSetting3hour.setOnClickListener(getListener(3 * 60));
        mSettingBinding.rbSetting5hour.setOnClickListener(getListener(5 * 60));
        mSettingBinding.rbSetting8hour.setOnClickListener(getListener(8 * 60));
        mSettingBinding.rbSetting12hour.setOnClickListener(getListener(12 * 60));

        mSettingBinding.rbSettingCustomTime.setOnClickListener(
                view -> mSettingBinding.rlCustomTimeNotifySetting.setVisibility(View.VISIBLE));


        return mSettingBinding.getRoot();
    }

    private void initSpinnerHour() {

        mListStatusSpinnerItem = new ArrayList<>();
        for (int i = 1; i <= 23; i++) {
            mListStatusSpinnerItem.add(i);
        }

        mArrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, mListStatusSpinnerItem);

        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSettingBinding.spinnerSettingCustomMinute.setAdapter(mArrayAdapter);

        mSettingBinding.spinnerSettingCustomMinute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                QueryPreferences.setStatusNotification(getContext(), (Integer) adapterView.getItemAtPosition(position * 60));
                mSplashViewModel.startServiceNotify();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initCheckedRadioButtons() {

        mSettingBinding.rbSettingCustomTime.setChecked(true);
        switch (QueryPreferences.getStatusNotification(getContext())) {
            case 0: {
                mSettingBinding.rbDisableNotifySetting.setChecked(true);
                break;
            }
            case 15: {
                mSettingBinding.rbSetting15minute.setChecked(true);
                break;
            }
            case 3: {
                mSettingBinding.rbSetting3hour.setChecked(true);
                break;
            }
            case 5: {
                mSettingBinding.rbSetting5hour.setChecked(true);
                break;
            }
            case 8: {
                mSettingBinding.rbSetting8hour.setChecked(true);
                break;
            }
            case 12: {
                mSettingBinding.rbSetting12hour.setChecked(true);
                break;
            }
        }

    }

    private View.OnClickListener getListener(int itemSelect) {
        return view -> {
            mSettingBinding.rlCustomTimeNotifySetting.setVisibility(View.GONE);

            if (itemSelect != 0) {
                QueryPreferences.setStatusWorkManager(getContext(), false);
            }

            QueryPreferences.setStatusNotification(getContext(), itemSelect);
            mSplashViewModel.startServiceNotify();
        };
    }

}
