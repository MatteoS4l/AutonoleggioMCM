package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.domain.Feedback;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreazioneFeedbackController implements Initializable, DataInitializable<Noleggio> {
	private ViewDispatcher dispatcher;
	private Veicolo veicolo;
	private Utente utente;
	private Noleggio noleggio;
	private FeedbackService feedbackservice;

	private int tmp = 0;

	@FXML
	private Label labelNome;
	@FXML
	private TextField recensioneTxtfield;
	@FXML
	private ImageView vuota1;
	@FXML
	private ImageView vuota2;
	@FXML
	private ImageView vuota3;
	@FXML
	private ImageView vuota4;
	@FXML
	private ImageView vuota5;
	@FXML
	private Button inviaBottone;
	@FXML
	private ImageView piena1;
	@FXML
	private ImageView piena2;
	@FXML
	private ImageView piena3;
	@FXML
	private ImageView piena4;
	@FXML
	private ImageView piena5;

	public CreazioneFeedbackController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		feedbackservice = factory.getFeedbackService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		piena1.setVisible(false);
		piena2.setVisible(false);
		piena3.setVisible(false);
		piena4.setVisible(false);
		piena5.setVisible(false);
	}

	@Override
	public void initializeData(Noleggio noleggio) {
		this.utente = noleggio.getCliente();
		this.veicolo = noleggio.getVeicolo();
		this.noleggio = noleggio;
		labelNome.setText(veicolo.getModello());
	}

	@FXML // INVIA FEEDBACK
	void InviaFeedback(ActionEvent event) {
		try {
			Feedback feedback = new Feedback();
			feedback.setTesto(recensioneTxtfield.getText());
			feedback.setStelle(tmp);
			feedback.setNoleggio(noleggio);
			feedbackservice.createFeedback(feedback);

			dispatcher.renderView("rilascio_feedback", utente);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	/************************ INIZIO STELLE **********************/

	@FXML
	void show1(MouseEvent event) {
		piena1.setVisible(true);
		vuota1.setVisible(false);
		piena2.setVisible(false);
		vuota2.setVisible(true);
		piena3.setVisible(false);
		vuota3.setVisible(true);
		piena4.setVisible(false);
		vuota4.setVisible(true);
		piena5.setVisible(false);
		vuota5.setVisible(true);
		tmp = 1;
	}

	@FXML
	void show2(MouseEvent event) {
		piena1.setVisible(true);
		vuota1.setVisible(false);
		piena2.setVisible(true);
		vuota2.setVisible(false);
		piena3.setVisible(false);
		vuota3.setVisible(true);
		piena4.setVisible(false);
		vuota4.setVisible(true);
		piena5.setVisible(false);
		vuota5.setVisible(true);
		tmp = 2;
	}

	@FXML
	void show3(MouseEvent event) {
		piena1.setVisible(true);
		vuota1.setVisible(false);
		piena2.setVisible(true);
		vuota2.setVisible(false);
		piena3.setVisible(true);
		vuota3.setVisible(false);
		piena4.setVisible(false);
		vuota4.setVisible(true);
		piena5.setVisible(false);
		vuota5.setVisible(true);
		tmp = 3;
	}

	@FXML
	void show4(MouseEvent event) {
		piena1.setVisible(true);
		vuota1.setVisible(false);
		piena2.setVisible(true);
		vuota2.setVisible(false);
		piena3.setVisible(true);
		vuota3.setVisible(false);
		piena4.setVisible(true);
		vuota4.setVisible(false);
		piena5.setVisible(false);
		vuota5.setVisible(true);
		tmp = 4;
	}

	@FXML
	void show5(MouseEvent event) {
		piena1.setVisible(true);
		vuota1.setVisible(false);
		piena2.setVisible(true);
		vuota2.setVisible(false);
		piena3.setVisible(true);
		vuota3.setVisible(false);
		piena4.setVisible(true);
		vuota4.setVisible(false);
		piena5.setVisible(true);
		vuota5.setVisible(false);
		tmp = 5;
	}

	/************************ FINE STELLE **********************/

}