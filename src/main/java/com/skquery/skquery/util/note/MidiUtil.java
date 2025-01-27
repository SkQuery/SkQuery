package com.skquery.skquery.util.note;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import ch.njol.skript.Skript;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class MidiUtil {

	private static HashMap<String, Sequencer> playing = new HashMap<>();

	private static void playMidi(Sequence sequence, float tempo, String ID, Player... listeners) {
		try {
			Sequencer sequencer = MidiSystem.getSequencer(false);
			sequencer.setSequence(sequence);
			sequencer.open();

			// slow it down just a bit
			sequencer.setTempoFactor(tempo);
			NoteBlockReceiver reciever = new NoteBlockReceiver(ID, listeners);
			sequencer.getTransmitter().setReceiver(reciever);
			sequencer.start();
			playing.put(ID.toLowerCase(), sequencer);
		} catch (MidiUnavailableException | InvalidMidiDataException e) {
			Skript.exception(e, "Error attempting to play a midi file");
		}
	}

	public static void playMidi(File file, float tempo, String ID, Player... listeners) throws InvalidMidiDataException, IOException {
		playMidi(MidiSystem.getSequence(file), tempo, ID, listeners);
	}

	public static void playMidi(InputStream stream, float tempo, String ID, Player... listeners) throws InvalidMidiDataException, IOException {
		playMidi(MidiSystem.getSequence(stream), tempo, ID, listeners);
	}

	public static void playMidiQuietly(File file, float tempo, String ID, Player... listeners) throws InvalidMidiDataException, IOException {
		playMidi(file, tempo, ID, listeners);
	}

	public static void playMidiQuietly(InputStream stream, float tempo, String ID, Player... listeners) throws InvalidMidiDataException, IOException {
		playMidi(stream, tempo, ID, listeners);
	}

	public static void playMidiQuietly(File file, String ID, Player... listeners) throws InvalidMidiDataException, IOException {
		playMidiQuietly(file, 1.0f, ID, listeners);
	}

	public static void playMidiQuietly(InputStream stream, String ID, Player... listeners) throws InvalidMidiDataException, IOException {
		playMidiQuietly(stream, 1.0f, ID, listeners);
	}

	public static boolean isPlaying(String ID) {
		return playing.containsKey(ID);
	}

	public static void stop(String ID) {
		String id = ID.toLowerCase();
		if (playing.containsKey(id)) {
			Sequencer sequencer = playing.get(id);
			try {
				sequencer.getReceiver().close();
			} catch (MidiUnavailableException e) {}
			sequencer.stop();
			sequencer.close();
			playing.remove(id);
		}
	}

	// provided by github.com/sk89q/craftbook

	private static final byte[] instruments = {
            0, 0, 0, 0, 0, 0, 0,11, // 0-7
            6, 6, 6, 6, 9, 9,15,11, // 8-15
            10,5, 5,10,10,10,10,10, // 16-23
            5, 5, 5, 5, 5, 5, 5, 5, // 24-31
            1, 1, 1, 1, 1, 1, 1, 1, // 32-39
            0,10,10, 1, 0, 0, 0, 4, // 40-47
            0, 0, 0, 0, 8, 8, 8,12, // 48-55
            8,14,14,14,14,14, 8, 8, // 56-63
            8, 8, 8,14,14, 8, 8, 8, // 64-71
            8, 8, 8, 8,14, 8, 8, 8, // 72-79
            8,14, 8, 8, 5, 8,12, 1, // 80-87
            1, 0, 0, 8, 0, 0, 0, 0, // 88-95
            0, 0, 7, 0, 0, 0, 0,12, // 96-103
            11,11,3, 3, 3,14,10, 6, // 104-111
            6, 3, 3, 2, 2, 2, 6, 5, // 112-119
            1, 1, 1,13,13, 2, 4, 7, // 120-127
    };

	private static final byte[] percussion = {
			9, 6, 4, 4, 3, 2, 3, 2, //40 - Electric Snare
			2, 2, 2, 2, 2, 2, 2, 2, //48 - Hi Mid Tom
			7, 2, 7, 7, 6, 3, 7, 6, //56 - Cowbell
			7, 3, 7, 2, 2, 3, 3, 3, //64 - Low Conga
			2, 2, 6, 6, 2, 2, 0, 0, //72 - Long Whistle
			3, 3, 3, 3, 3, 3, 5, 5, //80 - Open Cuica
			15, 15,				    //82 - Open Triangle
	};

	private static byte bytePercussion(int patch) {
		int i = patch - 33;
		if (i < 0 || i >= percussion.length)
			return 1;
		return (byte) percussion[i];
	}

	private static byte byteInstrument(int patch) {
		if (patch < 0 || patch >= instruments.length)
			return 0;
		return (byte) instruments[patch];
	}

	public static Sound patchToPercussion(int patch) {
		return Instrument.fromByte(bytePercussion(patch)).getSound();
	}

	public static Sound patchToInstrument(int patch) {
		return Instrument.fromByte(byteInstrument(patch)).getSound();
	}

}
