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
				System.out.println("�N���C�A���g����̐ڑ���҂��܂�");
			
				Socket socket = server.accept();
				System.out.println("�N���C�A���g�ڑ�");
			
				//�N���C�A���g����󂯎�������e��server_recv.txt�ɏo�͂���
				InputStream input = socket.getInputStream();
			 
				int ch;
				//�N���C�A���g�͏I������0�𑗐M���Ă���
				while ((ch = input.read()) != -1)
				{
					fos.write(ch);
				}
			 
				//server_send.txt�̓��e���N���C�A���g�ɑ��t
				OutputStream output = socket.getOutputStream();
				while((ch = fis.read()) != -1)
				{
					output.write(ch); 
				}
				socket.close();
				System.out.println("�ʐM�I��");
			 }
			 catch (Exception ex)
			 {
				 System.out.println("��O�����I�I�I");
				 ex.printStackTrace();
			 }
	}

}
