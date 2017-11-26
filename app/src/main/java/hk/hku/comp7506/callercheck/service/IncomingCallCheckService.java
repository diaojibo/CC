package hk.hku.comp7506.callercheck.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

import hk.hku.comp7506.callercheck.R;
import hk.hku.comp7506.callercheck.model.Contact;

public class IncomingCallCheckService extends Service {
    private TelephonyManager telephonyManager;
    private SimplePhoneStateListener simplePhoneStateListener;
    private ArrayList<Contact> contactArrayList;

    public IncomingCallCheckService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getIncomingCallCancel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.contactArrayList = intent.getParcelableArrayListExtra("contacts");
        getIncomingCall();
        return super.onStartCommand(intent, flags, startId);
    }

    private void getIncomingCall() {
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        simplePhoneStateListener = new SimplePhoneStateListener();
        telephonyManager.listen(simplePhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void getIncomingCallCancel() {
        telephonyManager.listen(simplePhoneStateListener, PhoneStateListener.LISTEN_NONE);
    }

    class SimplePhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    checkIncomingCall(incomingNumber);
                    break;
            }

        }
    }

    private void showAlertBox(String s, int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view;
        // type = contact
        if (type == 0) {
            view = inflater.inflate(R.layout.popup_contact, null);
            TextView contactMessage = view.findViewById(R.id.contactMessage);
            contactMessage.setText(s);
        }else{ // type = stranger
            view = inflater.inflate(R.layout.popup_stranger,null);
            TextView strangerMessage = view.findViewById(R.id.strangerMessage);
            strangerMessage.setText(s);
        }


        //builder.setTitle("Warning").setMessage(s);
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog mDialog = builder.create();
        mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        if (Build.VERSION.SDK_INT >= 26) {
            mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            Log.e("t", "good");
        }
        mDialog.show();
    }


    private void checkIncomingCall(String incomingNumber) {
        boolean isContact = false;
        for (Contact contact : contactArrayList) {
            String number = contact.getNumber();
            if (number.charAt(0) != '+') {
                String regEx = "[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(number);
                number = m.replaceAll("").trim();
                if (incomingNumber.equals(number)) {
                    String warningString = contact.getName() + "( " + incomingNumber + " ) is calling you";
                    showAlertBox(warningString, 0);
                    Toast.makeText(IncomingCallCheckService.this, contact.getName() + "(" + incomingNumber
                            + ") is calling you", Toast.LENGTH_LONG).show();
                    isContact = true;
                    break;
                }
            } else {
                if (incomingNumber.equals(number)) {
                    String warningString = contact.getName() + "( " + incomingNumber + " ) is calling you";
                    showAlertBox(warningString, 0);
                    Toast.makeText(IncomingCallCheckService.this, contact.getName() + "(" + incomingNumber
                            + ") is calling you", Toast.LENGTH_LONG).show();
                    isContact = true;
                    break;
                }
            }
        }

        if (!isContact) {
            String waringString = "Warning: a stranger( " + incomingNumber + " ) is calling you!!";
            showAlertBox(waringString, 1);
            Toast.makeText(IncomingCallCheckService.this, "Warning : a stranger (" + incomingNumber + " ) is calling you!!", Toast.LENGTH_LONG).show();
        }
    }
}
