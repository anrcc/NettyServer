package cc.anr.handlers;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import cc.anr.bean.TickBean;

public class TimeServerHandler extends ChannelHandlerAdapter {

	ChannelHandlerContext ctx;

	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		this.ctx = ctx;
		sendTime();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("=====channelInactive====");
		super.channelInactive(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("=====channelReadComplete====");
		sendTime();
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("=====channelUnregistered====");
		super.channelUnregistered(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("=====channelRegistered====");
		super.channelRegistered(ctx);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx)
			throws Exception {
		System.out.println("=====channelWritabilityChanged====");
		super.channelWritabilityChanged(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("=====channelRead====");

		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String message = new String(req, "UTF-8");

		System.out.println("Netty-Server:Receive Message," + message);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		// ctx.close();
	}

	public void sendTime() {
		if (ctx == null || !ctx.channel().isActive()) {
			return;
		}
		Channel channel = ctx.channel();
		TickBean tick = new TickBean(System.currentTimeMillis() + "", channel
				.id().asLongText(), channel.remoteAddress().toString());
		String jsonContent = JSON.toJSON(tick).toString();

		byte[] sdf = jsonContent.getBytes();
		ByteBuf contentBuf = ctx.alloc().buffer(sdf.length);
		contentBuf.writeBytes(sdf);
		ctx.writeAndFlush(contentBuf);
	}

	class Send implements Runnable {

		@Override
		public void run() {
			while (true) {
				sendTime();
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

}
