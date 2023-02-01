package projectgroup14.project_group_14_android;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HeadLibrarian extends AppCompatActivity {

    String loggedLibrarian = "";
    String librarianId = "";
    JSONArray allShifts;
    String userId = "";
    String selectedLibrarian = "";
    private ArrayAdapter<String> librarianAdapter;
    List<String> librariansList = new ArrayList<String>();
    private int timesDeleting = 0;
    Spinner librarianSpinner;
    String libraryName = "";
    String libraryaddress = "";
    String email = "";
    String phone = "";
    String updatedlibraryName = "";
    String updatedlibraryaddress = "";
    String updatedemail = "";
    String updatedphone = "";
    private String error;
    List<String> listOfShifts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_librarian);

        Button fireLibrarianButton = findViewById(R.id.fireLibrarian);
        Button logOutButton = findViewById(R.id.buttonLogOut);
        Button UpdateNameButton = (Button) findViewById(R.id.UpdateNameButton);
        Button UpdateEmailButton = (Button) findViewById(R.id.UpdateEmailButton);
        Button UpdateAddressButton = (Button) findViewById(R.id.UpdateAddressButton);
        Button UpdateContact = (Button) findViewById(R.id.UpdateContact);
        TextView textLibraryName = (TextView) findViewById(R.id.nameofthelib);
        TextView textAddressChange = (TextView) findViewById(R.id.addressofthelib);
        TextView textEmailChange = (TextView) findViewById(R.id.emailofthelib);
        TextView textContactChange = (TextView) findViewById(R.id.contactofthelib);

        updatedlibraryName = textLibraryName.toString();
        updatedemail = textEmailChange.toString();
        updatedlibraryaddress = textEmailChange.toString();
        updatedphone = textContactChange.toString();

        /**
         * Get logged in librarian
         */
        HttpUtils.get("login/librarian", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    loggedLibrarian = response.getString("username");
                    librarianId = response.getString("id");
                    changeText(loggedLibrarian);
                    System.out.println(loggedLibrarian);


                    HttpUtils.get("/shifts/librarian/" + librarianId, new RequestParams(), new JsonHttpResponseHandler() {
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            allShifts = response;
                            System.out.println("response: " + response.toString());

                            for (int i = 0; i < allShifts.length(); i++) {
                                try {
                                    JSONObject entry = allShifts.getJSONObject(i);
                                    listOfShifts.add(entry.getString("dayOfWeek") + "  From:" + entry.getString("startTime") + "  To:" + entry.getString("endTime"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            ArrayAdapter shiftAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, listOfShifts);

                            shiftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            ListView listView = (ListView) findViewById(R.id.shiftList);
                            listView.setAdapter(shiftAdapter);

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            try {
                                error += errorResponse.get("message").toString();
                                System.out.println(error);
                            } catch (JSONException e) {
                                error += e.getMessage();
                                System.out.println(error);

                            }
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        HttpUtils.get("library/", new RequestParams(),new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                try {
                    System.out.println(response.toString());
                    libraryName = response.getString("name");
                    textLibraryName.setText(libraryName);
                    libraryaddress = response.getString("address");
                    textAddressChange.setText(libraryaddress);
                    email = response.getString("email");
                    textEmailChange.setText(email);
                    phone = response.getString("phoneNumber").toString();
                    textContactChange.setText(phone);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

        /**
         * Create spinner handler and get list of librarians
         */
        librarianSpinner = (Spinner) findViewById(R.id.spinnerLibrarian);
//        librarianSpinner.setBackgroundColor(Color.BLACK);
        librariansList.add("Select a librarian");


        createListLibrarians();
        librarianAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, librariansList);
        librarianAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        librarianSpinner.setAdapter(librarianAdapter);

        librarianSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                librarianAdapter.notifyDataSetChanged();
                selectedLibrarian = librariansList.get(position);
                System.out.println("The selected thing is " + selectedLibrarian);
                TextView tvError = (TextView) findViewById(R.id.errorDeleting);
                tvError.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("item not selected");
            }
        });


        /**
         * Update Library Name
         */

        UpdateNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLibraryName();
            }
        });

        /**
         * Update Library Phone
         */
        UpdateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLibraryPhone();
            }
        });

        /**
         * Updates Library Email
         */
        UpdateEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLibraryEmail();
            }
        });

        /**
         * Updates Library Address
         */
        UpdateAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLibraryAddress();
            }
        });


        /**
         * Fire librarian
         */
        fireLibrarianButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireLibrarian();
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HeadLibrarian.this, MainActivity.class);
                HeadLibrarian.this.startActivity(myIntent);
            }
        });



    }


    /**
     * Updates Library Name
     */

    public void updateLibraryName(){
        EditText editnewlibraryname = (EditText)  findViewById(R.id.NameChange);
        String newlibraryname = editnewlibraryname.getText().toString();
        newlibraryname = newlibraryname.replaceAll(" ","%20");
        RequestParams requestParams = new RequestParams();
        requestParams.add("address", libraryaddress);
        requestParams.add("phone_number", phone);
        requestParams.add("email", email);
        if(!newlibraryname.isEmpty()) {
            HttpUtils.patch("library/edit/" + newlibraryname, requestParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        System.out.println("Success");
                        //                    finish();
                        //                    startActivity(getIntent());
                        editnewlibraryname.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    try {
                        if (responseString.isEmpty()) {
                            finish();
                            startActivity(getIntent());
                        } else {
                            refreshErrorMessageUpdating(responseString);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

//                @Override
//                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                    try {
//                        //                    if(errorResponse.getString("message").isEmpty()) {
//                        //                        finish();
//                        //                        startActivity(getIntent());
//                        //                    }
//                        //                    else {
//                        //                        refreshErrorMessageUpdating(errorResponse.getString("message"));
//                        //                    }
//                        System.out.println(errorResponse.toString());
//                    } catch (Exception e) {
//                        System.out.println(e.getMessage());
//                    }
//                }
            });
        }
        else {
            refreshErrorMessageUpdating("The new name can't be empty");
        }

    }

    /**
     * Update Library Address
     */

    public void updateLibraryAddress(){
        EditText editnewaddress = (EditText)  findViewById(R.id.AddressChange);
        String newlibraryaddress= editnewaddress.getText().toString();
        RequestParams requestParams = new RequestParams();
        requestParams.add("address", newlibraryaddress);
        requestParams.add("phone_number", phone);
        requestParams.add("email", email);


        HttpUtils.patch("library/edit/" + libraryName.replaceAll(" ","%20") ,requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                try {
                    System.out.println("Success");
//                    finish();
//                    startActivity(getIntent());
                    editnewaddress.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                try {
                    if(responseString.isEmpty()) {
                        finish();
                        startActivity(getIntent());
                    }
                    else {
                        refreshErrorMessageUpdating(responseString);
                    }
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    /**
     * Update email
     */
    public void updateLibraryEmail(){
        EditText editnewemail = (EditText)  findViewById(R.id.EmailAddressChange);
        String newemail= editnewemail.getText().toString();
        newemail = newemail.replaceAll(" ","");
        RequestParams requestParams = new RequestParams();
        requestParams.add("address", libraryaddress);
        requestParams.add("phone_number", phone);
        requestParams.add("email", newemail);


        HttpUtils.patch("library/edit/" + libraryName.replaceAll(" ","%20") ,requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                try {
                    System.out.println("Success");
//                    finish();
//                    startActivity(getIntent());
                    editnewemail.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                try {
                    if(responseString.isEmpty()) {
                        finish();
                        startActivity(getIntent());
                    }
                    else {
                        refreshErrorMessageUpdating(responseString);
                    }
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    /**
     * Updates phone
     */

    public void updateLibraryPhone(){
        EditText editnewphone = (EditText)  findViewById(R.id.PhoneChange);
        String newlibraryphone= editnewphone.getText().toString();
        newlibraryphone = newlibraryphone.replaceAll(" ","");
        RequestParams requestParams = new RequestParams();
        requestParams.add("address", libraryaddress);
        requestParams.add("phone_number", newlibraryphone);
        requestParams.add("email", email);


        HttpUtils.patch("library/edit/" + libraryName.replaceAll(" ","%20") ,requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                try {
                    System.out.println("Success");
//                    finish();
//                    startActivity(getIntent());
                    editnewphone.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                try {
                    if(responseString.isEmpty()) {
                        finish();
                        startActivity(getIntent());
                    }
                    else {
                        refreshErrorMessageUpdating(responseString);
                    }
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });
    }


    /**
     * Handles fire librarian button
     *
     */
    public void fireLibrarian(){
        Spinner spinnerLib = (Spinner) findViewById(R.id.spinnerLibrarian);
//        String textSpinnerLibrarian = spinnerLib.getSelectedItem().toString();
        System.out.println("Selected librarian is " +selectedLibrarian);
        if(!selectedLibrarian.equals("Select a librarian")) {
            HttpUtils.get("librarian/" + selectedLibrarian, new RequestParams(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        System.out.println(response);
                        userId = response.getString("id");
                        System.out.println("user id " + userId);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    System.out.println("Response " + responseString);
                }
            });
            HttpUtils.delete("https://project-group-14-backend.herokuapp.com/librarians/delete/" + userId, new RequestParams(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        System.out.println("Success deleting");
                    } catch (Exception e) {
                        librarianAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    System.out.println("Response is here " + responseString);
                    if (timesDeleting < 3) {
                        timesDeleting++;
                        fireLibrarian();
                    }
                    else{
                        finish();
                        startActivity(getIntent());
                    }
                }


                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    System.out.println("Response is there " + errorResponse.toString());
                    if (timesDeleting < 3) {
                        timesDeleting++;
                        fireLibrarian();
                    }
                    else{
                        finish();
                        startActivity(getIntent());
                    }
                }

            });
        }
        else{
            refreshErrorMessage();
        }
        librarianAdapter.notifyDataSetChanged();
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

    public void changeText(String loggedLibrarian){
        TextView LogLibrarian = (TextView) findViewById(R.id.LoggedLibrarian);
        LogLibrarian.setText("Welcome " +loggedLibrarian);
    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public void createListLibrarians(){
        HttpUtils.get("librarians/", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                for( int i = 0; i < response.length(); i++) {
                    try {
                        if(!response.getJSONObject(i).getBoolean("head")) {
                            librariansList.add(response.getJSONObject(i).getString("username").toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
//       librarianSpinner.setSelection(1);

    }

    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.errorDeleting);
        tvError.setVisibility(View.VISIBLE);
    }

    private void refreshErrorMessageUpdating(String message) {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.errorUpdating);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(message);
    }
}
