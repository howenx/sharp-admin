package util;

/**
 * 监听器
 * Created by hao on 16/5/9.
 */

import play.mvc.WebSocket;
import redis.clients.jedis.JedisPubSub;

public class RedisListener extends JedisPubSub {
    private WebSocket.Out<String> out;

    public RedisListener(WebSocket.Out<String> out) {
        this.out = out;
    }


    @Override
    public void onMessage(String channel, String message) {

        System.out.println("  <<< 订阅(SUBSCRIBE)< Channel:" + channel + " >接收到的Message:" + message);
        out.write(message);
        if (message.equalsIgnoreCase("quit")) {
            this.unsubscribe(channel);
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println("  <<< P订阅(SUBSCRIBE)< pattern: " + pattern + " Channel:" + channel + " >接收到的Message:" + message);
        out.write(message);
        if (message.equalsIgnoreCase("quit")) {
            this.punsubscribe(pattern);
        }
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("  <<< 正在订阅(onSubscribe)< Channel:" + channel + " >subscribedChannels:" + subscribedChannels);

    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("  <<< 正在取消订阅(onUnsubscribe)< Channel:" + channel + " >subscribedChannels:" + subscribedChannels);

    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.println("  <<< 正在P取消订阅(onPUnsubscribe)< Channel:" + pattern + " >subscribedChannels:" + subscribedChannels);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("  <<< 正在P订阅(onPSubscribe)< Channel:" + pattern + " >subscribedChannels:" + subscribedChannels);

    }
}