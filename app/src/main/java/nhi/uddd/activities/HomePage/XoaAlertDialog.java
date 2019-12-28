package nhi.uddd.activities.HomePage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import nhi.uddd.R;

public class XoaAlertDialog extends DialogFragment {
    String message ="Message";
    String yes = "Yes";
    String no = "No";
    private DialogInterface.OnClickListener onYesButtonClicked;
    private DialogInterface.OnClickListener onNoButtonClicked;
    public XoaAlertDialog(String message, String yes, String no){
        this.message=message;
        this.no=no;
        this.yes=yes;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton(yes, onYesButtonClicked)
                .setNegativeButton(no, onNoButtonClicked);
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void setOnYesButtonClicked(DialogInterface.OnClickListener onYesButtonClicked) {
        this.onYesButtonClicked = onYesButtonClicked;
    }

    public void setOnNoButtonClicked(DialogInterface.OnClickListener onNoButtonClicked) {
        this.onNoButtonClicked = onNoButtonClicked;
    }

}
