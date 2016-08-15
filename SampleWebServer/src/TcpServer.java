import java.io.*;
import java.net.*;

public class TcpServer
{
	public static void main(String[] argv) throws Exception
	{
		try (ServerSocket server = new ServerSocket(8001);
			 FileOutputStream fos = new FileOutputStream("server_recv.txt");
			 FileInputStream fis = new FileInputStream("server_send.txt"))
			{
				System.out.println("クライアントからの接続を待ちます");
			
				Socket socket = server.accept();
				System.out.println("クライアント接続");
			
				//クライアントから受け取った内容をserver_recv.txtに出力する
				InputStream input = socket.getInputStream();
			 
				int ch;
				//クライアントは終了時に0を送信してくる
				while ((ch = input.read()) != -1)
				{
					fos.write(ch);
				}
			 
				//server_send.txtの内容をクライアントに送付
				OutputStream output = socket.getOutputStream();
				while((ch = fis.read()) != -1)
				{
					output.write(ch); 
				}
				socket.close();
				System.out.println("通信終了");
			 }
			 catch (Exception ex)
			 {
				 System.out.println("例外発生！！！");
				 ex.printStackTrace();
			 }
	}

}
