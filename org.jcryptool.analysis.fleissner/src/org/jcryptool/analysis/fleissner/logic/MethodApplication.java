// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2019, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.analysis.fleissner.logic;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.jcryptool.analysis.fleissner.Activator;
import org.jcryptool.analysis.fleissner.logic.KopalAnalyzer.GrilleDecrypt;
import org.jcryptool.analysis.fleissner.logic.KopalAnalyzer.GrilleEncrypt;
import org.jcryptool.analysis.fleissner.logic.KopalAnalyzer.HillclimbGrilleResult;
import org.jcryptool.analysis.fleissner.logic.KopalAnalyzer.Rotation;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.crypto.classic.model.ngram.NGramFrequencies;

/**
 * @author Dinah
 *
 */
public class MethodApplication {

	private ArrayList<Integer> possibleTemplateLengths = new ArrayList<>();
	private ArrayList<String> analysisOut = new ArrayList<>();

//  parameters given by user or default
	private String method, decryptedText, encryptedText, textInLine;
	private int templateLength, holes, nGramSize;
	private int[] grille;
	private BigInteger restart, tries, sub;
	private boolean isPlaintext;
	private CryptedText ct;
	private FleissnerGrille fg;
	private TextValuator tv;
	private long start, end;
	private NGramFrequencies statistics;

//  parameters for analysis
	private double value, oldValue, alltimeLow = Double.MAX_VALUE;
	private int changes = 0, iAll = 0, grilleNumber = 0, improvement = 0, rotMove = 0;;
	private int x, y, move;
	private int[] bestTemplate = null;
	private String lastImprovement = null, bestDecryptedText = "", //$NON-NLS-1$
			procedure = "", change = ""; //$NON-NLS-1$ //$NON-NLS-2$

	private String fwAnalysisOutput;
	private HillclimbGrilleResult hillclimberResult;
	private Rotation rotation;
	private String encryptDecryptAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜß"; //$NON-NLS-1$
	private String timeTaken;

	/**
	 * applies parameter settings from ParameterSettings and sets and executes
	 * method
	 * 
	 * @param ps             object parameter settings
	 * @param analysisOutput the text field in FleissnerWindow where analysis
	 *                       progress is displayed
	 * @param statistics2
	 * @param rotation 
	 * @throws FileNotFoundException
	 */
	public MethodApplication(ParameterSettings ps, NGramFrequencies statistics2, Rotation rotation) throws FileNotFoundException {

		this.rotation = rotation;
		this.fwAnalysisOutput = new String(""); //$NON-NLS-1$
		this.method = ps.getMethod();
		this.textInLine = ps.getTextInLine();
		this.templateLength = ps.getTemplateLength();
		if (!ps.getPossibleTemplateLengths().isEmpty()) {
			this.possibleTemplateLengths = ps.getPossibleTemplateLengths();
		}
		this.isPlaintext = ps.isPlaintext();
		this.holes = ps.getHoles();
		this.grille = ps.getGrille();
		this.restart = ps.getRestart();
		this.statistics = statistics2;
		this.nGramSize = ps.getnGramSize();
		this.ct = new CryptedText();
		this.fg = new FleissnerGrille(templateLength);
		if (method.equals(Messages.MethodApplication_method_analyze)) {
			try {
				this.tv = new TextValuator(statistics, nGramSize);
			} catch (FileNotFoundException e) {
				LogUtil.logError(Activator.PLUGIN_ID, Messages.MethodApplication_error_statFileNotFound, e, true);
				throw new FileNotFoundException(Messages.MethodApplication_error_fileNotFound);
			}
		}
	}

	/**
	 * Executes one of the methods Brute-Force or Hill-Climbing dependent on
	 * templateLength
	 * @param monitor 
	 */
	public void analyze(IProgressMonitor monitor) {

		start = System.currentTimeMillis();
		fwAnalysisOutput += Messages.MethodApplication_output_StartAnalysis;
		LogUtil.logInfo(Activator.PLUGIN_ID, Messages.MethodApplication_info_StartAnalysis);

			ct.load(textInLine, isPlaintext, templateLength, grille, fg);
			fwAnalysisOutput += Messages.MethodApplication_output_info + ct.toString();
//              Hill-Cimbing for templates from 5 x 5. creates random grilles and evaluates those and slightly variations of them before
//              trying new random grilles.
				procedure = Messages.MethodApplication_procedure_hillClimbing;
				fwAnalysisOutput += Messages.MethodApplication_output_startProcedure + procedure;
				LogUtil.logInfo(Activator.PLUGIN_ID, Messages.MethodApplication_info_startProcedure + procedure);
//				this.hillClimbing_old();
				this.hillclimberResult = this.hillClimbing(monitor);

				decryptedText = hillclimberResult.bestplaintext;
//				bestTemplate = new FleissnerGrille(templateLength);
				fwAnalysisOutput += String.format(Messages.MethodApplication_M1, hillclimberResult.restarts, hillclimberResult.cost);
				FleissnerGrille bestkeyRepr = new FleissnerGrille(templateLength);
//				bestkeyRepr.clearGrille(); bestkeyRepr.useTemplate(hillclimberResult.asGrilleKey().toTecleIntArray(), hillclimberResult.grilleSize);
				bestTemplate = hillclimberResult.asGrilleKey().toTecleIntArray();
				bestDecryptedText = hillclimberResult.bestplaintext;
				timeTaken = hillclimberResult.getTimeSpentInSecondsFormatted();
				
				StringBuilder bestkeyReprS = new StringBuilder();
				List<Integer> tecleFormat = hillclimberResult.asGrilleKey().toTecleFormat();
				for (int i = 0; i < tecleFormat.size(); i = i+2) {
					bestkeyReprS.append(String.format("(%s,%s) ", tecleFormat.get(i), tecleFormat.get(i+1))); //$NON-NLS-1$
				}
				fwAnalysisOutput += String.format(Messages.MethodApplication_M2, bestkeyReprS);
				fwAnalysisOutput += Messages.MethodApplication_output_finishedProcedure + procedure;
				LogUtil.logInfo(Activator.PLUGIN_ID, Messages.MethodApplication_output_finishedProcedure + procedure);

	}

	/**
	 * bruteForce() method tries every possible grille with current key size and
	 * chooses the one that generates the best text value
	 */
	public void bruteForce() {

//      clears grilles in FleissnerGrille from potential earlier encryptions before building new ones
		fg.clearGrille();
		ArrayList<int[]> templateList = fg.bruteForce(templateLength, holes);
		if (templateList.isEmpty()) {
			fwAnalysisOutput += Messages.MethodApplication_output_emptyList;
			LogUtil.logInfo(Activator.PLUGIN_ID, Messages.MethodApplication_info_emptyList);
			return;
		}
		fwAnalysisOutput += Messages.MethodApplication_output_siftThroughTemplates;
		LogUtil.logInfo(Activator.PLUGIN_ID, Messages.MethodApplication_info_siftThroughTemplates);
		// using every possible template / Grille
		for (int[] template : templateList) {

			iAll++;
			fg.useTemplate(template, templateLength);
			decryptedText = fg.decryptText(ct.getText());
			value = tv.evaluate(decryptedText);
			if (value < alltimeLow) {
//              better grille found, saves new template
				alltimeLow = value;
				bestTemplate = template;
				grilleNumber = iAll;
			}

			fwAnalysisOutput += Messages.MethodApplication_output_grille + iAll + Messages.FleissnerGrille_break + fg
					+ "\n" + fg.templateToString(holes); //$NON-NLS-1$
			fwAnalysisOutput += Messages.MethodApplication_output_acurateness + myRound(value) + " (" //$NON-NLS-1$
					+ Messages.MethodApplication_output_best + myRound(alltimeLow) + ")"; //$NON-NLS-1$
			fwAnalysisOutput += Messages.MethodApplication_output_decrypted + decryptedText
					+ "\n"; //$NON-NLS-1$

			LogUtil.logInfo(Activator.PLUGIN_ID,
					Messages.MethodApplication_output_grille + iAll + Messages.FleissnerGrille_break + fg
							+ Messages.FleissnerGrille_break + fg.templateToString(holes));
			LogUtil.logInfo(Activator.PLUGIN_ID, Messages.MethodApplication_info_acurateness + myRound(value) + " (" //$NON-NLS-1$
					+ Messages.MethodApplication_output_best + myRound(alltimeLow) + ")"); //$NON-NLS-1$
			LogUtil.logInfo(Activator.PLUGIN_ID,
					Messages.MethodApplication_info_decrypted + decryptedText + "\n"); //$NON-NLS-1$

			String lastLine = fwAnalysisOutput.substring(fwAnalysisOutput.lastIndexOf("\n")); //$NON-NLS-1$

			analysisOut.add(fwAnalysisOutput);
			this.fwAnalysisOutput = new String(""); //$NON-NLS-1$

			String visualDivide = "\n"; //$NON-NLS-1$
			for (int i = 0; i < lastLine.length() * 2; i++)
				visualDivide += "-"; //$NON-NLS-1$
			LogUtil.logInfo(Activator.PLUGIN_ID, visualDivide + "\n"); // $NON-NLS-1$ //$NON-NLS-1$

		}
		fg.useTemplate(bestTemplate, templateLength);
	}

	
	public HillclimbGrilleResult hillClimbing(IProgressMonitor monitor) {
		var grilleSize = templateLength;
		var strciphertext = textInLine;
		var alphabet = statistics.alphabet;
		var ciphertext = KopalAnalyzer.MapTextIntoNumberSpace(strciphertext, alphabet);
		NGramFrequencies grams = statistics;
		return KopalAnalyzer.HillclimbGrilleWithMonitor(monitor, ciphertext, grilleSize, restart.intValue(), this.rotation, grams);
	}
	

	/**
	 * encrypts given plaintext with given key directly through load method of class
	 * CryptedText
	 */
	public void encrypt_old() {
		ct.load(textInLine, isPlaintext, templateLength, grille, fg);
		encryptedText = ""; //$NON-NLS-1$
		for (char[][] textPart : ct.getText()) {
			for (int y = 0; y < templateLength; y++) {
				for (int x = 0; x < templateLength; x++) {
					encryptedText += textPart[x][y];
				}
			}
		}
		LogUtil.logInfo(Activator.PLUGIN_ID, Messages.MethodApplication_info_encryptionSuccesfull);
	}

	/**
	 * encrypts given plaintext with given key directly through load method of class
	 * CryptedText
	 */
	public void encrypt() {
		ct.load(textInLine, isPlaintext, templateLength, grille, fg);
		fg.useTemplate(grille, templateLength);
		int[][] key = KopalAnalyzer.keyFromTecleFormat(grille);
		int[] input = KopalAnalyzer.MapTextIntoNumberSpace(textInLine, this.encryptDecryptAlphabet );
		GrilleEncrypt encrypt = new KopalAnalyzer.GrilleEncrypt(input, key, key.length*2, rotation);
		String result = KopalAnalyzer.MapNumbersIntoTextSpace(encrypt.Encrypt(), this.encryptDecryptAlphabet);
		encryptedText = result;
		LogUtil.logInfo(Activator.PLUGIN_ID, Messages.MethodApplication_info_encryptionSuccesfull);
	}

	/**
	 * decrypts given crypted text with given key by decryption method of class
	 * FleissnerGrille
	 */
	public void decrypt() {
		ct.load(textInLine, isPlaintext, templateLength, grille, fg);
		fg.useTemplate(grille, templateLength);
		int[][] key = KopalAnalyzer.keyFromTecleFormat(grille);
		int[] input = KopalAnalyzer.MapTextIntoNumberSpace(textInLine, this.encryptDecryptAlphabet );
		GrilleDecrypt decrypt = new KopalAnalyzer.GrilleDecrypt(input, key, key.length*2, rotation);
		String result = KopalAnalyzer.MapNumbersIntoTextSpace(decrypt.Decrypt(), this.encryptDecryptAlphabet);
		decryptedText = result;

		LogUtil.logInfo(Activator.PLUGIN_ID, Messages.MethodApplication_info_decryptionSuccesfull);
	}
	public void decrypt_old() {

		ct.load(textInLine, isPlaintext, templateLength, grille, fg);
		fg.useTemplate(grille, templateLength);
		decryptedText = fg.decryptText(ct.getText());
		LogUtil.logInfo(Activator.PLUGIN_ID, Messages.MethodApplication_info_decryptionSuccesfull);
	}

	/**
	 * generates random key and saves it as int array 'grille'
	 */
	public void keyGenerator() {
		this.grille = new int[2 * holes];
		fg.clearGrille();

		for (int i = 0; i < holes; i++) {
			do {
				x = ThreadLocalRandom.current().nextInt(0, templateLength);
				y = ThreadLocalRandom.current().nextInt(0, templateLength);
			} while (!fg.isPossible(x, y));

			fg.setState(x, y, true);
			grille[2 * i] = x;
			grille[(2 * i) + 1] = y;
		}
	}

	public String countChanges() {
		if (this.changes != 1)
			change = Messages.MethodApplication_output_countChanges_pl;
		else
			change = Messages.MethodApplication_output_countChanges_sg;
		return change;
	}

	public String countRotations() {
		String rotations = ""; //$NON-NLS-1$
		if (this.rotMove != 1)
			rotations = Messages.MethodApplication_output_countRotations_pl;
		else
			rotations = Messages.MethodApplication_output_countRotations_sg;
		return rotations;
	}

	public String countImprovements() {
		String improvements = ""; //$NON-NLS-1$
		if (this.improvement != 1)
			improvements = Messages.MethodApplication_output_countImprovements_pl;
		else
			improvements = Messages.MethodApplication_output_countImprovements_sg;
		return improvements;
	}

	public String myRound(double wert) {
		double tempWert = wert;
		DecimalFormat formatter = new DecimalFormat("#.##"); //$NON-NLS-1$
		return formatter.format(tempWert);
	}

	/**
	 * @return the fg
	 */
	public FleissnerGrille getFg() {
		return fg;
	}

	/**
	 * @param fg the fg to set
	 */
	public void setFg(FleissnerGrille fg) {
		this.fg = fg;
	}

	public String getEncryptedText() {
		return encryptedText;
	}

	public String getDecryptedText() {
		return decryptedText;
	}

	public String getBestDecryptedText() {
		return bestDecryptedText;
	}

	public int[] getBestTemplate() {
		return bestTemplate;
	}

	/**
	 * @return the fwAnalysisOutput
	 */
	public String getFwAnalysisOutput() {
		return fwAnalysisOutput;
	}

	/**
	 * @param fwAnalysisOutput the fwAnalysisOutput to set
	 */
	public void setFwAnalysisOutput(String fwAnalysisOutput) {
		this.fwAnalysisOutput = fwAnalysisOutput;
	}

	/**
	 * @return the analysisOut
	 */
	public ArrayList<String> getAnalysisOut() {
		return analysisOut;
	}

	public static String templateToForm1(int[] templateTecle) {
		if (templateTecle == null) {
			return ""; //$NON-NLS-1$
		}
		String result = ""; //$NON-NLS-1$
		for (int i = 0; i < templateTecle.length; i=i+2) {
			result += "(" + templateTecle[i] + "," + templateTecle[i+1] + ") "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return result;
	}
	public static String templateToForm2(int[] templateTecle) {
		if (templateTecle == null) {
			return ""; //$NON-NLS-1$
		}
		int grillesize = (int) Math.floor(Math.sqrt((templateTecle.length / 2)*4));
		List<Integer> holePositions = new LinkedList<>();
		for (int i = 0; i < templateTecle.length; i=i+2) {
			holePositions.add(templateTecle[i] + templateTecle[i+1]*grillesize + 1);
		}
		Collections.sort(holePositions);
		return holePositions.stream().map(i -> i.toString()).collect(Collectors.joining(" ")); //$NON-NLS-1$
	}
	public static String templateToForm3(int[] templateTecle) {
		boolean[][] boolform = KopalAnalyzer.tecleFormatToBool(templateTecle);
		return KopalAnalyzer.Arr2Text(boolform);
	}
	
	
	public static String threeKeyFormats(int[] templateTecle, String firstline) {
		return String.format("%s\n%s: %s\n%s: %s\n%s:\n%s",  //$NON-NLS-1$
				firstline,
				Messages.MethodApplication_XX1, 
				templateToForm1(templateTecle),
				Messages.MethodApplication_XX2, 
				templateToForm2(templateTecle), 
				Messages.MethodApplication_XX3, 
				templateToForm3(templateTecle)
				);
				 
	}
	
	@Override
	public String toString() {

		String output = null;
		String bestTemplateCoordinates = ""; //$NON-NLS-1$

		switch (this.method) {

		case "analyze": //$NON-NLS-1$
			String time;
//			bestDecryptedText = fg.decryptText(ct.getText());
//			value = tv.evaluate(bestDecryptedText);
			bestTemplateCoordinates = threeKeyFormats(bestTemplate, Messages.MethodApplication_XX4);
			output = Messages.MethodApplication_zz8 + textInLine + "\n\n"; //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$
			output += bestTemplateCoordinates + Messages.MethodApplication_M6 + hillclimberResult.cost;
			output += "\n" + Messages.MethodApplication_output_decrypted_final + bestDecryptedText //$NON-NLS-1$
					+ "\n"; // $NON-NLS-2$ //$NON-NLS-1$
			output += Messages.MethodApplication_output_length_final + templateLength;
			output += "\n" + Messages.MethodApplication_r1 + KopalAnalyzer.RotationToString(rotation); //$NON-NLS-1$
			output += "\n\n" + String.format(Messages.MethodApplication_ZZZ1, timeTaken); //$NON-NLS-1$
//          adjusts time format depending of spent time for analysis
//			if (end < 60000)
//				time = end + Messages.MethodApplication_outputTime;
//			else {
//				long timeInDays, timeInHours, timeInMinutes, timeInSeconds;
//
//				timeInSeconds = end / 1000;
//				Duration duration = Duration.ofSeconds(timeInSeconds);
//
//				timeInDays = duration.toDays();
//				duration = duration.minusDays(timeInDays);
//				timeInHours = duration.toHours();
//				duration = duration.minusHours(timeInHours);
//				timeInMinutes = duration.toMinutes();
//				duration = duration.minusMinutes(timeInMinutes);
//				timeInSeconds = duration.getSeconds();
//				time = String.format(Messages.MethodApplication_output_timeFormat_in, timeInDays, timeInHours,
//						timeInMinutes, timeInSeconds) + Messages.MethodApplication_output_timeFormat_out;
//			}
//			output += Messages.MethodApplication_output_finished_final + time;
			break;
		case "encrypt": //$NON-NLS-1$
			String outputForKeyEncrypt = threeKeyFormats(fg.saveTemplate(), Messages.MethodApplication_output_encryptionKey);
			output = Messages.MethodApplication_zz8 + textInLine + "\n"; //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$
			output += Messages.MethodApplication_output_encrypted + encryptedText
					+  "\n" + outputForKeyEncrypt; //$NON-NLS-1$
//			bestTemplateCoordinates = templateToForm1(fg.saveTemplate()) + "\n" + Messages.MethodApplication_r2 + templateToForm2(fg.saveTemplate()); //$NON-NLS-1$
//			output += Messages.MethodApplication_output_keyCoordinates + bestTemplateCoordinates;
			output += Messages.MethodApplication_output_length_final + templateLength;
			output += "\n" + Messages.MethodApplication_r1 + KopalAnalyzer.RotationToString(rotation); //$NON-NLS-1$
			break;
		case "decrypt": //$NON-NLS-1$
			String outputForKeyDecrypt = threeKeyFormats(fg.saveTemplate(), Messages.MethodApplication_output_decryptionKey);
			output = Messages.MethodApplication_zz8 + textInLine + Messages.MethodApplication_4; //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-1$
			output += Messages.MethodApplication_output_decrypted_final + decryptedText
					+ "\n" + outputForKeyDecrypt; //$NON-NLS-1$
//			bestTemplateCoordinates = templateToForm1(fg.saveTemplate()) + "\n" + Messages.MethodApplication_r2 + templateToForm2(fg.saveTemplate()); //$NON-NLS-1$
//			output += Messages.MethodApplication_output_keyCoordinates + bestTemplateCoordinates;
			output += Messages.MethodApplication_output_length_final + templateLength;
			output += "\n" + Messages.MethodApplication_r1 + KopalAnalyzer.RotationToString(rotation); //$NON-NLS-1$
			break;
		case "keyGenerator": //$NON-NLS-1$
			output = Messages.MethodApplication_output_key + "\n" + fg; //$NON-NLS-1$
			for (int h = 0; h < grille.length; h++) {
				output += grille[h] + ","; //$NON-NLS-1$
			}
			output += Messages.MethodApplication_output_length_final + templateLength;
			fg.clearGrille();
		}
		return output;
	}
}