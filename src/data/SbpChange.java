package data;

import gui.SoundButton;

public class SbpChange {
	SoundButton sbLastUpdate;
	SoundButtonProperties sbpLastUpdate = new SoundButtonProperties();

	public SbpChange(SoundButton sbUpdate, SoundButtonProperties sbpUpdate) {
		sbLastUpdate = sbUpdate;
		sbpLastUpdate.copyProperties(sbpUpdate);
	}

	public SoundButton getSbLastUpdate() {
		return sbLastUpdate;
	}

	public void setSbLastUpdate(SoundButton sbLastUpdate) {
		this.sbLastUpdate = sbLastUpdate;
	}

	public SoundButtonProperties getSbpLastUpdate() {
		return sbpLastUpdate;
	}

	public void setSbpLastUpdate(SoundButtonProperties sbpLastUpdate) {
		this.sbpLastUpdate = sbpLastUpdate;
	}

}
