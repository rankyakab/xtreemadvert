package com.xtreemadvert.xtreemads;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {
 private Button chooseImageButton, uploadImageButton;
 private EditText productName,productDesc,productPrice;
 private ImageView productImage;
 private final int IMAGE_REQUEST = 1;
 private Bitmap bitmap;
 private final String  UPLOAD_URL = "http://xtreemadvert.com/version1/upload_product.php";

 //for the navigation darawer
  //private  DrawerLayout mDrawerLayout;

  private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        chooseImageButton = (Button)findViewById(R.id.chooseImageButton);
        uploadImageButton = (Button)findViewById(R.id.uploadImageButton);
        productName = (EditText)findViewById(R.id.productName);
        productImage = (ImageView)findViewById(R.id.productImage);
        productDesc = (EditText)findViewById(R.id.productDesc) ;
        productPrice = (EditText)findViewById(R.id.productPrice);
        chooseImageButton.setOnClickListener(this);
        uploadImageButton.setOnClickListener(this);

        //You have to innitialieze the toolbar for the side navigation toogle to work
       // Toolbar uploadtoolbar =(Toolbar)findViewById(R.id.uploadtoolbar);

        //we have to set the actionbar to the new tool bar
      //  setSupportActionBar(uploadtoolbar);
        //select the drawable layout in other to work with it
       // mDrawerLayout = (DrawerLayout)findViewById(R.id.uploadActivity);
        //this is for the toogling of the navigation toogle button
     //   mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,uploadtoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
   //     mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
   //   NavigationView navigationView = findViewById(R.id.homeview);
  //     navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chooseImageButton:
                    selectImage();
            break;
            case R.id.uploadImageButton:
                Toast.makeText(getApplicationContext(), "execute function k", Toast.LENGTH_LONG).show();
                    uploadImage();
            break;
        }

    }
    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                productImage.setImageBitmap(bitmap);
                productImage.setVisibility(View.VISIBLE);
                productName.setVisibility(View.VISIBLE);
                productDesc.setVisibility(View.VISIBLE);
                productPrice.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getApplicationContext(), "on hte low", Toast.LENGTH_LONG).show();
        }
    }
    private void uploadImage(){
        final Integer uploaderId = SharedPreferrenceManager.getInstance(this).getUserId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response =  jsonObject.getString("message");
                            productImage.setImageResource(0);
                            productImage.setVisibility(View.GONE);

                            productName.setText("");
                            productName.setVisibility(View.GONE);
                            productDesc.setText("");
                            productDesc.setVisibility(View.GONE);
                            productPrice.setText("");
                            productPrice.setVisibility(View.GONE);
                            Toast.makeText(UploadActivity.this, Response, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("productName", productName.getText().toString().trim());
                params.put("productDesc", productDesc.getText().toString().trim());
               params.put("productPrice",productPrice.getText().toString().trim());
               params.put("uploaderId", String.valueOf(uploaderId));
               params.put("productImage", convertImageToString(bitmap));
                return params;
            }
        };
        RequestHandler.getInstance(UploadActivity.this).addToRequestQueue(stringRequest);

    }
    private String convertImageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageByte,Base64.DEFAULT);
    }
    // for the backbutton of the navigation icon
  //  @Override
   // public void onBackPressed() {
      //  if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
        //    mDrawerLayout.closeDrawer(GravityCompat.START);
    //    }else {
        //    super.onBackPressed();
 //       }

  //  }
    //for the menu button toggle
   // @Override
  //  public boolean onOptionsItemSelected(MenuItem item) {
    //    if(mToggle.onOptionsItemSelected(item)){
         //   return true;
     //   }
      //  return super.onOptionsItemSelected(item);
   // }

    // this is for the onclick of the items in the navigation
   // @Override
   /* public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Intent i;
        switch (id) {
            case R.id.home:
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;

            case R.id.adverts:
                i = new Intent(this, AdvertFeedsActivity.class);
                startActivity(i);
                break;


            case R.id.profile:
                i = new Intent(this, UploadActivity.class);
                startActivity(i);
                break;
            case R.id.register:
                i = new Intent(this, RegisterActivity.class);
                startActivity(i);
                break;
            case R.id.login:
                i = new Intent(this, SigninActivity.class);
                startActivity(i);
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
    */
}
