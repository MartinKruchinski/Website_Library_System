package projectgroup14.project_group_14_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LibrarianActivity extends AppCompatActivity {

    JSONArray allShifts;
    JSONArray openingHours;
    String loggedLibrarian = "";
    String librarianId = "";
    List<String> listOfShifts = new ArrayList<>();
    List<String> listOfOpeningHours = new ArrayList<>();
    String username;
    String libraryName = "";
    String libraryaddress = "";
    String email = "";
    String phone = "";
    private String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian);


        TextView textLibraryName = (TextView) findViewById(R.id.nameofthelib);
        TextView textAddressChange = (TextView) findViewById(R.id.addressofthelib);
        TextView textEmailChange = (TextView) findViewById(R.id.emailofthelib);
        TextView textContactChange = (TextView) findViewById(R.id.contactofthelib);



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

        HttpUtils.get("/login/librarian", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    loggedLibrarian = response.getString("username");
                    changeText(loggedLibrarian);
                    librarianId = response.getString("id");

                    HttpUtils.get("/shifts/librarian/" + librarianId, new RequestParams(), new JsonHttpResponseHandler() {
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            LibrarianActivity.this.allShifts = response;
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

                    HttpUtils.get("/openingHours/", new RequestParams(), new JsonHttpResponseHandler() {
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            openingHours = response;
                            System.out.println("response: " + response.toString());

                            for (int i = 0; i < openingHours.length(); i++) {
                                try {
                                    JSONObject entry = openingHours.getJSONObject(i);
                                    listOfOpeningHours.add(entry.getString("dayOfWeek") + "  From:" + entry.getString("startTime") + "  To:" + entry.getString("endTime"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            ArrayAdapter openingHourAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, listOfOpeningHours);

                            openingHourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            ListView listView = (ListView) findViewById(R.id.openingHourList);
                            listView.setAdapter(openingHourAdapter);

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
        Button logoutButton = (Button) findViewById(R.id.buttonLogOut);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void changeText(String loggedLibrarian){
        TextView LogLibrarian = (TextView) findViewById(R.id.LoggedLibrarian2);
        LogLibrarian.setText("Welcome " +loggedLibrarian);
    }
}