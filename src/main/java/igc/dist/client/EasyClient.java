package igc.dist.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EasyClient {

  private static final EventLoopGroup workerGroup = new NioEventLoopGroup(4);
  private static final ClientGatewayHandler GATEWAY_HANDLER = new ClientGatewayHandler();

  public static void main(String[] args) throws InterruptedException {
    connectToGateway();

    Thread.sleep(100000);
  }

  private static void connectToGateway() throws InterruptedException {
    var connectFuture = new Bootstrap().group(workerGroup)
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, true)
        .handler(new ClientInitializer(GATEWAY_HANDLER))
        .connect("localhost", 6666);

    connectFuture.sync();
  }

  public static void connectToDatabase(
      final String host, final int port, final String token)
      throws InterruptedException {
    var connectFuture = new Bootstrap().group(workerGroup)
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, true)
        .handler(new ClientInitializer(new ClientDatabaseHandler(token, GATEWAY_HANDLER)))
        .connect(host, port);

    connectFuture.sync();
  }

}
