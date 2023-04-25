package org.jcryptool.visual.signalencryption.util;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.osgi.util.NLS;
import org.jcryptool.visual.signalencryption.ui.Messages;

public class Templating {

    private static final Pattern TEMPLATE = Pattern.compile("\\{[a-zA-Z0-9_]+\\}");
    private static final Map<String, String> BOB_TOKENS = Map.of(
                "{Name}", Messages.Name_Bob,
                "{NameGenitive}", Messages.Name_BobGenitive,
                "{OtherName}", Messages.Name_Alice,
                "{OtherNameGenitive}", Messages.Name_AliceGenitive,
                "{Pronoun}", capitalize(Messages.Name_BobPronoun),
                "{pronoun}", Messages.Name_BobPronoun,
                "{Possessive}", capitalize(Messages.Name_BobPronounPossessive),
                "{possessive}", Messages.Name_BobPronounPossessive
            );

    private static final Map<String, String> ALICE_TOKENS = Map.of(
                "{Name}", Messages.Name_Alice,
                "{NameGenitive}", Messages.Name_AliceGenitive,
                "{OtherName}", Messages.Name_Bob,
                "{OtherNameGenitive}", Messages.Name_BobGenitive,
                "{Pronoun}", capitalize(Messages.Name_AlicePronoun),
                "{pronoun}", Messages.Name_AlicePronoun,
                "{Possessive}", capitalize(Messages.Name_AlicePronounPossessive),
                "{possessive}", Messages.Name_AlicePronounPossessive
            );

    public static String forBob(String msg) {
        return apply(msg, BOB_TOKENS);
    }

    public static String forAlice(String msg) {
        return apply(msg, ALICE_TOKENS);
    }

    /**
     * Replace templates in an Eclipse-Message-Bundle environment by named templates.
     * <p/>
     * <b>messages.properties</b>
     * <pre>
     * Name=Peter
     * Possession=my
     * msg=Hello this is {Name} and this is {Possession} house.
     * </pre>
     * <i>Output:</i> "Hello this is Peter and this is my house"
     * <p/>
     * <b>Note</b> that the given template key-value pairs must BOTH match the properties file!<br>
     * <b>Note</b> that the given template key MUST be surrounded by spaces!
     *
     *  */
    public static String apply(String msg, Map<String, String> templates) {
        var tokens = msg.split(" ");
        var sb = new StringBuilder();
        var replacements = new ArrayList<String>();
        var i = 0;
        for (var token : tokens) {
            if (templates.containsKey(token)) {
                // The eclipse message bundle system supports templating only in form of {0}.
                // That's why we replace the named tokens with their respective integer ID.
                sb.append('{').append(i).append('}').append(' ');
                i++;
                replacements.add(templates.get(token));
            } else if (TEMPLATE.matcher(token).find()) {
                throw new TemplatingException("Found template " + token + " but did not find key/value to relace with");
            }else {
                sb.append(token).append(' ');
            }
        }
        return NLS.bind(sb.toString(), replacements.toArray());
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private Templating() {
        // prevent instantiation of all-static class
    }

    public static class TemplatingException extends RuntimeException {

        public TemplatingException(String message) {
            super(message);
        }
    }

}
