package cc.anr.handlers;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import cc.anr.bean.TickBean;

public class TimeServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {

		Channel channel = ctx.channel();
		while (true) {

			TickBean tick = new TickBean(System.currentTimeMillis() + "",
					channel.id().asLongText(), channel.remoteAddress().toString());
			String jsonContent = JSON.toJSON(tick).toString();

			byte[] sdf = jsonContent.getBytes();
			ByteBuf contentBuf = ctx.alloc().buffer(sdf.length);
			contentBuf.writeBytes(sdf);
			ctx.writeAndFlush(contentBuf);
			Thread.sleep(10000);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
