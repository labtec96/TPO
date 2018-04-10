package zad1;

import java.util.HashMap;

public class Main
{
	public static void main(String[] args)
	{
		  LoadDictionary loadDictionary = new LoadDictionary();
		  HashMap<String, Integer> mapaPortowSlownikow =  loadDictionary.load();
		  (new Thread(new MainServer(mapaPortowSlownikow))).start();
		  MainGui ramka = new MainGui(mapaPortowSlownikow);
		  ramka.setVisible(true);
	}
}
