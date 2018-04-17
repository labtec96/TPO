package StartServers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerDictionary extends Thread
{
	HashMap<String, String> dictionary;
	String symbol;
	Integer port;
	ServerSocket welcomeSocket;
	String adresIp;
	Socket responseToClient;
	public ServerDictionary(String symbol, Integer port)
	{
		this.symbol = symbol;
		this.port = port;
		dictionary = readToMap();
		try
		{
			 adresIp = InetAddress.getLocalHost().getHostAddress();
		}catch(
		IOException ie)
		{
			System.out.println("Problem z pobraniem adresu IP");
		}
	}

	private HashMap<String, String> readToMap()
	{
		HashMap<String, String> dictionary = new HashMap<>();
		String nazwaPliku = System.getProperty("user.dir") + "\\dic\\" + symbol + ".txt";
		try (BufferedReader br = new BufferedReader(new FileReader(nazwaPliku)))
		{
			String currentLine;
			while ((currentLine = br.readLine()) != null)
			{
				dictionary.put(currentLine.substring(0, currentLine.indexOf(" ")),currentLine.substring(currentLine.indexOf(" ")+1,currentLine.length()));
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return dictionary;
	}

	@SuppressWarnings("resource")
	public void run()
	{
		try
		{
			welcomeSocket = new ServerSocket(port);
			while (true)
			{
				Socket universalSocket = welcomeSocket.accept();
				System.out.println("Polaczono do Serwera Jezykowego");

				InputStream sis = universalSocket.getInputStream();
				InputStreamReader isr = new InputStreamReader(sis);
				BufferedReader br = new BufferedReader(isr);


				String haslo = br.readLine();
				System.out.println(haslo);
				String kodKraju = br.readLine();
				System.out.println(kodKraju);
				int port = Integer.parseInt(br.readLine());
				System.out.println(port);
				
				responseToClient = new Socket(adresIp, port);
				OutputStream sos = responseToClient.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(sos);
				BufferedWriter bw = new BufferedWriter(osw);
				
				String hasloRespons;
				if ( dictionary.get(haslo) == null)
				{
					hasloRespons = "Brak hasla";
				}
        		else
        		{
        			hasloRespons = dictionary.get(haslo);
        		}
				bw.write(hasloRespons);
				bw.newLine();
				bw.flush();
				responseToClient.close();
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			System.out.println("Nie znaleziono takiego hasla");
		}
	}
}
