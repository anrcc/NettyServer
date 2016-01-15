package cc.anr.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cc.anr.bean.TickBean;

import com.alibaba.fastjson.JSON;

public class TimeServerHandler extends ChannelHandlerAdapter {

	List<ChannelHandlerContext> channelList = new ArrayList<ChannelHandlerContext>();

	int count =0;
	public TimeServerHandler() {
		
		
		//定时给所有客户端发消息 10 秒
		Timer timer = new Timer();
		long delay = 0;
		long intevalPeriod = 10 * 1000;
		// schedules the task to be run in an interval
		timer.scheduleAtFixedRate(task, delay, intevalPeriod);
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		if (!channelList.contains(ctx)) {
			channelList.add(ctx);
		}
	}

	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String message = new String(req, "UTF-8");

		System.out.println("Netty-Server:Receive Message," + message);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		// ctx.close();
	}

	public void sendTime(ChannelHandlerContext ctx) {
		if (ctx == null || !ctx.channel().isActive()) {
			return;
		}
		Channel channel = ctx.channel();
		TickBean tick = new TickBean(count,System.currentTimeMillis() + "", channel.id().asShortText(), channel.remoteAddress().toString());
		String jsonContent = JSON.toJSON(tick).toString();

		byte[] sdf = jsonContent.getBytes();
		ByteBuf contentBuf = ctx.alloc().buffer(sdf.length);
		contentBuf.writeBytes(sdf);
		ctx.writeAndFlush(contentBuf);
	}

	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			count++;
			for (ChannelHandlerContext ctx : channelList) {
				sendTime(ctx);
			}
		}
	};

}
