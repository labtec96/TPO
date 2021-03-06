package StartServers;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MainServer extends Thread
{
	final int serverPort = 5000;
	ServerSocket welcomeSocket;
	String adresIp;
	HashMap<String, Integer> mapaPortowSlownikow;

	public MainServer(HashMap<String, Integer> mapaPortowSlownikow)
	{
		this.mapaPortowSlownikow = mapaPortowSlownikow;
	}

	@SuppressWarnings("resource")
	@Override
	public void run()
	{
		try
		{
			adresIp = InetAddress.getLocalHost().getHostAddress();
		} catch (IOException ie)
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
				System.out.println(universalSocket.getPort());
				System.out.println("Polaczono do Serwera");

				InputStream sis = universalSocket.getInputStream();
				InputStreamReader isr = new InputStreamReader(sis);
				BufferedReader br = new BufferedReader(isr);

				OutputStream sos = universalSocket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(sos);
				BufferedWriter bw = new BufferedWriter(osw);

				String haslo = br.readLine();
				System.out.println(haslo);
				if (haslo.equals("requestForLanguage"))
				{
					String language="";
					for (String key : mapaPortowSlownikow.keySet()) 
					{
						language = language + " " +  key;
					}
					bw.write(language);
					bw.newLine();
					bw.flush();
					universalSocket.close();
				} else
				{
					String kodKraju = br.readLine();
					System.out.println(kodKraju);
					int port = Integer.parseInt(br.readLine());
					System.out.println(port);

					int nrPortuSerweraJezykowego = 0;
					String czyDobryKod;
					if (mapaPortowSlownikow.get(kodKraju) == null)
					{
						czyDobryKod = "Nie";
						bw.write(czyDobryKod);
						bw.newLine();
						bw.flush();
						universalSocket.close();
					} else
					{
						czyDobryKod = "Tak";
						nrPortuSerweraJezykowego = mapaPortowSlownikow.get(kodKraju);
						bw.write(czyDobryKod);
						bw.newLine();
						bw.flush();
						universalSocket.close();
						System.out.println(nrPortuSerweraJezykowego);
						universalSocket = new Socket(adresIp, nrPortuSerweraJezykowego);

						sos = universalSocket.getOutputStream();
						osw = new OutputStreamWriter(sos);
						bw = new BufferedWriter(osw);

						bw.write(haslo);
						bw.newLine();
						bw.flush();
						bw.write(kodKraju);
						bw.newLine();
						bw.flush();
						bw.write(Integer.toString(port));
						bw.newLine();
						bw.flush();
						universalSocket.close();
					}
				}
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			System.out.println("Podales zly kod jezykowy");
		}
		super.run();
	}

}
