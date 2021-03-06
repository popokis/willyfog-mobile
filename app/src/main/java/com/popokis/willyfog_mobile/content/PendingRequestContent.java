package com.popokis.willyfog_mobile.content;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.popokis.http.SecureClient;
import com.popokis.models.PendingRequest;
import com.popokis.models.UserRequests;
import com.popokis.willyfog_mobile.MainActivity;
import com.popokis.willyfog_mobile.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PendingRequestContent {

    public static List<UserRequests> ITEMS;

    private ProgressDialog dialog;
    private final Gson gson = new Gson();

    public PendingRequestContent(ProgressDialog dialog) {
        this.dialog = dialog;

        SharedPreferences sharedPref = MainActivity.contextOfApplication.getSharedPreferences(
                MainActivity.contextOfApplication.getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE
        );

        String key = MainActivity.contextOfApplication.getResources().getString(R.string.auth_pref_key);
        String userIdent = MainActivity.contextOfApplication.getResources().getString(R.string.user_id);

        String accessToken = sharedPref.getString(key, null);
        String userId = sharedPref.getString(userIdent, null);

        try {
            addAllItems(new GetUserRequests().execute("http://popokis.com:7000/api/v1/users/" + userId + "/requests", accessToken).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void addAllItems(Collection<? extends UserRequests> requests) {
        ITEMS = new ArrayList<>();
        ITEMS.addAll(requests);
    }

    private class GetUserRequests extends AsyncTask<String, String, List<UserRequests>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected List<UserRequests> doInBackground(String... data) {
            String url = data[0];
            String accessToken = data[1];

            List<UserRequests> result = null;
            PendingRequest pendingRequest;

            try {
                pendingRequest = gson.fromJson((new SecureClient(accessToken)).get(url), PendingRequest.class);
                result = pendingRequest.getPending();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<UserRequests> param) {
            dialog.dismiss();
        }
    }

    public List<UserRequests> getITEMS() {
        return ITEMS;
    }
}
