/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package cc.anr.netty5eg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles a client-side channel.
 */
public class DiscardClientHandler extends SimpleChannelInboundHandler<Object> {

    /**
     * 在没有encode , decoder的时候,netty默认的数据传输格式是ByteBuf,其他格式都不合法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	
    }
    
    public void channelInactive(ChannelHandlerContext ctx)throws Exception{
    	System.out.println("---- Channel Inactive ----");
    	ctx.channel().close();
    }
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
    	System.out.println("--- client messageReceived ---");
    	Message message = (Message) msg;
    	System.out.println("message is " + message.getHeader());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
