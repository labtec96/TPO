package zad1;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer extends Thread
{
	final int serverPort = 5000;
	ServerSocket welcomeSocket;
	String adresIp;
	@Override
	public void run()
	{
		try
		{
			 adresIp = InetAddress.getLocalHost().getHostAddress();
		}catch(IOException ie)
		{
			System.out.println("Problem z pobraniem adresu IP");
		}
		try
		{
			System.out.println("Serwer Wystartowal");
			welcomeSocket = new ServerSocket(serverPort);
			while (true)
			{
				Socket universalSocket = welcomeSocket.accept();
				System.out.println("Polaczono do Serwera");
				
				InputStream sis = universalSocket.getInputStream();
        		InputStreamReader isr = new InputStreamReader(sis);	
        		BufferedReader br = new BufferedReader(isr);
        		
        		String haslo = br.readLine();
        		System.out.println(haslo);
        		String kodKraju = br.readLine();
        		System.out.println(kodKraju);
        		int port = Integer.parseInt(br.readLine());
        		System.out.println(port);
        		universalSocket.close();
        		universalSocket = new Socket(adresIp, serverPort);
        		
        		OutputStream sos = universalSocket.getOutputStream();
        		OutputStreamWriter osw = new OutputStreamWriter(sos);
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
   			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.run();
	}

}