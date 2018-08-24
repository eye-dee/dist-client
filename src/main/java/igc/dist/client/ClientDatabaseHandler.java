package igc.dist.client;

import igc.dist.proto.Connection.CreateConnection;
import igc.dist.proto.Connection.MessageAccepted;
import igc.dist.proto.Query.QueryRequest;
import igc.dist.proto.Query.QueryType;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ClientDatabaseHandler extends ChannelHandlerAdapter {

  private final String token;
  private final ClientGatewayHandler gatewayHandler;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);

    ctx.writeAndFlush(CreateConnection.newBuilder()
        .setToken(token)
        .build());
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    super.channelRead(ctx, msg);

    log.info("received message {} of class {}", msg, msg.getClass());

    if (msg instanceof MessageAccepted) {
      gatewayHandler.runQuery(QueryRequest.newBuilder()
          .setQuery("SELECT * FROM messages")
          .setQueryType(QueryType.READ)
          .build());
    }
  }
}
