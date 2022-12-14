package de.tinycodecrank.l4j.ui.settings;

import java.awt.event.ActionEvent;

import de.tinycodecrank.l4j.prefs.FileSettings;
import de.tinycodecrank.l4j.startup.Localizer4J;
import de.tinycodecrank.util.swing.DialogLogicTemplate;

final class BusinessLogic extends DialogLogicTemplate<Settings, FileSettings>
{
	private final FileSettings fileSettings;
	
	BusinessLogic(Settings gui, FileSettings settings)
	{
		super(gui);
		this.fileSettings = settings;
	}
	
	protected void disposeAction()
	{
		gui.if_(gui->
		{
			Localizer4J.prefs.settingsWindow.posX = gui.getX();
			Localizer4J.prefs.settingsWindow.posY = gui.getY(); 
			Localizer4J.prefs.save();
		});
	}
	
	void fileTypeChanged(ActionEvent event)
	{
		gui.if_(gui->
		{
			int fileType = gui.comboBoxFileType.getSelectedIndex();
			switch(fileType)
			{
				case (0):
					gui.panelSettingsLang.setVisible(false);
					break;
				case (1):
					gui.panelSettingsLang.setVisible(true);
					break;
				default:
					break;
			}
		});
	}
	
	void buttonOK(ActionEvent event)
	{
		gui.if_(gui->
		{
			String langFileExtension = gui.textField.getText().trim();
			if(!langFileExtension.startsWith("."))
			{
				langFileExtension = "." + langFileExtension;
			}
			
			fileSettings.usePropertyFiles = gui.comboBoxFileType.getSelectedIndex() == 0;
			fileSettings.langFileExtension = langFileExtension;
			fileSettings.localizationDelimiter = gui.textField_1.getText().trim();
			fileSettings.versionListFile = gui.chckbxSaveVersionFile.isSelected();
			Localizer4J.prefs.history.maxLength = (int) gui.spinnerHistoryLength.getModel().getValue();
			
			while(Localizer4J.prefs.history.recent.size() > Localizer4J.prefs.history.maxLength)
			{
				Localizer4J.prefs.history.recent.removeLast();
			}
			while(Localizer4J.prefs.history.recent.size() > Localizer4J.prefs.history.maxLength)
			{
				Localizer4J.prefs.history.recent.removeLast();
			}
	
			gui.dispose();
		});
	}
	
	void buttonCancle(ActionEvent event)
	{
		gui.if_(Settings::dispose);
	}
}