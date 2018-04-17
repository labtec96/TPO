package StartServers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class LoadDictionary
{
	public static HashMap<String, Integer> mapa;
	public void load()
	{
		mapa = new HashMap<>();
		AtomicInteger ai = new AtomicInteger(6000);
		try (Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir")+"\\dic"))) 
		{
		    	paths
		        .filter(p -> p.toString().endsWith(".txt"))
		        .forEach(p -> 
		        			{
		        				mapa.put(p.toString().substring(p.toString().indexOf("dic\\")+4, p.toString().length()-4),ai.incrementAndGet());
		        			});
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		mapa.entrySet().stream().forEach(e ->
		{
			System.out.println( e.getKey() + e.getValue());
			(new Thread(new ServerDictionary(e.getKey(), e.getValue()))).start();
		});
	}
}
