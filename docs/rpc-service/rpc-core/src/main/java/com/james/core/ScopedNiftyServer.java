package com.james.core;

import com.facebook.nifty.core.NettyServerTransport;
import com.facebook.nifty.core.ThriftServerDef;
import com.facebook.nifty.core.ThriftServerDefBuilder;
import org.apache.thrift.TProcessor;

import java.net.InetSocketAddress;

public class ScopedNiftyServer implements AutoCloseable {
    private final NettyServerTransport server;

    public ScopedNiftyServer(ThriftServerDefBuilder defBuilder) {
        server = new NettyServerTransport(defBuilder.build());
        server.start();
    }

    public int getPort() {
        InetSocketAddress localAddress = (InetSocketAddress)server.getServerChannel()
                                                                  .getLocalAddress();
        return localAddress.getPort();
    }

    @Override
    public void close() throws Exception {
        server.stop();
    }

    public static ThriftServerDefBuilder defaultServerDefBuilder(TProcessor processor) {
        return ThriftServerDef.newBuilder()
                              .listen(0)
                              .withProcessor(processor);
    }
}