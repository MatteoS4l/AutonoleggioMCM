package it.univaq.disim.oop.MCMprogetto.view;
import it.univaq.disim.oop.MCMprogetto.controller.DataInitializable;
import javafx.scene.Parent;

public class View<T,S>
{
	
	private Parent view;
	private DataInitializable<T,S> controller;
	
	public View(Parent view, DataInitializable<T,S> controller) {
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

	public DataInitializable<T,S> getController() {
		return controller;
	}
	
	public void setController(DataInitializable<T,S> controller) {
		this.controller = controller;
	}
	
	

}