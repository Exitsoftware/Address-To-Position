package address;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class Cal{
	ArrayList<String> lines = new ArrayList<String>();
	ArrayList<String> result_line = new ArrayList<String>();

	void load(String filename){
		try{
			BufferedReader br = new BufferedReader(new FileReader(filename));
			while(true){
				String line = br.readLine();
				if(line == null) break;
				lines.add(line);
			}
			br.close();	
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	void output(){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("Output4.txt"));
//			bw.write("1123\n");
//			bw.flush();
//			bw.write("1123\n");
//			bw.flush();
//			bw.write("1123\n");
//			bw.flush();
//			bw.close();
			for(String line : result_line){
				bw.write(line+"\n");
				bw.flush();
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}

	void start(){
		Scanner s = new Scanner(System.in);
		String filename = s.next();

		load(filename);

		for(String line : lines){
			try{
				String arr[] = line.split("\t");
				System.out.println(arr[1]);
//				line.replaceAll(" ", "");
//				System.out.println(line);
//				String str = "Vicki Yi";
//				System.out.println(str + " : " + str.replaceAll("\\p{Space}", ""));
				String address = "https://apis.daum.net/local/geo/addr2coord?apikey=df5ae15d24c97796df80b2b233841b68&q=";
				address += arr[1];
				address += "&output=json";
				URL url = new URL(address);	
				InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
				// JSON을 Parsing 한다. 문법오류가 날 경우 Exception 발생, without Exception -> parse 메소드
				JSONObject object = (JSONObject)JSONValue.parseWithException(isr);
				// 객체
				JSONObject channel = (JSONObject)(object.get("channel"));
				// item 배열
				JSONArray items = (JSONArray)channel.get("item");

				for(int i = 0 ; i < items.size(); i++) {
					JSONObject obj1 = (JSONObject)items.get(i);
//					System.out.println("주소 : "+obj1.get("title").toString());
//					System.out.println("위도 : "+obj1.get("lat").toString());
//					System.out.println("경도 : "+obj1.get("lng").toString());
//					
					result_line.add(arr[0]+"\t"+obj1.get("title").toString()+"\t" + obj1.get("lat".toString()) + "\t" + obj1.get("lng").toString()  );
				}
				
				
//				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//				conn.setRequestMethod("GET");
//
//				BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
////				InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
//				String jsonResult = "";
//				while(true){
//					String jsonString = br.readLine();
//					if(jsonString == null) break;
//					jsonResult += jsonString;
//				}
//				System.out.println(jsonResult);
//				JSONObject json = new JSONObject(jsonResult);
//				System.out.println("주소 : " + line);
//				System.out.println("위도 : "+json.get("lat"));
//				System.out.println("경도 : "+json.get("lng"));
//				result_line.add("주소  : " + line + "위도  : " + json.get("lat" + "경도 : " + json.get("lng")));
//				br.close();

			}
			catch(Exception ex){
				System.out.println(ex);
			}
		}
		output();
		System.out.println("done");


	}
	public static void main(String[] args) {
		Cal m = new Cal();
		m.start();
	}
}