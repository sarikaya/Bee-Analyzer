package com.itu.itu_map_project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.maps.GoogleMap;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

import static android.content.Context.TELEPHONY_SERVICE;

public class DateField implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private EditText editText;
    private Button button;
    private Calendar Start, Stop;
    private Context ctx;
    private GoogleMap mapIn;
    private Timer markerTimer;
    private int choiceTime = 0;

    public DateField(EditText editText, Button button, Context ctx, GoogleMap mapIn, Timer markerTimer){
        this.ctx = ctx;
        this.mapIn = mapIn;
        this.markerTimer = markerTimer;
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this.button = button;
        this.button.setOnClickListener(this);
        Start = Calendar.getInstance();
        Stop = Calendar.getInstance();
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

        Start.set(Calendar.YEAR, year);
        Start.set(Calendar.MONTH, monthOfYear);
        Start.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Stop.set(Calendar.YEAR, year);
        Stop.set(Calendar.MONTH, monthOfYear);
        Stop.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setTextField();
        new TimePickerDialog(ctx, this, Start.get(Calendar.HOUR_OF_DAY),Start.get(Calendar.MINUTE),true).show();
    }
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        if(choiceTime == 0){
            Start.set(Calendar.HOUR_OF_DAY, hourOfDay);
            Start.set(Calendar.MINUTE, minute);
            choiceTime = 1;
            new TimePickerDialog(ctx, this, Start.get(Calendar.HOUR_OF_DAY),Start.get(Calendar.MINUTE),true).show();
        }
        else if(choiceTime == 1){
            Stop.set(Calendar.HOUR_OF_DAY, hourOfDay);
            Stop.set(Calendar.MINUTE, minute);
            choiceTime = 0;
        }
        setTextField();
    }

    public void setTextField(){
        SimpleDateFormat startformat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat stopformat = new SimpleDateFormat("HH:mm");
        editText.setText(startformat.format(Start.getTime()) + "-" + stopformat.format(Stop.getTime()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editText:
                new DatePickerDialog(ctx, this, Start
                        .get(Calendar.YEAR), Start.get(Calendar.MONTH),
                        Start.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.button:
                String dateText = String.valueOf(editText.getText());
                if(dateText == null || dateText.isEmpty())
                    new DatePickerDialog(ctx, this, Start
                            .get(Calendar.YEAR), Start.get(Calendar.MONTH),
                            Start.get(Calendar.DAY_OF_MONTH)).show();
                if(!(dateText == null || dateText.isEmpty())) {
                    markerTimer.cancel();
                    TelephonyManager tm = (TelephonyManager) ctx.getSystemService(TELEPHONY_SERVICE);
                    String imei = tm.getDeviceId();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String mindate = df.format(Start.getTime());
                    String maxdate = df.format(Stop.getTime());
                    new DBTransaction.AsyncGetOwnRoutes(this.mapIn).execute(imei, mindate, maxdate);
                }
                break;
        }


    }

}
