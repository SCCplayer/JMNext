package testbereich;

import gui.SoundButton;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import data.SoundButtonProperties;

public class TransferableSoundButtonProperties implements Transferable {
	SoundButtonProperties sbp;

	protected static DataFlavor soundButtonPropertiesFlavor = new DataFlavor(
			SoundButton.class, "A SoundButtonProperties Object");

	protected static DataFlavor[] supportedFlavors = {
			soundButtonPropertiesFlavor, DataFlavor.stringFlavor, };

	public TransferableSoundButtonProperties(SoundButtonProperties soundButtonProperties) {
		sbp = soundButtonProperties;
	}

	public DataFlavor[] getTransferDataFlavors() {
		return supportedFlavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		if (flavor.equals(soundButtonPropertiesFlavor)
				|| flavor.equals(DataFlavor.stringFlavor)) {
			return true;
		} else if (flavor.equals(DataFlavor.stringFlavor)) {
			return false;
		} else {
			return false;
		}
	}

	public static DataFlavor getSoundButtonPropertiesFlavor() {
		return soundButtonPropertiesFlavor;
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException {
		if (flavor.equals(soundButtonPropertiesFlavor))
			return sbp;
		else
			throw new UnsupportedFlavorException(flavor);
	}
}