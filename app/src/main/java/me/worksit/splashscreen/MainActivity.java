package me.worksit.splashscreen;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import me.worksit.splashscreen.listeners.ProgressListener;

public class MainActivity extends AppCompatActivity {

    private TextView txtStatusInfo;
    private AppLoaderHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        handler = new AppLoaderHandler();
        new LoadApp(handler).execute();
    }

    private void bindComponents() {
        txtStatusInfo = (TextView) findViewById(R.id.status_info);
    }

    private void showPrincipalScreen() {
        Intent myIntent = new Intent(this, principal.class);
        startActivity(myIntent);
        finish();
    }

    /**
     * Classe assíncrona responsável por executar as ações durante a apresenção
     * da splash screen
     */
    private class LoadApp extends AsyncTask<Void, String, Void> {

        private Exception e;
        private ProgressListener listener;

        public LoadApp(ProgressListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            this.listener.onProgressUpdate(values[0], 0);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                for (int i = 0; i < 10; i++) {
                    publishProgress(String.format("Carregando item %d...", i));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception ex) {
                e = ex;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (e == null)
                showPrincipalScreen();
        }
    }

    /**
     * Handler para executar as ações notificadas pela thread de carregamento
     */
    private class AppLoaderHandler implements ProgressListener{

        @Override
        public void onPreExecute(String message) {

        }

        @Override
        public void onProgressUpdate(final String message, long progress) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtStatusInfo.setText(message);
                }
            });
        }

        @Override
        public void onFinished(String message) {

        }
    }

}
