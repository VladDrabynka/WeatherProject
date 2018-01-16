package com.netcracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Locale;

import com.netcracker.model.FullWeatherInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class WeatherService {

    static private final String APPID_HEADER = "x-api-key";


    private String baseOwmUrl = "http://api.openweathermap.org/data/2.5/";
    private String owmAPPID = "1d66f0f454d6133e0e9be0ea797a1e2b";

    private HttpClient httpClient;

    public WeatherService() {
        this.httpClient = new DefaultHttpClient();
    }

    public WeatherService(HttpClient httpClient) {
        if (httpClient == null)
            throw new IllegalArgumentException("Can't construct a OwmClient with a null HttpClient");
        this.httpClient = httpClient;
    }

    /**
     * Find current city weather
     *
     * @param cityId is the ID of the city
     * @return the StatusWeatherData received
     * @throws JSONException if the response from the OWM server can't be parsed
     * @throws IOException   if there's some network error or the OWM server replies with a error.
     */
    public StatusWeatherData currentWeatherAtCity(int cityId) throws IOException, JSONException {
        String subUrl = String.format(Locale.ROOT, "weather/city/%d?type=json", Integer.valueOf(cityId));
        JSONObject response = doQuery(subUrl);
        return new StatusWeatherData(response);
    }

    /**
     * Find current city weather
     *
     * @param cityName is the name of the city
     * @return the StatusWeatherData received
     * @throws JSONException if the response from the OWM server can't be parsed
     * @throws IOException   if there's some network error or the OWM server replies with a error.
     */
    public WeatherStatusResponse currentWeatherAtCity(String cityName) throws IOException, JSONException {
        String subUrl = String.format(Locale.ROOT, "find/name?q=%s", cityName);
        JSONObject response = doQuery(subUrl);
        return new WeatherStatusResponse(response);
    }

    /**
     * Find current city weather
     *
     * @param cityName    is the name of the city
     * @param countryCode is the two letter country code
     * @return the StatusWeatherData received
     * @throws JSONException if the response from the OWM server can't be parsed
     * @throws IOException   if there's some network error or the OWM server replies with a error.
     */
    public WeatherStatusResponse currentWeatherAtCity(String cityName, String countryCode) throws IOException, JSONException {
        String subUrl = String.format(Locale.ROOT, "find/name?q=%s,%s", cityName, countryCode.toUpperCase());
        JSONObject response = doQuery(subUrl);
        return new WeatherStatusResponse(response);
    }

    private FullWeatherInfo doQuery(String subUrl) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String responseBody;
        HttpGet httpget = new HttpGet(this.baseOwmUrl + subUrl);
        if (this.owmAPPID != null) {
            httpget.addHeader(WeatherService.APPID_HEADER, this.owmAPPID);
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
        } catch (IOException e) {
            throw e;
        } catch (RuntimeException re) {
            httpget.abort();
            throw re;
        } finally {
            if (contentStream != null)
                contentStream.close();
        }
        return mapper.readValue(responseBody, FullWeatherInfo.class);
    }
}
