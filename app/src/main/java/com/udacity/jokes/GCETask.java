package com.udacity.jokes;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.jokes.backend.myApi.MyApi;

import java.io.IOException;

class GCETask extends AsyncTask<onReceiveJoke, Void, String> {
    private static MyApi myApiService = null;
    private onReceiveJoke listener;
    private boolean success;

    @Override
    protected String doInBackground(onReceiveJoke... params) {
        success = false;
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(BuildConfig.ROOT_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }
        listener = params[0];
        try {
            success = true;
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            success = false;
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        listener.onReceive(success, result);
    }
}
