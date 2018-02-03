package cc.anr;

import org.apache.log4j.Logger;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/** 
 * Sharable表示此对象在channel间共享 
 * handler类是我们的具体业务类 
 * */  
@Sharable//注解@Sharable可以让它在channels间共享  
public class MyServerInboundHandler extends ChannelInboundHandlerAdapter{  
	
	public static Logger log = Logger.getLogger(MyServerInboundHandler.class);
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelRegistered(ctx);
		log.info("MyServerInboundHandler====channelRegistered");

	}
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		log.info("MyServerInboundHandler====channelActive");

	}
	
    public void channelRead(ChannelHandlerContext ctx, Object msg) {   
        System.out.println("server received data :" + msg);   
        ctx.write(msg);//写回数据，  
		log.info("MyServerInboundHandler====channelRead");

    }   
    public void channelReadComplete(ChannelHandlerContext ctx) {   
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER); //flush掉所有写回的数据  
      //  .addListener(ChannelFutureListener.CLOSE); //当flush完成后关闭channel  
        
		log.info("MyServerInboundHandler====channelReadComplete");

    }   
    
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {   
        cause.printStackTrace();//捕捉异常信息  
        ctx.close();//出现异常时关闭channel   
		log.info("MyServerInboundHandler====exceptionCaught");

    }

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// TODO Auto-generated method stub
		super.userEventTriggered(ctx, evt);
		log.info("MyServerInboundHandler====userEventTriggered");

	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
		log.info("MyServerInboundHandler====channelInactive");

	}
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelUnregistered(ctx);
		log.info("MyServerInboundHandler====channelUnregistered");

	}



	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelWritabilityChanged(ctx);
		log.info("MyServerInboundHandler====channelWritabilityChanged");

		
	}     
    
    
    
    
    
}  