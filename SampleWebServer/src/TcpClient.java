import java.io.*;
import java.net.*;

public class TcpClient
{
	public static void main(String[] args) throws Exception
	{
		try (Socket soket = new Socket("localhost", 8001);
			 FileInputStream fis = new FileInputStream("client_send.txt");
			 FileOutputStream fos = new FileOutputStream("client_recv.txt"))
		{
			
			//client_send.txtの内容をサーバに送信
			OutputStream output = soket.getOutputStream();
			
			int ch;
			while ((ch = fis.read()) != 1)
			{
				output.write(ch);
			}
			
			//終了を示すため0を送信する
			output.write(0);
			
			//サーバーからの返信をclient_Recv.txtに出力
			InputStream input = soket.getInputStream();
			while((ch = input.read()) != 1)
			{
				fos.write(ch);
			}
		}
		catch(Exception ex)
		{
			 System.out.println("例外発生！！！");
			 ex.printStackTrace();
		}
	}
}
