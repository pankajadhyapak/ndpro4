package com.udacity.jokes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.jokeandroid.JokePresentationActivity;


public class MainActivityFragment extends Fragment {

    private InterstitialAd mInterstitialAd;
    private ProgressBar progress;
    private String lastJoke;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        initBannerAds(root);
        initInterstitialAds();
        progress = (ProgressBar) root.findViewById(R.id.progressbar);
        root.findViewById(R.id.btnGetJoke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);

                new GCETask().execute(new onReceiveJoke() {
                    @Override
                    public void onReceive(boolean success, String joke) {
                        progress.setVisibility(View.GONE);
                        if (success) {
                            lastJoke = joke;
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            } else {
                                if (lastJoke != null)
                                    startActivity(new Intent(getActivity(), JokePresentationActivity.class).putExtra("joke", lastJoke));
                            }
                        } else
                            Toast.makeText(getActivity(), joke, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return root;
    }


    private void initBannerAds(View root) {
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    private void initInterstitialAds() {
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.ads_interstitial));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                if (lastJoke != null)
                    startActivity(new Intent(getActivity(), JokePresentationActivity.class).putExtra("joke", lastJoke));
            }
        });
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

}
