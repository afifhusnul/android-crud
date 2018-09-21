package test1.android.com.test1.Utils;

import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class ScanTimeOut {
    public long timeOut; //Application time out value (600L = 10 mins)
    private Timer appTimer = null;
    private Handler handler = null;

    public ScanTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public void startTimer() {

        final CountDownLatch AuthorisationLatch = new CountDownLatch(
                (int) timeOut);
        if (appTimer != null && handler != null) {
            appTimer.cancel();
            appTimer.purge();
            appTimer = null;
            handler = null;
        }
        handler = new Handler();
        appTimer = new Timer();
        appTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        AuthorisationLatch.countDown();
                        Log.d("Scan Timer :", " ------ " + AuthorisationLatch.getCount());
                        if (AuthorisationLatch.getCount() == 0) {
//                            showTimeOutDialog();
                            if (appTimer != null && handler != null) {
                                appTimer.cancel();
                                appTimer.purge();
                                handler = null;
                                appTimer = null;
                            }
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    public void stopTimer() {
        if (appTimer != null && handler != null) {
            appTimer.cancel();
            appTimer.purge();
            handler = null;
            appTimer = null;
        }
    }
}
