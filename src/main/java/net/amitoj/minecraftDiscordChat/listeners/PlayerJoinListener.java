package net.amitoj.minecraftDiscordChat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class PlayerJoinListener implements Listener {
    private boolean _enabled = true;
    private String _webhookUrl;
    private String _serverName;
    private String _serverIcon;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (_enabled) {
            HttpURLConnection con = null;
            try {
                JSONObject postData = new JSONObject();
                postData.put("content", "");


                JSONArray embeds = new JSONArray();
                JSONObject embed = new JSONObject();
                JSONObject thumbnail = new JSONObject();
                thumbnail.put("url", "https://mc-heads.net/avatar/" + event.getPlayer().getPlayerProfile().getName());

                embed.put("title", "Player Joined");
                embed.put("description", event.getPlayer().getPlayerProfile().getName() + " has joined the server!");
                embed.put("color", 65280);
                embed.put("thumbnail", thumbnail);
                embeds.add(embed);

                postData.put("embeds", embeds);
                postData.put("username", _serverName);
                postData.put("avatar_url", _serverIcon);

                con = (HttpURLConnection) new URL(_webhookUrl).openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");

                try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                    wr.write(postData.toJSONString().getBytes());
                }
                con.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void set_enabled(boolean _enabled) {
        this._enabled = _enabled;
    }

    public void set_webhookUrl(String _webhookUrl) {
        this._webhookUrl = _webhookUrl;
    }

    public void set_serverName(String _serverName) {
        this._serverName = _serverName;
    }

    public void set_serverIcon(String _serverIcon) {
        this._serverIcon = _serverIcon;
    }
}

// https://cdn.amitoj.net/mc.amitoj.net/server-icon.png