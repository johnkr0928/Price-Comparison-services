package com.spa.servicedealz.services;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.spa.servicedealz.R;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.utils.EditTextLocker;
import com.spa.utils.Jsondata;
import com.spa.utils.MenuItemGlobal;
import com.spa.utils.Validation;
import com.spa.fragment.ShowDailog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by E0115Diwakar on 2/16/2015.
 */
/**
 * FileName : ElectricityServiceActivity
 * Description : Show ElectricityService
 * Dependencies : Internet
 */
public class ElectricityServiceActivity extends Activity implements View.OnClickListener, NetworkUtil.changeNetworkInterFace {
    //ActionBar actionBar;
    private Spinner mSpinnerVender;
    public static ArrayList<HashMap<String, String>> service_prefrences = new ArrayList<HashMap<String, String>>();
    private Boolean mIsInternetPresent = false;
    private EditText mEditTextEndDate, mEditTextPrice;
    public static int YEAR, MONTH, DAY, position, count;
    private Button mButtonUpdate, mButtonUnsubscribe;
    public static final int DATE_DIALOG_ID = 0, DATE_DIALOG_ID1 = 1;
    private TextView mTextViewTitle, mTextViewVendor, mTextViewPrice, mTextViewEndDate;
    private CheckBox mCheckBoxContract;
    private ArrayList<String> mVenderlist = new ArrayList<String>();
    private ImageView mImageViewClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_form);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        //==========================================================================================
        mTextViewTitle = (TextView) findViewById(R.id.btn_title);
        mTextViewTitle.setText(getResources().getText(R.string.electricity_services));
        mTextViewVendor = (TextView) findViewById(R.id.txt_vendor);
        String text = "<font color=#000000>Vendor</font> <font color=#ff00000>*</font>";
        mTextViewVendor.setText(Html.fromHtml(text));
        //==========================================================================================
        mCheckBoxContract = (CheckBox) findViewById(R.id.chk_contract);
        mImageViewClose = (ImageView) findViewById(R.id.img_close);
        mButtonUpdate = (Button) findViewById(R.id.btn_update);
        mEditTextPrice = (EditText) findViewById(R.id.etx_price);
        EditTextLocker decimalEditTextLocker = new EditTextLocker(mEditTextPrice);
        decimalEditTextLocker.limitFractionDigitsinDecimal(3);
        mEditTextEndDate = (EditText) findViewById(R.id.etx_end_date);
        mSpinnerVender = (Spinner) findViewById(R.id.spn_vender);
        mButtonUnsubscribe = (Button) findViewById(R.id.btn_unsubscribe);
        mButtonUnsubscribe.setOnClickListener(this);
        // spn_state = (Spinner) findViewById(R.id.spn_state);
        // spn_upload = (Spinner) findViewById(R.id.spn_upload);
        // spn_download = (Spinner) findViewById(R.id.spn_download);
        //etx_start_date.setOnClickListener(this);
        mEditTextEndDate.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
        mCheckBoxContract.setOnClickListener(this);
        mImageViewClose.setOnClickListener(this);
        mTextViewPrice = (TextView) findViewById(R.id.txt_price);
        mTextViewEndDate = (TextView) findViewById(R.id.txt_enddate);
        mTextViewEndDate.setText(Html.fromHtml("<font color=#000000>Contract End Date</font> <font color=#ff00000>*</font>"));
        mTextViewPrice.setText(Html.fromHtml("<font color=#000000>Price($)</font> <font color=#ff00000>*</font>"));

        //==========================================================================================

        final Calendar c = Calendar.getInstance();
        YEAR = c.get(Calendar.YEAR);

        MONTH = c.get(Calendar.MONTH);

        DAY = c.get(Calendar.DAY_OF_MONTH);
        c.before(Calendar.DATE);
        //==========================================================================================
        //==========================================================================================
        NetworkUtil.setChangeNetWorkListener(ElectricityServiceActivity.this);
        mIsInternetPresent = NetworkUtil.isConnectingToInternet(ElectricityServiceActivity.this);
        if (!mIsInternetPresent) {
            // Internet Connection is not present
            Toast.makeText(ElectricityServiceActivity.this, "No Internet Connection!",
                    Toast.LENGTH_SHORT).show();
            // stop executing code by return
            return;
        } else {
            new HttpGetAsyncTask_vender().execute();
            new HttpGetAsyncTask_get().execute();


        }
        //==========================================================================================
    }

    private void updateDisplay1() {
        if (MONTH >= 9 && DAY >= 10) {
            mEditTextEndDate.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append(YEAR).append("-").append(MONTH + 1).append("-")

                    .append(DAY));
        } else if (MONTH >= 9 && DAY < 10) {
            mEditTextEndDate.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append(YEAR).append("-").append(MONTH + 1).append("-")
                    .append("0").append(DAY));

        } else if (MONTH < 9 && DAY >= 10) {
            mEditTextEndDate.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append(YEAR).append("-")

                    .append("0").append(MONTH + 1).append("-").append(DAY));
        } else {
            mEditTextEndDate.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append(YEAR).append("-").append("0").append(MONTH + 1)
                    .append("-")

                    .append("0").append(DAY));
        }
    }


    private DatePickerDialog.OnDateSetListener DateSetListener1 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            YEAR = year;
            MONTH = monthOfYear;
            DAY = dayOfMonth;

            updateDisplay1();
        }
    };

    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case DATE_DIALOG_ID1:
                DatePickerDialog da1 = new DatePickerDialog(this, DateSetListener1,
                        YEAR, MONTH, DAY);

                da1 = new DatePickerDialog(this, DateSetListener1, YEAR, MONTH, DAY);

                da1.setCanceledOnTouchOutside(false);
                da1.setCancelable(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                    da1.getDatePicker().setCalendarViewShown(false);
                    da1 = new DatePickerDialog(this, R.style.Base_Theme_AppCompat_Light_Dialog, DateSetListener1, YEAR, MONTH, DAY);
                } else {
                    da1 = new DatePickerDialog(this, DateSetListener1, YEAR, MONTH, DAY);

                }
                da1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                return da1;

        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chk_contract:
                //showDialog(DATE_DIALOG_ID);

                if (mCheckBoxContract.isChecked()) {
                    mEditTextEndDate.setText("");
                    mEditTextEndDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                } else {
                    mEditTextEndDate.setBackgroundResource(R.drawable.edt_background);
                }
                break;
         /*   case R.id.etx_start_date:
                showDialog(DATE_DIALOG_ID);
                break;*/
            case R.id.etx_end_date:
                if (mCheckBoxContract.isChecked()) {

                } else {
                    showDialog(DATE_DIALOG_ID1);
                }

                break;
            case R.id.img_close:
                finish();

                break;
            case R.id.btn_unsubscribe:
                if (!mIsInternetPresent) {
                    // Internet Connection is not present
                    Toast.makeText(ElectricityServiceActivity.this, "No Internet Connection!",
                            Toast.LENGTH_SHORT).show();
                    // stop executing code by return

                } else {

                    new HttpGetAsyncTask_unsubscribe().execute();


                }


                break;
            case R.id.btn_update:
                if (!mIsInternetPresent) {
                    // Internet Connection is not present
                    Toast.makeText(ElectricityServiceActivity.this, "No Internet Connection!",
                            Toast.LENGTH_SHORT).show();
                    // stop executing code by return
                    return;
                } else {
                    if (mSpinnerVender.getSelectedItem().toString().equalsIgnoreCase("Select Vendor")) {
                        Toast.makeText(ElectricityServiceActivity.this, "Please Select Vendor",
                                Toast.LENGTH_LONG).show();
                    } else if (Validation.isFieldEmpty(mEditTextPrice)) {
                        Toast.makeText(this, "Please fill price field",
                                Toast.LENGTH_SHORT).show();
                    } else if (mCheckBoxContract.isChecked()) {
                        new HttpGetAsyncTask().execute();

                    } else {
                        if (Validation.isFieldEmpty(mEditTextEndDate)) {
                            Toast.makeText(this, "Please Select Date",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            new HttpGetAsyncTask().execute();
                        }
                    }


                }


                break;
        }
    }

    @Override
    public void updateNetwork(String s) {

    }

    class HttpGetAsyncTask_unsubscribe extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(ElectricityServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                SharedPreferences sp = getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);
                response = Jsondata.get_Unsuscribe_service(sp.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.electricity_id)), ElectricityServiceActivity.this);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress dialog
         * **/

        protected void onPostExecute(String response) {
            pDialog.dismiss();
            if (Jsondata.Success.equalsIgnoreCase("true")) {
                SharedPreferences sp = getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("cell_phone_completed", "yes");
                editor.commit();
                finish();


            } else {
                ShowDailog.Show_Alert_Login(ElectricityServiceActivity.this, getResources().getText(R.string.server_error).toString());
            }
        }

    }

    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ElectricityServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String is_contract = "false";
                if (mCheckBoxContract.isChecked()) {
                    is_contract = "true";
                } else {

                }
                String price = "0";
                if (mEditTextPrice.getText().toString().length() <= 0) {

                } else {
                    price = mEditTextPrice.getText().toString();
                }
                SharedPreferences sp = getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);
                String provider = mSpinnerVender.getSelectedItem().toString();
                int index = MenuItemGlobal.getIndex(provider, Jsondata.provider_id_list);
                String provider_id = Jsondata.provider_id_list.get(index).get("id").toString();
                response = Jsondata.getservices(sp.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.electricity_id)), provider_id, mEditTextEndDate.getText().toString(), is_contract, price, "", "", "", ElectricityServiceActivity.this);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress dialog
         * **/

        protected void onPostExecute(String response) {
            pDialog.dismiss();
            if (Jsondata.Success.equalsIgnoreCase("true")) {
                SharedPreferences sp = getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("electricity_completed", "yes");
                editor.commit();
                finish();


            } else {
                ShowDailog.Show_Alert_Login(ElectricityServiceActivity.this, getResources().getText(R.string.server_error).toString());
            }
        }

    }

    class HttpGetAsyncTask_get extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ElectricityServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                SharedPreferences sp = getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);
                service_prefrences = Jsondata.getservices_prefences(sp.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.electricity_id)), ElectricityServiceActivity.this);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress dialog
         * **/

        protected void onPostExecute(String response) {
            pDialog.dismiss();

            try {
//==================================================================================================

                if (Jsondata.Success.equalsIgnoreCase("true")) {
                    mButtonUnsubscribe.setVisibility(View.VISIBLE);
                    String check_string = service_prefrences.get(0).get("is_contract").toString();
                    String spin_string = service_prefrences.get(0).get("service_provider").toString();
                    if (check_string.equalsIgnoreCase("true")) {
                        mCheckBoxContract.setChecked(true);
                        mEditTextEndDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                    } else {
                        mCheckBoxContract.setChecked(false);
                        String[] departure = service_prefrences.get(0).get("contract_date").toString().split(" ");
                        // String[] returndgate = returndate.split("/");
                        String depart = departure[0];
                        mEditTextEndDate.setText(depart);
                    }

                    mEditTextPrice.setText(service_prefrences.get(0).get("contract_fee").toString());


                    int index = MenuItemGlobal.getIndex(spin_string, Jsondata.provider_id_list);
                    String provider_name = Jsondata.provider_id_list.get(index).get("provider_name").toString();
                    int value = mVenderlist.indexOf(provider_name);

                    mSpinnerVender.setSelection(value);
//==================================================================================================
                } else {
                    // Show_Dailog.Show_Alert_Login(Electricity_Services.this, getResources().getText(R.string.server_error).toString());
                }
            } catch (Exception e) {
            }
        }

    }

    class HttpGetAsyncTask_vender extends AsyncTask<String, Void, String> {
        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                SharedPreferences sp = getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);
                mVenderlist = Jsondata.getservices_provider(sp.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.electricity_id)), ElectricityServiceActivity.this);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress dialog
         * **/

        protected void onPostExecute(String response) {


            String[] array = mVenderlist.toArray(new String[mVenderlist.size()]);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ElectricityServiceActivity.this, android.R.layout.simple_spinner_dropdown_item, array); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            mSpinnerVender.setAdapter(spinnerArrayAdapter);

        }

    }
}
