package edu.url.salle.eric.eugenio.eventme.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import edu.url.salle.eric.eugenio.eventme.R;

public class ProfileBottomSheet extends BottomSheetDialogFragment {

    public static final String PROFILE_TAG = "PROFILE_TAG";

    public static final int LOGOUT_ID = 0;
    public static final int DELETE_ACCOUNT_ID = 1;

    private BottomSheetListener mListener;

    public void setListener(BottomSheetListener eventListener) {
        this.mListener = eventListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        TextView logoutTextView = view.findViewById(R.id.bottomSheet_logout);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(LOGOUT_ID);
                    dismiss();
                }
            }
        });

        TextView deleteAccountTextView = view.findViewById(R.id.bottomSheet_deleteAccount);
        deleteAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(DELETE_ACCOUNT_ID);
                    dismiss();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public interface BottomSheetListener {
        void onClick(int id);
    }
}
