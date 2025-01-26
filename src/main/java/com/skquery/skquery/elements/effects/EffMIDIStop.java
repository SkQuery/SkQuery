package com.skquery.skquery.elements.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Examples;
import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.note.MidiUtil;

import org.bukkit.event.Event;

@Name("Stop MIDI")
@Description("Stops a midi file that is playing.")
@Examples({"on join:",
		"	play midi \"login\" to player",
		"	wait 5 seconds",
		"	stop midi \"login\""})
@Patterns("stop midi[s] [[with] id] %strings%")
public class EffMIDIStop extends Effect {

	private Expression<String> IDs;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int markedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		IDs = (Expression<String>) expressions[0];
		return true;
	}
	
	@Override
	protected void execute(Event event) {
		for (String track : IDs.getArray(event)) {
			if (track == null)
				continue;
			if (MidiUtil.isPlaying(track))
				MidiUtil.stop(track);
		}
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "stop midi " + IDs.toString(event, debug);
	}

}
