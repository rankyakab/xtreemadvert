package com.xtreemadvert.xtreemads;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextUsername, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private TextView textViewLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //redirect back to login if user has already been logged in
        if(SharedPreferrenceManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent (this,SigninActivity.class));
            return;
        }
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        buttonRegister = (Button) findViewById(R.id.registeruser);
        textViewLogin = (TextView)findViewById(R.id.textViewLogin);


        progressDialog = new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

    }
    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.setMessage("Member Registration in progress...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
      //  RequestQueue requestQueue = Volley.newRequestQueue(this);
        //requestQueue.add(stringRequest);
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    public void onClick(View v) {
        if(v ==buttonRegister){
            registerUser();
        }
        if(v==textViewLogin){
            startActivity(new Intent(this, SigninActivity.class));

        }
    }


}
