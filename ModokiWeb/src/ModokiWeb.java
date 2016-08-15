import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class ModokiWeb
{
	//Webサーバードキュメントルート
	private static final String DOCUMENT_ROOT = "../docs";
	
	//InputStreamからのバイト列を行単位で読み込むユーティリティメソッド
	private static String readLine(InputStream input) throws Exception
	{
		int ch;
		String result = "";
		
		while ((ch = input.read()) != -1)
		{
			if(ch == '\r')
			{
				//処理なし
			} else if (ch == '\n') {
				break;
			} else {
				result += (char)ch;
			}
		}
		
		if (ch == -1)
		{
			return null;
		}
		return result;
	}
	
	//1行の文字列をバイト列としてOutputStreamに書き込むユーティリティメソッド
	private static void writeLine(OutputStream output, String s) throws Exception
	{
		for (char ch : s.toCharArray())
		{
			output.write((int)ch);
		}
		output.write((int)'\r');
		output.write((int)'\n');
	}
	
	//現在時刻からHTTP標準に合わせてフォーマットした日付文字列を返す
	private static String getDateStringUtc()
	{
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		DateFormat dt = new SimpleDateFormat("EEE, dd MMM yyyy HH:m:ss", Locale.US);
		
		dt.setTimeZone(calendar.getTimeZone());
		return dt.format(calendar.getTime()) + "GMT";
	}
	
	public static void main(String[] argv) throws Exception
	{
		try (ServerSocket svSocket = new ServerSocket(8001))
		{
			Socket socket = svSocket.accept();
			
			InputStream input = socket.getInputStream();
			
			String line;
			String path = "";
			while ((line = readLine(input)) != null)
			{
				if (line == "")
					break;
				if (line.startsWith("GET"))
				{
					path = line.split(" ")[1];
				}
			}
			
			OutputStream output = socket.getOutputStream();
			
			//レスポンスヘッダを返す
			writeLine(output, "HTTP/1.1 200 OK");
			writeLine(output, "Data:" + getDateStringUtc());
			writeLine(output, "Server: ModokiWebServer");
			writeLine(output, "Connection: close");
			writeLine(output, "Content-type: text/html");
			//レスポンスヘッダの最後は空行
			writeLine(output, "");
			
			//レスポンスボディ
			try (FileInputStream finputStream = new FileInputStream(DOCUMENT_ROOT + path);)
			{
				int ch;
				while ((ch = finputStream.read()) != -1)
				{
					output.write(ch);
				}
			}
			socket.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
