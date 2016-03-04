package com.example.maguoqing.androiddemo.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseActivity;
import com.example.maguoqing.androiddemo.entity.Contact;

import java.util.List;

/**
 * Created by magq on 16/3/3.
 */
public class SugarActivity extends BaseActivity {

    @ViewId(R.id.btn_add)
    private Button btnAdd;
    @ViewId(R.id.btn_query)
    private Button btnQuery;

    @ViewId(R.id.tv_contacts)
    private TextView tvContacts;

    @Override
    protected void setListeners() {
        btnAdd.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Contact contact = new Contact("abc", 10, "130-1111-2222");
                contact.save();
                break;
            case R.id.btn_query:
                List<Contact> contacts = Contact.listAll(Contact.class);
                String out = "";
                for (Contact c: contacts) {
                    out += c.getId() + c.getName() + ", " + c.getAge() + ", " + c.getPhone_number() + "\n";
                }
                tvContacts.setText(out);
        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_sugar;
    }
}
