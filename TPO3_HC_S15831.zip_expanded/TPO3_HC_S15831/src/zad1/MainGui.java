package zad1;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;

public class MainGui extends JFrame
{

	private JPanel contentPane;
	private JLabel lblKodKraju;
	private JLabel lblPort;
	private JLabel lblHaslo;
	private JTextField tfHaslo;
	private JTextField tfKodKraju;
	private JTextField tfPort;
	private JLabel lbvarlTlumaczenie;
	private JLabel lblTlumaczenie;
	private JLabel lblDostepneJezyki;
	private JLabel lblLanguage;
	private JButton btnNewButton;

	public MainGui()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 158);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfHaslo = new JTextField();
		tfHaslo.setBounds(92, 9, 164, 20);
		contentPane.add(tfHaslo);
		tfHaslo.setColumns(10);

		lblHaslo = new JLabel("Haslo");
		lblHaslo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblHaslo.setBounds(10, 11, 72, 14);
		contentPane.add(lblHaslo);

		lblKodKraju = new JLabel("Kod kraju");
		lblKodKraju.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblKodKraju.setBounds(10, 42, 72, 14);
		contentPane.add(lblKodKraju);

		lblPort = new JLabel("Port");
		lblPort.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPort.setBounds(10, 67, 72, 14);
		contentPane.add(lblPort);

		tfKodKraju = new JTextField();
		tfKodKraju.setColumns(10);
		tfKodKraju.setBounds(92, 40, 164, 20);
		contentPane.add(tfKodKraju);

		tfPort = new JTextField();
		tfPort.setColumns(10);
		tfPort.setBounds(92, 65, 164, 20);
		contentPane.add(tfPort);

		lbvarlTlumaczenie = new JLabel("");
		lbvarlTlumaczenie.setHorizontalAlignment(SwingConstants.CENTER);
		lbvarlTlumaczenie.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbvarlTlumaczenie.setBounds(304, 42, 147, 14);
		contentPane.add(lbvarlTlumaczenie);

		lblTlumaczenie = new JLabel("Tlumaczenie:");
		lblTlumaczenie.setHorizontalAlignment(SwingConstants.CENTER);
		lblTlumaczenie.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTlumaczenie.setBounds(304, 15, 147, 14);
		contentPane.add(lblTlumaczenie);
		
		btnNewButton = new JButton("Sprawdz");
		btnNewButton.setBounds(340, 71, 89, 23);
		contentPane.add(btnNewButton);
		lblDostepneJezyki = new JLabel("Dostepne jezyki:");
		lblDostepneJezyki.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDostepneJezyki.setBounds(10, 92, 110, 14);
		contentPane.add(lblDostepneJezyki);
		
		lblLanguage = new JLabel("");
		lblLanguage.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLanguage.setBounds(130, 93, 110, 14);
		contentPane.add(lblLanguage);
		
		///RequestForLanguges
		
		try
		{
			Socket clientSocket = new Socket(InetAddress.getLocalHost().getHostAddress(), 5000);
			OutputStream sos = clientSocket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(sos);
			BufferedWriter bw = new BufferedWriter(osw);
			
			InputStream sis = clientSocket.getInputStream();
			InputStreamReader isr = new InputStreamReader(sis);
			BufferedReader br = new BufferedReader(isr);
			
			bw.write("requestForLanguage");
			bw.newLine();
			bw.flush();
			String languge = br.readLine();
			lblLanguage.setText(languge);
			clientSocket.close();
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

				try
				{
					if (tfHaslo.getText().equals("") || tfKodKraju.getText().equals("") || tfPort.getText().equals(""))
						throw new Exception();
					else
					{
						int port = Integer.parseInt(tfPort.getText());
						(new Thread(new Klient(tfHaslo.getText(), tfKodKraju.getText(), port,lbvarlTlumaczenie,lblLanguage))).start();
					}
				} catch (NumberFormatException e)
				{
					System.out.println("Podaj liczbe w porcie");
				} catch (Exception e)
				{
					System.out.println("Podaj wszystkie parametry");
				}

			}
		});
	}

	public JTextField getTfHaslo()
	{
		return tfHaslo;
	}

	public JTextField getTfKodKraju()
	{
		return tfKodKraju;
	}

	public JTextField getTfPort()
	{
		return tfPort;
	}

	public void setLblTlumaczenie(JLabel lblTlumaczenie)
	{
		this.lbvarlTlumaczenie = lblTlumaczenie;
	}
}
