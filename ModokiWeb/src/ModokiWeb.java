import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class ModokiWeb
{
	//Web�T�[�o�[�h�L�������g���[�g
	private static final String DOCUMENT_ROOT = "../docs";
	
	//InputStream����̃o�C�g����s�P�ʂœǂݍ��ރ��[�e�B���e�B���\�b�h
	private static String readLine(InputStream input) throws Exception
	{
		int ch;
		String result = "";
		
		while ((ch = input.read()) != -1)
		{
			if(ch == '\r')
			{
				//�����Ȃ�
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
	
	//1�s�̕�������o�C�g��Ƃ���OutputStream�ɏ������ރ��[�e�B���e�B���\�b�h
	private static void writeLine(OutputStream output, String s) throws Exception
	{
		for (char ch : s.toCharArray())
		{
			output.write((int)ch);
		}
		output.write((int)'\r');
		output.write((int)'\n');
	}
	
	//���ݎ�������HTTP�W���ɍ��킹�ăt�H�[�}�b�g�������t�������Ԃ�
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
			
			//���X�|���X�w�b�_��Ԃ�
			writeLine(output, "HTTP/1.1 200 OK");
			writeLine(output, "Data:" + getDateStringUtc());
			writeLine(output, "Server: ModokiWebServer");
			writeLine(output, "Connection: close");
			writeLine(output, "Content-type: text/html");
			//���X�|���X�w�b�_�̍Ō�͋�s
			writeLine(output, "");
			
			//���X�|���X�{�f�B
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
