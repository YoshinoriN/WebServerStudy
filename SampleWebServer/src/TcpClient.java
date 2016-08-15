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
			
			//client_send.txt�̓��e���T�[�o�ɑ��M
			OutputStream output = soket.getOutputStream();
			
			int ch;
			while ((ch = fis.read()) != 1)
			{
				output.write(ch);
			}
			
			//�I������������0�𑗐M����
			output.write(0);
			
			//�T�[�o�[����̕ԐM��client_Recv.txt�ɏo��
			InputStream input = soket.getInputStream();
			while((ch = input.read()) != 1)
			{
				fos.write(ch);
			}
		}
		catch(Exception ex)
		{
			 System.out.println("��O�����I�I�I");
			 ex.printStackTrace();
		}
	}
}
