package de.uwuwhatsthis.YeetsDiscordLibrary.httpEndpoint.genericRequest;

import de.uwuwhatsthis.YeetsDiscordLibrary.httpEndpoint.requestCallback.RequestCallback;
import de.uwuwhatsthis.YeetsDiscordLibrary.httpEndpoint.requestType.RequestType;
import de.uwuwhatsthis.YeetsDiscordLibrary.state.StateManager;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Constants;
import de.uwuwhatsthis.YeetsDiscordLibrary.utils.Debugger;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericHttpRequest {
    protected String route;
    protected JSONObject body, headers;
    protected RequestType requestType;
    protected RequestCallback callback;

    private final static Debugger debugger = new Debugger("GenericHttpRequest");

    public GenericHttpRequest(String route, JSONObject body, RequestType requestType, RequestCallback callback) {
        this.route = route;
        this.body = body;
        this.requestType = requestType;
        this.callback = callback;
    }

    public void doRequest(){
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = null;
        HttpEntity entity = null;
        String fullRoute = Constants.HTTP_ENDPOINT_BASE + route;

        switch (requestType){
            case POST:
                HttpPost post = new HttpPost(fullRoute);

                debugger.debug("fullRoute = " + fullRoute);

                post.addHeader("Authorization", "Bot " + StateManager.getInstance().getToken());

                StringEntity requestEntity = new StringEntity(
                        body.toString(),
                        ContentType.APPLICATION_JSON
                );

                debugger.debug(body.toString(2));

                post.setEntity(requestEntity);

                try {
                    response = client.execute(post);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                entity = response.getEntity();
                break;

            case GET:
                break;

            case UPDATE:
                break;
        }

        if (entity != null){
            try(InputStream stream = entity.getContent()){
                onResult(IOUtils.toString(stream, StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onResult(String result){
        if (callback != null){
            callback.execute(result);
        }
    }
}
