package cc.anr;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import cc.anr.handlers.TimeServerHandler;

public class NettyServer {

	int inetPort;
	
	NettyServer (int inetPort){
		this.inetPort=inetPort;
	}
	
	
	public void run() throws Exception{
		
		EventLoopGroup bossGroup=new NioEventLoopGroup();//parentGroup
		EventLoopGroup workerGroup=new NioEventLoopGroup();//childGroup
		
		
		try {
			
			ServerBootstrap b=new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {

				/* (non-Javadoc)
				 * @see io.netty.channel.ChannelInitializer#initChannel(io.netty.channel.Channel)
				 */
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					
					ch.pipeline().addLast(new TimeServerHandler());
				}

			
			})
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true);
			
			
		 	ChannelFuture cf= b.bind(inetPort).sync();
		 	
		 	cf.channel().closeFuture().sync();
			
			
		} catch (Exception e) {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
		
	}
	
	
	public static void main(String[] args) {
		try {
			new NettyServer(8080).run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
