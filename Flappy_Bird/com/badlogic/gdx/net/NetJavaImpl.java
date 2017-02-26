package com.badlogic.gdx.net;

import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import com.badlogic.gdx.utils.StringBuilder;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetJavaImpl {
    private final ExecutorService executorService;

    /* renamed from: com.badlogic.gdx.net.NetJavaImpl.1 */
    class C00581 implements Runnable {
        final /* synthetic */ HttpURLConnection val$connection;
        final /* synthetic */ boolean val$doingOutPut;
        final /* synthetic */ HttpRequest val$httpRequest;
        final /* synthetic */ HttpResponseListener val$httpResponseListener;

        C00581(boolean z, HttpRequest httpRequest, HttpURLConnection httpURLConnection, HttpResponseListener httpResponseListener) {
            this.val$doingOutPut = z;
            this.val$httpRequest = httpRequest;
            this.val$connection = httpURLConnection;
            this.val$httpResponseListener = httpResponseListener;
        }

        public void run() {
            try {
                if (this.val$doingOutPut) {
                    String contentAsString = this.val$httpRequest.getContent();
                    InputStream contentAsStream = this.val$httpRequest.getContentStream();
                    OutputStream outputStream = this.val$connection.getOutputStream();
                    if (contentAsString != null) {
                        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                        writer.write(contentAsString);
                        writer.flush();
                        writer.close();
                    } else if (contentAsStream != null) {
                        StreamUtils.copyStream(contentAsStream, outputStream);
                        outputStream.flush();
                        outputStream.close();
                    }
                }
                this.val$connection.connect();
                this.val$httpResponseListener.handleHttpResponse(new HttpClientResponse(this.val$connection));
            } catch (Exception e) {
                this.val$httpResponseListener.failed(e);
            } finally {
                this.val$connection.disconnect();
            }
        }
    }

    static class OptimizedByteArrayOutputStream extends ByteArrayOutputStream {
        OptimizedByteArrayOutputStream() {
        }

        OptimizedByteArrayOutputStream(int initialSize) {
            super(initialSize);
        }

        public synchronized byte[] toByteArray() {
            byte[] bArr;
            if (this.count == this.buf.length) {
                bArr = this.buf;
            } else {
                bArr = super.toByteArray();
            }
            return bArr;
        }
    }

    static class HttpClientResponse implements HttpResponse {
        private HttpURLConnection connection;
        private InputStream inputStream;
        private HttpStatus status;

        public HttpClientResponse(HttpURLConnection connection) throws IOException {
            this.connection = connection;
            try {
                this.inputStream = connection.getInputStream();
            } catch (IOException e) {
                this.inputStream = connection.getErrorStream();
            }
            try {
                this.status = new HttpStatus(connection.getResponseCode());
            } catch (IOException e2) {
                this.status = new HttpStatus(-1);
            }
        }

        public byte[] getResult() {
            try {
                ByteArrayOutputStream buffer;
                int contentLength = this.connection.getContentLength();
                if (contentLength > 0) {
                    buffer = new OptimizedByteArrayOutputStream(contentLength);
                } else {
                    buffer = new OptimizedByteArrayOutputStream();
                }
                StreamUtils.copyStream(this.inputStream, buffer);
                return buffer.toByteArray();
            } catch (IOException e) {
                return StreamUtils.EMPTY_BYTES;
            }
        }

        public String getResultAsString() {
            String stringBuilder;
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.inputStream));
            try {
                StringBuilder b;
                int approxStringLength = this.connection.getContentLength();
                if (approxStringLength > 0) {
                    b = new StringBuilder(approxStringLength);
                } else {
                    b = new StringBuilder();
                }
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    b.append(line);
                }
                stringBuilder = b.toString();
            } catch (IOException e) {
                stringBuilder = "";
            } finally {
                StreamUtils.closeQuietly(reader);
            }
            return stringBuilder;
        }

        public InputStream getResultAsStream() {
            return this.inputStream;
        }

        public HttpStatus getStatus() {
            return this.status;
        }

        public String getHeader(String name) {
            return this.connection.getHeaderField(name);
        }

        public Map<String, List<String>> getHeaders() {
            return this.connection.getHeaderFields();
        }
    }

    public NetJavaImpl() {
        this.executorService = Executors.newCachedThreadPool();
    }

    public void sendHttpRequest(HttpRequest httpRequest, HttpResponseListener httpResponseListener) {
        if (httpRequest.getUrl() == null) {
            httpResponseListener.failed(new GdxRuntimeException("can't process a HTTP request without URL set"));
            return;
        }
        try {
            URL url;
            String method = httpRequest.getMethod();
            if (method.equalsIgnoreCase(HttpMethods.GET)) {
                String queryString = "";
                String value = httpRequest.getContent();
                if (!(value == null || "".equals(value))) {
                    queryString = "?" + value;
                }
                url = new URL(httpRequest.getUrl() + queryString);
            } else {
                url = new URL(httpRequest.getUrl());
            }
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            boolean doingOutPut = method.equalsIgnoreCase(HttpMethods.POST) || method.equalsIgnoreCase(HttpMethods.PUT);
            connection.setDoOutput(doingOutPut);
            connection.setDoInput(true);
            connection.setRequestMethod(method);
            for (Entry<String, String> header : httpRequest.getHeaders().entrySet()) {
                connection.addRequestProperty((String) header.getKey(), (String) header.getValue());
            }
            connection.setConnectTimeout(httpRequest.getTimeOut());
            connection.setReadTimeout(httpRequest.getTimeOut());
            this.executorService.submit(new C00581(doingOutPut, httpRequest, connection, httpResponseListener));
        } catch (Exception e) {
            httpResponseListener.failed(e);
        }
    }
}
