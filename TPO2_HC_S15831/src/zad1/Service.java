/**
 *
 *  @author Han Cezary S15831
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Service
{
	String nazwaKraju;
	String nazwaMiasta;
	Map<String, Currency> mapaWalut;
	String symbolKraju;

	JSONObject valuesObject;
	String description;
	Double temp;
	Double tempMin;
	Double tempMax;
	Double pressure;
	Double wind;


	private static String readAll(Reader rd) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1)
		{
			sb.append((char) cp);
		}
		return sb.toString();
	}

	private static JSONObject readJsonFromUrl(String url) throws JSONException, MalformedURLException, IOException
	{
		InputStream is = new URL(url).openStream();
		try
		{
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally
		{
			is.close();
		}
	}

	private Map<String, Currency> CountryNameToCode()
	{
		Map<String, Currency> countries = new HashMap<>();
		for (String iso : Locale.getISOCountries())
		{
			Locale l = new Locale("", iso);
			countries.put(l.getDisplayCountry(new Locale("ENGLISH", "US")), Currency.getInstance(l));
		}
		return countries;
	}

	public Service(String nazwaKraju)
	{
		this.nazwaKraju = nazwaKraju;
		mapaWalut = CountryNameToCode();
		symbolKraju = mapaWalut.get(nazwaKraju).toString();
	}

	public Service()
	{
	}

	public String getWeather(String nazwaMiasta)
	{
		this.nazwaMiasta = nazwaMiasta;
		JSONObject json;

		try
		{
			json = readJsonFromUrl("http://api.openweathermap.org/data/2.5/weather?q=" + nazwaMiasta
					+ "&appid=ddb6b3bd5c5ba17ee25f40bb49a51e21");
			return json.toString();
			/*
			valuesObject = new JSONObject();
			valuesObject.put("description", description);
			valuesObject.put("temp", temp);
			valuesObject.put("tempMax", tempMax);
			valuesObject.put("tempMin", tempMin);
			valuesObject.put("pressure", pressure);
			valuesObject.put("wind", wind);
			return valuesObject.toString();
			*/
		} catch (JSONException | IOException e)
		{
			System.out.println("Podane zle parametry Miasta");
			return null;
		}
	}

	public Double getRateFor(String symbolKrajuPodanegoArgument)
	{
		try
		{
			JSONObject js = readJsonFromUrl(
					"http://data.fixer.io/api/latest?access_key=751e4f0baaeba10d70baafd9f801128c&symbols=" + symbolKraju
							+ "," + symbolKrajuPodanegoArgument + "&format=1");
			Double result = Double.valueOf(js.getJSONObject("rates").getDouble(symbolKraju)
					/ js.getJSONObject("rates").getDouble(symbolKrajuPodanegoArgument));
			return BigDecimal.valueOf(result).setScale(5, RoundingMode.HALF_UP).doubleValue();
		} catch (JSONException | IOException e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}

	public Double getNBPRate()
	{
		if (symbolKraju.equals("PLN"))
			return 1.0;
		else
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			try
			{
				db = dbf.newDocumentBuilder();
				Document doc = db.parse(new URL("http://www.nbp.pl/kursy/xml/a061z180327.xml").openStream());
				NodeList nList = doc.getElementsByTagName("pozycja");

				for (int temp = 0; temp < nList.getLength(); temp++)
				{

					Node nNode = nList.item(temp);

					if (nNode.getNodeType() == Node.ELEMENT_NODE)
					{

						Element eElement = (Element) nNode;
						if (eElement.getElementsByTagName("kod_waluty").item(0).getTextContent().equals(symbolKraju))
						{
							String tmp = eElement.getElementsByTagName("kurs_sredni").item(0).getTextContent();
							tmp = tmp.replaceAll(",", ".");
							return new BigDecimal(Double.valueOf(tmp)/1.0).setScale(5, RoundingMode.HALF_UP)
									.doubleValue();
						}

					}
				}
			} catch (ParserConfigurationException | SAXException | IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public synchronized String getNazwaKraju()
	{
		return nazwaKraju;
	}

	public synchronized void setNazwaKraju(String nazwaKraju)
	{
		this.nazwaKraju = nazwaKraju;
		mapaWalut = CountryNameToCode();
		symbolKraju = mapaWalut.get(nazwaKraju).toString();
	}

	public synchronized String getNazwaMiasta()
	{
		return nazwaMiasta;
	}

	public synchronized void setNazwaMiasta(String nazwaMiasta)
	{
		this.nazwaMiasta = nazwaMiasta;
	}

	public synchronized String getSymbolKraju()
	{
		return symbolKraju;
	}
}
