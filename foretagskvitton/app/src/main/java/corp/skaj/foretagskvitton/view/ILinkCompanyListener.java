package corp.skaj.foretagskvitton.view;

import android.widget.Button;

public interface ILinkCompanyListener {
    void setEditEmployeeListener (Button button);
    void setRemoveEmployeeListener (Button button);
    void setEditCardListener (Button button);
    void setRemoveCardListener (Button button);
    void setSaveCommentListener (Button button);
}