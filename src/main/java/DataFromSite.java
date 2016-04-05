import org.json.simple.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class DataFromSite {

    private static final String SITE = "http://randus.ru/api.php";
    private static final String CHARSET = "UTF-8";

    public static JSONObject getDataFromSite(){
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(SITE);

            CloseableHttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = entity.getContent();
                StringWriter writer = new StringWriter();
                IOUtils.copy(inputStream, writer, CHARSET);
                try {
                    return (JSONObject) (new JSONParser().parse(writer.toString()));
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null, e, "JSON Parse Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e, "IO Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }


}
