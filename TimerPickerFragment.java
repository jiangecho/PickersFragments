package fr.prcaen.pickerfragment;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.app.DialogFragment;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.NumberPicker;
import org.holoeverywhere.widget.TextView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class TimerPickerFragment extends DialogFragment implements OnClickListener {
  public static final String TAG                = "TimerPickerFragment";
  public OnTimeSetListener   listener;

  private Context            mContext;
  private View               mView;

  private String             title;
  private String             message;
  private String             positiveButtonText = "Set";
  private String             negativeButtonText = "Cancel";
  private Drawable           icon;

  private int                time;

  private NumberPicker       minsNumberPicker;
  private NumberPicker       secondsNumberPicker;
  private String             unitMins           = "m";
  private String             unitSeconds        = "s";

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    mContext = this.getActivity();
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

    LinearLayout viewGroup = new LinearLayout(mContext);
    viewGroup.setOrientation(LinearLayout.HORIZONTAL);
    viewGroup.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    viewGroup.setGravity(Gravity.CENTER_HORIZONTAL);

    Integer mins = 0;
    Integer seconds = 0;

    if (time > 0) {
      mins = Math.round(time % 3600 / 60);
      seconds = Math.round(time % 60);
    }

    viewGroup.addView(minutes(mins));
    viewGroup.addView(seconds(seconds));

    alertDialogBuilder.setView(viewGroup);

    // Set title
    if (!TextUtils.isEmpty(title))
      alertDialogBuilder.setTitle(title);

    // Set message
    if (!TextUtils.isEmpty(message))
      alertDialogBuilder.setMessage(message);

    // Set icon
    if (icon != null)
      alertDialogBuilder.setIcon(icon);

    // Set dialog positive button
    alertDialogBuilder.setPositiveButton(positiveButtonText, this);

    // Set dialog negative button
    alertDialogBuilder.setNegativeButton(negativeButtonText, this);

    return alertDialogBuilder.create();
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setTime(int seconds) {
    this.time = seconds;
  }

  public void setUnitMins(String unitMins) {
    this.unitMins = unitMins;
  }

  public void setUnitSeconds(String unitSeconds) {
    this.unitSeconds = unitSeconds;
  }

  public void setView(View v) {
    this.mView = v;
  }

  public void setPositiveButtonText(String positiveButtonText) {
    this.positiveButtonText = positiveButtonText;
  }

  public void setNegativeButtonText(String negativeButtonText) {
    this.negativeButtonText = negativeButtonText;
  }

  public void setOnTimerSetListener(OnTimeSetListener listener) {
    this.listener = listener;
  }

  private LinearLayout minutes(int mins) {
    LinearLayout minsLayout = new LinearLayout(mContext);
    minsLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    minsLayout.setOrientation(LinearLayout.HORIZONTAL);
    minsLayout.setGravity(Gravity.CENTER);

    // NumberPicker
    minsNumberPicker = new NumberPicker(mContext);
    minsNumberPicker.setMaxValue(59);
    minsNumberPicker.setMinValue(1);

    if (mins > 0)
      minsNumberPicker.setValue(mins);

    minsLayout.addView(minsNumberPicker);

    // Unit
    TextView unit = new TextView(mContext);
    unit.setText(unitMins);
    LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    ll.setMargins(25, 0, 25, 0);
    unit.setLayoutParams(ll);

    minsLayout.addView(unit);

    return minsLayout;
  }

  private LinearLayout seconds(int seconds) {
    LinearLayout minsLayout = new LinearLayout(mContext);
    minsLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    minsLayout.setOrientation(LinearLayout.HORIZONTAL);
    minsLayout.setGravity(Gravity.CENTER);

    // NumberPicker
    secondsNumberPicker = new NumberPicker(mContext);
    secondsNumberPicker.setMaxValue(59);
    secondsNumberPicker.setMinValue(0);

    if (seconds > 0)
      secondsNumberPicker.setValue(seconds);

    minsLayout.addView(secondsNumberPicker);

    // Unit
    TextView unit = new TextView(mContext);
    unit.setText(unitSeconds);
    LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    ll.setMargins(25, 0, 0, 0);
    unit.setLayoutParams(ll);

    minsLayout.addView(unit);

    return minsLayout;
  }

  public interface OnTimeSetListener {
    public void onTimeSet(int time, View v);
  }

  @Override
  public void onClick(DialogInterface dialog, int which) {
    switch (which) {
    case DialogInterface.BUTTON_POSITIVE:
      int timeResult = (minsNumberPicker.getValue() * 60) + secondsNumberPicker.getValue();
      try {
        listener.onTimeSet(timeResult, mView);
      } catch (Exception e) {
        Log.e("Exception", "TimerPickerFragment must implement OnTimeSetListener");
      }
      break;
    case DialogInterface.BUTTON_NEGATIVE:

      break;
    }
  }
}
