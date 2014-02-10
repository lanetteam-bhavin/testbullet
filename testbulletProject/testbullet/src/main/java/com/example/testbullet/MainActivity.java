package com.example.testbullet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testbullet.singleclass.Name;
import com.example.testbullet.singleclass.Seperator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends Activity
{
    String BULLET_SYMBOL = "&#8226";
    ListView listView1;
    TextView tv1, tv2;
    List<String> names = new ArrayList<String>();
    List<Name> persons = new ArrayList<Name>();
    List<Object> namesToSend = new ArrayList<Object>();
    List<String> alpha = new ArrayList<String>();
    Name oName = new Name();
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView1 = (ListView) findViewById(R.id.listView1);
        listView1.setEmptyView(findViewById(R.id.empty));
        String[] emptyData = new String[]{};
        ArrayAdapter<String> adapterTrial = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, emptyData);
//        listView1.setAdapter(adapterTrial);
		 new loadFromWeb().execute();
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Name temp = (Name) parent.getItemAtPosition(position);
                Global_application.selectedPerson = temp;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public class loadFromWeb extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params)
        {
            String response = null;
            StringBuilder builder = new StringBuilder();
            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse httpResponse;
            HttpContext httpcontext = new BasicHttpContext();
            HttpPost request = new HttpPost("http://api.androidhive.info/contacts/");
            try
            {
                httpResponse = client.execute(request);
                int responseCode = httpResponse.getStatusLine().getStatusCode();
                // Get the response
                BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                String line = "";
                while ((line = rd.readLine()) != null)
                {
                    System.out.println(line);
                    builder.append(line);
                }
                response = builder.toString();
            }
            catch (ClientProtocolException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result)
        {
            try
            {
                JSONObject contacts = new JSONObject(result);
                JSONArray jaNames = contacts.getJSONArray("contacts");
                for (int i = 0; i < jaNames.length(); i++)
                {
                    oName = new Name();
                    oName.setId(jaNames.getJSONObject(i).getString("id"));
                    oName.setNames(jaNames.getJSONObject(i).getString("name"));
                    oName.setEmails(jaNames.getJSONObject(i).getString("email"));
                    oName.setAddress(jaNames.getJSONObject(i).getString("address"));
                    oName.setGender(jaNames.getJSONObject(i).getString("gender"));
                    oName.setPhone(jaNames.getJSONObject(i).getJSONObject("phone").getString("mobile"));
                    persons.add(oName);
                    names.add(jaNames.getJSONObject(i).getString("name"));
                }
            }
            catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (names.size() > 0)
            {
                Collections.sort(persons,new Comparator<Name>() {
                    @Override
                    public int compare(Name lhs, Name rhs) {
                        return lhs.getNames().compareToIgnoreCase(rhs.getNames());
                    }
                });
                String temp="",temp1="";
                Seperator oSeperator;
                for(int i=0;i<names.size();i++)
                {
                temp = persons.get(i).getNames();
                temp1 = temp.trim().substring(0,1);
                    if(!alpha.contains(temp1))
                    {
                        oSeperator = new Seperator();
                        oSeperator.setNames(temp1);
                        namesToSend.add(oSeperator);
                        alpha.add(temp1);
                    }
                    oName =persons.get(i);
                    namesToSend.add(oName);
                }
                Log.v("TAG", "" + names.toString());
                Log.v("TAG", "size is :" + names.size());
//                ListAdapter lAdapter = new ListAdapter(MainActivity.this, names);
                ListAdapter lAdapter1 = new ListAdapter(MainActivity.this,namesToSend);
                listView1.setAdapter(lAdapter1);
            }
        }

    }

}
