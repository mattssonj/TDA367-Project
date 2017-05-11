package corp.skaj.foretagskvitton.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import corp.skaj.foretagskvitton.R;
import corp.skaj.foretagskvitton.services.DataHolder;
import corp.skaj.foretagskvitton.model.User;


public class ReadDataActivity extends AbstractActivity {
    public static final String KEY_FOR_IMAGE = "ReadDataActivity_Key_For_Image";
    public static final String BUILD_NEW_RECEIPT = "ReadDataActivity_build_receipt";
    private boolean openedFromOutside;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_application);

        Intent intent = getIntent();
        openedFromOutside = intent.getAction().equals(Intent.ACTION_SEND) && intent.getType().startsWith("image/");

        initData().start();
    }

    private Thread initData() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                readData();
                endLoadingBar();
            }
        });
    }


    private void readData() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        // Removes all SharedPreferences.
        /*
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        prefsEditor.clear();
        prefsEditor.apply();
        */
        Gson gson = new Gson();
        String savedData = sharedPref.getString(User.class.getName().toString(), "");
        DataHolder dataHolder = (DataHolder) getApplicationContext();
        dataHolder.setUser(gson.fromJson(savedData, User.class));

        // Testing...
        /*
        try {
            System.out.println(dataHolder.getUser().getCompanies().get(0).getName());
        } catch (Exception e) {}
        */
    }

    private void endLoadingBar() {
        if (openedFromOutside) {
            Uri URI = getIntent().getParcelableExtra(Intent.EXTRA_STREAM);
            Intent intent = new Intent(this, InitWizardActivity.class);
            intent.putExtra(KEY_FOR_IMAGE, URI);
            intent.setAction(BUILD_NEW_RECEIPT);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, AddNewPostActivity.class);
            startActivity(intent);
        }
    }


}
