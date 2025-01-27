package com.skquery.skquery.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Examples;
import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.note.MidiUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;

@Name("Play MIDI")
@Description("Plays a file with the extention .mid to a player.")
@Examples("on join:;->play midi \"login\" to player")
@Patterns({"play midi [from] %string% (for|to) %players% [at [tempo] %-number%] [with id %-string%]", "play midi from (website|link) %string% (for|to) %players% [at [tempo] %-number%] [with id %-string%]"})
public class EffMIDI extends Effect {

	private Expression<String> midi, ID;
	private Expression<Player> players;
	private Expression<Number> tempo;
	private boolean url;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		midi = (Expression<String>) expressions[0];
		players = (Expression<Player>) expressions[1];
		tempo = (Expression<Number>) expressions[2];
		ID = (Expression<String>) expressions[3];
		url = matchedPattern == 1;
		return true;
	}

	@Override
	protected void execute(Event event) {
		String track = midi.getSingle(event);
		if (track == null)
			return;
		File file = new File(Skript.getInstance().getDataFolder(), Skript.SCRIPTSFOLDER + File.separator + track + ".mid");
		if (!file.exists()) {
			file = new File(track);
			if (!file.exists() && !file.getName().endsWith(".mid")) {
				Bukkit.getLogger().warning("Could not find midi file " + track + ".mid");
				return;
			}
		}
		Number tempo = 1;
		if (this.tempo != null)
			tempo = this.tempo.getSingle(event);
		Player[] players = this.players.getArray(event);
		if (players == null)
			return;
		String ID = track;
		if (this.ID != null)
			ID = this.ID.getSingle(event);
		try {
			if (url) {
				URL url = new URL(track);
				MidiUtil.playMidi(url.openStream(), tempo.floatValue(), ID, players);
			} else {
				MidiUtil.playMidi(file, tempo.floatValue(), ID, players);
			}
		} catch (InvalidMidiDataException | IOException e) {
			Skript.exception(e, "Could not play midi file " + track);
		}
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "play midi " + midi.toString(event, debug) + " to " + players.toString(event, debug);
	}

}
