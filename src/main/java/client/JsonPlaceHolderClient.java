package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.UserDTO;
import domain.UserTask;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class JsonPlaceHolderClient {

    CloseableHttpClient httpClient = HttpClients.createDefault();
    CloseableHttpResponse closeableHttpResponse;
    ObjectMapper objectMapper = new ObjectMapper();
    List<UserDTO> userDTOList;

    public JsonPlaceHolderClient(){
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private URI generateURITaskForUser(String userId) throws URISyntaxException {
        return new URIBuilder("https://jsonplaceholder.typicode.com/todos")
                .addParameter("userId", userId)
                .build();
    }

    private URI generateURIGetUsers() throws URISyntaxException {
        return new URIBuilder("https://jsonplaceholder.typicode.com/users")
                .build();
    }

    public List<UserTask> getTaskForUser(String userId) {
        try {
            HttpGet requestGetTask = new HttpGet(generateURITaskForUser(userId));
            closeableHttpResponse = httpClient.execute(requestGetTask);
            HttpEntity httpEntity = closeableHttpResponse.getEntity();
            if (httpEntity != null) {
                String result = EntityUtils.toString(httpEntity);
                List<UserTask> userTaskList = objectMapper.readValue(result, new TypeReference<List<UserTask>>() {
                });
                return userTaskList;
            }
        } catch (ClientProtocolException e) {
            System.out.println(e.getMessage());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        } catch (URISyntaxException uriSyntaxException) {
            System.out.println(uriSyntaxException.getMessage());
        }
        return new ArrayList<>();
    }

    public List<UserDTO> getUsersWithTasks() throws IOException {
        try {
            System.out.println("Try to connect...");
            HttpGet requestGet = new HttpGet(generateURIGetUsers() );
            closeableHttpResponse = httpClient.execute(requestGet);
            try {
                HttpEntity entity = closeableHttpResponse.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    userDTOList = objectMapper.readValue(result, new TypeReference<List<UserDTO>>(){});
                    userDTOList.stream()
                            .forEach(s -> s.setTask(getTaskForUser(Long.toString(s.getId()))));
                    return userDTOList;
                }
            } finally {
                closeableHttpResponse.close();
            }
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        } finally {
            closeableHttpResponse.close();
        }
        return new ArrayList<>();
    }
}
