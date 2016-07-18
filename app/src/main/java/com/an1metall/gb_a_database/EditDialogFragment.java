package com.an1metall.gb_a_database;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.EditText;

public class EditDialogFragment extends AppCompatDialogFragment implements AlertDialog.OnClickListener{

    private AlertDialog.OnClickListener listener;

    public static EditDialogFragment newInstance(final String title, final String message, final String description, final String cost, final AlertDialog.OnClickListener listener) {
        Bundle args = new Bundle();
        args.putString(Contract.EDIT_DIALOG_FRAGMENT_TITLE_TAG, title);
        args.putString(Contract.EDIT_DIALOG_FRAGMENT_MESSAGE_TAG, message);
        args.putString(Contract.EDIT_DIALOG_FRAGMENT_DESCRIPTION, description);
        args.putString(Contract.EDIT_DIALOG_FRAGMENT_COST, cost);
        EditDialogFragment fragment = new EditDialogFragment();
        fragment.setArguments(args);
        fragment.listener = listener;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle arguments = getArguments();
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setCancelable(true);
        View view = View.inflate(getContext(), R.layout.edit_dialog_ftagment_layout, null);
        EditText description = (EditText) view.findViewById(R.id.edit_item_dialog_fragment_description);
        EditText cost = (EditText) view.findViewById(R.id.edit_item_dialog_fragment_cost);
        description.setText(arguments.getString(Contract.EDIT_DIALOG_FRAGMENT_DESCRIPTION));
        cost.setText(arguments.getString(Contract.EDIT_DIALOG_FRAGMENT_COST));
        dialog.setView(view);
        dialog.setMessage(arguments.getString(Contract.EDIT_DIALOG_FRAGMENT_MESSAGE_TAG));
        dialog.setTitle(arguments.getString(Contract.EDIT_DIALOG_FRAGMENT_TITLE_TAG));
        dialog.setPositiveButton(R.string.edit_item_dialog_fragment_positive, listener);
        dialog.setNegativeButton(R.string.edit_item_dialog_fragment_negative, this);
        return dialog.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        dismiss();
    }


}
