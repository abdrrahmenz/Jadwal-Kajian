package id.or.qodr.jadwalkajianpekalongan.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import id.or.qodr.jadwalkajianpekalongan.R;

/**
 * Created by adul on 23/01/17.
 */

public class BaseFragment extends Fragment {
    private ProgressDialog progressDialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (progressDialog == null)
            progressDialog =new ProgressDialog(getContext());
    }

    protected void showProgress() {
        if (progressDialog != null)
            if (!progressDialog.isShowing()) {
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }
    }

    public void setActiveFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void open(Class clazz) {
        startActivity(new Intent(getActivity(), clazz));
    }

    public void finish() {
        getActivity().finish();
    }

    public View inflate(ViewGroup container, int layoutRes) {
        return LayoutInflater.from(container.getContext()).inflate(layoutRes, container, false);
    }

    public boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    public String getString(EditText editText){
        return editText.getText().toString();
    }

    public void showSnack(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }


}
