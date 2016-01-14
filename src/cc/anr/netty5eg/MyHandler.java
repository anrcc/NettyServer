package cc.anr.netty5eg;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
 public class MyHandler extends ChannelHandlerAdapter {
     @Override
     public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
         if (evt instanceof IdleStateEvent) {
             IdleStateEvent e = (IdleStateEvent) evt;
             if (e.state() == IdleState.READER_IDLE) {
            	 System.out.println("--- Reader Idle ---");
                 ctx.close();
             } else if (e.state() == IdleState.WRITER_IDLE) {
            	 System.out.println("--- Write Idle ---");
            	 ctx.close();
             }
         }
     }
 }