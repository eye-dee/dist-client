package igc.dist.client;

import igc.dist.proto.Connection.CreateConnection;
import igc.dist.proto.Connection.CreateConnectionRequest;
import igc.dist.proto.Connection.CreateConnectionResponse;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientHandler extends ChannelHandlerAdapter {

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);

    ctx.writeAndFlush(CreateConnectionRequest.newBuilder().build());
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    super.channelRead(ctx, msg);
    log.info("new message with class {}", msg.getClass());

    if (msg instanceof CreateConnectionResponse) {
      var connectionResponse = (CreateConnectionResponse) msg;

      log.info("connection host = {}", connectionResponse.getHost());
    }

  }
}
