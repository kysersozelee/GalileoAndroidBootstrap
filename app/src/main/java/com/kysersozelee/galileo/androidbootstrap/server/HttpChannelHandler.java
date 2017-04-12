package com.kysersozelee.galileo.androidbootstrap.server;

import com.kysersozelee.galileo.androidbootstrap.server.route.Route;
import com.kysersozelee.galileo.androidbootstrap.server.route.RouteTable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by kysersoze.lee on 2017-04-11.
 */

public class HttpChannelHandler extends ChannelInboundHandlerAdapter {
    public static final String TYPE_PLAIN = "text/plain; charset=UTF-8";
    public static final String TYPE_JSON = "application/json; charset=UTF-8";


    private final String TAG = "HttpChannelHandler";
    private RouteTable routeTable;

/*
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int)(System.currentTimeMillis() / 1000L + 2208988800L));

        final ChannelFuture future = ctx.writeAndFlush(time);

        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
            }
        });

    }*/

    public HttpChannelHandler(final RouteTable routeTable) {
        this.routeTable = routeTable;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof FullHttpRequest)) {
            return;
        }

        final FullHttpRequest request = (FullHttpRequest) msg;
        final FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);

        final HttpMethod method = request.method();
        final String uri = request.uri();

        //do works
        final Route route = this.routeTable.findRoute(method, uri);

        if (route == null) {
            writeNotFound(ctx, request);
            return;
        }

        try {
            final HttpRequest requestWrapper = new HttpRequest(request);
            final Object obj = route.getHandler().handle(requestWrapper, null);
            final String content = obj == null ? "" : obj.toString();
            writeResponse(ctx, request, HttpResponseStatus.OK, TYPE_PLAIN, content);
        } catch (final Exception ex) {
            ex.printStackTrace();
            writeInternalServerError(ctx, request);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }




    /**
     * Writes a 404 Not Found response.
     *
     * @param ctx The channel context.
     * @param request The HTTP request.
     */
    private static void writeNotFound(
            final ChannelHandlerContext ctx,
            final FullHttpRequest request) {

        writeErrorResponse(ctx, request, HttpResponseStatus.NOT_FOUND);
    }


    /**
     * Writes a 500 Internal Server Error response.
     *
     * @param ctx The channel context.
     * @param request The HTTP request.
     */
    private static void writeInternalServerError(
            final ChannelHandlerContext ctx,
            final FullHttpRequest request) {

        writeErrorResponse(ctx, request, HttpResponseStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Writes a HTTP error response.
     *
     * @param ctx The channel context.
     * @param request The HTTP request.
     * @param status The error status.
     */
    private static void writeErrorResponse(
            final ChannelHandlerContext ctx,
            final FullHttpRequest request,
            final HttpResponseStatus status) {

        writeResponse(ctx, request, status, TYPE_PLAIN, status.reasonPhrase().toString());
    }


    /**
     * Writes a HTTP response.
     *
     * @param ctx The channel context.
     * @param request The HTTP request.
     * @param status The HTTP status code.
     * @param contentType The response content type.
     * @param content The response content.
     */
    private static void writeResponse(
            final ChannelHandlerContext ctx,
            final FullHttpRequest request,
            final HttpResponseStatus status,
            final CharSequence contentType,
            final String content) {

        final byte[] bytes = content.getBytes(Charset.forName("UTF-8"));
        final ByteBuf entity = Unpooled.wrappedBuffer(bytes);
        writeResponse(ctx, request, status, entity, contentType, bytes.length);
    }


    /**
     * Writes a HTTP response.
     *
     * @param ctx The channel context.
     * @param request The HTTP request.
     * @param status The HTTP status code.
     * @param buf The response content buffer.
     * @param contentType The response content type.
     * @param contentLength The response content length;
     */
    private static void writeResponse(
            final ChannelHandlerContext ctx,
            final FullHttpRequest request,
            final HttpResponseStatus status,
            final ByteBuf buf,
            final CharSequence contentType,
            final int contentLength) {

        // Decide whether to close the connection or not.
        final boolean keepAlive = HttpServer.KEEP_ALIVE;

        // Build the response object.
        final FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                status,
                buf,
                false);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.KOREA);
        Date date = new Date((System.currentTimeMillis() / 1000L + 2208988800L));
        String currentDateTime = sdf.format(new Date());

        final DefaultHttpHeaders headers = (DefaultHttpHeaders) response.headers();
        headers.set(HttpHeaderNames.SERVER, HttpServer.SERVER_NAME);
        headers.set(HttpHeaderNames.DATE, currentDateTime);
        headers.set(HttpHeaderNames.CONTENT_TYPE, contentType);
        headers.set(HttpHeaderNames.CONTENT_LENGTH, Integer.toString(contentLength));

        // Close the non-keep-alive connection after the write operation is done.
        if (!keepAlive) {
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            ctx.writeAndFlush(response, ctx.voidPromise());
        }
    }


    /**
     * Writes a 100 Continue response.
     *
     * @param ctx The HTTP handler context.
     */
    private static void send100Continue(final ChannelHandlerContext ctx) {
        ctx.write(new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.CONTINUE));
    }
}
