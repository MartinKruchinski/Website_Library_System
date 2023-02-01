package projectgroup14.project_group_14_android;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;

import android.text.method.LinkMovementMethod;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;


import cz.msebera.android.httpclient.Header;
import projectgroup14.project_group_14_android.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String error = null;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    String [] options = {"HeadLibrarian", "Librarian", "Customer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        Button loginButton = findViewById(R.id.loginButton);

        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,options);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        Hyperlink(); //make the hyperlink clickable

    }

    private void Hyperlink() {
        TextView linkTextView = findViewById(R.id.signup);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
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


    public void Login(){

        final EditText editusername = (EditText) findViewById(R.id.Username);
        final EditText editpassword = (EditText) findViewById(R.id.Password);

        String password = editpassword.getText().toString();
        String username = editusername.getText().toString();
        System.out.println("This is the username " + username);
        System.out.println("This is the password " +password);
        RequestParams requestParam = new RequestParams();
        requestParam.add("password", password);

        String urlC = "/login/customer/" + username;
        System.out.println(urlC);
        Spinner mySpinner = (Spinner) findViewById(R.id.simpleSpinner);
        String text = mySpinner.getSelectedItem().toString();
        if(text.equals("Customer")) {
            if(!username.isEmpty() && !password.isEmpty()) {
                HttpUtils.post(urlC, requestParam, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        System.out.println("Gets success");
                        redirectCustomerActivity();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        System.out.println(responseString);
                        if (responseString.equals("Success")) {
                            editusername.setText("");
                            editpassword.setText("");
                            redirectCustomerActivity();
                        } else {
                            refreshErrorMessage(responseString.toString());
                        }
                    }


                });
            }
            else {
                refreshErrorMessage("Invalid username or password");
            }

        }
        else if(text.equals("HeadLibrarian")) {
            System.out.println("Gets here");
            if(!username.isEmpty() && !password.isEmpty()) {
                String urlH = "/login/librarian/" + username;
                HttpUtils.post(urlH, requestParam, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Intent myIntent = new Intent(MainActivity.this, HeadLibrarian.class);
                        MainActivity.this.startActivity(myIntent);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        System.out.println(responseString);
                        if (responseString.equals("Success")) {
                            editusername.setText("");
                            editpassword.setText("");
                            Intent myIntent = new Intent(MainActivity.this, HeadLibrarian.class);
                            MainActivity.this.startActivity(myIntent);
                        } else {
                            refreshErrorMessage(responseString.toString());
                        }
                    }
                });
            } else {
                refreshErrorMessage("Invalid username or password");
            }
        }
        else {
            if (!username.isEmpty() && !password.isEmpty()) {
                String Lurl = "/login/librarian/" + username;
                HttpUtils.post(Lurl, requestParam, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Intent myIntent = new Intent(MainActivity.this, HeadLibrarian.class);
                        MainActivity.this.startActivity(myIntent);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        System.out.println(responseString);
                        if (responseString.equals("Success")) {
                            editusername.setText("");
                            editpassword.setText("");
                            Intent myIntent = new Intent(MainActivity.this, LibrarianActivity.class);
                            MainActivity.this.startActivity(myIntent);
                        } else {
                            refreshErrorMessage(responseString.toString());
                        }
                    }
                });
            } else {
                refreshErrorMessage("Invalid username or password");
            }
        }
    }

    public void redirectCustomerActivity(){
        Intent myIntent = new Intent(this, CustomerActivity.class);
        MainActivity.this.startActivity(myIntent);
    }


    private void refreshErrorMessage(String errorMessage) {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(errorMessage);

        if (errorMessage == null || errorMessage.length() == 0) {
            tvError.setVisibility(View.INVISIBLE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }
}