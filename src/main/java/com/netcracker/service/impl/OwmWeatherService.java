package com.netcracker.service.impl;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

import com.netcracker.model.WeatherInfo;
import com.netcracker.service.WeatherService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class OwmWeatherService implements WeatherService {

    private final static Logger logger = Logger.getLogger(OwmWeatherService.class);

    static private final String APPID_HEADER = "x-api-key";

    private String baseOwmUrl = "http://api.openweathermap.org/data/2.5/";
    private String owmAPPID = "1d66f0f454d6133e0e9be0ea797a1e2b";

    private HttpClient httpClient;

    public OwmWeatherService() {
        this.httpClient = new DefaultHttpClient();
    }

    public OwmWeatherService(HttpClient httpClient) {
        if (httpClient == null)
            throw new IllegalArgumentException("Can't construct a OwmClient with a null HttpClient");
        this.httpClient = httpClient;
    }

    public WeatherInfo currentWeatherAtCity(int cityId) {
        try {
            String subUrl = String.format(Locale.ROOT, "weather?id=%d&units=metric", cityId);
            return doQuery(subUrl);
        } catch (IOException ex){
            logger.error("Exception in OwmWeatherService: ", ex);
        }
        return null;
    }

    public WeatherInfo currentWeatherAtCity(String cityName){
        try {
            String subUrl = String.format(Locale.ROOT, "weather?q=%s&units=metric", cityName);
            return doQuery(subUrl);
        } catch (IOException ex){
            logger.error("Exception in OwmWeatherService: ", ex);
        }
        return null;
    }

    public WeatherInfo currentWeatherAtCity(String cityName, String countryCode) {
        try {
            String subUrl = String.format(Locale.ROOT, "weather?q=%s,%s&units=metric", cityName, countryCode.toUpperCase());
            return doQuery(subUrl);
        } catch (IOException ex){
            logger.error("Exception in OwmWeatherService: ", ex);
        }
        return null;
    }

    private WeatherInfo doQuery(String subUrl) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String responseBody;
        HttpGet httpget = new HttpGet(this.baseOwmUrl + subUrl);
        if (this.owmAPPID != null) {
            httpget.addHeader(OwmWeatherService.APPID_HEADER, this.owmAPPID);
        }

        HttpResponse response = this.httpClient.execute(httpget);
        InputStream contentStream = null;
        try {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine == null) {
                throw new IOException("Unable to get a response from OWM server");
            }
            int statusCode = statusLine.getStatusCode();
            if (statusCode < 200 && statusCode >= 300) {
                throw new IOException(
                        String.format("OWM server responded with status code %d: %s", statusCode, statusLine));
            }
            /* Read the response content */
            HttpEntity responseEntity = response.getEntity();
            contentStream = responseEntity.getContent();
            Reader isReader = new InputStreamReader(contentStream);
            int contentSize = (int) responseEntity.getContentLength();
            if (contentSize < 0)
                contentSize = 8 * 1024;
            StringWriter strWriter = new StringWriter(contentSize);
            char[] buffer = new char[8 * 1024];
            int n = 0;
            while ((n = isReader.read(buffer)) != -1) {
                strWriter.write(buffer, 0, n);
            }
            responseBody = strWriter.toString();
            contentStream.close();
            EntityUtils.consume(responseEntity);
        } catch (IOException e) {
            throw e;
        } catch (RuntimeException ex) {
            httpget.abort();
            throw ex;
        } finally {
            if (contentStream != null)
                contentStream.close();
        }
        WeatherInfo info = null;
        try {
            JsonNode root = mapper.readTree(responseBody);

            long visibility = root.path("visibility").asLong();
            Timestamp dt = new Timestamp(root.path("dt").asLong());

            JsonNode main = root.path("main");
            double temp = main.path("temp").asDouble();
            long pressure = main.path("pressure").asLong();
            long humidity = main.path("humidity").asLong();
            double temp_min = main.path("temp_min").asDouble();
            double temp_max = main.path("temp_max").asDouble();

            JsonNode sys = root.path("sys");
            Timestamp sunrise = new Timestamp(sys.path("sunrise").asLong());
            Timestamp sunset = new Timestamp(sys.path("sunset").asLong());

            info = new WeatherInfo(temp, pressure, humidity, temp_min, temp_max, visibility,
                    dt, sunrise, sunset);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        contentStream.close();
        return info;
    }
}