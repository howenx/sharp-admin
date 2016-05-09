package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import play.Logger;
import play.mvc.WebSocket;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于produce消息到阿里云mns的Actor
 * Created by howen on 15/12/24.
 */
public class SubscribeActor extends AbstractActor {

    public SubscribeActor(Jedis jedis, WebSocket.In<String> in, WebSocket.Out<String> out) {
        receive(ReceiveBuilder.match(String.class, channel -> {
            try {

                class KVStoreMessageListener extends JedisPubSub {

                    private WebSocket.Out<String> out;

                    private KVStoreMessageListener(WebSocket.Out<String> out){
                        this.out = out;
                    }

                    @Override
                    public void onMessage(String channel, String message) {

                        System.out.println("  <<< 订阅(SUBSCRIBE)< Channel:" + channel + " >接收到的Message:" + message);
                        System.out.println();
                        out.write(message);
                        if (message.equalsIgnoreCase("quit")) {
                            this.unsubscribe(channel);
                        }
                    }
                }

                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(() -> {
                    KVStoreMessageListener listener = new KVStoreMessageListener(out);
                    if (channel == null) {
                        System.err.println("Error:SubClient> listener or channel is null");
                    }

                    System.out.println("  >>> 订阅(SUBSCRIBE) > Channel:" + channel);
                    System.out.println();
                    jedis.subscribe(listener, channel);
                });

            } catch (Exception ignored) {
            }
        }).matchAny(s -> {
            Logger.error("MnsActor received messages not matched: {}", s.toString());
            unhandled(s);
        }).build());
    }
}