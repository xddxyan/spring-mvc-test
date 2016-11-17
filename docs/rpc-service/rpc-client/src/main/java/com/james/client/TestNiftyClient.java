package com.james.client;


import static com.james.core.ScopedNiftyServer.defaultServerDefBuilder;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.junit.Test;

import com.facebook.nifty.client.FramedClientConnector;
import com.facebook.nifty.client.NiftyClient;
import com.google.common.base.Throwables;
import com.james.core.LogEntry;
import com.james.core.MyService;
import com.james.core.ResultCode;
import com.james.core.ScopedNiftyServer;

import io.airlift.log.Logger;
import junit.framework.Assert;

public class TestNiftyClient
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
    private static final Logger log = Logger.get(TestNiftyClient.class);

    @Test
    public void testServerDisconnect()
            throws Exception
    {
        try (ScopedNiftyServer server = makeServer()) {
            final NiftyClient niftyClient = new NiftyClient();
            MyService.Client client = makeNiftyClient(niftyClient, server);
            new Thread()
            {
                @Override
                public void run()
                {
                    try {
                        sleep(1000L);
                        server.close();
                    }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        Throwables.propagate(e);
                    }
                }
            }.start();

            int max = (int) (Math.random() * 100) + 10;
            int exceptionCount = 0;
            for (int i = 0; i < max; i++) {
                Thread.sleep(100L);
                try {
                    client.Log(Arrays.asList(new LogEntry("hello", "world " + i)));
                }
                catch (TException e) {
                    log.info("caught expected exception: %s", e);
                    exceptionCount++;
                }
                catch (Throwable t) {
                    log.warn(t, "caught unexpected exception");
                }
            }
            Assert.assertTrue(exceptionCount > 0);

            niftyClient.close();
        }
    }

    private ScopedNiftyServer makeServer()
    {
    	MyService.Processor processor = new MyService.Processor<>(new MyService.Iface()
        {
            @Override
            public ResultCode Log(List<LogEntry> messages) throws TException
            {
                for (LogEntry message : messages) {
                    log.info("%s: %s", message.getCategory(), message.getMessage());
                }
                return ResultCode.OK;
            }
        });

        return new ScopedNiftyServer(defaultServerDefBuilder(processor));
    }

    private MyService.Client makeNiftyClient(final NiftyClient niftyClient, ScopedNiftyServer server)
            throws TTransportException, InterruptedException
    {
        InetSocketAddress address = new InetSocketAddress("localhost", server.getPort());
        FramedClientConnector framedClientConnector = new FramedClientConnector(address);
        TTransport transport = niftyClient.connectSync(MyService.Client.class, framedClientConnector);
        //TTransport transport = niftyClient.connectSync(address);
        TBinaryProtocol tp = new TBinaryProtocol(transport);
        return new MyService.Client(tp);
    }
}