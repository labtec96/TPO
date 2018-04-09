package zad1;

import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * SwingFXWebView
 */
@SuppressWarnings("serial")
public class Browser extends JPanel
{
	String nazwaMiasta;
	
	private Stage stage;
	private WebView browser;
	private JFXPanel jfxPanel;
	private JButton swingButton;
	private WebEngine webEngine;

	public Browser()
	{
		initComponents();
	}
	private void initComponents()
	{
		jfxPanel = new JFXPanel();
		createScene();

		setLayout(new BorderLayout());
		add(jfxPanel, BorderLayout.CENTER);

		swingButton = new JButton();
		swingButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Platform.runLater(new Runnable()
				{

					@Override
					public void run()
					{
						webEngine.reload();
					}
				});
			}
		});
	}

	private void createScene()
	{
		PlatformImpl.startup(new Runnable()
		{
			@Override
			public void run()
			{

				stage = new Stage();

				stage.setResizable(true);

				Group root = new Group();
				Scene scene = new Scene(root, 80, 20);
				stage.setScene(scene);

				browser = new WebView();
				webEngine = browser.getEngine();
				webEngine.load("https://en.wikipedia.org/wiki");

				ObservableList<Node> children = root.getChildren();
				children.add(browser);

				jfxPanel.setScene(scene);
			}
		});
	}
	public void update(String nazwaMiasta)
	{
		PlatformImpl.startup(new Runnable()
		{
			public void run()
			{
				webEngine.load("https://en.wikipedia.org/wiki/"+nazwaMiasta);
			}
		});
	}
}
