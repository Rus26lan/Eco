package com.rundgrun.eco.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rundgrun.eco.data.exceptions.AuthorizationException;
import com.rundgrun.eco.data.exceptions.UpdateTokenException;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class GrafanaClient {
    private GrafanaClientData data;
    private GrafanaListener listener;
    volatile boolean isRunning = false;

    public void singleConnection(){
        data = new GrafanaClientData();
        data.setUser("r.melnikov");
        data.setPassword("234Mt234");
        register();
        try {
            updateToken();
        } catch (UpdateTokenException e) {
            throw new RuntimeException(e);
        }
        getEventLog();
    }

    public void startClient() {
        if (!isRunning) {
            isRunning = true;
            readData();
            try {
                updateToken();
            } catch (UpdateTokenException ex) {
                register();
                writeData();
            }
            Thread thread = new Thread(() -> {
                try {
                    while (isRunning) {
                        updateToken();
                        getEventLog();
                        getAllPak();
                        Thread.sleep(300000);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
        }
    }

    public void stopClient() {
        writeData();
        if (isRunning) {
            isRunning = false;
        }
    }

    private void register() {
        HttpURLConnection con;
        String payload = "{\"user\":\"" + data.getUser() + "\",\"password\":\"" + data.getPassword() + "\"}";
        try {
            URL url;
            url = new URL("http://10.1.3.85:3000/login");
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("accept", "application/json, text/plain, */*");
            con.setRequestProperty("Accept-Encoding", "gzip, deflate");
            con.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
            con.setRequestProperty("Connection", "keep-alive");
            con.setRequestProperty("Content-Length", "43");
            con.setRequestProperty("Host", "10.1.3.85:3000");
            con.setRequestProperty("Origin", "http://10.1.3.85:3000");
            con.setRequestProperty("Referer", "http://10.1.3.85:3000/login");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
            con.setRequestProperty("content-type", "application/json");
            con.setRequestMethod("POST");
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(payload);
            wr.flush();
            int responseCode = con.getResponseCode();
            System.out.println("Registration: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String cookie = con.getHeaderField("Set-Cookie");
                if (cookie != null) {
                    data.setToken(cookie.split(";")[0]);
                }
            } else {
                System.out.println(responseCode);
                throw new AuthorizationException();
            }
            con.disconnect();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (AuthorizationException e) {
            throw new RuntimeException(e);
        }
    }

    private void getEventLog() {
        if (listener != null) {
            HttpURLConnection con;
            String payload = "{\"queries\":[{\"refId\":\"D\",\"datasource\":{\"uid\":\"hqShlfU7k\",\"type\":\"mysql\"},\"rawSql\":\"select \\nCONVERT_TZ(m.created, @@session.time_zone, '+00:00') as \\\"time\\\",\\nc.modem_name,\\nm.state_220 AS \\\"220v\\\",\\n(m.state_modem) AS \\\"modem\\\",\\n(m.state_sensors) AS \\\"sensors\\\",\\nc.district_name,\\nc.post_address,\\nm.event_id,\\nc.point_id\\nfrom points_events m \\nleft join points_catalog c USING(point_id) \\n\\n\",\"format\":\"table\",\"datasourceId\":3,\"intervalMs\":60000,\"maxDataPoints\":740}],\"range\":{\"from\":\"2023-02-01T06:43:13.320Z\",\"to\":\"2023-02-01T12:43:13.321Z\",\"raw\":{\"from\":\"now-6h\",\"to\":\"now\"}},\"from\":\"1675233793320\",\"to\":\"1675255393321\"}";
            try {
                URL url;
                url = new URL("http://10.1.3.85:3000/api/ds/query");
                con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Accept-Encoding", "gzip, deflate");
                con.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
                con.setRequestProperty("Connection", "keep-alive");
                con.setRequestProperty("Content-Length", "628");
                con.setRequestProperty("Cookie", data.getToken());
                con.setRequestProperty("Host", "10.1.3.85:3000");
                con.setRequestProperty("Origin", "http://10.1.3.85:3000");
                con.setRequestProperty("Referer", "http://10.1.3.85:3000/d/BV9D9zUnk/monitoring-airnode?orgId=1&refresh=5m");
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
                con.setRequestProperty("accept", "application/json, text/plain, */*");
                con.setRequestProperty("content-type", "application/json");
                con.setRequestProperty("x-grafana-org-id", "1");
                con.setRequestMethod("POST");
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(payload);
                wr.flush();
                int responseCode = con.getResponseCode();
                System.out.println("EventLog: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    listener.updateEventLog(PakResponseAnalyser.analyzeEventLog(response.toString()));
                    in.close();
                } else {
                    System.out.println("request did not work.");
                }
                con.disconnect();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void getAllPak() {
        if (listener != null) {
            HttpURLConnection con;
            String payload = "{\"queries\":[{\"refId\":\"A\",\"datasource\":{\"uid\":\"hqShlfU7k\",\"type\":\"mysql\"},\"rawSql\":\"select \\nCONVERT_TZ(m.created, @@session.time_zone, '+00:00') as \\\"time\\\",\\nc.modem_name,\\nm.state_220 AS \\\"220v\\\",\\n(m.state_modem) AS \\\"modem\\\",\\n(m.state_sensors) AS \\\"sensors\\\",\\nc.district_name,\\nc.post_address,\\nc.point_id,\\nc.note as \\\"Комментарий\\\"\\nfrom points_catalog c \\nleft join points_info m USING(point_id) \\norder by modem_name\\n\",\"format\":\"table\",\"datasourceId\":3,\"intervalMs\":60000,\"maxDataPoints\":1011}],\"range\":{\"from\":\"2023-03-10T04:40:19.903Z\",\"to\":\"2023-03-10T10:40:19.903Z\",\"raw\":{\"from\":\"now-6h\",\"to\":\"now\"}},\"from\":\"1678423219903\",\"to\":\"1678444819903\"}";
            try {
                URL url;
                url = new URL("http://10.1.3.85:3000/api/ds/query");
                con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Accept-Encoding", "gzip, deflate");
                con.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
                con.setRequestProperty("Connection", "keep-alive");
                con.setRequestProperty("Content-Length", "628");
                con.setRequestProperty("Cookie", data.getToken());
                con.setRequestProperty("Host", "10.1.3.85:3000");
                con.setRequestProperty("Origin", "http://10.1.3.85:3000");
                con.setRequestProperty("Referer", "http://10.1.3.85:3000/d/BV9D9zUnk/monitoring-airnode?orgId=1&refresh=5m");
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
                con.setRequestProperty("accept", "application/json, text/plain, */*");
                con.setRequestProperty("content-type", "application/json");
                con.setRequestProperty("x-grafana-org-id", "1");
                con.setRequestMethod("POST");
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(payload);
                wr.flush();
                int responseCode = con.getResponseCode();
                System.out.println("AllPak: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    listener.updateAllPAK(PakResponseAnalyser.analyzeAllPak(response.toString()));
                    in.close();
                } else {
                    System.out.println("request did not work.");
                }
                con.disconnect();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void updateToken() throws UpdateTokenException {
        long now = new Date().getTime();
        long then = now - 21600000;
        HttpURLConnection con;
        try {
            URL url;
            url = new URL("http://10.1.3.85:3000/api/annotations?from=" + then + "&to=" + now + "&limit=100&matchAny=false&dashboardId=6");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("accept", "application/json, text/plain, */*");
            con.setRequestProperty("Accept-Encoding", "gzip, deflate");
            con.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
            con.setRequestProperty("Connection", "keep-alive");
            con.setRequestProperty("Cookie", data.getToken());
            con.setRequestProperty("Host", "10.1.3.85:3000");
            con.setRequestProperty("Referer", "http://10.1.3.85:3000/d/BV9D9zUnk/monitoring-airnode?orgId=1&refresh=5m");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
            con.setRequestProperty("x-grafana-org-id", "1");
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("Update token: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String cookie = con.getHeaderField("Set-Cookie");
                if (cookie != null) {
                    String token = cookie.split(";")[0];
                    System.out.println("Token updated to " + token);
                    data.setToken(token);
                }
            } else {
                System.out.println(responseCode);
                throw new UpdateTokenException();
            }
            con.disconnect();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeData() {
        try (FileWriter writer = new FileWriter("GrafanaClientData.json")) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            String json = gson.toJson(data);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readData() {
        try (FileReader reader = new FileReader("GrafanaClientData.json")) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            data = gson.fromJson(reader, GrafanaClientData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setListener(GrafanaListener listener) {
        this.listener = listener;
    }
}
