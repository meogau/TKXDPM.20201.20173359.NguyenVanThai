package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;

/**
 * class provider method call api request send data to server and receive data return
 * Date: 28/11/2020
 * @author Truong Huu Tien
 * @version 1.0
 */
public class API {

    /**
     * format date yyyy/MM/dd HH:mm:ss
     */
    public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    /**
     * Attributes help log information in console
     */
    private static Logger LOGGER = Utils.getLogger(Utils.class.getName());

    /**
     * Method call api GET
     * @param url : path to server need request
     * @param token : text code for Authorization in server
     * @return response : data is received from server
     * @throws Exception
     */
    public static String get(String url, String token) throws Exception {
        // step 1: setup
        HttpURLConnection conn = setUpConnection(url,"GET", token);

        // step 2: read data from server
        String response = readResponse(conn);
        return response.substring(0, response.length() - 1);
    }

    /**
     * method read data receive from server
     * @param conn:
     * @return response : data is received from server
     * @throws IOException
     */
    private static String readResponse(HttpURLConnection conn) throws IOException {
        BufferedReader in;
        if (conn.getResponseCode() / 100 == 2) {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        String inputLine;
        StringBuilder response = new StringBuilder(); // ising StringBuilder for the sake of memory and performance
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
            response.append(inputLine + "\n");
        }
        in.close();
        LOGGER.info("Response Info: " + response);
        return response.toString();
    }

    /**
     * method handle connection with server
     * @param url : path to server need request
     * @param token : text code for Authorization in server
     * @param method : method call api
     * @return HttpURLConnection : object connection with server
     * @throws IOException
     */
    private static HttpURLConnection setUpConnection(String url, String method, String token) throws IOException {
        LOGGER.info("Request URL: " + url + "\n");
        URL line_api_url = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        return conn;
    }

    int var;

    /**
     * method help call api POST
     * @param url : path to server need request
     * @param data : data need send to server
     * @return response: data is received from server
     * @throws IOException
     */
    public static String post(String url, String data
//			, String token
    ) throws IOException {
        allowMethods("PATCH");

        // step 1: setup
        HttpURLConnection conn = setUpConnection(url, "POST", null);

        // step 2: send data
        Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(data);
        writer.close();

        //step 3: read data from server
        String response = readResponse(conn);
        return response;
    }


    /**
     * method allow call api such as PATCH, PUT,... (only active with java 11)
     * @param methods : protocol only allow (PATCH, PUT)
     * @deprecated only active with java 11
     */
    private static void allowMethods(String... methods) {
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
            methodsField.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null/* static field */, newMethods);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

}
