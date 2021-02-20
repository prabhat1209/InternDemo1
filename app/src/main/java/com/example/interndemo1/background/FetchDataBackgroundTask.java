package com.example.interndemo1.background;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.interndemo1.parser.JSONParse;
import com.example.interndemo1.pojo.CovidData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FetchDataBackgroundTask extends AsyncTask<String,String,String> {

    String url = "https://api.rootnet.in/covid19-in/stats/latest";
    String res = "";
    Context context;
    JSONObject jsonObject = null;
    JSONParse jsonParse = null;
    String status;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;
    public List<CovidData> covidDataList;
    public static String total, confirmedCasesIndian, confirmedCasesForeign, discharged, deaths;

    public FetchDataBackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "", "Loading...Please wait...");
        alertDialogBuilder = new AlertDialog.Builder(context);
        covidDataList = new ArrayList<>();
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            if(isNetworkAvailable(context)) {
                HashMap<String, String> para = new HashMap<String, String>();
//            para.put("name", name); // format to put parameter in map

                jsonParse = new JSONParse();

                res = jsonParse.makeHttpRequest(url, para); // getting response from URL in res

                jsonObject = new JSONObject(res);

                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("regional");

                total = jsonObject.getJSONObject("data").getJSONObject("summary").getString("total");
                confirmedCasesIndian = jsonObject.getJSONObject("data").getJSONObject("summary").getString("confirmedCasesIndian");
                confirmedCasesForeign = jsonObject.getJSONObject("data").getJSONObject("summary").getString("confirmedCasesForeign");
                discharged = jsonObject.getJSONObject("data").getJSONObject("summary").getString("discharged");
                deaths = jsonObject.getJSONObject("data").getJSONObject("summary").getString("deaths");

                for (int i = 0; i < jsonArray.length(); i++) {
                    CovidData covidData = new CovidData();
                    JSONObject json = jsonArray.getJSONObject(i);
                    covidData.setLoc(json.getString("loc"));
                    covidData.setConFor(json.getString("confirmedCasesForeign"));
                    covidData.setConInd(json.getString("confirmedCasesIndian"));
                    covidData.setDischarge(json.getString("discharged"));
                    covidData.setTotalCon(json.getString("totalConfirmed"));
                    covidData.setDeath(json.getString("deaths"));
                    covidDataList.add(covidData);
                }

                return "done";
            } else {
                return "noI";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "null";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        if(s.equalsIgnoreCase("noI")){
            String title = "Oops !!!";
            String desc = "No Internet Connection";
            String ok = "OK";
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder
                    .setMessage(desc)
                    .setCancelable(true)
                    .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else if(s.equalsIgnoreCase("done")){
            System.out.println("Size : "+covidDataList.size());
            for(int i=0;i<covidDataList.size();i++){
                System.out.println("1 : "+covidDataList.get(i).getLoc());
                System.out.println("2 : "+covidDataList.get(i).getConInd());
                System.out.println("3 : "+covidDataList.get(i).getConFor());
                System.out.println("4 : "+covidDataList.get(i).getDischarge());
                System.out.println("5 : "+covidDataList.get(i).getDeath());
                System.out.println("6 : "+covidDataList.get(i).getTotalCon());
            }
            System.out.println(confirmedCasesIndian);
            System.out.println(total);
            System.out.println(confirmedCasesForeign);
            System.out.println(deaths);
            System.out.println(discharged);
        } else {
            String title = "Oops !!!";
            String desc = "Something went wrong, Plz try again later.";
            String ok = "OK";
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder
                    .setMessage(desc)
                    .setCancelable(true)
                    .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}
