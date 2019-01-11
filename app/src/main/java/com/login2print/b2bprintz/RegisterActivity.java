package com.login2print.b2bprintz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.login2print.b2bprintz.interfaceClass.TaskCompletedInterface;
import com.login2print.b2bprintz.supportClasses.supportLibs;
import com.login2print.b2bprintz.supportClasses.Validators;
import com.login2print.b2bprintz.webServiceHandlers.WSHttpClientAsyncTaskHandler;
import com.login2print.b2bprintz.webServiceHandlers.WSHttpURLAsyncTaskHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TaskCompletedInterface {
    private String webServiceURL;
    private Spinner statesSpinner;
    private List<String> stateNames = new ArrayList<String>();
    private List<Integer> stateIDS = new ArrayList<Integer>();
    private ArrayAdapter<String> stateAdapter;
    private String debugTAG = "debugger";
    private Validators validateThis = new Validators();
    JSONObject sendJsonDataObject = new JSONObject();

    String nameVar, companyVar, addressVar, mobileVar, emailVar;
    private EditText name, company,address, mobile, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //make activity full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //connect layout
        setContentView(R.layout.activity_register);


        name = (EditText) findViewById(R.id.name);
        company = (EditText) findViewById(R.id.company);
        address = (EditText) findViewById(R.id.address);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);
        statesSpinner = (Spinner) findViewById(R.id.states);


        stateIDS.add(0);
        stateNames.add("Select State");

        stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,  stateNames);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statesSpinner.setAdapter(stateAdapter);
        // Spinner click listener
        statesSpinner.setOnItemSelectedListener(this);

        loadStatesWebServiceCall();


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position ).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    public void Register(View v){

        nameVar = name.getText().toString();
        companyVar = company.getText().toString();
        addressVar = address.getText().toString();
        mobileVar = mobile.getText().toString();
        emailVar = email.getText().toString();

        String validateResult="";

        validateResult = validateThis.validateDefault("Name", nameVar);
        if (!validateResult.equals("NoError")){
            Toast.makeText(this,validateResult,Toast.LENGTH_SHORT).show();
            name.setError(validateResult);
            name.setFocusable(true);
            return;
        }

        validateResult = validateThis.validateDefault("Company Name", companyVar);
        if (!validateResult.equals("NoError")){
            Toast.makeText(this,validateResult,Toast.LENGTH_SHORT).show();
            company.setError(validateResult);
            company.setFocusable(true);
            return;
        }

        validateResult = validateThis.validateDefault("Address", addressVar);
        if (!validateResult.equals("NoError")){
            Toast.makeText(this,validateResult,Toast.LENGTH_SHORT).show();
            address.setError(validateResult);
            address.setFocusable(true);
            return;
        }


        if(statesSpinner.getSelectedItem().toString()=="Select State"){
            Toast.makeText(this,"Select State?",Toast.LENGTH_SHORT).show();
            statesSpinner.setFocusable(true);
            return;
        }


        validateResult = validateThis.validatePhone(mobileVar);
        if (!validateResult.equals("NoError")){
            Toast.makeText(this,validateResult,Toast.LENGTH_SHORT).show();
            mobile.setError(validateResult);
            mobile.setFocusable(true);
            return;
        }

        validateResult = validateThis.validateEmail(emailVar);
        if (!validateResult.equals("NoError")){
            Toast.makeText(this,validateResult,Toast.LENGTH_SHORT).show();
            email.setError(validateResult);
            email.setFocusable(true);
            return;
        }


        webServiceURL = getString(R.string.WebServiceURLRegister);

        try {
            sendJsonDataObject.put("PersonName",name.getText().toString());
            sendJsonDataObject.put("CompanyName",company.getText().toString());
            sendJsonDataObject.put("CustoemrAddress",address.getText().toString());
            sendJsonDataObject.put("StateId",stateIDS.get(statesSpinner.getSelectedItemPosition()));
            sendJsonDataObject.put("Mobile_1",mobile.getText().toString());
            sendJsonDataObject.put("Email",email.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (supportLibs.isConnectedToInternet(this)) {
            new WSHttpClientAsyncTaskHandler(this, webServiceURL, sendJsonDataObject, "Processing...").execute();
        } else {
            Toast.makeText(this, getString(R.string.NoInternetMsg), Toast.LENGTH_SHORT).show();
        }

    }







    private void loadStatesWebServiceCall() {
        webServiceURL = getString(R.string.WebServiceURLStateList);

        if (supportLibs.isConnectedToInternet(this)) {
            new WSHttpURLAsyncTaskHandler(this, webServiceURL, "GET","Loading State List").execute();
        } else {
            Toast.makeText(this, getString(R.string.NoInternetMsg), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onTaskComplete(String responseFromServer) {

        if (responseFromServer != null) {

            Log.d(debugTAG, responseFromServer);
            responseFromServer = supportLibs.filterResponse(responseFromServer);

            try {
                JSONObject jsonObj = new JSONObject(responseFromServer);
                JSONObject responseStatusObject = jsonObj.getJSONObject("status");
                String jsonResponseType = responseStatusObject.getString("requestType");
                int jsonDataCount=0;

                switch (jsonResponseType) {
                    case "state":
                        jsonDataCount = responseStatusObject.getInt("count");
                        if (jsonDataCount==0) {
                            Toast.makeText(this, getString(R.string.NoStatesFoundMsg), Toast.LENGTH_LONG).show();
                        } else {
                            JSONArray stateListReponseArray = jsonObj.getJSONArray("allstates");
                            fillStateList(stateListReponseArray);
                        }
                        break;

                   /* case "newUserReg":
                        String jsonResponse = responseStatusObject.getString("response");
                        if (jsonResponse.contains("AlreadyExists")) {
                            Toast.makeText(this, getString(R.string.CustomerExistMsg), Toast.LENGTH_LONG).show();
                        } else if (jsonResponse.contains("Fail")) {
                            Toast.makeText(this, getString(R.string.CustomerRegErrorMsg), Toast.LENGTH_LONG).show();
                        } else if (jsonResponse.contains("Success")) {
                            Toast.makeText(this, nameVar+" "+ getString(R.string.CustomerRegSuccessMsg)+" Review Team Will Call You Soon...Thanks", Toast.LENGTH_LONG).show();

                            String msg= nameVar + getString(R.string.CustomerRegistrationSuccessMsg);
                            Intent i = new Intent(this, RegistrationSuccessActivity.class);
                            i.putExtra("Message", msg);
                            startActivity(i);
                            finish();
                        }
                        break;*/
                }

            } catch (final JSONException e) {
                Toast.makeText(this, "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, getString(R.string.NoResponseFromServerMsg), Toast.LENGTH_LONG).show();
        }

    }


    private void fillStateList(JSONArray jsonArrayDATA){
        stateIDS.clear();
        stateNames.clear();
        stateIDS.add(0);
        stateNames.add("Select State");
        //Attempt to invoke virtual method 'void android.widget.ArrayAdapter.clear()' on a null object reference
        //Attempt to invoke virtual method 'boolean android.widget.ArrayAdapter.isEmpty()' on a null object reference
        /*if(!stateAdapter.isEmpty()) {
            stateAdapter.clear();
        } */
        try {
            for (int i = 0; i < jsonArrayDATA.length(); i++) {
                JSONObject jsonDATA = jsonArrayDATA.getJSONObject(i);
                int stateid = jsonDATA.getInt("stateId");
                String statename = jsonDATA.getString("statename");
                stateIDS.add(stateid);
                stateNames.add(statename);
            }
            stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,  stateNames);
            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            statesSpinner.setAdapter(stateAdapter);

        } catch (JSONException e) {
            Toast.makeText(this, "Json State List Parsing Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}
