package hk.hku.comp7506.callercheck.helper;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hk.hku.comp7506.callercheck.application.AndroidApplication;
import hk.hku.comp7506.callercheck.model.Contact;

/**
 * Created by rocklct on 2017/10/1.
 */

public class ContactProvider {


    public ContactProvider() {
    }

    public ArrayList<Contact> readContacts() {
        Cursor cursor = null;
        ArrayList<Contact> contactList = new ArrayList<Contact>();
        try {

            // get the data of contacts
            cursor = AndroidApplication.getInstance().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String displayName = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    );

                    String number = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    ));

                    contactList.add(new Contact(displayName, number));

                }
            }


        } catch (Exception e) {
            Log.e("getContactsError", "failure in getting Contacts");
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contactList;
    }
}
