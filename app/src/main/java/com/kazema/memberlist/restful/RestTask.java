package com.kazema.memberlist.restful;

import android.content.Context;
import android.os.AsyncTask;

import com.kazema.memberlist.R;
import com.kazema.memberlist.utility.GlobalVariable;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Android RestTask (REST) modified from the Android Recipes book.
 */
public class RestTask extends AsyncTask<HttpUriRequest, Void, String> {

    private Context mContext;
    private RestCallBack mRestCallBack;
    private HttpClient mClient = new DefaultHttpClient();

    public RestTask(Context context, RestCallBack restCallBack) {
        mContext = context;
        mRestCallBack = restCallBack;
    }

    @Override
    protected String doInBackground(HttpUriRequest... params) {
        try {
            GlobalVariable globalVariable = (GlobalVariable) mContext.getApplicationContext();
            int retryCount = 0;
            // refresh token if expired
            while (System.currentTimeMillis() / 1000 > globalVariable.getExpTime()) {
                HttpPost postRequest = new HttpPost(GlobalVariable.API_DOMAIN_NAME);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", globalVariable.getUsername());
                jsonObject.put("pwd", globalVariable.getPassword());

                StringEntity input = new StringEntity(jsonObject.toString(), "UTF-8");
                input.setContentType("application/json");
                postRequest.setEntity(input);

                HttpResponse serverResponse = mClient.execute(postRequest);
                if (serverResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader((serverResponse.getEntity().getContent())));

                    String respond = "";
                    String output;
                    while ((output = br.readLine()) != null) {
                        respond += output;
                    }
                    JSONObject respondJsonObject = new JSONObject(respond);
                    JSONObject tokenJsonObject = respondJsonObject.optJSONObject("token");
                    globalVariable.setExpTime(tokenJsonObject.optLong("exp"));
                    globalVariable.setToken(tokenJsonObject.optString("token"));
                    break;
                } else {
                    if (++retryCount > 3) {
                        mRestCallBack.onFailed(mContext.getString(R.string.error_auth_timeout));
                        return null;
                    }
                }
            }

            String respond = "";
            HttpUriRequest request = params[0];
            request.setHeader("Authorization", globalVariable.getToken());
            mClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
            mClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
            HttpResponse serverResponse = mClient.execute(request);
            int statusCode = serverResponse.getStatusLine().getStatusCode();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((serverResponse.getEntity().getContent())));

            String output;
            while ((output = br.readLine()) != null) {
                respond += output;
            }

            mClient.getConnectionManager().shutdown();
            if ((statusCode != HttpStatus.SC_OK)
                    && (statusCode != HttpStatus.SC_CREATED)
                    && (statusCode != HttpStatus.SC_ACCEPTED)
                    && (statusCode != HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION)
                    && (statusCode != HttpStatus.SC_NO_CONTENT)
                    && (statusCode != HttpStatus.SC_RESET_CONTENT)
                    && (statusCode != HttpStatus.SC_PARTIAL_CONTENT)
                    && (statusCode != HttpStatus.SC_MULTI_STATUS)) {
                mRestCallBack.onFailed(respond);
                return null;
            }
            return respond;
        } catch (Exception e) {
            e.printStackTrace();
            mRestCallBack.onFailed(e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String respond) {
        if (respond == null) {
            return;
        }
        mRestCallBack.onSuccess(respond);
    }
}
