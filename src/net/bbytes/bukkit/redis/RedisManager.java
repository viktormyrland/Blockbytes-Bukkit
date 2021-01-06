package net.bbytes.bukkit.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import net.bbytes.bukkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class RedisManager {
	
	private String host;
	private String password;
	private int port;
	
	private StatefulRedisConnection<String, String> connection;
	private StatefulRedisPubSubConnection<String, String> pubSubConnection;
	private RedisPubSubListener<String, String> listener;
	private RedisCommands<String, String> redis;
	private RedisPubSubCommands<String, String> redisPubSub;
	private ArrayList<RedisMessageReceiver> redisMessageHandlers;
	
	public RedisManager() {
		
		redisMessageHandlers = new ArrayList<RedisMessageReceiver>();
		
	}
	
	public void connectToRedis() {
		
		FileConfiguration cfg = Main.getInstance().getConfig();
		boolean canConnect = true;
		
		try {

			
			host = cfg.getString("Redis.Host");
			port = cfg.getInt("Redis.Port");
			password = cfg.getString("Redis.Password");
		
		} catch(NullPointerException ex) {
			
			canConnect = false;
			ex.printStackTrace();
			
		} 
		
		if(canConnect) {
			
			RedisClient client = RedisClient.create("redis://" + password + "@" + host + ":" + port);
			connection = client.connect();
			
			if(connection.isOpen()) {
				
				redis = connection.sync();
				
				pubSubConnection = client.connectPubSub();
				redisPubSub = pubSubConnection.sync();
				
				setupPubSub();
				
			}
			
		}
		
	}
	
	public void disconnectFromRedis() {
		
		if(connection != null && connection.isOpen()) {
			
			connection.close();
			
		}
		
		if(pubSubConnection != null && pubSubConnection.isOpen()) {
			
			pubSubConnection.close();
			
		}
		
	}
	
	private void setupPubSub() {
		
		listener = new RedisPubSubAdapter<String, String>() {
			
			@Override
			public void message(String channel, String message) {
				
				Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						for(RedisMessageReceiver receiver : redisMessageHandlers) {
							
							receiver.onRedisMessageReceived(channel, message);
							
						}
						
					}
					
				});
				
			}
			
		};
		
		pubSubConnection.addListener(listener);
		
	}
	
	public void registerMessageReceiver(RedisMessageReceiver messageReceiver) {
		
		if(!redisMessageHandlers.contains(messageReceiver)) redisMessageHandlers.add(messageReceiver);
		
	}
	
	public void unregisterMessageReceiver(RedisMessageReceiver messageReceiver) {
		
		if(redisMessageHandlers.contains(messageReceiver)) redisMessageHandlers.remove(messageReceiver);
		
	}
	
	public void subscribeToChannel(String... channels) {
		
		redisPubSub.subscribe(channels);
		
		String channel = "";
		for(String c : channels) channel += ", " + c;
		channel = channel.replaceFirst(", ", "");

		
	}
	
	public void publishMessage(String channel, String message) {
		
		redis.publish(channel, message);
		System.out.println("published");

		
	}
	
	public RedisCommands<String, String> getRedis() {
		return redis;
	}
	
}
