package com.kysersozelee.galileo.androidbootstrap.server;

import com.kysersozelee.galileo.androidbootstrap.server.route.Route;
import com.kysersozelee.galileo.androidbootstrap.server.route.RouteTable;

import java.util.concurrent.atomic.AtomicBoolean;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Created by kysersoze.lee on 2017-04-11.
 */


public class HttpServer {
    public static final String SERVER_NAME = "GalileoAndroidBootstrap";
    public static final boolean KEEP_ALIVE = true;
    public static final boolean TCP_NO_DELAY = true;
    public static final int LINGER = 0;

    private final RouteTable routeTable = new RouteTable();

    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private int port;
    private Thread serverThread;

    public HttpServer(int port) {
        this.port = port;
    }

    public void startServer(boolean wait) {
        if (serverThread != null) {
            throw new IllegalStateException("Server is already running");
        }
        serverThread = new Thread() {
            @Override
            public void run() {
                EventLoopGroup bossGroup = new NioEventLoopGroup();
                EventLoopGroup workerGroup = new NioEventLoopGroup();
                try {
                    ServerBootstrap bootstrap = new ServerBootstrap();
                    bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
                    bootstrap.group(bossGroup, workerGroup)
                            .channel(NioServerSocketChannel.class)
                            .option(ChannelOption.SO_KEEPALIVE, KEEP_ALIVE)
                            .option(ChannelOption.SO_LINGER, LINGER)
                            .option(ChannelOption.TCP_NODELAY, TCP_NO_DELAY)
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ChannelPipeline p = ch.pipeline();
                                    p.addLast("decoder", new HttpRequestDecoder(4096, 8192, 8192, false));
                                    p.addLast("aggregator", new HttpObjectAggregator(100 * 1024 * 1024));
                                    p.addLast("encoder", new HttpResponseEncoder());
                                    p.addLast("handler", new HttpChannelHandler(routeTable));
                                }
                            });
                    Channel ch = bootstrap.bind(port).sync().channel();
                    ch.closeFuture().sync();
                } catch (InterruptedException ignored) {
                } finally {
                    bossGroup.shutdownGracefully();
                    workerGroup.shutdownGracefully();
                }
            }
        };
        serverThread.start();
        if(wait){
            try {
                serverThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopServer() {
        if (serverThread == null || !isRunning.get()) {
            throw new IllegalStateException("Server is not running");
        }
        serverThread.interrupt();
    }

    public int getPort() {
        return port;
    }

    public AtomicBoolean getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(AtomicBoolean isRunning) {
        this.isRunning = isRunning;
    }

    public RouteTable getRouteTable() {
        return routeTable;
    }


    /**
     * Adds a GET route.
     *
     * @param path The URL path.
     * @param handler The request handler.
     * @return This WebServer.
     */
    public HttpServer get(final String path, final IHandler handler) {
        this.routeTable.addRoute(new Route(HttpMethod.GET, path, handler));
        return this;
    }


    /**
     * Adds a POST route.
     *
     * @param path The URL path.
     * @param handler The request handler.
     * @return This WebServer.
     */
    public HttpServer post(final String path, final IHandler handler) {
        this.routeTable.addRoute(new Route(HttpMethod.POST, path, handler));
        return this;
    }

}