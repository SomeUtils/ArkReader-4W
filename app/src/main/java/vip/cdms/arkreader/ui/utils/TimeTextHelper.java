package vip.cdms.arkreader.ui.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeTextHelper extends CountDownTimer {
    private final TextView textView;
    private final String timeFormat;

    public TimeTextHelper(TextView textView) {
        this(textView, "HH:mm", /* 30s */ 30 * 1000);
    }

    public TimeTextHelper(TextView textView, String timeFormat, long timeInterval) {
        super(Long.MAX_VALUE, timeInterval);
        this.textView = textView;
        this.timeFormat = timeFormat;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setText(getCurrentTime());
    }

    @Override
    public void onFinish() {}

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.getDefault());
        return sdf.format(new Date());
    }
}
