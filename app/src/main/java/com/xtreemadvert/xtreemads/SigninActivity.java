package com.xtreemadvert.xtreemads;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextUsernameLogin, editTextPasswordLogin;
    private Button buttonLogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        if(SharedPreferrenceManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent (this,ProfileActivity.class));
            return;
        }
        editTextUsernameLogin = (EditText) findViewById(R.id.editTextUsernameLogin);
        editTextPasswordLogin = (EditText) findViewById(R.id.editTextPasswordLogin);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        buttonLogin.setOnClickListener(this);
    }
    public void login(){
        final String username = editTextUsernameLogin.getText().toString().trim();
        final String password = editTextPasswordLogin.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.MEMBER_LOGIN__URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(!jsonResponse.getBoolean("error")){
                                SharedPreferrenceManager.getInstance(getApplicationContext()).userLogin(
                                        jsonResponse.getInt("id"),
                                        jsonResponse.getString("username"),
                                        jsonResponse.getString("email")
                                );

                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                               finish();
                            }else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        jsonResponse.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        progressDialog.show();

    }

    @Override
    public void onClick(View v) {
        if(v == buttonLogin){
            login();
        }
    }
}
