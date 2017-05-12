package corp.skaj.foretagskvitton.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import corp.skaj.foretagskvitton.R;
import corp.skaj.foretagskvitton.model.WizardModel;

public class WriteDataFragment extends DialogFragment {

    private WizardModel model;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.last_step_text)
                .setPositiveButton(R.string.last_step_approve_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (model != null) {
                            model.collectData();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    public void setModel(WizardModel model) {
        this.model = model;
    }
}
