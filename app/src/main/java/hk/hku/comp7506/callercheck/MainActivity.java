package hk.hku.comp7506.callercheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hk.hku.comp7506.callercheck.adapter.ContactsAdapter;
import hk.hku.comp7506.callercheck.service.IncomingCallCheckService;
import hk.hku.comp7506.callercheck.helper.ContactProvider;
import hk.hku.comp7506.callercheck.model.Contact;

public class MainActivity extends AppCompatActivity {

    //private ListView contactsView;
    //private ArrayAdapter<String> adapter;
    private List<String> stringList = new ArrayList<>();
    private ArrayList<Contact> contactList = new ArrayList<>();
    private RecyclerView contactsRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView text = (TextView) findViewById(R.id.text);

        updateContacts();
        if(contactList.isEmpty()){
            text.setText("There are no contacts in the local phone book，please add some contacts");
        }
        else{
            text.setVisibility(View.INVISIBLE);
        }

        contactsRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsRecyclerView.setAdapter(new ContactsAdapter(contactList,this));
        

        Intent callingServiceIntent = new Intent(this, IncomingCallCheckService.class);
        callingServiceIntent.putParcelableArrayListExtra("contacts",contactList);
        startService(callingServiceIntent);


    }

    private void updateContacts(){
        contactList = new ContactProvider().readContacts();
        for (Contact contact : contactList) {
            stringList.add(contact.getName() + " : " + contact.getNumber() + "\n");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
