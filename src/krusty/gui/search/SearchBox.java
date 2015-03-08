package krusty.gui.search;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import krusty.controllers.CookieController;
import krusty.controllers.PalletController;


public class SearchBox extends GridPane {

	private PalletController palletController;
	private CookieController cookieController;

	private ComboBox<String> selectedCookie;
	private DatePicker fromDatePicker;
	private DatePicker toDatePicker;
	private CheckBox isBlocked;

	public SearchBox(PalletController palletController, CookieController cookieController) {
		this.palletController = palletController;
		this.cookieController = cookieController;

		init();
	}

	private void init() {

		this.setAlignment(Pos.CENTER);
		this.setHgap(5);

		Label fromDateLabel = new Label("From date");
		fromDatePicker = new DatePicker();
		fromDatePicker.setMaxSize(110, 40);

		this.add(fromDateLabel, 0, 0);
		this.add(fromDatePicker, 0, 1);

		Label toDateLabel = new Label("To date");
		toDatePicker = new DatePicker();
		toDatePicker.setMaxSize(110, 40);

		this.add(toDateLabel, 1, 0);
		this.add(toDatePicker, 1, 1);


		Label cookieLabel = new Label("Cookie");

		ObservableList<String> obList = FXCollections.observableArrayList(cookieController.getObservableList());
		obList.add(0, "Any cookie");

		selectedCookie = new ComboBox<String>(obList);
		selectedCookie.getSelectionModel().select("Any cookie");

		this.add(cookieLabel, 2, 0);
		this.add(selectedCookie, 2, 1);

		Label isBlockedLabel = new Label("Blocked");
		isBlocked = new CheckBox();

		this.add(isBlockedLabel, 3, 0);
		this.add(isBlocked, 3, 1);

		Button clearButton = new Button("Clear");
		clearButton.setCancelButton(true);
		clearButton.setOnAction(new ClearButtonEventHandler());
		this.add(clearButton, 4, 0);

		Button button = new Button("Search");
		button.setDefaultButton(true);
		button.setOnAction(new SearchButtonEventHandler());
		this.add(button, 4, 1);

	}

	private class SearchButtonEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String cookie = selectedCookie.getValue();
			if (cookie.equals("Any cookie")) {
				cookie = null;
			}
			palletController.search(cookie, fromDatePicker.getValue(), toDatePicker.getValue());
		}
	}

	private class ClearButtonEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			selectedCookie.getSelectionModel().select(0);
			toDatePicker.setValue(null);
			fromDatePicker.setValue(null);

			palletController.resetSearch();
		}
	}


}