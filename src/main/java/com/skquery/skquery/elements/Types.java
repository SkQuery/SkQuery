package com.skquery.skquery.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;

import com.skquery.skquery.annotations.AbstractTask;
import com.skquery.skquery.skript.LambdaCondition;
import com.skquery.skquery.skript.LambdaEffect;
import com.skquery.skquery.skript.Markup;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

public class Types extends AbstractTask {

	@Override
	public void run() {
		Classes.registerClass(new ClassInfo<>(Markup.class, "markup")
			.parser(new Parser<Markup>() {
				@Override
				public Markup parse(String s, ParseContext parseContext) {
					if (s.charAt(0) == '`' && s.charAt(s.length() - 1) == '`') {
						return new Markup(s.substring(1, s.length() - 1));
					} else {
						return null;
					}
				}

				@Override
				public boolean canParse(ParseContext context) {
					return true;
				}

				@Override
				public String toString(Markup markup, int i) {
					return markup.toString();
				}

				@Override
				public String toVariableNameString(Markup markup) {
					return markup.toString();
				}
			}).serializer(new Serializer<Markup>() {
				@Override
				public Fields serialize(Markup markup) throws NotSerializableException {
					Fields f = new Fields();
					f.putObject("src", markup.toString());
					return f;
				}

				@Override
				public void deserialize(Markup markup, Fields fieldContexts) throws StreamCorruptedException, NotSerializableException {
					assert false;
				}

				@Override
				protected Markup deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
					return new Markup((String) fields.getObject("src"));
				}

				@Override
				public boolean mustSyncDeserialization() {
					return true;
				}

				@Override
				public boolean canBeInstantiated(Class<? extends Markup> aClass) {
					return false;
				}

				@Override
				protected boolean canBeInstantiated() {
					return false;
				}
			}));

		Classes.registerClass(new ClassInfo<>(LambdaCondition.class, "predicate")
			.parser(new Parser<LambdaCondition>() {
				@Override
				public LambdaCondition parse(String s, ParseContext parseContext) {
					if (s.length() > 2 && s.charAt(0) == '[' && s.charAt(s.length() - 1) == ']') {
						Condition e = (Condition) Condition.parse(s.substring(1, s.length() - 1), null);
						if (e == null) {
							Skript.error(s + " is not a valid lambda statement.", ErrorQuality.SEMANTIC_ERROR);
						} else {
							return new LambdaCondition(e);
						}
					}
					return null;
				}

				@Override
				public boolean canParse(ParseContext context) {
					return true;
				}

				@Override
				public String toString(LambdaCondition lambdaCondition, int i) {
					return lambdaCondition.toString();
				}

				@Override
				public String toVariableNameString(LambdaCondition lambdaCondition) {
					return lambdaCondition.toString();
				}
			}));

		Classes.registerClass(new ClassInfo<>(LambdaEffect.class, "lambda")
			.parser(new Parser<LambdaEffect>() {
				@Override
				public LambdaEffect parse(String s, ParseContext parseContext) {
					if (s.length() > 3 && s.charAt(0) == '[' && s.charAt(s.length() - 1) == ']') {
						if ("void".equals(s.substring(1, s.length() - 1))) return new LambdaEffect(true);
						Effect e = Effect.parse(s.substring(1, s.length() - 1), null);
						if (e == null) {
							Skript.error(s + " is not a valid lambda statement.", ErrorQuality.SEMANTIC_ERROR);
						} else {
							return new LambdaEffect(e);
						}
					}
					return null;
				}

				@Override
				public boolean canParse(ParseContext context) {
					return true;
				}

				@Override
				public String toString(LambdaEffect lambdaEffect, int i) {
					return lambdaEffect.toString();
				}

				@Override
				public String toVariableNameString(LambdaEffect lambdaEffect) {
					return lambdaEffect.toString();
				}
			}));
	}

}
