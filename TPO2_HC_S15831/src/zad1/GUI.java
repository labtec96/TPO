package zad1;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

@SuppressWarnings("serial")
public class GUI extends JFrame
{

	private Service service;
	private Browser myBrowser;
	
	
	JPanel contentPane;
	JTextField tfKraj;
	JTextField tfMiasto;
	JPanel panel;
	JLabel variableWindSpeed;
	JLabel windSpeed;
	JLabel City;
	JLabel variablePressure;
	JLabel variableMaxTemprature;
	JLabel variableMinTemprature;
	JLabel variableTemperature;
	JLabel pressure;
	JLabel MaxTemprature;
	JLabel minTemprature;
	JLabel temperature;
	JLabel variabledescription;
	JLabel description;
	JLabel variablelKursWalutyPodanego;
	JLabel lblKursWalutyPodanego;
	JLabel lbKursWobecPLN;
	JLabel lblWaluta;
	
	String nazwaMiasta;
	String nazwaKraju;
	String walutaSymbol;
	private JTextField tfWaluta;
	private JLabel variablelKursDoPLN;
	
	
	private double KevinToCels(double kevin)
	{
		return Math.round((kevin - 273.0) * 100.0) / 100.0;
	}
	
	public GUI()
	{
		setResizable(false);
		service = new Service();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 685);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblD = new JLabel("Country");
		lblD.setBounds(24, 18, 48, 17);
		contentPane.add(lblD);

		tfKraj = new JTextField();
		tfKraj.setToolTipText("Kraj");
		tfKraj.setBounds(84, 16, 86, 20);
		contentPane.add(tfKraj);
		tfKraj.setColumns(10);

		JLabel lblNewLabel = new JLabel("City");
		lblNewLabel.setBounds(205, 18, 48, 17);
		contentPane.add(lblNewLabel);

		tfMiasto = new JTextField();
		tfMiasto.setToolTipText("Miasto");
		tfMiasto.setBounds(273, 16, 86, 20);
		contentPane.add(tfMiasto);
		tfMiasto.setColumns(10);

		JButton btnSprawdz = new JButton("Sprawdz");
		btnSprawdz.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					if (tfKraj.getText().equals("") || tfMiasto.getText().equals("") || tfWaluta.getText().equals(""))
					{
						throw new Exception("Podaj wszystkie elementy");
					} else
					{
						nazwaKraju = tfKraj.getText();
						nazwaMiasta = tfMiasto.getText();
						walutaSymbol = tfWaluta.getText(); 
						service.setNazwaMiasta(nazwaMiasta);
						service.setNazwaKraju(nazwaKraju);
						City.setText(nazwaMiasta);
						
						
						JSONObject js = new JSONObject(service.getWeather(nazwaMiasta));
						JSONArray weatherArray = js.getJSONArray("weather");
						for (int i = 0; i < weatherArray.length(); i++)
						{
							variabledescription.setText(weatherArray.getJSONObject(i).getString("description"));
						}
						
						
						
						variableTemperature.setText(String.valueOf(KevinToCels(js.getJSONObject("main").getDouble("temp"))));
						variableMinTemprature.setText(String.valueOf(KevinToCels(js.getJSONObject("main").getDouble("temp_min"))));
						variableMaxTemprature.setText(String.valueOf(KevinToCels(js.getJSONObject("main").getDouble("temp_max"))));
						variablePressure.setText(String.valueOf(js.getJSONObject("main").getDouble("pressure")));
						variableWindSpeed.setText(String.valueOf(js.getJSONObject("wind").getDouble("speed")));
						//-------
						myBrowser.update(service.getNazwaMiasta());
						//------
						variablelKursWalutyPodanego.setText("1" + walutaSymbol + " = " + service.getRateFor(walutaSymbol).toString() + service.getSymbolKraju());
						//---
						variablelKursDoPLN.setText("1" + service.getSymbolKraju() +" = " +  service.getNBPRate() + "PLN");
					}
				} catch (Exception e)
				{
					System.out.println(e.getMessage());
				}
			}
		});
		btnSprawdz.setBounds(594, 15, 89, 23);
		contentPane.add(btnSprawdz);
		myBrowser = new Browser();
		panel = myBrowser;
		panel.setBounds(24, 154, 802, 459);
		contentPane.add(panel);

		description = new JLabel("Description:");
		description.setFont(new Font("Tahoma", Font.BOLD, 15));
		description.setVerticalTextPosition(SwingConstants.BOTTOM);
		description.setHorizontalAlignment(SwingConstants.CENTER);
		description.setBounds(836, 94, 328, 32);
		contentPane.add(description);
		variabledescription = new JLabel("");
		variabledescription.setVerticalTextPosition(SwingConstants.BOTTOM);
		variabledescription.setHorizontalAlignment(SwingConstants.CENTER);
		variabledescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
		variabledescription.setBounds(836, 140, 328, 32);
		contentPane.add(variabledescription);

		temperature = new JLabel("Temperature:");
		temperature.setVerticalTextPosition(SwingConstants.BOTTOM);
		temperature.setHorizontalAlignment(SwingConstants.CENTER);
		temperature.setFont(new Font("Tahoma", Font.BOLD, 15));
		temperature.setBounds(836, 183, 328, 32);
		contentPane.add(temperature);

		minTemprature = new JLabel("Min Temperature:");
		minTemprature.setVerticalTextPosition(SwingConstants.BOTTOM);
		minTemprature.setHorizontalAlignment(SwingConstants.CENTER);
		minTemprature.setFont(new Font("Tahoma", Font.BOLD, 15));
		minTemprature.setBounds(836, 269, 328, 32);
		contentPane.add(minTemprature);

		MaxTemprature = new JLabel("Max Temperature:");
		MaxTemprature.setVerticalTextPosition(SwingConstants.BOTTOM);
		MaxTemprature.setHorizontalAlignment(SwingConstants.CENTER);
		MaxTemprature.setFont(new Font("Tahoma", Font.BOLD, 15));
		MaxTemprature.setBounds(836, 355, 328, 32);
		contentPane.add(MaxTemprature);

		pressure = new JLabel("Pressure:");
		pressure.setVerticalTextPosition(SwingConstants.BOTTOM);
		pressure.setHorizontalAlignment(SwingConstants.CENTER);
		pressure.setFont(new Font("Tahoma", Font.BOLD, 15));
		pressure.setBounds(836, 441, 328, 32);
		contentPane.add(pressure);

		variableTemperature = new JLabel("");
		variableTemperature.setVerticalTextPosition(SwingConstants.BOTTOM);
		variableTemperature.setHorizontalAlignment(SwingConstants.CENTER);
		variableTemperature.setFont(new Font("Tahoma", Font.PLAIN, 14));
		variableTemperature.setBounds(836, 226, 328, 32);
		contentPane.add(variableTemperature);

		variableMinTemprature = new JLabel("");
		variableMinTemprature.setVerticalTextPosition(SwingConstants.BOTTOM);
		variableMinTemprature.setHorizontalAlignment(SwingConstants.CENTER);
		variableMinTemprature.setFont(new Font("Tahoma", Font.PLAIN, 14));
		variableMinTemprature.setBounds(836, 312, 328, 32);
		contentPane.add(variableMinTemprature);

		variableMaxTemprature = new JLabel("");
		variableMaxTemprature.setVerticalTextPosition(SwingConstants.BOTTOM);
		variableMaxTemprature.setHorizontalAlignment(SwingConstants.CENTER);
		variableMaxTemprature.setFont(new Font("Tahoma", Font.PLAIN, 14));
		variableMaxTemprature.setBounds(836, 398, 328, 32);
		contentPane.add(variableMaxTemprature);

		variablePressure = new JLabel("");
		variablePressure.setVerticalTextPosition(SwingConstants.BOTTOM);
		variablePressure.setHorizontalAlignment(SwingConstants.CENTER);
		variablePressure.setFont(new Font("Tahoma", Font.PLAIN, 14));
		variablePressure.setBounds(836, 484, 328, 32);
		contentPane.add(variablePressure);

		City = new JLabel("City");
		City.setVerticalTextPosition(SwingConstants.BOTTOM);
		City.setHorizontalAlignment(SwingConstants.CENTER);
		City.setFont(new Font("Tahoma", Font.BOLD, 17));
		City.setBounds(836, 43, 328, 32);
		contentPane.add(City);

		windSpeed = new JLabel("Wind Speed:");
		windSpeed.setVerticalTextPosition(SwingConstants.BOTTOM);
		windSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		windSpeed.setFont(new Font("Tahoma", Font.BOLD, 15));
		windSpeed.setBounds(836, 527, 328, 32);
		contentPane.add(windSpeed);

		variableWindSpeed = new JLabel("");
		variableWindSpeed.setVerticalTextPosition(SwingConstants.BOTTOM);
		variableWindSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		variableWindSpeed.setFont(new Font("Tahoma", Font.PLAIN, 14));
		variableWindSpeed.setBounds(836, 570, 328, 32);
		contentPane.add(variableWindSpeed);

		lblWaluta = new JLabel("Currency");
		lblWaluta.setBounds(395, 19, 46, 14);
		contentPane.add(lblWaluta);

		tfWaluta = new JTextField();
		tfWaluta.setToolTipText("Waluta");
		tfWaluta.setBounds(466, 16, 86, 20);
		contentPane.add(tfWaluta);
		tfWaluta.setColumns(10);

		lbKursWobecPLN = new JLabel("Kurs waluty złotego wobec waluty podanego kraju:");
		lbKursWobecPLN.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbKursWobecPLN.setBounds(46, 105, 392, 21);
		contentPane.add(lbKursWobecPLN);

		lblKursWalutyPodanego = new JLabel("Kurs waluty kraju wobec waluty podanej:");
		lblKursWalutyPodanego.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblKursWalutyPodanego.setBounds(46, 73, 321, 21);
		contentPane.add(lblKursWalutyPodanego);
		
		variablelKursWalutyPodanego = new JLabel("");
		variablelKursWalutyPodanego.setFont(new Font("Tahoma", Font.PLAIN, 15));
		variablelKursWalutyPodanego.setBounds(377, 78, 306, 14);
		contentPane.add(variablelKursWalutyPodanego);
		
		variablelKursDoPLN = new JLabel("");
		variablelKursDoPLN.setFont(new Font("Tahoma", Font.PLAIN, 15));
		variablelKursDoPLN.setBounds(448, 112, 235, 14);
		contentPane.add(variablelKursDoPLN);
	}	
}
