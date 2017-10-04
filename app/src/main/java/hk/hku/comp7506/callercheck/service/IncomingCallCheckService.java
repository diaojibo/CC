package hk.hku.comp7506.callercheck.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

    private void checkIncomingCall(String incomingNumber) {
        boolean isContact = false;
        for (Contact contact : contactArrayList) {
            if (incomingNumber.equals(contact.getNumber().replace("-",""))) {
                Toast.makeText(IncomingCallCheckService.this, contact.getName() + "(" + incomingNumber
                        + ") is calling you", Toast.LENGTH_LONG).show();
                isContact = true;
                break;
            }
        }

        if (!isContact) {
            Toast.makeText(IncomingCallCheckService.this, "Warning : a stranger (" + incomingNumber + " ) is calling you!!", Toast.LENGTH_LONG).show();
        }
    }

}
