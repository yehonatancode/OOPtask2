package File_format;

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.FileReader;

import java.io.FileWriter;

import java.io.IOException;



public class Csv2kml {

	private static final String ICON_URL = "http://campanalbero.net/icon/";

	private final BufferedWriter writer;

	private final BufferedReader reader;





	public Csv2kml(String input, String output) throws IOException {
		reader = new BufferedReader(new FileReader(input));
		writer = new BufferedWriter(new FileWriter(output));

	}
	private void writeStart() throws IOException {
		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		writer.write("<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document><Style id=\"red\">"
				+ "<IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon>"
				+ "</IconStyle></Style><Style id=\"yellow\"><IconStyle>"
				+ "<Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon>"
				+ "</IconStyle></Style><Style id=\"green\"><IconStyle>"
				+ "<Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon>"
				+ "</IconStyle></Style><Folder>"
				+ "<name>Wifi Networks</name>\n");
	}

	public String timeStamp(String name) {
		System.out.println(name);
		  	name.replaceAll("-", "");
	        name.replaceAll(":","");
	        name.replaceAll(" ", "");
	        System.out.println(name.toString());
	        long year = ((Long.parseLong((String) name.substring(0, 4))-1970)*12);
	        long month = (year + Long.parseLong((String) name.substring(5, 7)))/2*30 + (year + Long.parseLong((String) name.substring(5, 7)))/2*31 ;
	        long day = (month + Long.parseLong((String) name.substring(8, 10)))*24;
	        long hour = (day + Long.parseLong((String) name.substring(11, 13)))*60;
	        long minute = (hour + Long.parseLong((String) name.substring(14, 16)))*60;
	        long seconds = (minute + Long.parseLong((String) name.substring(17, 19)));
	        return ""+seconds;
	        
	}

	public void run() throws IOException {
		try {
			writeStart();
			reader.readLine();
			reader.readLine(); // skip 1st line
			String str = reader.readLine();
			String[] parsed = str.split(",");
			while (str != null) {
				writer.write("<Placemark>\n");
				writer.write("<name><![CDATA["+parsed[1]+"]]></name>\n");
				writer.write("<description> <![CDATA[BSSID: <b>"+parsed[0] +
						"</b><br/>Capabilities: <b>"+parsed[2]+"</b><br/>Frequency: <b>"+2462+
						"</b><br/>Timestamp: <b>" + timeStamp(parsed[3]) +"</b><br/>Date: <b>"+parsed[3]+"</b>]]></description><styleUrl>#red</styleUrl>\n");
				writer.write("<Point>\r\n" + 
						"<coordinates>" + parsed[7]+","+parsed[6]+"</coordinates></Point>\n");
				writer.write("</Placemark>\r\n");
				str = reader.readLine();
				if (str!=null)
					parsed = str.split(",");

			}
			writer.write("</Folder>\n"
					+ "</Document></kml>");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} finally {
					if (writer != null) {
						writer.close();
					}
				}
			} else {
				if (writer != null) {
					writer.close();
				}
			}
		}
	}
}
