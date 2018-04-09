/**
 *
 *  @author Han Cezary S15831
 *
 */

package zad1;

import javax.swing.JFrame;

public class Main {
  public static void main(String[] args) {
    Service s = new Service("Poland");
    String weatherJson = s.getWeather("Warsaw");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();
    System.out.println(weatherJson);
    JFrame gui = new GUI();
    gui.setVisible(true);
  }
}
