package com.skquery.skquery.elements.conditions;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Checker;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.skquery.skquery.annotations.Description;
import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.note.MidiUtil;

@Name("Midi is playing")
@Description("Checks whether or not a midi ID is playing.")
@Patterns({"midi [ids] %strings% (are|is) playing", "midi [ids] %strings% (are|is)(n't| not) playing"})
public class CondMidi extends Condition {

	private Expression<String> ID;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		ID = (Expression<String>) expressions[0];
		setNegated(matchedPattern == 1);
		return true;
	}
	
	@Override
	public boolean check(Event event) {
		return ID.check(event, new Checker<String>() {
			@Override
			public boolean check(String title) {
				return MidiUtil.isPlaying(title);
			}
		}, isNegated());
	}
	
	@Override
	public String toString(Event event, boolean debug) {
		return "midi " + ID.toString(event, debug) + " is playing";
	}

}
