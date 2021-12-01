// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.aco.view;

import org.eclipse.osgi.util.NLS;

public class Messages {
	private static final String BUNDLE_NAME = "org.jcryptool.visual.aco.view.messages"; //$NON-NLS-1$

	public static String Control_language_own;

	public static String graph_probHintLbl;

	public static String Func_allSteps;
	public static String Func_alpha;
	public static String Func_analyseCreated;
	public static String Func_analyseGiven;
	public static String Func_analysis;
	public static String Func_animation;
	public static String Func_antAnalysis;
	public static String Func_beta;
	public static String Func_cycle;
	public static String Func_doWithNewAnt;
	public static String Func_encryption;
	public static String Func_error;
	public static String Func_evap;
	public static String Func_initial_plaintext;
	public static String Func_keyLength;
	public static String Func_newAnt;
	public static String Func_permutation;
	public static String Func_plaintext;
	public static String Func_ciphertext;
	public static String Func_general;
	public static String Func_keytext;
	public static String Func_oneStep;
	public static String Func_proceed;
	public static String Func_proceedToAnalysis;
	public static String Func_stopAnalysis;
	public static String Func_reset;
	public static String Func_analyseConfiguration;
	public static String Func_analyseGroupLabel;
	public static String Func_step;
	public static String Info_description1;
	public static String Info_description2;
	public static String Info_description3;
	public static String Info_description4;
	public static String Func_possibleInputs;
	public static String Func_textLanguage;
	public static String Func_analysisType;
	public static String Func_settings;
	public static String Show_decryptedBestKnown;
	public static String Show_decryptedByAnt1;
	public static String Show_encryptedText;
	public static String Show_encryptionKey;
	public static String Show_permutationMatrix;
	public static String Show_pheromoneMatrix;
	public static String Show_wrongInputTextSize;
	public static String Description_title;
	public static String Description_tooltip;
	public static String Func_radioCompleteRound;
	public static String Control_language1;
	public static String Control_language2;
	public static String Control_language1_short;
	public static String Control_language2_short;
	public static String Analysis_multipleCycles;
	public static String Control_generateText;
	public static String Visual_graphVisualRadio;
	public static String Visual_matrixVisualRadio;
	public static String Header_title;
	public static String Header_text;
	public static String Control_or;
	public static String Control_textLength;
	public static String Control_wrongInputToolTip;
	public static String Viusal_ResultGroup;
	public static String Visual_GraphMatrixGroupTitle;
	public static String Viusal_CurrAntGroup;
	public static String Viusal_BestAntGroup;
	public static String Result_currEncryptionLbl;
	public static String Result_bestEncryptionLbl;
	public static String Result_emptyText2;
	public static String Result_emptyText1;
	public static String Result_currTrailLbl;

	public static String Desc_configComp_left;
	public static String Desc_configComp_right;
	public static String Desc_analysisComp_left;
	public static String Desc_analysisComp_right;
	public static String Desc_analysisCompMulti_right;
	public static String Desc_analysisAlgoSett_right;
	public static String Desc_analysisAlgoSett_left;
	public static String PherMatrix_knotContent;

	public static String Result_description;

	public static String PherMatrix_description;

	public static String Analysis_newAntButtonToolTip;

	public static String Control_noVisualAvailable;


	private Messages() {
	}

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
