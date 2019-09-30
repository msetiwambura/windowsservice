package com.crdb;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
public class RestBroker {
public String jsonString = "";
public String urlString = "";
    public void sendPost() throws IOException {
        InforLogger inforLogger = new InforLogger();
        String response="";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String userpass = "Admin" + ":" + "Admin_1234";
            String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
            connection.setConnectTimeout(500);
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestProperty("Content-Type", "Application/json;charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Charset", "utf-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            OutputStream stream = connection.getOutputStream();
            stream.write(jsonString.getBytes("UTF-8"));
            stream.close();
            //read response
            InputStream inputStream;
            if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST){
                inputStream = connection.getInputStream();
            }else{
                inputStream = connection.getErrorStream();
            }
            String result = IOUtils.toString(inputStream, "UTF-8");
            if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST){
                inforLogger.LogTissInfo("result:"+result+"");
            }else{
                inforLogger.LogTissError("result:" + result);
            }

            if(connection.getResponseCode() != HttpURLConnection.HTTP_CREATED){
                response = connection.getResponseCode()+":"+connection.getResponseMessage();
                inforLogger.LogTissError("response:{"+response+"}");
            }
            else{
                String instream="";
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((instream = br.readLine()) != null){
                    response = response+instream;
                }
            }
            inputStream.close();
            connection.disconnect();
        } catch (IOException e) {
            inforLogger.LogTissError("response to the request==={" + e.getMessage() + "}");
        }
    }
}
