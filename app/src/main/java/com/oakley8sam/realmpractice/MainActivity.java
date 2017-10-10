package com.oakley8sam.realmpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oakley8sam.realmpractice.Model.Person;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private EditText txtName, txtAge;
    private Button btnAdd, btnView, btnDel;
    private TextView logTxt;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();

        txtName = (EditText) findViewById(R.id.nameText);
        txtAge = (EditText) findViewById(R.id.ageText);
        btnAdd = (Button) findViewById(R.id.addButton);
        btnView = (Button) findViewById(R.id.viewButton);
        btnDel = (Button) findViewById(R.id.delButton);
        logTxt = (TextView) findViewById(R.id.logText);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                save_to_database(txtName.getText().toString().trim(), Integer.parseInt(txtAge.getText().toString().trim()));
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh_database();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = txtName.getText().toString();
                delete_from_database(Name);
            }
        });
    }


    private void save_to_database(final String Name, final int Age) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Person person = bgRealm.createObject(Person.class);
                person.setName(Name);
                person.setAge(Age);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.v("Success", "------------>OK<-------------");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("Failed", error.getMessage());
            }
        });
    }


    private void refresh_database() {
        RealmResults<Person> result = realm.where(Person.class).findAllAsync();
        result.load();
        String output="";

        for (Person person:result){
            output+=person.toString();
            output+="\n";
        }
        logTxt.setText(output);
    }


    private void delete_from_database(String Name) {
        final RealmResults<Person> persons = realm.where(Person.class).equalTo("Name", Name).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute (Realm realm) {
                persons.deleteFromRealm(0);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}

