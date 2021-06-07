package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ControllerPagamenti implements Initializable,DataInitializable<Utente, Object>
{
	ViewDispatcher dispatcher;
	

    @FXML
    private TableView<Noleggio> tabella_pagamento;
    @FXML
    private TableColumn<Noleggio, Integer> id_pagamento;
    @FXML
    private TableColumn<Noleggio, String> veicolo_pagamento;
    @FXML
    private TableColumn<Noleggio, LocalDateTime> data_in_pagamento;
    @FXML
    private TableColumn<Noleggio, LocalDateTime> data_fi_pagamento;
    @FXML
    private TableColumn<Noleggio, Button> azioni_pagamento;
    @FXML
    private TextField text_set_km;
    @FXML
    private Button bottone_pagamento;

    @FXML
    void registraPagamento(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
	}

}

