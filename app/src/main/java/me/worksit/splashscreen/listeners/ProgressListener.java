package me.worksit.splashscreen.listeners;

/**
 * Created by Everton on 10/06/2016.
 *
 * Listener para tratar progresso de ações
 */
public interface ProgressListener {

    void onPreExecute(String message);

    void onProgressUpdate(String message, long progress);

    void onFinished(String message);

}