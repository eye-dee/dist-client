package igc.dist.client;

import igc.dist.proto.Connection.CreateConnection;
import igc.dist.proto.Connection.CreateConnectionRequest;
import igc.dist.proto.Connection.CreateConnectionResponse;
import igc.dist.proto.Query.QueryRequest;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientGatewayHandler extends ChannelHandlerAdapter {

  private ChannelHandlerContext context;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);

    ctx.writeAndFlush(CreateConnectionRequest.newBuilder().build());
    this.context = ctx;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    super.channelRead(ctx, msg);
    log.info("new message with class {}", msg.getClass());

    if (msg instanceof CreateConnectionResponse) {
      var connectionResponse = (CreateConnectionResponse) msg;

      EasyClient.connectToDatabase(
          connectionResponse.getHost(), connectionResponse.getPort(),
          connectionResponse.getToken());
    }

    this.context = ctx;
  }

  public void runQuery(QueryRequest request) {
    log.info("run query {}", request.getQuery());
    context.writeAndFlush(request);
  }
}
