package com.zzikza.springboot.web.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.http.ParseException;
import org.springframework.boot.json.JsonParseException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.zzikza.springboot.web.util.JsonUtil.jsonStringToMap;

public class URLConnectUtil {

    /**
     * get URL 전송 후 결과값 얻는 메소드
     *
     * @param con 원하는 URL을 가지고 있는 커텍션
     * @return 결과값
     * @throws IOException
     * @throws ParseException
     * @throws JsonParseException
     * @throws JsonMappingException
     */
    public static Map<String, Object> getUrlResultMap(HttpURLConnection con) throws IOException, ParseException, JsonParseException, JsonMappingException {
        int responseCode = con.getResponseCode();
        BufferedReader br = null;
        if (responseCode == 200) {
            // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine = null;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
        // JSON string 을 Map으로 변환
        Map<String, Object> map = jsonStringToMap(response.toString());
        return map;
    }


    /**
     * POST 형식의 커넥션 세팅
     *
     * @param paramMap
     * @param apiURL
     * @param urlParameters
     * @return
     * @throws IOException
     */
    public static HttpURLConnection setHttpUrlConnection(Map<String, Object> paramMap, String apiURL, String urlParameters) throws IOException {
        URL url = new URL(apiURL);
        byte[] postDataBytes = urlParameters.getBytes(StandardCharsets.UTF_8);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setInstanceFollowRedirects(false);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        if (paramMap.get("token") != null) {
            con.setRequestProperty("Authorization", paramMap.get("token").toString());
        }
        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(con.getOutputStream());
            wr.write(postDataBytes);
        } catch (Exception e) {
            wr.flush();
            wr.close();
        } finally {
            wr.flush();
            wr.close();
        }
        return con;
    }

}
