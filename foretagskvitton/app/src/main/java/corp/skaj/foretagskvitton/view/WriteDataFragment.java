package corp.skaj.foretagskvitton.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import corp.skaj.foretagskvitton.R;
import corp.skaj.foretagskvitton.activities.WizardActivity;
import corp.skaj.foretagskvitton.model.IObserver;
import corp.skaj.foretagskvitton.model.WizardModel;
import corp.skaj.foretagskvitton.services.DataHandler;
import corp.skaj.foretagskvitton.services.IData;

public class WriteDataFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.last_step_text)
                .setPositiveButton(R.string.last_step_approve_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Under construction...
                        //IData dataHandler = (DataHandler)getContext();
                        //WizardModel wizardModel = (WizardModel) dataHandler.readData(WizardModel.class.getName(), WizardModel.class);
                        //wizardModel.collectData();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }
}
