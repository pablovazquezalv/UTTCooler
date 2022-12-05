package app.singleton;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Singleton {
    private static Singleton instancia;
    private RequestQueue requestQueue;
    private  static Context ctx;

    private Singleton(Context context)
    {
        ctx=context;
        requestQueue = getRequestQueue();
    }

    public static synchronized  Singleton getInstance(Context context)
    {
        if(instancia==null)
        {
            instancia=new Singleton(context);
        }
        return instancia;
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return  requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}
