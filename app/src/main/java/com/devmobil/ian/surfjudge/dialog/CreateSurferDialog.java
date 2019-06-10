package com.devmobil.ian.surfjudge.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.devmobil.ian.surfjudge.R;
import com.devmobil.ian.surfjudge.controller.Surfers;
import com.devmobil.ian.surfjudge.model.Surfer;

public class CreateSurferDialog extends DialogFragment {

    NoticeDialogListener mListener;


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.fragment_cad4, null);

        builder.setTitle("Create Surfer");

        final EditText editName = view.findViewById(R.id.edtName);
        final EditText edtCountry = view.findViewById(R.id.edtCountry);
        builder.setView(view).setPositiveButton("Salve", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Surfer surfer = new Surfer();
                Surfers surfers = new Surfers(getActivity());
                surfer.setName(editName.getText().toString());
                surfer.setCountry(edtCountry.getText().toString());
                int id = (int) surfers.insert(surfer);
                if(id != 0){
                    surfer.setId(id);
                    mListener.onDialogPositiveClick(surfer);
                    dismiss();
                }

            }
        }).setNegativeButton("Close", null);
        return builder.create();
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(Surfer surfer);
    }

}
