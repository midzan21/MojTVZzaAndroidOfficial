package markoidzan.mojtvzandroid;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class RasporedSati extends Fragment {

    private ProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rasporedsati, container, false);

        final WebView webStranica;

        webStranica = (WebView) rootView.findViewById(R.id.stranica);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);

        WebSettings javascriptUkljucen = webStranica.getSettings();

        javascriptUkljucen.setJavaScriptEnabled(true);


        webStranica.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webStranica.loadUrl("javascript:(function(){" +
                        "document.getElementsByClassName('navigacija')[0].style.display='none';" +
                        "})()");
                webStranica.loadUrl("javascript:(function(){" +
                        "document.getElementsByClassName('potpis')[0].style.display='none';" +
                        "})()");
                webStranica.loadUrl("javascript:(function(){" +
                        "document.getElementsByClassName('footer')[0].style.display='none';" +
                        "})()");
                webStranica.loadUrl("javascript:(function(){" +
                        "document.getElementsByTagName('img')[0].style.display='none';" +
                        "})()");
                webStranica.loadUrl("javascript:(function(){" +
                        "document.getElementsByTagName('a')[0].style.font-size='1.1em';" +
                        "})()");
                webStranica.loadUrl("javascript:(function(){" +
                        "document.getElementsByTagName('body')[0].background='#fff';" +
                        "})()");
                webStranica.loadUrl("javascript:(function(){" +
                        "document.getElementsByClassName('link_dno')[0].style.display='none';" +
                        "})()");

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getHost().contains("moj.tvz.hr")) {
                    return false;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            }


        });

        webStranica.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        webStranica.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE)
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                progressBar.setProgress(progress);
                if (progress == 100)
                    progressBar.setVisibility(ProgressBar.GONE);

            }

        });

        webStranica.loadUrl("https://moj.tvz.hr/prikaz/raspored");

        webStranica.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                    }
                }
                return false;
            }
        });


        return rootView;

    }

}
