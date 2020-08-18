package org.mineacademy.fo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.SerializeUtil;
import org.mineacademy.fo.SerializeUtil.SerializeFailedException;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.collection.SerializedMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An elegant way to find {variables} and replace them.
 * <p>
 * Use {@link #find(String...)} method to find {} variables such as find("health") and then
 * replace them with {@link #replace(Object...)} with the objects such as replace(20)
 * <p>
 * Something like {@link String#format(String, Object...)} but more useful for Minecraft
 */
@AllArgsConstructor
public final class Replacer {

	/**
	 * The internal deliminer used to separate variables
	 */
	private final static String DELIMITER = "%D3L1M1T3R%";

	/**
	 * The messages we are replacing variables in
	 */
	private final String[] messages;

	// ------------------------------------------------------------------------------------------------------------
	// Temporary variables
	// ------------------------------------------------------------------------------------------------------------

	/**
	 * The variables we are looking for to replace
	 */
	private String[] variables;

	/**
	 * The finished replaced message
	 */
	@Getter
	private String[] replacedMessage;

	/**
	 * Create a new instance for the given messages
	 *
	 * @param messages
	 */
	private Replacer(String... messages) {
		this.messages = messages;
	}

	/**
	 * Find the {} variables
	 *
	 * @param variables
	 * @return
	 *
	 * @deprecated use {@link Replacer#replaceArray(String, Object...)} for simplicity
	 */
	@Deprecated
	public Replacer find(String... variables) {
		this.variables = variables;

		return this;
	}

	/**
	 * Attempts to replace key:value pairs automatically
	 * <p>
	 * See {@link SerializedMap#ofArray(Object...)}
	 *
	 * @deprecated use {@link Replacer#replaceArray(String, Object...)} for simplicity
	 *
	 * @param associativeArray
	 * @return
	 */
	@Deprecated
	public String replaceAll(Object... associativeArray) {
		final SerializedMap map = SerializedMap.ofArray(associativeArray);

		final List<String> find = new ArrayList<>();
		final List<Object> replaced = new ArrayList<>();

		for (final Map.Entry<String, Object> entry : map.entrySet()) {
			find.add(entry.getKey());
			replaced.add(entry.getValue());
		}

		find(find.toArray(new String[find.size()]));
		replace(replaced.toArray(new Object[replaced.size()]));

		return getReplacedMessageJoined();
	}

	/**
	 * Replaces stored variables with their counterparts
	 * <p>
	 * Call this after {@link #find(String...)}!
	 *
	 * @deprecated use {@link Replacer#replaceArray(String, Object...)} for simplicity
	 *
	 * @param replacements
	 * @return
	 */
	@Deprecated
	public Replacer replace(Object... replacements) {
		Valid.checkNotNull(variables, "call find() first");
		Valid.checkBoolean(replacements.length == variables.length, "Variables " + variables.length + " != replacements " + replacements.length);

		// Join and replace as 1 message for maximum performance
		String message = StringUtils.join(messages, DELIMITER);

		for (int i = 0; i < variables.length; i++) {
			String find = variables[i];

			{ // Auto insert brackets
				if (!find.startsWith("{"))
					find = "{" + find;

				if (!find.endsWith("}"))
					find = find + "}";
			}

			final Object rep = i < replacements.length ? replacements[i] : null;

			// Convert it into a human readable string
			String serialized;

			try {
				serialized = Objects.toString(SerializeUtil.serialize(rep));

			} catch (final SerializeFailedException ex) {
				// ignore and convert to string
				serialized = rep.toString();
			}

			message = message.replace(find, rep != null ? serialized : "");
		}

		this.replacedMessage = message.split(DELIMITER);

		return this;
	}

	/**
	 * Sends the finished message with variables replaced to the player
	 *
	 * @deprecated use {@link Replacer#replaceArray(String, Object...)} for simplicity
	 *
	 * @param recipient
	 */
	@Deprecated
	public void tell(CommandSender recipient) {
		Valid.checkNotNull(replacedMessage, "Replaced message not yet set, use find() and replace()");

		Common.tell(recipient, replacedMessage);
	}

	/**
	 * Return the replaced message joined with spaces
	 *
	 * @deprecated use {@link Replacer#replaceArray(String, Object...)} for simplicity
	 *
	 * @return
	 */
	@Deprecated
	public String getReplacedMessageJoined() {
		Valid.checkNotNull(replacedMessage, "Replaced message not yet set, use find() and replace()");

		return StringUtils.join(replacedMessage, " ");
	}

	// ------------------------------------------------------------------------------------------------------------
	// Static access
	// ------------------------------------------------------------------------------------------------------------

	/**
	 * Create a new replacer instance in which you can easily find and replace variables and send the message to player
	 *
	  * @deprecated use {@link Replacer#replaceArray(String, Object...)} for simplicity
	 *
	 * @param messages
	 * @return
	 */
	@Deprecated
	public static Replacer of(String... messages) {
		return new Replacer(messages);
	}

	/**
	 * Replace all variables in the {@link SerializedMap#ofArray(Object...)} format
	 * adding {} to them if they do not contain it already
	 *
	 * @param message
	 * @param associativeArray
	 * @return
	 */
	public static String replaceArray(String message, Object... replacements) {
		final SerializedMap map = SerializedMap.ofArray(replacements);

		return replaceVariables(message, map);
	}

	/**
	 * Replace all variables in the {@link SerializedMap#ofArray(Object...)} format
	 * adding {} to them if they do not contain it already
	 *
	 * @param list
	 * @param replacements
	 * @return
	 */
	public static List<String> replaceArray(List<String> list, Object... replacements) {

		// Create a copy, do not alter existing list
		list = new ArrayList<>(list);

		final SerializedMap map = SerializedMap.ofArray(replacements);

		for (int i = 0; i < list.size(); i++) {
			String message = list.get(i);

			message = replaceVariables(message, map);
			list.set(i, message);
		}

		return list;
	}

	private static String replaceVariables(String message, SerializedMap variables) {
		for (final Entry<String, Object> replacement : variables.entrySet()) {
			String key = replacement.getKey();

			key = key.charAt(0) != '{' ? "{" + key : key;
			key = key.charAt(key.length() - 1) != '}' ? key + "}" : key;

			message = message.replace(key, Common.simplify(replacement.getValue()));
		}

		return message;
	}
}
