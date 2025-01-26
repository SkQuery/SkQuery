package com.skquery.skquery.util.note;

import org.bukkit.Sound;

import com.skquery.skquery.util.minecraft.Utils;

public enum Instrument {

	// 1.8 +
	PIANO(0, "BLOCK_NOTE_BLOCK_HARP", "BLOCK_NOTE_HARP", "NOTE_PIANO"),
	BASS(1, "BLOCK_NOTE_BLOCK_BASS", "BLOCK_NOTE_BASS", "NOTE_BASS"),
	SNARE_DRUM(2, "BLOCK_NOTE_BLOCK_SNARE", "BLOCK_NOTE_SNARE", "NOTE_SNARE_DRUM"),
	STICKS(3, "BLOCK_NOTE_BLOCK_HAT", "BLOCK_NOTE_HAT", "NOTE_STICKS"),
	BASE_DRUM(4, "BLOCK_NOTE_BLOCK_BASEDRUM", "BLOCK_NOTE_BASEDRUM", "NOTE_BASS_DRUM"),
	GUITAR(5, "BLOCK_NOTE_BLOCK_GUITAR", "BLOCK_NOTE_GUITAR", "NOTE_BASS_GUITAR"),

	// 1.12+
	BELL(6, "BLOCK_NOTE_BLOCK_BELL", "BLOCK_NOTE_BELL", "NOTE_PIANO"),
	CHIME(7, "BLOCK_NOTE_BLOCK_CHIME", "BLOCK_NOTE_CHIME", "NOTE_PIANO"),
	FLUTE(8, "BLOCK_NOTE_BLOCK_FLUTE", "BLOCK_NOTE_FLUTE", "NOTE_PIANO"),
	XYLOPHONE(9, "BLOCK_NOTE_BLOCK_XYLOPHONE", "BLOCK_NOTE_XYLOPHONE", "NOTE_STICKS"),
	PLING(10, "BLOCK_NOTE_BLOCK_PLING", "BLOCK_NOTE_PLING", "NOTE_PIANO"),

	// 1.14.2+ this essentially dumps 1.8 support.
	BANJO(11, "BLOCK_NOTE_BLOCK_BANJO", "BLOCK_NOTE_BLOCK_GUITAR", "BLOCK_NOTE_BASS_GUITAR"),
	BIT(12, "BLOCK_NOTE_BLOCK_BIT", "BLOCK_NOTE_BLOCK_PLING", "BLOCK_NOTE_PIANO"),
	COW_BELL(13, "BLOCK_NOTE_BLOCK_COW_BELL", "BLOCK_NOTE_BLOCK_BELL", "BLOCK_NOTE_BELL"),
	DIDGERIDOO(14, "BLOCK_NOTE_BLOCK_DIDGERIDOO", "BLOCK_NOTE_BLOCK_BASS", "BLOCK_NOTE_BASS"),
	IRON_XYLOPHONE(15, "BLOCK_NOTE_BLOCK_IRON_XYLOPHONE", "BLOCK_NOTE_BLOCK_XYLOPHONE", "BLOCK_NOTE_XYLOPHONE");

	private final int pitch;
	private Sound sound;

	/*
	 * sound - The current sound name for the latest version.
	 * fallback - The 1.12 sound name.
	 * old - the 1.8-1.12 sound name.
	 */
	Instrument(int pitch, String sound, String fallback, String old) {
		this.sound = Utils.soundAttempt(sound, fallback);
		if (sound == null)
			this.sound = Utils.soundAttempt(old, fallback);
		this.pitch = pitch;
	}

	public static Instrument fromByte(byte instrument) {
		switch (instrument) {
			case 1:
				return BASS;
			case 2:
				return SNARE_DRUM;
			case 3:
				return STICKS;
			case 4:
				return BASE_DRUM;
			case 5:
				return GUITAR;
			case 6:
				return BELL;
			case 7:
				return CHIME;
			case 8:
				return FLUTE;
			case 9:
				return XYLOPHONE;
			case 10:
				return PLING;
			case 11:
				return BANJO;
			case 12:
				return BIT;
			case 13:
				return COW_BELL;
			case 14:
				return DIDGERIDOO;
			case 15:
				return IRON_XYLOPHONE;
			case 0:
			default:
				return PIANO;
		}
	}

	public Sound getSound() {
		return sound;
	}

	public byte getByte() {
		return (byte) pitch;
	}

}
