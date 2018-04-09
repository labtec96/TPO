package zad1;

import java.net.InetAddress;
import java.net.Socket;
import java.io.*;
import javax.swing.JFrame;

public class Klient extends Thread
{
	
	String adresIp;
	final int serverPort = 5000;
	String haslo;
	String kodKraju;
	int port;

	public Klient(String haslo, String kodKraju, int port)
	{
		this.haslo = haslo;
		this.kodKraju = kodKraju;
		this.port = port;
		try
		{
			 adresIp = InetAddress.getLocalHost().getHostAddress();
		}catch(
		IOException ie)
		{
			System.out.println("Problem z pobraniem adresu IP");
		}
	}

	@Override
	public void run()
	{
		Socket clientSocket;
		try
		{
			clientSocket = new Socket(adresIp, serverPort);
			InputStream sis = clientSocket.getInputStream();
			OutputStream sos = clientSocket.getOutputStream();

			InputStreamReader isr = new InputStreamReader(sis);
			OutputStreamWriter osw = new OutputStreamWriter(sos);

			BufferedReader br = new BufferedReader(isr);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(haslo);
			bw.newLine();
			bw.flush();
			bw.write(kodKraju);
			bw.newLine();
			bw.flush();
			bw.write(Integer.toString(port));
			bw.newLine();
			bw.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}