package ehu.isad;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class ComboBoxExperiments extends Application  {

    private String coinmarket(String txanpon) {
        String inputLine = "";
        URL url;

        try {
            url = new URL("https://api.gdax.com/products/" + txanpon + "-eur/ticker");
            URLConnection yc = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            inputLine = br.readLine();
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputLine;
    }

    private float prezioaLortu(String mota) {
        String emaitza = this.coinmarket(mota);
        Gson gson = new Gson();
        Txanpona txanpon = gson.fromJson(emaitza, Txanpona.class);
        return txanpon.price;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Txanponak");

        ComboBox comboBox = new ComboBox();

        comboBox.getItems().add("BTC");
        comboBox.getItems().add("ETH");
        comboBox.getItems().add("LTC");
        comboBox.setEditable(true);

        Label label = new Label(comboBox.getValue() + "=" + this.prezioaLortu((String) comboBox.getValue()) + "EUR");

        comboBox.setOnAction(event -> label.setText("1 " + comboBox.getValue() + "=" + this.prezioaLortu((String) comboBox.getValue()) + "€"));

        VBox vbox = new VBox(label, comboBox);

        Scene scene = new Scene(vbox, 200, 120);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
