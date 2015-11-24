package compumovil.udea.edu.co.weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dante on 23/11/15.
 */
public class WeatherHTTPClient {
    private static String BASE_URL="http://api.openweathermap.org/data/2.5/weather?APPID=";
    private static String OPEN_WEATHER_MAP_API_KEY="335b89bc9cb411e5a0a1b4b992cf5656";
    public String getWeatherData(String location){
        HttpURLConnection conn=null;
        InputStream is=null;
        URL url=null;
        try{
            url=new URL(BASE_URL+ OPEN_WEATHER_MAP_API_KEY+"&q="+location);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        try{
            conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
            StringBuffer buffer =new StringBuffer();
            is=conn.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line+"\r\n");
            }
            is.close();
            conn.disconnect();
            return buffer.toString();
        }catch(Throwable t){
            t.printStackTrace();
        }
        finally {
            try{
                is.close();
            }catch (Throwable t){

            }
            try {
                conn.disconnect();
            }catch (Throwable t){

            }
        }
        return null;
    }
}
