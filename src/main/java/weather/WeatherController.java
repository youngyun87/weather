package weather;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Controller
public class WeatherController {

    @RequestMapping("/")
    //@ResponseBody
    public String Hello(Model model){

        return "redirect:/weather";
    }

    @RequestMapping("weather")
    public String weather(Model model){

         String jsonString = whetherApiResultJson();
         model.addAttribute("jsonString", jsonString);



        return "weather.html";
    }


    @RequestMapping("/weatherInfo")
    @ResponseBody
    public String weatherInfo(){
        String jsonString = whetherApiResultJson();
        return jsonString;
    }

    private String whetherApiResultJson() {

        String jsonString = "";

        try{
            // 오늘날짜 yyyymmdd
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar c1 = Calendar.getInstance();
            String strToday = sdf.format(c1.getTime());

            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=%2B5m3O8Rjr04idU6zglHxFz3KCCIwKfrl2hIGwYLK4ykKYV6dVbfPUSux%2FRAv5u6J%2FrU038hJczJtsKnUisHU2g%3D%3D"); /*Service Key*/

            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("300", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
            urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(strToday, "UTF-8")); /*‘날짜 yyyymmdd*/


            //urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(getCurrentDateHhmm(), "UTF-8")); /*06시 발표(정시단위) */
             urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0500", "UTF-8")); /*05시 발표*/

            // 논현동 x,y
            urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("61", "UTF-8")); /*예보지점의 X 좌표값*/
            urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("126", "UTF-8")); /*예보지점의 Y 좌표값*/
            URL url = new URL(urlBuilder.toString());

            // 기상청 api로 연결
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            System.out.println("Response code: " + conn.getResponseCode());

            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            // 결과 String
            jsonString = sb.toString();

        }catch (Exception e){
            e.printStackTrace();
        }

        return jsonString;
    }

    private String getCurrentDateHhmm() {
        Date today = new Date();
		Locale currentLocale = new Locale("KOREAN", "KOREA");
		String pattern = "hhmmss"; //hhmmss로 시간,분,초만 뽑기도 가능
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);

        int hour = Integer.parseInt(formatter.format(today).substring(0,2));
        String hourStr = "";
        if(hour != 0){
            hour = hour - 1;
        }
        if(hour < 10){
            hourStr = "0" + String.valueOf(hour);
        }else{
            hourStr = String.valueOf(hour);
        }

		return hourStr + "30";
    }
}
