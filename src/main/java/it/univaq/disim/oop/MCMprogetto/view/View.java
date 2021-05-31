package it.univaq.disim.oop.MCMprogetto.view;
import it.univaq.disim.oop.MCMprogetto.controller.DataInitializable;
import javafx.scene.Parent;

public class View<T,E>
{
	
	private Parent view;
	private DataInitializable<T,E> controller;
	
	public View(Parent view, DataInitializable<T,E> controller) {
		super();
		this.view = view;
		this.controller = controller;
	}
	
	public Parent getView() {
		return view;
	}
	public void setView(Parent view) {
		this.view = view;
	}

	public DataInitializable<T,E> getController() {
		return controller;
	}
	
	public void setController(DataInitializable<T,E> controller) {
		this.controller = controller;
	}
	
	

}