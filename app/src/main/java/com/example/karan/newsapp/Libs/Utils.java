package com.example.karan.newsapp.Libs;

import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.app.ActivityManager;
import android.content.Context;
import androidx.fragment.app.FragmentTransaction;
import com.example.karan.newsapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import org.json.JSONObject;
import retrofit2.Response;


public class Utils {
    public static Snackbar snackbar;

    public static void addFragment(FragmentManager fm, int layoutId, Fragment fragment) {
        if (fm != null && fragment != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(layoutId, fragment);
            ft.commitAllowingStateLoss();
        }
    }

    public static void addFragmentWithLeftRightAnim(FragmentManager fragmentManager, Fragment fragment, int fragContainerResID) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        fragmentTransaction.add(fragContainerResID, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    public static void showSnackBarForUnsuccessfulResponse(Response response, final Context ctx, View view) {
        try {
            JSONObject jsonObject = new JSONObject(response.errorBody().string());
            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("error"));
            if (jsonObject1 != null) {
                showSnackBarForError(ctx, jsonObject1.getString("message"), view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showSnackBarForError(final Context ctx, String message, View view) {
        snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
        setSnackBarColor(ctx, R.color.snackbar_message);
        snackbar.show();
    }

    public static void setSnackBarColor(Context ctx, int snackBarBgColorId) {
        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);

        // Changing snackbar background color
        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(ContextCompat.getColor(ctx, snackBarBgColorId));

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        TextView snackbarActionTextView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_action);
        snackbarActionTextView.setTypeface(snackbarActionTextView.getTypeface(), Typeface.BOLD);

    }

    public static boolean isOnline() {

        boolean connected = false;
//        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//            //we are connected to a network
//            connected = true;
//        }
//        else
//            connected = false;
        return connected;
    }

    public static Boolean isValidString(String string) {
        if(string == null || string.trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public static String getPushToken(){
        return FirebaseInstanceId.getInstance().getToken();
    }


    }
