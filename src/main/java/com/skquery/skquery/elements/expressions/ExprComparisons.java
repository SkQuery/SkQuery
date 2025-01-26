package com.skquery.skquery.elements.expressions;

import org.bukkit.event.Event;
import org.skriptlang.skript.lang.comparator.Comparators;
import org.skriptlang.skript.lang.comparator.Relation;

import com.skquery.skquery.annotations.Patterns;
import com.skquery.skquery.util.Collect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.LiteralUtils;
import ch.njol.util.Kleenean;

@Patterns({"%object%[ ]===[ ]%object%",
		"%object%[ ]==[ ]%object%",
		"%object%[ ]\\>[ ]%object%",
		"%object%[ ]\\<[ ]%object%",
		"%object%[ ]\\>=[ ]%object%",
		"%object%[ ]\\<=[ ]%object%"})
public class ExprComparisons extends SimpleExpression<Boolean> {

	private Expression<?> first, second;
	int match;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		first = exprs[0];
		second = exprs[1];
		match = matchedPattern;
		if (first instanceof Variable && second instanceof Variable) {
			first = first.getConvertedExpression(Object.class);
			second = second.getConvertedExpression(Object.class);
		} else if (first instanceof Literal<?> && second instanceof Literal<?>) {
			first = first.getConvertedExpression(Object.class);
			second = second.getConvertedExpression(Object.class);
			if (first == null || second == null) return false;
		} else {
			if (first instanceof Literal<?>) {
				first = first.getConvertedExpression(second.getReturnType());
				if (first == null) return false;
			} else if (second instanceof Literal<?>) {
				second = second.getConvertedExpression(first.getReturnType());
				if (second == null) return false;
			}
			if (first instanceof Variable) {
				first = first.getConvertedExpression(second.getReturnType());
			} else if (second instanceof Variable) {
				second = second.getConvertedExpression(first.getReturnType());
			}
			assert first != null && second != null;
		}
		if (LiteralUtils.hasUnparsedLiteral(first))
			first = LiteralUtils.defendExpression(first);
		if (LiteralUtils.hasUnparsedLiteral(second))
			second = LiteralUtils.defendExpression(second);
		return LiteralUtils.canInitSafely(first, second);
	}

	@Override
	protected Boolean[] get(Event e) {
		Relation r = Comparators.compare(first.getSingle(e), second.getSingle(e));
		switch (match) {
			case 0:
				return Collect.asArray(Relation.EQUAL.isImpliedBy(r));
			case 1:
				return Collect.asArray((first.getSingle(e) + "").equals(second.getSingle(e) + ""));
			case 2:
				return Collect.asArray(Relation.GREATER.isImpliedBy(r));
			case 3:
				return Collect.asArray(Relation.SMALLER.isImpliedBy(r));
			case 4:
				return Collect.asArray(Relation.GREATER_OR_EQUAL.isImpliedBy(r));
			case 5:
				return Collect.asArray(Relation.SMALLER_OR_EQUAL.isImpliedBy(r));
		}
		return null;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "compare";
	}

}
