package com.itu.itu_map_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AdminPage extends ActionBarActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

    }
    public void sendAnalysis(View view){
        Intent startAnalyzeActivity = new Intent(this, GateAnalysis.class);
        startActivity(startAnalyzeActivity);
    }
    public void sendNotification(View view){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_textarea_dialog, null);
        final EditText textArea = (EditText) alertLayout.findViewById(R.id.edit_notification);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Bildirim Gönder");
        alert.setView(alertLayout);
        alert.setCancelable(true);
        /*alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });*/

        alert.setPositiveButton("Gönder", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DBTransaction.AsyncSendFirebase().execute(textArea.getText().toString());
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

}
