package edu.ufcg.es.es_front.httpClient;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

public class GsonReflectionRequest<T> extends Request<T> {
    private final Gson gson  = new Gson();
    private final Type clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;


    public GsonReflectionRequest(String url, Type clazz, Map<String, String> headers,
                                 Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }


    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers)
            );
            T parseObject = gson.fromJson(json, clazz);

            return Response.success(parseObject, HttpHeaderParser.parseCacheHeaders(response));
        }catch (UnsupportedEncodingException e){
            return Response.error(new ParseError(e));
        }catch (JsonSyntaxException e){
            return Response.error(new ParseError(e));
        }
    }
}
