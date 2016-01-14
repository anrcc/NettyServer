package cc.anr.netty5eg;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

public class MyChannelInitializer extends ChannelInitializer<Channel> {
     @Override
     public void initChannel(Channel channel) {
         channel.pipeline().addLast(new IdleStateHandler(10, 10, 0));
         channel.pipeline().addLast(new MyHandler());
         channel.pipeline().addLast(new Encoder());
         channel.pipeline().addLast(new Decoder());
         channel.pipeline().addLast(new DiscardServerHandler());
         
     }
 }