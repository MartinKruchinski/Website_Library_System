package projectgroup14.project_group_14_android;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CustomerActivity extends AppCompatActivity {

    String loggedCustomer = "";
    String customerId = "";
    JSONArray availableItems;
    JSONArray openingHours;
    List<String> listOfItems = new ArrayList<>();
    JSONArray reservations;
    List<String> listOfReservations = new ArrayList<>();
    private ArrayAdapter<String> itemAdapter;
    private ArrayAdapter<String> reservationAdapter;
    String username;
    String libraryName = "";
    String libraryaddress = "";
    String email = "";
    String phone = "";
    private Button reserveItemButton;
    private Button cancelReservationButton;
    private Spinner itemSpinner;
    private Spinner reservationSpinner;
    private String error;
    List<String> listOfOpeningHours = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        reserveItemButton = findViewById(R.id.reserveItemButton);
        cancelReservationButton = findViewById(R.id.cancelReservationButton);

        reserveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserveItem();
            }
        });

        cancelReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelReservation();
            }
        });

        customerId = "";

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

        HttpUtils.get("/availableItems/", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                availableItems = response;

                for (int i = 0; i < availableItems.length(); i++) {
                    try {
                        JSONObject entry = availableItems.getJSONObject(i);
                        listOfItems.add(entry.getString("title") + " " + entry.getString("author") + " " + entry.getString("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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


        itemSpinner = (Spinner) findViewById(R.id.itemSpinner);

        listOfItems.add("Select Item");
        listOfReservations.add("Reservations");


        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(0xFF839e2e);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        //Setting the ArrayAdapter data on the Spinner

        itemAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listOfItems);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(itemAdapter);

        itemSpinner.setOnItemSelectedListener(listener);

        reserveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserveItem();
            }
        });

        HttpUtils.get("/login/customer", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    System.out.println(response.toString());
                    loggedCustomer = response.getString("firstName");

                    customerId = response.getString("id");
                    username = response.getJSONObject("loginCredential").getString("username");
                    changeText(username);

                    HttpUtils.get("bookings/customer/" + customerId, new RequestParams(), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            reservations = response;

                            for (int i = 0; i < reservations.length(); i++) {
                                try {
                                    JSONObject entry = reservations.getJSONObject(i);

                                    if (entry.getString("bookingType").equals("Reservation")){
                                        listOfReservations.add(entry.getJSONObject("item").getString("title") + " " + entry.getJSONObject("item").getString("author") + " " + entry.getString("id"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            ArrayAdapter reservationAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, listOfReservations);

                            reservationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            ListView listView = (ListView) findViewById(R.id.reservationList);
                            listView.setAdapter(reservationAdapter);

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

        reservationSpinner = (Spinner) findViewById(R.id.reservationSpinner);

        AdapterView.OnItemSelectedListener listenerReservations = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(0xFF839e2e);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        //Setting the ArrayAdapter data on the Spinner

        reservationAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listOfReservations);
        reservationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reservationSpinner.setAdapter(reservationAdapter);

        reservationSpinner.setOnItemSelectedListener(listenerReservations);

        cancelReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelReservation();
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

    public void reserveItem(){
        Spinner item = (Spinner) findViewById(R.id.itemSpinner);
        String itemText = item.getSelectedItem().toString();
        if(!itemText.equals("Select Item")) {

            String[] itemList = itemText.split(" ");

            String itemId = itemList[itemList.length - 1];


            String urlH = "/booking/" + customerId;
            System.out.println(urlH);

            RequestParams requestParam = new RequestParams();
            requestParam.add("bookingType", "Reservation");
            requestParam.add("item", itemId);

            HttpUtils.post(urlH, requestParam, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        System.out.println(response);
                        System.out.println("Reserved");
                        refreshErrorMessage("");
                        finish();
                        startActivity(getIntent());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    System.out.println("Response " + responseString);
                    System.out.println("Not reserved");
                    refreshErrorMessage(responseString);
                }
            });



        } else {
            refreshErrorMessage("Please select an item");
        }
        itemAdapter.notifyDataSetChanged();
    }

    public void cancelReservation(){

        Spinner reservations = (Spinner) findViewById(R.id.reservationSpinner);
        String reservationText = reservations.getSelectedItem().toString();
        if(!reservationText.equals("Reservations")) {

            String[] reservationList = reservationText.split(" ");

            String reservationId = reservationList[reservationList.length - 1];

            String urlH = "https://project-group-14-backend.herokuapp.com/bookings/delete/" + reservationId;
            System.out.println(urlH);

        HttpUtils.delete(urlH, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    System.out.println("Cancelled");
                    finish();
                    startActivity(getIntent());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("canceled?");
                refreshErrorMessage("Cancelled");
                finish();
                startActivity(getIntent());
            }
        });
        } else {
            refreshErrorMessage("Please select a reservation");
        }
        reservationAdapter.notifyDataSetChanged();
    }

    private void refreshErrorMessage(String inError) {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.errorReserve);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(inError);
    }

    public void changeText(String customerUsername){
        TextView LogLibrarian = (TextView) findViewById(R.id.LoggedCustomer);
        LogLibrarian.setText("Welcome " + customerUsername);
    }


}