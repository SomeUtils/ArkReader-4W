package vip.cdms.arkreader.resource.network;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import lombok.SneakyThrows;
import lombok.val;
import vip.cdms.arkreader.resource.utils.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public class Network {
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;

    public static NetworkCache cache = null;
    public static Proxy proxy = null;

    public static JsonValue fetchJson(String url) {
        return Json.parse(fetchString(url));
    }

    @SneakyThrows
    public static String fetchString(String url) {
        val cached = cache != null ? cache.getAsString(url) : null;
        if (cached != null) return cached;

        val connection = openConnection(url);
        val inputStream = connection.getInputStream();
        val reader = new BufferedReader(new InputStreamReader(inputStream));
        val response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            response.append(line);

        val body = response.toString();
        if (cache != null) cache.set(url, body);
        reader.close();
        connection.disconnect();
        return body;
    }

    public static byte[] fetchRawOrNull(String url) {
        try {
            return fetchRaw(url);
        } catch (Exception e) {
            return null;
        }
    }

    @SneakyThrows
    public static byte[] fetchRaw(String url) {
        val cached = cache != null ? cache.get(url) : null;
        if (cached != null) return cached;

        val connection = openConnection(url);
        val inputStream = connection.getInputStream();
        val bytes = IOUtils.readAll(inputStream);
        if (cache != null) cache.set(url, bytes);
        connection.disconnect();
        return bytes;
    }

    @SneakyThrows
    private static HttpURLConnection openConnection(String url) {
        val httpUrl = new URL(url);
        val connection = (HttpURLConnection) (proxy == null ?
                httpUrl.openConnection() : httpUrl.openConnection(proxy));
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);

        val responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK)
            throw new IOException("HTTP error code: " + responseCode);
        return connection;
    }
}
