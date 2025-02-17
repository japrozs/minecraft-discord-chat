package net.amitoj.minecraftDiscordChat.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util {

    public static void sendWH(JSONObject data, String url){
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(data.toJSONString().getBytes());
            }
            con.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendServerStartStopMessage(Config config, String event) {


            JSONObject postData = new JSONObject();
            postData.put("content", "");

            JSONArray embeds = new JSONArray();
            JSONObject embed = new JSONObject();

            if (event.equals("start")) {
                embed.put("title", "Server Started");
                embed.put("description", "The server has started");
                embed.put("color", 65280);
            } else {
                embed.put("title", "Server Shutting Down");
                embed.put("description", "The minecraft server is shutting down");
                embed.put("color", 16711680);
            }

        embeds.add(embed);

        postData.put("embeds", embeds);
        postData.put("username", config.serverName);
        postData.put("avatar_url", config.serverIcon);

        sendWH(postData,config.eventsWebhookUrl);
    }
}
