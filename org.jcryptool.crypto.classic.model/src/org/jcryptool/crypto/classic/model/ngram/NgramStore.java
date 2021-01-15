package org.jcryptool.crypto.classic.model.ngram;

import java.io.File;
import java.util.HashMap;
import java.util.function.Supplier;

import org.jcryptool.crypto.classic.model.ClassicCryptoModelPlugin;

public class NgramStore {

	public static NgramStoreEntry de_3_nospace;
	public static NgramStoreEntry en_3_nospace;
	public static NgramStoreEntry de_4_nospace;
	public static NgramStoreEntry en_4_nospace;
	public static NgramStoreEntry de_5_nospace;
	public static NgramStoreEntry en_5_nospace;
	public static NgramStoreEntry de_3_space;
	public static NgramStoreEntry en_3_space;
	public static NgramStoreEntry de_4_space;
	public static NgramStoreEntry en_4_space;
	public static NgramStoreEntry de_5_space;
	public static NgramStoreEntry en_5_space;

	static { // these won't work if we do not have the corresponding files in ./ngrams/ -- currently, many are not yet exported from CT2
		NgramStore.de_3_nospace = new NgramStoreEntry(3, "de", false);
		NgramStore.en_3_nospace = new NgramStoreEntry(3, "en", false);
		NgramStore.de_4_nospace = new NgramStoreEntry(4, "de", false);
		NgramStore.en_4_nospace = new NgramStoreEntry(4, "en", false);
		NgramStore.de_5_nospace = new NgramStoreEntry(5, "de", false);
		NgramStore.en_5_nospace = new NgramStoreEntry(5, "en", false);
		NgramStore.de_3_space = new NgramStoreEntry(3, "de", true);
		NgramStore.en_3_space = new NgramStoreEntry(3, "en", true);
		NgramStore.de_4_space = new NgramStoreEntry(4, "de", true);
		NgramStore.en_4_space = new NgramStoreEntry(4, "en", true);
		NgramStore.de_5_space = new NgramStoreEntry(5, "de", true);
		NgramStore.en_5_space = new NgramStoreEntry(5, "en", true);
	}
	
	public static class NgramStoreEntry {
		public int n;
		public String language;
		public boolean withSpace;
		public String alphabet() {
			String result;
			if (language.equals("de")) {
				result = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜß";
			} else {
				result = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			} // TODO: encode more alphabets for languages when available!
			if (withSpace) {
				result = result + " ";
			}
			return result;
		}
		public NgramStoreEntry(int n, String language, boolean withSpace) {
			super();
			this.n = n;
			this.language = language;
			this.withSpace = withSpace;
		}
	}
	

	
	public NGramFrequencies getFrequenciesFor(File file, int n) {
		return NGramFrequencies.parseGzipFile(file, n);
	}
	public NGramFrequencies getBuiltinFrequenciesFor(NgramStoreEntry entry) {
		if (statsCache.containsKey(entry)) {
			return statsCache.get(entry);
		} else {
			String filename = String.format("%s-%sgram-nocs%s.txt.gz", entry.language, entry.n, entry.withSpace ? "-sp" : "");
			statsCache.put(entry, NGramFrequencies.parseGzipFile(ClassicCryptoModelPlugin.getBundleFile("ngrams/" + filename ), entry.n));
			// TODO: soon: replace with resources from plugin
		}
		return statsCache.get(entry);
	}


	private static NgramStore instance;
	private HashMap<NgramStoreEntry, NGramFrequencies> statsCache = new HashMap<NgramStoreEntry, NGramFrequencies>();

	public static NgramStore getInstance() {
		if (instance == null) {
			instance = new NgramStore();
			return instance;
		}
		return instance;
	}
	
	
}
