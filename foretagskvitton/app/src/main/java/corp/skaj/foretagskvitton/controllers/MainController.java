package corp.skaj.foretagskvitton.controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

import corp.skaj.foretagskvitton.R;
import corp.skaj.foretagskvitton.model.IData;
import corp.skaj.foretagskvitton.model.Supplier;
import corp.skaj.foretagskvitton.model.User;
import corp.skaj.foretagskvitton.view.ArchiveAdapter;
import corp.skaj.foretagskvitton.view.CompanyAdapter;
import corp.skaj.foretagskvitton.view.SupplierAdapter;
import corp.skaj.foretagskvitton.view.SupplierListFragment;

public class MainController {

    private IView mListener;
    private Context mContext;


    public MainController(IView listener, Context context) {
        mListener = listener;
        mContext = context;

    }

    public void setArchiveAdapterListener(final ArchiveAdapter sAdapter, final Class<?> nextActivity, final String key) {
        sAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String data = sAdapter.getData().get(position).getId();
                mListener.nextActivity(nextActivity, key, data);
            }
        });
    }

    public void setCompanyAdapterListener(final CompanyAdapter sAdapter, final Class<?> nextActivity, final String key) {
        sAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String data = sAdapter.getData().get(position).getName();
                mListener.nextActivity(nextActivity, key, data);
            }
        });
    }

    public void setSupplierAdapterListener(final SupplierAdapter sAdapter, final Class<?> nextActivity, final String key) {
        sAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String data = sAdapter.getData().get(position).getName();
                showEditSupplierDialog(data);
            }
        });
    }

    public void initBottomBar (final BottomBar bottomBar) {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (bottomBar.getCurrentTabId() != tabId) {
                    switch (tabId) {
                        case R.id.action_business:
                            mListener.buildCompanyFragment();
                            return;
                        case R.id.action_archive:
                            mListener.buildArchiveFragment();
                            return;
                        case R.id.action_supplier:
                            mListener.buildSupplierFragment();
                            return;
                        default:
                            return;
                    }
                }
            }
        });
    }

    public void showEditSupplierDialog(String data) {

        final IData handler = (IData) mContext.getApplicationContext();
        final User user = handler.getUser();
        final Supplier supplier = user.getSupplier(data);

        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

        final EditText edittext = new EditText(mContext);
        edittext.setSingleLine(true);
        edittext.setText(supplier.getName());

        alert.setMessage("Skriv grossist namn:");
        alert.setTitle("Editera " + supplier.getName());

        alert.setView(edittext);

        alert.setPositiveButton("Spara", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                Editable supplierName = edittext.getText();

                if(supplierName.toString().length() < 1){
                    return;
                }

                supplier.setName(supplierName.toString());
                handler.saveUser();
                List<Supplier> suppliers = handler.getUser().getSuppliers();

                AppCompatActivity activity = (AppCompatActivity) mContext;
                SupplierListFragment fragment = (SupplierListFragment) activity.getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
                fragment.getAdapter().setNewData(suppliers);

                Toast.makeText(mContext, "Ändrat till " + supplier.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // nothing to do here
            }
        });

        alert.show();
    }
}
