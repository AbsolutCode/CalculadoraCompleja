package dad.javafx.CalculadoraCompleja;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraCompleja extends Application {
	
	
	// modelo
	
	private Complejo primerNumero = new Complejo();
	private Complejo segundoNumero = new Complejo();
	private Complejo tercerNumero = new Complejo();
	private Complejo cuartoNumero = new Complejo();
	private Complejo quintoNumero = new Complejo();
	private Complejo sextoNumero = new Complejo();
	private StringProperty operador = new SimpleStringProperty();
	
	// vista
	
	private TextField primerNumeroText;
	private TextField segundoNumeroText;
	private TextField tercerNumeroText;
	private TextField cuartoNumeroText;
	private TextField quintoNumeroText;
	private TextField sextoNumeroText;
	private ComboBox<String> operadorCombo;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		primerNumeroText = new TextField();
		primerNumeroText.setPrefColumnCount(4);
		
		segundoNumeroText = new TextField();
		segundoNumeroText.setPrefColumnCount(4);
		
		tercerNumeroText = new TextField();
		tercerNumeroText.setPrefColumnCount(4);
		
		cuartoNumeroText = new TextField();
		cuartoNumeroText.setPrefColumnCount(4);
		
		quintoNumeroText = new TextField();
		quintoNumeroText.setPrefColumnCount(4);
		quintoNumeroText.setDisable(true);
		
		sextoNumeroText = new TextField();
		sextoNumeroText.setPrefColumnCount(4);
		sextoNumeroText.setDisable(true);
		
		operadorCombo = new ComboBox<String>();
		operadorCombo.getItems().addAll("+", "-", "*", "/");
		
		HBox arriba = new HBox(4, primerNumeroText, new Label("+"), segundoNumeroText, new Label("i"));
		HBox medio = new HBox(4, tercerNumeroText, new Label("+"), cuartoNumeroText, new Label("i"));
		HBox abajo = new HBox(4, quintoNumeroText, new Label("+"), sextoNumeroText, new Label("i"));
		VBox primerVBox = new VBox(1, operadorCombo);
		primerVBox.setAlignment(Pos.CENTER);
		VBox segundoVBox = new VBox(4, arriba, medio, new Separator(), abajo);
		segundoVBox.setAlignment(Pos.CENTER);
		HBox root = new HBox(2, primerVBox, segundoVBox);
		root.setAlignment(Pos.CENTER);
		
		Scene escena = new Scene(root, 320, 200);

		primaryStage.setScene(escena);
		primaryStage.setTitle("Calculadora Compleja");
		primaryStage.show();

		// bindeos
		
		Bindings.bindBidirectional(primerNumeroText.textProperty(), primerNumero.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(segundoNumeroText.textProperty(), segundoNumero.imaginarioProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(tercerNumeroText.textProperty(), tercerNumero.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(cuartoNumeroText.textProperty(), cuartoNumero.imaginarioProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(quintoNumeroText.textProperty(), quintoNumero.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(sextoNumeroText.textProperty(), sextoNumero.imaginarioProperty(), new NumberStringConverter());
		operador.bind(operadorCombo.getSelectionModel().selectedItemProperty());
		
		// listeners
		
		operador.addListener((o, ov, nv) -> onOperadorChanged(nv));

		operadorCombo.getSelectionModel().selectFirst();
		
	}
	
	private void onOperadorChanged(String nv) {

		switch(nv) {
		case "+":
			quintoNumero.realProperty().bind(primerNumero.realProperty().add(tercerNumero.realProperty()));
			sextoNumero.imaginarioProperty().bind(segundoNumero.imaginarioProperty().add(cuartoNumero.imaginarioProperty()));
			break;
		case "-":
			quintoNumero.realProperty().bind(primerNumero.realProperty().subtract(tercerNumero.realProperty()));
			sextoNumero.imaginarioProperty().bind(segundoNumero.imaginarioProperty().subtract(cuartoNumero.imaginarioProperty()));
			break;
		case "*": 
			quintoNumero.realProperty().bind(primerNumero.realProperty().multiply(tercerNumero.realProperty()).subtract(segundoNumero.imaginarioProperty().multiply(cuartoNumero.imaginarioProperty())));
			sextoNumero.imaginarioProperty().bind(primerNumero.realProperty().multiply(cuartoNumero.imaginarioProperty()).add(segundoNumero.imaginarioProperty().multiply(tercerNumero.realProperty())));
			break;
		case "/":
			quintoNumero.realProperty().bind(((primerNumero.realProperty().multiply(tercerNumero.realProperty())).add(segundoNumero.imaginarioProperty().multiply(cuartoNumero.imaginarioProperty()))).divide((tercerNumero.realProperty().multiply(tercerNumero.realProperty())).add((cuartoNumero.imaginarioProperty()).multiply(cuartoNumero.imaginarioProperty()))));
			sextoNumero.imaginarioProperty().bind(((segundoNumero.imaginarioProperty().multiply(tercerNumero.realProperty())).subtract(primerNumero.realProperty().multiply(cuartoNumero.imaginarioProperty()))).divide((tercerNumero.realProperty().multiply(tercerNumero.realProperty())).add((cuartoNumero.imaginarioProperty()).multiply(cuartoNumero.imaginarioProperty()))));
			break;
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
