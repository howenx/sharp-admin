package util;

/**
 * 并发监听器
 * Created by hao on 16/5/9.
 */

import redis.clients.jedis.JedisPubSub;

public class ConcurrentRedisListener extends JedisPubSub {


    @Override
    public void onMessage(String channel, String message) {
        System.out.println("订阅频道:" + channel + "  > 订阅消息:" + message);
        if (SysParCom.WEBSOCKET_OUT_LIST.size() > 0) {
            System.out.println("并发遍历>>订阅频道:" + channel + "  > 订阅消息:" + message);
            SysParCom.WEBSOCKET_OUT_LIST.iterator().forEachRemaining(out -> {
                try {
                    if (out != null) {
                        out.write(message);
                    }
                    if (message.equalsIgnoreCase("quit")) {
                        this.unsubscribe(channel);
                    }
                } catch (Exception e) {
                    this.unsubscribe(channel);
                }
            });
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        if (SysParCom.WEBSOCKET_OUT_LIST.size() > 0) {

            SysParCom.WEBSOCKET_OUT_LIST.iterator().forEachRemaining(out -> {
                try {
                    if (out != null) {
                        out.write(message);
                    }
                    if (message.equalsIgnoreCase("quit")) {
                        this.punsubscribe(pattern);
                    }
                } catch (Exception e) {
                    this.punsubscribe(pattern);
                }
            });
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