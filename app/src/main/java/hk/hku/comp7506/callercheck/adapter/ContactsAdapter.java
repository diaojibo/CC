package hk.hku.comp7506.callercheck.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import hk.hku.comp7506.callercheck.R;
import hk.hku.comp7506.callercheck.model.Contact;

/**
 * Created by rocklct on 2017/11/15.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyContactsHolder> {

    private ArrayList<Contact> contactsList;
    private Context context;

    public ContactsAdapter(ArrayList<Contact> contactsList, Context context) {
        this.contactsList = contactsList;
        this.context = context;
    }

    @Override
    public MyContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyContactsHolder myContactsHolder = new MyContactsHolder(LayoutInflater.from(context)
                .inflate(R.layout.contacts_item, parent, false));
        return myContactsHolder;
    }

    @Override
    public void onBindViewHolder(MyContactsHolder holder, int position) {
        String contactsName = contactsList.get(position).getName();
        String telNumber = contactsList.get(position).getNumber();

        holder.contactsName.setText(contactsName);
        holder.telNumber.setText("Tel: " + telNumber);
    }


    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    class MyContactsHolder extends RecyclerView.ViewHolder {
        TextView contactsName;
        TextView telNumber;

        public MyContactsHolder(View itemView) {
            super(itemView);
            contactsName = (TextView) itemView.findViewById(R.id.contacts_name);
            telNumber = (TextView) itemView.findViewById(R.id.contacts_tel);
        }
    }
}
