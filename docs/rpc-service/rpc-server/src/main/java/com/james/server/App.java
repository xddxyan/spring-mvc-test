package com.james.server;

import com.facebook.nifty.core.NettyServerConfig;
import com.facebook.nifty.core.NettyServerTransport;
import com.facebook.nifty.core.RequestContext;
import com.facebook.nifty.core.RequestContexts;
import com.facebook.nifty.core.ThriftServerDefBuilder;
import com.james.core.LogEntry;
import com.james.core.MyService;
import com.james.core.ResultCode;
import io.airlift.log.Logger;
import org.apache.thrift.TException;
import org.jboss.netty.channel.group.DefaultChannelGroup;


import java.net.InetSocketAddress;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger log = Logger.get(MyService.class);
    private NettyServerTransport server;
    private int port;
    
    public static void main( String[] args )
    {
    	new App(){{
    		startServer();
    	}};
    }

    protected void startServer()
    {
        startServer(getThriftServerDefBuilder());
    }

    private void startServer(final ThriftServerDefBuilder thriftServerDefBuilder)
    {
        server = new NettyServerTransport(thriftServerDefBuilder.build(),
                                          NettyServerConfig.newBuilder().build(),
                                          new DefaultChannelGroup());
        server.start();
        port = ((InetSocketAddress)server.getServerChannel().getLocalAddress()).getPort();
    }

    private ThriftServerDefBuilder getThriftServerDefBuilder()
    {
        return new ThriftServerDefBuilder()
                .listen(0)
                .withProcessor(new MyService.Processor<>(new MyService.Iface() {
                    @Override
                    public ResultCode Log(List<LogEntry> messages)
                            throws TException
                    {
                        RequestContext context = RequestContexts.getCurrentContext();

                        for (LogEntry message : messages) {
                            log.info("[Client: %s] %s: %s",
                                    context.getConnectionContext().getRemoteAddress(),
                                    message.getCategory(),
                                    message.getMessage());
                        }
                        return ResultCode.OK;
                    }
                }));
    }

}
