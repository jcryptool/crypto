package org.jcryptool.analysis.fleissner.logic;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Point;
import org.jcryptool.crypto.classic.model.ngram.NGramFrequencies;
import org.jcryptool.crypto.classic.model.ngram.NgramStore;

/*
Copyright 2020 Nils Kopal <Nils.Kopal<AT>cryptool.org>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

public class KopalAnalyzer {

	public static enum Rotation { Left, Right }
	public static String RotationToString(Rotation rot) {
		if (rot.equals(Rotation.Left)) {
			return Messages.KopalAnalyzer_0;
		}
		return Messages.KopalAnalyzer_1;
	}
	public static String RotationToStringShort(Rotation rot) {
		if (rot.equals(Rotation.Left)) {
			return Messages.KopalAnalyzer_l0;
		}
		return Messages.KopalAnalyzer_r0;
	}
	public static final String defaultAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // TODO //$NON-NLS-1$

	static IProgressMonitor dummyMonitor = new IProgressMonitor() {
		
		@Override
		public void worked(int work) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void subTask(String name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setTaskName(String name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setCanceled(boolean value) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean isCanceled() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void internalWorked(double work) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void done() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beginTask(String name, int totalWork) {
			// TODO Auto-generated method stub
			
		}
	};


	public static void main(String[] args)
	{           
		var grilleSize = 12;
		var key = GenerateRandomKey(grilleSize);

//		System.out.println("grille definition:");
//		System.out.println(ConvertGrilleKeyToString(key, grilleSize / 2));

		//some text plaintexts
		//var strplaintext = "THISISATESTOFMYTURNINGGRILLEHILLCLIMBERANDITWORKSVERYWELLTOSOLVE";
		//var strplaintext = "PLANSFORMANNEDMOONEXPEDITIONSORIGINATEDDURINGTHEEISENHOWERERAINANARTICLESERIESWERNHERVONBRAUNPOPULARIZEDTHEIDEAOFAMOONEXPEDITIONAMANNEDMOONLANDINGPOSEDMANYTECHNICALCHALLENGESBESIDESGUIDANCEANDWEIGHTMANAGEMENTATMOSPHERICREENTRYWITHOUTOVERHEATINGWASAMAJORHURDLEAFTERTHESOVIETUNIONSLAUNCHOFTHESPUTNIKSATELLITEVONBRAUNPROMOTEDAPLANFORTHEUNITEDSTATESARMYTOESTABLISHAMILITARYLUNAROUTPOSTBYNINETEENSIXTYFIVE";
		//var strplaintext = "INTHEHISTORYOFCRYPTOGRAPHYAGRILLECIPHERWASATECHNIQUEFORENCRYPTIN";                        
		//var strplaintext = "IHAVEFURTHERPROVEDTHEUTILITYOFTHISINVENTIONINTHEFORMOFTHETELEGRAMBUTTORENDERITSAFEFORSHORTMESSAGESOTHERCIPHERMAYBEUSEDTOINSUREAGREATERCOMPLEXITY";
		//var plaintext = MapTextIntoNumberSpace(strplaintext.Substring(0, grilleSize * grilleSize), alphabet);
		//var ciphertext = EncryptGrille(plaintext, key, grilleSize, Rotation.Right);                        

		//ciphertext from 1870 (see https://scienceblogs.de/klausis-krypto-kolumne/2020/09/29/can-you-solve-this-turning-grille-cryptogram-from-1870/)
		//the Grille was used with left rotation
		var strciphertext = "NVRDIEMHNEATIRVOAEINFYIUBRNTTTEHSEUAFHSEREPEFDRFOORRMOSVTOHOEIDFNOTSHTUHRETRTEEEAMLEEUGGSTSRIELATARIEGTEAMRYOBSFOUCTTOHTEMTRPHCOLIIEXPSIHRTEIEYN";                                 //$NON-NLS-1$
		var ciphertext = MapTextIntoNumberSpace(strciphertext, defaultAlphabet);

		//mtc3 challenge
		//the Grille was used with right rotation
		//var strciphertext = "STRWAEODGRIUNTNENERYSRTHOBUYEUINCSADALELBERULNTHSEATESDIELEMFAOE";
		//var ciphertext = MapTextIntoNumberSpace(strciphertext, alphabet);

//		System.out.println("Ciphertext:" + MapNumbersIntoTextSpace(ciphertext, defaultAlphabet));

		//hillclimb it            
		NGramFrequencies grams = NgramStore.getInstance().getFrequenciesFor(new File("/home/snuc/Desktop/ngrams/convert/en-5gram-nocs.txt.gz"), 5); //$NON-NLS-1$
		HillclimbGrilleResult result = HillclimbGrilleWithMonitor(dummyMonitor, ciphertext, grilleSize, 10, Rotation.Right, grams);
		System.out.println(ConvertGrilleKeyToString(result.bestkey, 6));
		
		
		FleissnerGrille tG = tecleFormatFromKey(result.bestkey);
		int[] intarray = tG.saveTemplate();
		for (int i = 0; i < intarray.length; i+=2) {
			System.out.println(String.format("(%s,%s)", intarray[i], intarray[i+1])); //$NON-NLS-1$
		}
		
		boolean[][] tBA = result.asGrilleKey().toFullMatrix();
		System.out.println(Arr2Text(tBA));
		int[][] loopback = keyFromTecleFormat(tBA);
		System.out.println(ConvertGrilleKeyToString(loopback, 6));
		
//		for(int i=0; i<grilleSize/2; i++) {
//			for (int k=0; k<grilleSize/2; k++) {
//				int el = result.bestkey[i][k];
//				System.out.println(String.format("[%s,%s]=%s", i, k, el));
//			}
//		}
//		System.out.println(ConvertGrilleKeyToString(result.bestkey, grilleSize/2));
//		System.out.println(result.bestplaintext);
//		System.out.println(Arr2Text(result.asGrilleKey().toFullMatrix()));
//		System.out.println(Arr2Text(result.asGrilleKey().toTecleFormat()));
//		HillclimbGrilleRandomly(ciphertext, grilleSize, 100, grams);
//		SAGrille(ciphertext, grilleSize, 0.1, grams);
	}
	
	public static String Arr2Text(boolean[][] arr) {
		StringBuilder builder = new StringBuilder();

		for(int i=0; i<arr.length; i++) {
			for (int k=0; k<arr.length; k++) {
				boolean el = arr[k][i];
				builder.append(String.format(el ? "\u25A1 " : "\u25A0 ")); //$NON-NLS-1$ //$NON-NLS-2$
//				builder.append(String.format("[%s,%s]=%s\n", i, k, el));
			}
			builder.append("\n"); //$NON-NLS-1$
		}
		return builder.toString();
	}
	public static String Arr2Text(int[][] arr) {
		StringBuilder builder = new StringBuilder();

		for(int i=0; i<arr.length; i++) {
			for (int k=0; k<arr.length; k++) {
				int el = arr[i][k];
				builder.append(String.format("[%s,%s]=%s\n", i, k, el)); //$NON-NLS-1$
			}
		}
		return builder.toString();
	}
	public static String Arr2Text(List<Integer> arr) {
		StringBuilder builder = new StringBuilder();

		for(int i=0; i<arr.size(); i++) {
			int el = arr.get(i);
			builder.append(String.format("[%s]=%s\n", i, el)); //$NON-NLS-1$
		}
		return builder.toString();
	}
	
	
	/// <summary>
	/// Generates a random Grille key
	/// If "smarter key" is set, it generates keys where no two holes are connected
	/// </summary>
	/// <param name="grilleSize"></param>
	/// <param name="smarterKey"></param>
	/// <returns></returns>
	private static int[][] GenerateRandomKey(int grilleSize)
	{
		boolean generateSmarterKey = true; // TODO: later: could be controlled by parameter but for now, not
		Random random = new Random();
		var key = new int[grilleSize / 2][grilleSize / 2];
		for (int y = 0; y < grilleSize / 2; y++)
		{
			for (int x = 0; x < grilleSize / 2; x++)
			{
				boolean ok;
				do
				{
					ok = true;
					key[x][y] = random.nextInt(4);
					if(x > 0 && x < grilleSize / 2 && key[x - 1][y] == key[x][y])
					{
						ok = false;
					}
					if (y > 0 && y < grilleSize / 2 && key[x][y - 1] == key[x][y])
					{
						ok = false;
					}
				} while (generateSmarterKey && !ok);
			}
		}
		return key;
	}
	
	public static double calculateCost(NGramFrequencies grams, int[] plaintext) {
		int n = grams.n;
		double result = 0;

		int[] ngram = new int[n];
		for(int i = 0; i<plaintext.length-n; i++) {
			System.arraycopy(plaintext, i, ngram, 0, n);
			result += grams.getFrequencyFor(ngram);
		}
		return result;
	}
	
	
	public static class HillclimbGrilleResult {

		public int[] ciphertext;
		public int grilleSize;
		public int restarts;
		public Rotation rotation;
		public double cost;
		public int[][] bestkey;
		public int[] bestplaintextAsNumbers;
		public String bestplaintext;
		private long timeStart;
		private long timeEnd;

		public HillclimbGrilleResult(int[] ciphertext, int grilleSize, int restarts,
				Rotation rotation, NGramFrequencies grams) {
			this.ciphertext = ciphertext;
			this.grilleSize = grilleSize;
			this.restarts = restarts;
			this.rotation = rotation;
		}

		public void setStats(double globalbestkeycost, int[][] globalbestkey) {
			this.cost = globalbestkeycost; 
			this.bestkey = globalbestkey;
		}

		public void setBestTextAsInt(int[] globalbestplaintext) {
			this.bestplaintextAsNumbers = globalbestplaintext;
		}

		public void setBestTextAsString(String mapNumbersIntoTextSpace) {
			this.bestplaintext = mapNumbersIntoTextSpace;		
		}
		
		public GrilleKey asGrilleKey() {
			return new GrilleKey(bestkey, grilleSize, Rotation.Right);
		}

		public void setStartAndEndTime(long timeStart, long timeEnd) {
			this.timeStart = timeStart;
			this.timeEnd = timeEnd;
		}
		
		public double getTimeSpentInSeconds() {
			return (double)(timeEnd-timeStart)/1000;
		}
		public String getTimeSpentInSecondsFormatted() {
			return String.format("%.2f", getTimeSpentInSeconds());
		}

	}
	
	public static HillclimbGrilleResult HillclimbGrilleWithMonitor(IProgressMonitor monitor, int[] ciphertext, int grilleSize, int restarts, Rotation rotation, NGramFrequencies grams)
	{
		HillclimbGrilleResult result = new HillclimbGrilleResult(ciphertext, grilleSize, restarts, rotation, grams);
		//var grams = new Bigrams("en", false);            
		//var grams = new Tetragrams("en", false);      
		//var grams = new Hexagrams("en");
		//var grams = new Tetragrams("english_quadgrams.txt");
		//var grams = new Pentagrams("english_quintgrams.txt");            
		
		monitor.beginTask("Grille Hillclimbing", restarts); //$NON-NLS-1$

		long timeStart = System.currentTimeMillis();
		int[][] globalbestkey = new int[0][0]; // size: ,
		double globalbestkeycost = Double.NEGATIVE_INFINITY;
		int[] globalbestplaintext = new int[] {};
		var globalbestplaintextlength = 0;
		var totalrestarts = restarts;
		do
		{
			//1. Generate Random Key
			var runkey = GenerateRandomKey(grilleSize);

			//2. Perform Hillclimbing                
			var bestfoundkey = new int[grilleSize / 2][grilleSize / 2];
			var bestkeycost = Double.NEGATIVE_INFINITY;
			var bestplaintext = new int[0];
			boolean better;
			do
			{

				better = false;
				//change in neighborhood 1: change one hole
				for (int x = 0; x < grilleSize / 2; x++)
				{
					for (int y = 0; y < grilleSize / 2; y++)
					{                                                        
						for (int s = 0; s < 4; s++)
						{
							int[][] copykey = cloneIntMatrix(runkey);
							if (copykey[x][y] == s)
							{
								continue;
							}
							copykey[x][y] = s;
							var plaintext = new GrilleDecrypt(ciphertext, copykey, grilleSize, rotation).Decrypt();
							//rate using cost function
							double cost = calculateCost(grams, plaintext);
							if (cost > bestkeycost)
							{
//							System.out.println(String.format("1: cost: %s; bestkeycost: %s", cost, bestkeycost));
								better = true;
								bestkeycost = cost;
								bestfoundkey = cloneIntMatrix(copykey);
								bestplaintext = plaintext;
								runkey = cloneIntMatrix(copykey);
							}
						}
					}
				}

				//change 2 in neighborhood: exchange two hole
				for (int x = 0; x < grilleSize / 2; x++)
				{
					for (int y = 0; y < grilleSize / 2; y++)
					{
						for (int x2 = x; x2 < grilleSize / 2; x2++)
						{
							for (int y2 = y; y2 < grilleSize / 2; y2++)
							{
								if (x == x2 && y == y2)
								{
									continue;
								}
								var copykey = cloneIntMatrix(runkey);
								var swap = copykey[x][y];
								copykey[x][y] = copykey[x2][y2];
								copykey[x2][y2] = swap;

								var plaintext = new GrilleDecrypt(ciphertext, copykey, grilleSize, rotation).Decrypt();
								//rate using cost function
								double cost = calculateCost(grams, plaintext);
								if (cost > bestkeycost)
								{
//								System.out.println(String.format("2: cost: %s; bestkeycost: %s", cost, bestkeycost));
									better = true;
									bestkeycost = cost;
									bestfoundkey = cloneIntMatrix(copykey);
									bestplaintext = plaintext;
									runkey = cloneIntMatrix(copykey);
								}
							}
						}
					}
				}

				//change 3 in neighborhood: rotate
				for (int grilleRotation = 1; grilleRotation < 4; grilleRotation++)
				{
					var copykey = cloneIntMatrix(runkey);
					for (int x = 0; x < grilleSize / 2; x++)
					{
						for (int y = 0; y < grilleSize / 2; y++)
						{
							copykey[x][y] = (copykey[x][y] + 1) % 4;
						}
					}

					var plaintext = new GrilleDecrypt(ciphertext, copykey, grilleSize, rotation).Decrypt();
					//rate using cost function
					double cost = calculateCost(grams, plaintext);
					if (cost > bestkeycost)
					{
//					System.out.println(String.format("3: cost: %s; bestkeycost: %s", cost, bestkeycost));
						better = true;
						bestkeycost = cost;
						bestfoundkey = cloneIntMatrix(copykey);
						bestplaintext = plaintext;
						runkey = cloneIntMatrix(copykey);
					}
				}

			} while (better);

			if (bestkeycost > globalbestkeycost)
			{
				globalbestkeycost = bestkeycost;
				globalbestkey = bestfoundkey;
				globalbestplaintext = bestplaintext;
//				System.out.println(ConvertGrilleKeyToString(globalbestkey, grilleSize / 2));
			}
			restarts--;
			monitor.worked(1);
		} while (restarts > 0);
		
		List<int[][]> rotatedKeys = new LinkedList<>();
		List<int[]> rotatedPlaintexts = new LinkedList<>();
		double bestCost = Double.NEGATIVE_INFINITY;
		int bestRotIdx = -1;
		for (int i = 0; i < 4; i++) {
//			System.out.println(ConvertGrilleKeyToString(globalbestkey, grilleSize/2));
			int[][] rotatedBestKey = Invariance(cloneIntMatrix(globalbestkey), i);
//			System.out.println("---");
//			System.out.println(ConvertGrilleKeyToString(rotatedBestKey, grilleSize/2));
//			System.out.println("---");
			int[] decrypted = new GrilleDecrypt(ciphertext, rotatedBestKey, grilleSize, rotation).Decrypt();
			double cost = calculateCost(grams, decrypted);
			rotatedKeys.add(rotatedBestKey);
			rotatedPlaintexts.add(decrypted);
			if (cost > bestCost) {
				bestRotIdx = i;
				bestCost = cost;
			}
//			System.out.println(String.format("bestRotIdx:%s, decrypted:%s, cost: %s, bestCost:%s", bestRotIdx, MapNumbersIntoTextSpace(decrypted, grams.alphabet), cost, bestCost));
		}
		globalbestplaintext = rotatedPlaintexts.get(bestRotIdx);
		globalbestkey = rotatedKeys.get(bestRotIdx);
		globalbestkeycost = bestCost;
		long timeEnd = System.currentTimeMillis();

		result.setStartAndEndTime(timeStart, timeEnd);
		result.setStats(globalbestkeycost, globalbestkey);
		result.setBestTextAsInt(globalbestplaintext);
		result.setBestTextAsString(MapNumbersIntoTextSpace(globalbestplaintext, grams.alphabet, globalbestplaintextlength));
		return result;
	}
	
	private static int[][] Invariance(int[][] key, int number) {
		int[][] result = new int[key.length][key.length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result.length; j++) {
				result[i][j] = (key[i][j] + number) % 4;
			}
		}
		return result;
	}

	/// <summary>
	/// Tries to break the ciphertext using hillclimbing...
	/// </summary>
	/// <param name="ciphertext"></param>
	/// <param name="grilleSize"></param>
	/// <param name="restarts"></param>
	public static void HillclimbGrille(int[] ciphertext, int grilleSize, int restarts, Rotation rotation, NGramFrequencies grams)
	{
		System.out.println("HC now..."); //$NON-NLS-1$
		//var grams = new Bigrams("en", false);            
		//var grams = new Tetragrams("en", false);      
		//var grams = new Hexagrams("en");
		//var grams = new Tetragrams("english_quadgrams.txt");
		//var grams = new Pentagrams("english_quintgrams.txt");            

		int[][] globalbestkey; // size: ,
		double globalbestkeycost = Double.NEGATIVE_INFINITY;
		int[] globalbestplaintext = new int[] {};
		var globalbestplaintextlength = 0;
		var totalrestarts = restarts;
		do
		{
			//1. Generate Random Key
			var runkey = GenerateRandomKey(grilleSize);

			//2. Perform Hillclimbing                
			var bestfoundkey = new int[grilleSize / 2][grilleSize / 2];
			var bestkeycost = Double.NEGATIVE_INFINITY;
			var bestplaintext = new int[0];
			boolean better;
			do
			{

				better = false;
				//change in neighborhood 1: change one hole
				for (int x = 0; x < grilleSize / 2; x++)
				{
					for (int y = 0; y < grilleSize / 2; y++)
					{                                                        
						for (int s = 0; s < 4; s++)
						{
							int[][] copykey = cloneIntMatrix(runkey);
							if (copykey[x][y] == s)
							{
								continue;
							}
							copykey[x][y] = s;
							var plaintext = new GrilleDecrypt(ciphertext, copykey, grilleSize, rotation).Decrypt();
							//rate using cost function
							double cost = calculateCost(grams, plaintext);
							if (cost > bestkeycost)
							{
//							System.out.println(String.format("1: cost: %s; bestkeycost: %s", cost, bestkeycost));
								better = true;
								bestkeycost = cost;
								bestfoundkey = cloneIntMatrix(copykey);
								bestplaintext = plaintext;
								runkey = cloneIntMatrix(copykey);
							}
						}
					}
				}

				//change 2 in neighborhood: exchange two hole
				for (int x = 0; x < grilleSize / 2; x++)
				{
					for (int y = 0; y < grilleSize / 2; y++)
					{
						for (int x2 = x; x2 < grilleSize / 2; x2++)
						{
							for (int y2 = y; y2 < grilleSize / 2; y2++)
							{
								if (x == x2 && y == y2)
								{
									continue;
								}
								var copykey = cloneIntMatrix(runkey);
								var swap = copykey[x][y];
								copykey[x][y] = copykey[x2][y2];
								copykey[x2][y2] = swap;

								var plaintext = new GrilleDecrypt(ciphertext, copykey, grilleSize, rotation).Decrypt();
								//rate using cost function
								double cost = calculateCost(grams, plaintext);
								if (cost > bestkeycost)
								{
//								System.out.println(String.format("2: cost: %s; bestkeycost: %s", cost, bestkeycost));
									better = true;
									bestkeycost = cost;
									bestfoundkey = cloneIntMatrix(copykey);
									bestplaintext = plaintext;
									runkey = cloneIntMatrix(copykey);
								}
							}
						}
					}
				}

				//change 3 in neighborhood: rotate
				for (int grilleRotation = 1; grilleRotation < 4; grilleRotation++)
				{
					var copykey = cloneIntMatrix(runkey);
					for (int x = 0; x < grilleSize / 2; x++)
					{
						for (int y = 0; y < grilleSize / 2; y++)
						{
							copykey[x][y] = (copykey[x][y] + 1) % 4;
						}
					}

					var plaintext = new GrilleDecrypt(ciphertext, copykey, grilleSize, rotation).Decrypt();
					//rate using cost function
					double cost = calculateCost(grams, plaintext);
					if (cost > bestkeycost)
					{
//					System.out.println(String.format("3: cost: %s; bestkeycost: %s", cost, bestkeycost));
						better = true;
						bestkeycost = cost;
						bestfoundkey = cloneIntMatrix(copykey);
						bestplaintext = plaintext;
						runkey = cloneIntMatrix(copykey);
					}
				}

			} while (better);

			System.out.println(String.format("bestkeycost: %s; globalbestkeycost: %s", bestkeycost, globalbestkeycost)); //$NON-NLS-1$
			if (bestkeycost > globalbestkeycost)
			{
				globalbestkeycost = bestkeycost;
				globalbestkey = bestfoundkey;
				globalbestplaintext = bestplaintext;
				System.out.println("Found a better global key in restart: " + (totalrestarts - restarts)); //$NON-NLS-1$
				System.out.println("-> Best global cost:" + globalbestkeycost); //$NON-NLS-1$
				System.out.println("-> Best global plaintext:" + MapNumbersIntoTextSpace(globalbestplaintext, defaultAlphabet, globalbestplaintextlength)); //$NON-NLS-1$
				System.out.println("-> Best global key:"); //$NON-NLS-1$
				System.out.println(ConvertGrilleKeyToString(globalbestkey, grilleSize / 2));
			}
			restarts--;
			System.out.println("Restarts left: " + restarts); //$NON-NLS-1$
		} while (restarts > 0);
		System.out.println("Hillcimbing terminated after " + totalrestarts + " restarts..."); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println("Best plaintext: " + MapNumbersIntoTextSpace(globalbestplaintext, defaultAlphabet, globalbestplaintextlength)); //$NON-NLS-1$

	}

	/// <summary>
	/// Tries to break the ciphertext using hillclimbing...
	/// </summary>
	/// <param name="ciphertext"></param>
	/// <param name="grilleSize"></param>
	/// <param name="restarts"></param>
	private static int[][] cloneIntMatrix(int[][] runkey) {
		// TODO Auto-generated method stub
		int[][] cloned = new int[runkey.length][runkey.length > 0 ? runkey[0].length : 0];
		for (int i = 0; i < cloned.length; i++) {
			int[] subarr = cloned[i];
			for (int j = 0; j < subarr.length; j++) {
				cloned[i][j] = runkey[i][j];
			}
		}
		return cloned;
	}

	private static void HillclimbGrilleRandomly(int[] ciphertext, int grilleSize, int restarts, NGramFrequencies grams)
	{            
		System.out.println("HC randomly now..."); //$NON-NLS-1$
		//var grams = new Bigrams("en", false);            
		//var grams = new Tetragrams("en", false);      
		//var grams = new Pentagrams("en", false);
		//var grams = new Tetragrams("english_quadgrams.txt");
//		var grams = new Pentagrams("english_quintgrams.txt");
		//var grams = new Hexagrams("en");

		Rotation rotation = Rotation.Right;
		Random random = new Random();

		int[][] globalbestkey;
		var globalbestkeycost = Double.NEGATIVE_INFINITY;
		int[] globalbestplaintext;
		var globalbestplaintextlength = 0;
		var totalrestarts = restarts;
		do
		{
			//1. Generate Random Key
			var runkey = GenerateRandomKey(grilleSize);

			//2. Perform Hillclimbing                
			var bestfoundkey = new int[grilleSize / 2][grilleSize / 2];
			var bestkeycost = Double.NEGATIVE_INFINITY;
			var bestplaintext = new int[0];
			
			int changes = 1000;
			int cycles = 100;

			do
			{                    
				var copykey = cloneIntMatrix(runkey);
				var x = random.nextInt(grilleSize / 2);
				var y = random.nextInt(grilleSize / 2);
				var s = random.nextInt(3);
				copykey[x][y] = s;
				var plaintext = new GrilleDecrypt(ciphertext, copykey, grilleSize, rotation).Decrypt();
				//rate using cost function
				double cost = calculateCost(grams, plaintext);
				if (cost > bestkeycost)
				{
					changes = 1000;
					bestkeycost = cost;
					bestfoundkey = cloneIntMatrix(copykey);
					bestplaintext = plaintext;
					runkey = cloneIntMatrix(copykey);

					if (bestkeycost > globalbestkeycost)
					{
						globalbestkeycost = bestkeycost;
						globalbestkey = bestfoundkey;
						globalbestplaintext = bestplaintext;
						System.out.println("Found a better global key in restart: " + (totalrestarts - restarts)); //$NON-NLS-1$
						System.out.println("-> Best global cost:" + globalbestkeycost); //$NON-NLS-1$
						System.out.println("-> Best global plaintext:" + MapNumbersIntoTextSpace(globalbestplaintext, defaultAlphabet, globalbestplaintextlength)); //$NON-NLS-1$
						System.out.println("-> Best global key:"); //$NON-NLS-1$
						System.out.println(ConvertGrilleKeyToString(globalbestkey, grilleSize / 2));
					}
				}

				changes--;     

				if(changes == 0)
				{
					cycles--;
					changes = 1000;
					for(int i = 0; i < 10; i++)
					{
						x = random.nextInt(grilleSize / 2);
						y = random.nextInt(grilleSize / 2);
						s = random.nextInt(3);
						runkey[x][y] = s;
					}
				}

			} while (cycles > 0);                
			restarts--;
			System.out.println("Restarts left: " + restarts); //$NON-NLS-1$
		} while (restarts > 0);
		System.out.println("Hillcimbing terminated after " + totalrestarts + " restarts..."); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static void SAGrille(int[] ciphertext, int grilleSize, double temperature, NGramFrequencies grams)
	{
		Rotation rotDir = Rotation.Right;
		var random = new Random();

		//var grams = new Bigrams("en", false);            
		//var grams = new Tetragrams("en", false);      
		//var grams = new Pentagrams("en", false);
		//var grams = new Tetragrams("english_quadgrams.txt");
//		var grams = new Pentagrams("english_quintgrams.txt");
		//var grams = new Hexagrams("en");

		//1. Generate Random Key
		var runkey = GenerateRandomKey(grilleSize);

		//1. Perform SA                
		var bestfoundkey = new int[grilleSize / 2][grilleSize / 2];
		var bestkeycost = Double.NEGATIVE_INFINITY;
		var bestplaintext = new int[0];

		do
		{
			//change in neighborhood 1: change one hole
			for (int x = 0; x < grilleSize / 2; x++)
			{
				for (int y = 0; y < grilleSize / 2; y++)
				{                        
					for (int s = 0; s < 4; s++)
					{
						var copykey = cloneIntMatrix(runkey);
						if (copykey[x][y] == s)
						{
							continue;
						}
						copykey[x][y] = s;
						var plaintext = new GrilleDecrypt(ciphertext, copykey, grilleSize, rotDir).Decrypt();
						//rate using cost function
						double cost = calculateCost(grams, plaintext);
						var exp = -Math.abs(bestkeycost - cost) / temperature;
						var prob = Math.exp(exp);

						if (cost >= bestkeycost || random.nextDouble() < prob)
						{
							if (cost > bestkeycost)
							{
								bestkeycost = cost;
								bestfoundkey = cloneIntMatrix(copykey);
								bestplaintext = plaintext;
								System.out.println("\r-> Best plaintext:" + MapNumbersIntoTextSpace(bestplaintext, defaultAlphabet)); //$NON-NLS-1$
								System.out.println("-> Best key:"); //$NON-NLS-1$
								System.out.println(ConvertGrilleKeyToString(bestfoundkey, grilleSize / 2));
								System.out.println("-> Best cost:" + bestkeycost); //$NON-NLS-1$
								System.out.println("-> Temperature:" + temperature); //$NON-NLS-1$
								System.out.println("-> Probability:" + prob); //$NON-NLS-1$
							}
							runkey = cloneIntMatrix(copykey);
						}
					}
				}
			}

			//change 2 in neighborhood: exchange two hole
			for (int x = 0; x < grilleSize / 2; x++)
			{
				for (int y = 0; y < grilleSize / 2; y++)
				{
					for (int x2 = x; x2 < grilleSize / 2; x2++)
					{
						for (int y2 = y; y2 < grilleSize / 2; y2++)
						{
							if (x == x2 && y == y2)
							{
								continue;
							}
							var copykey = cloneIntMatrix(runkey);
							var swap = copykey[x][y];
							copykey[x][y] = copykey[x2][y2];
							copykey[x2][y2] = swap;

							var plaintext = new GrilleDecrypt(ciphertext, copykey, grilleSize, rotDir).Decrypt();
							//rate using cost function
							double cost = calculateCost(grams, plaintext);
							var exp = -Math.abs(bestkeycost - cost) / temperature;
							var prob = Math.exp(exp);

							if (cost >= bestkeycost || random.nextDouble() < prob)
							{
								if (cost > bestkeycost)
								{
									bestkeycost = cost;
									bestfoundkey = cloneIntMatrix(copykey);
									bestplaintext = plaintext;
									System.out.println("\r-> Best plaintext:" + MapNumbersIntoTextSpace(bestplaintext, defaultAlphabet)); //$NON-NLS-1$
									System.out.println("-> Best key:"); //$NON-NLS-1$
									System.out.println(ConvertGrilleKeyToString(bestfoundkey, grilleSize / 2));
									System.out.println("-> Best cost:" + bestkeycost); //$NON-NLS-1$
									System.out.println("-> Temperature:" + temperature); //$NON-NLS-1$
									System.out.println("-> Probability:" + prob); //$NON-NLS-1$
								}
								runkey = cloneIntMatrix(copykey);
							}
						}
					}
				}
			}

			//change 3 in neighborhood: rotate
			for (int rotation = 1; rotation < 4; rotation++)
			{
				var copykey = cloneIntMatrix(runkey);
				for (int x = 0; x < grilleSize / 2; x++)
				{
					for (int y = 0; y < grilleSize / 2; y++)
					{
						copykey[x][y] = (copykey[x][y] + 1) % 4;
					}
				}

				var plaintext = new GrilleDecrypt(ciphertext, copykey, grilleSize, rotDir).Decrypt();
				//rate using cost function
				double cost = calculateCost(grams, plaintext);
				var exp = -Math.abs(bestkeycost - cost) / temperature;
				var prob = Math.exp(exp);

				if (cost >= bestkeycost || random.nextDouble() < prob)
				{
					if (cost > bestkeycost)
					{
						bestkeycost = cost;
						bestfoundkey = cloneIntMatrix(copykey);
						bestplaintext = plaintext;
						System.out.println("\r-> Best plaintext:" + MapNumbersIntoTextSpace(bestplaintext, defaultAlphabet)); //$NON-NLS-1$
						System.out.println("-> Best key:"); //$NON-NLS-1$
						System.out.println(ConvertGrilleKeyToString(bestfoundkey, grilleSize / 2));
						System.out.println("-> Best cost:" + bestkeycost); //$NON-NLS-1$
						System.out.println("-> Temperature:" + temperature); //$NON-NLS-1$
						System.out.println("-> Probability:" + prob); //$NON-NLS-1$
					}
					runkey = cloneIntMatrix(copykey);
				}
			}

			temperature = temperature - 0.00001;                
			System.out.println("-> Temperature:" + temperature); //$NON-NLS-1$

		} while (temperature > 0);
		//System.out.println("SA terminated ...");
	}

	
	private static boolean[][] rotateBoolarr(boolean[][] arr, int times) {
		int[][] matrix = new int[arr.length][arr.length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = arr[i][j] ? 1 : 0;
			}
		}
		for (int rotNr = 0; rotNr < times; rotNr++) {
			matrix = RotateMatrix(matrix, matrix.length);
		}
		boolean[][] result = new boolean[matrix.length][matrix.length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result.length; j++) {
				result[i][j] = matrix[i][j] == 1;
			}
		}
		return result;
	}
	private static boolean[][] subBoolArr(boolean[][] full, int xOffset, int yOffset, int size) {
		boolean[][] result = new boolean[size][size];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result.length; j++) {
				result[i][j] = full[xOffset+i][yOffset+j];
			}
		}
		return result;
	}

	public static int[][] keyFromTecleFormat(FleissnerGrille grille) {
		return keyFromTecleFormat(grille.getGrilleFilled());
	}
	public static int[][] keyFromTecleFormat(int[] tecleForm) {
		int size = ((int) Math.floor(Math.sqrt(tecleForm.length/2)))*2;
		FleissnerGrille grille = new FleissnerGrille(size);
		grille.useTemplate(tecleForm, size);
		return keyFromTecleFormat(grille.getGrilleFilled());
	}
	public static boolean[][] tecleFormatToBool(int[] tecleForm) {
		int[][] kopalForm = keyFromTecleFormat(tecleForm);
		int size = ((int) Math.floor(Math.sqrt(tecleForm.length/2)))*2;
		GrilleKey key = new GrilleKey(kopalForm, size, Rotation.Left);
		return key.toFullMatrix();
	}
	public static FleissnerGrille tecleFormatFromKey(int[][] key) {
		GrilleKey grille = new GrilleKey(key, key.length*2, Rotation.Right);
		return grille.toTecleKey();
	}
	
	public static int[][] keyFromTecleFormat(boolean[][] boolform) {
		int size = boolform.length;
		int half = size/2;

		int[][] kopalform = new int[half][half];

		int xOffset;
		int yOffset;
		int quarterIdx;
		boolean[][] boolquarter;
		int counterRotationTimes;


		xOffset = 0;
		yOffset = 0;
		quarterIdx = 0;
		boolquarter = subBoolArr(boolform, xOffset, yOffset, half);
		counterRotationTimes = 0; // no rot
		boolquarter = rotateBoolarr(boolquarter, counterRotationTimes);
//		System.out.println();
//		System.out.println(Arr2Text(boolquarter));

		for (int i = 0; i < half; i++) {
			for (int j = 0; j < half; j++) {
					if (boolquarter[i][j]) {
						kopalform[i][j] = quarterIdx;
					}
			}
		}

		xOffset = half;
		yOffset = 0;
		quarterIdx = 1;
		boolquarter = subBoolArr(boolform, xOffset, yOffset, half);
		counterRotationTimes = 3;
		boolquarter = rotateBoolarr(boolquarter, counterRotationTimes);
//		System.out.println();
//		System.out.println(Arr2Text(boolquarter));

		for (int i = 0; i < half; i++) {
			for (int j = 0; j < half; j++) {
					if (boolquarter[i][j]) {
						kopalform[i][j] = quarterIdx;
					}
			}
		}

		xOffset = half;
		yOffset = half;
		quarterIdx = 2;
		boolquarter = subBoolArr(boolform, xOffset, yOffset, half);
		counterRotationTimes = 2;
		boolquarter = rotateBoolarr(boolquarter, counterRotationTimes);
//		System.out.println();
//		System.out.println(Arr2Text(boolquarter));

		for (int i = 0; i < half; i++) {
			for (int j = 0; j < half; j++) {
					if (boolquarter[i][j]) {
						kopalform[i][j] = quarterIdx;
					}
			}
		}

		xOffset = 0;
		yOffset = half;
		quarterIdx = 3;
		boolquarter = subBoolArr(boolform, xOffset, yOffset, half);
		counterRotationTimes = 1;
		boolquarter = rotateBoolarr(boolquarter, counterRotationTimes);
//		System.out.println();
//		System.out.println(Arr2Text(boolquarter));

		for (int i = 0; i < half; i++) {
			for (int j = 0; j < half; j++) {
					if (boolquarter[i][j]) {
						kopalform[i][j] = quarterIdx;
					}
			}
		}
		
		return kopalform;

	}

	/// <summary>
	/// Method encrypts a given text using the given key
	/// </summary>
	/// <param name="plaintext"></param>
	/// <param name="key"></param>
	/// <param name="grilleSize"></param>
	/// <param name="rotation"></param>
	/// <returns></returns>
	public static class Grille {
		int[] plaintext;
		int[][] key;
		int grilleSize;
		Rotation rotation;
		public Grille(int[][] key, int grilleSize, Rotation rotation) {
			super();
			this.key = key;
			this.grilleSize = grilleSize;
			this.rotation = rotation;
		}
	}
	public static class GrilleKey extends Grille {
		int[][] A;
		int[][] B;
		int[][] C;
		int[][] D;
		private int[] ciphertext;
		private int position;
		private int[][] M1;
		private int[][] M2;
		private int[][] M3;
		private int[][] M4;

		public GrilleKey(int[][] key, int grilleSize, Rotation rotation) {
			super(key, grilleSize, rotation);
			this.M1 = key;
			this.M2 = RotateMatrix(this.M1, grilleSize / 2);
			this.M3 = RotateMatrix(this.M2, grilleSize / 2);
			this.M4 = RotateMatrix(this.M3, grilleSize / 2);
		}
		
		public boolean[][] toFullMatrix() {
			boolean[][] result = new boolean[this.grilleSize][this.grilleSize];
			int xOff;
			int yOff;
			int[][] subRef;
			int counter;

			xOff = 0;
			yOff = 0;
			subRef = M1;
			counter = 0;
			for (int i = 0; i < grilleSize/2; i++) {
				for (int k = 0; k < grilleSize/2; k++) {
					result[xOff + i][yOff + k] = subRef[i][k] == counter;
				}
			}

			xOff = grilleSize/2;
			yOff = 0;
			subRef = M2;
			counter = 1;
			for (int i = 0; i < grilleSize/2; i++) {
				for (int k = 0; k < grilleSize/2; k++) {
					result[xOff + i][yOff + k] = subRef[i][k] == counter;
				}
			}

			xOff = grilleSize/2;
			yOff = grilleSize/2;
			subRef = M3;
			counter = 2;
			for (int i = 0; i < grilleSize/2; i++) {
				for (int k = 0; k < grilleSize/2; k++) {
					result[xOff + i][yOff + k] = subRef[i][k] == counter;
				}
			}

			xOff = 0;
			yOff = grilleSize/2;
			subRef = M4;
			counter = 3;
			for (int i = 0; i < grilleSize/2; i++) {
				for (int k = 0; k < grilleSize/2; k++) {
					result[xOff + i][yOff + k] = subRef[i][k] == counter;
				}
			}
			
			return result;
		}
		
		public List<Integer> toTecleFormat() {
			List<Integer> result = new LinkedList<>();
			boolean[][] fullMatrix = toFullMatrix();
			for (int i = 0; i < grilleSize; i++) {
				for (int k = 0; k < grilleSize; k++) {
					if (fullMatrix[i][k]) {
						result.add(i);
						result.add(k);
					}
				}
			}
			return result;
		}
		
		public int[] toTecleIntArray() {
			List<Integer> list = toTecleFormat();
			int[] result = new int[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Integer integer = list.get(i);
				result[i] = integer;
			}
			return result;
		}
		
		public FleissnerGrille toTecleKey() {
			FleissnerGrille grille = new FleissnerGrille(this.grilleSize);
			grille.clearGrille();
			grille.useTemplate(this.toTecleIntArray(), this.grilleSize);
			return grille;
		}

		public List<Point> positions() {
			LinkedList<Point> result = new LinkedList<>();
			return result;

			
		}
	}
	
	public static class GrilleEncrypt extends Grille {
		int[][] A;
		int[][] B;
		int[][] C;
		int[][] D;
		private int[] ciphertext;
		private int position;
		private int[][] M1;
		private int[][] M2;
		private int[][] M3;
		private int[][] M4;

		public GrilleEncrypt(int[] plaintext, int[][] key, int grilleSize, Rotation rotation) {
			super(key, grilleSize, rotation);
			this.plaintext = plaintext;
			this.ciphertext = new int[plaintext.length];
			this.position = 0;

			this.M1 = key;
			this.M2 = RotateMatrix(this.M1, grilleSize / 2);
			this.M3 = RotateMatrix(this.M2, grilleSize / 2);
			this.M4 = RotateMatrix(this.M3, grilleSize / 2);
		}

        public void EncryptTopOrientation()
        {
            A = M1;
            B = M2;
            C = M3;
            D = M4;

            for (int j = 0; j < grilleSize; j++)
            {
                for (int i = 0; i < grilleSize; i++)
                {
                    if (i < grilleSize / 2 && j < grilleSize / 2)
                    {
                        //A
                        if (A[i][j] == 0)
                        {
                            encryptAt(j, i);
                            position++;
                        }
                    }
                    else if (i >= grilleSize / 2 && j < grilleSize / 2)
                    {
                        //B
                        if (B[i - grilleSize / 2][j] == 1)
                        {
                            encryptAt(j, i);
                            position++;
                        }
                    }
                    else if (i >= grilleSize / 2 && j >= grilleSize / 2)
                    {
                        //C
                        if (C[i - grilleSize / 2][j - grilleSize / 2] == 2)
                        {
                            encryptAt(j, i);
                            position++;
                        }
                    }
                    else if (i < grilleSize / 2 && j >= grilleSize / 2)
                    {
                        //D
                        if (D[i][j - grilleSize / 2] == 3)
                        {
                            encryptAt(j, i);
                            position++;
                        }
                    }
                }
            }
        }

		public void EncryptRightOrientation()  {
			//encrypt 1 rotation                                            
			A = M2;
			B = M3;
			C = M4;
			D = M1;

			for (int j = 0; j < grilleSize; j++)
			{
				for (int i = 0; i < grilleSize; i++)
				{
					if (i < grilleSize / 2 && j < grilleSize / 2)
					{
						//D
						if (D[i][j] == 3)
						{
							encryptAt(j, i);
							position++;
						}
					}
					else if (i >= grilleSize / 2 && j < grilleSize / 2)
					{
						//A
						if (A[i - grilleSize / 2][j] == 0)
						{
							encryptAt(j, i);
							position++;
						}
					}
					else if (i >= grilleSize / 2 && j >= grilleSize / 2)
					{
						//B
						if (B[i - grilleSize / 2][j - grilleSize / 2] == 1)
						{
							encryptAt(j, i);
							position++;
						}
					}
					else if (i < grilleSize / 2 && j >= grilleSize / 2)
					{
						//C
						if (C[i][j - grilleSize / 2] == 2)
						{
							encryptAt(j, i);
							position++;
						}
					}
				}
			}
		}

		public void EncryptBottomOrientation() {
			//encrypt 2 rotation 
			A = M3;
			B = M4;
			C = M1;
			D = M2;

			for (int j = 0; j < grilleSize; j++)
			{
				for (int i = 0; i < grilleSize; i++)
				{
					if (i < grilleSize / 2 && j < grilleSize / 2)
					{
						//C
						if (C[i][j] == 2)
						{
							encryptAt(j, i);
							position++;
						}
					}
					else if (i >= grilleSize / 2 && j < grilleSize / 2)
					{
						//D
						if (D[i - grilleSize / 2][j] == 3)
						{
							encryptAt(j, i);
							position++;
						}
					}
					else if (i >= grilleSize / 2 && j >= grilleSize / 2)
					{
						//A
						if (A[i - grilleSize / 2][j - grilleSize / 2] == 0)
						{
							encryptAt(j, i);
							position++;
						}
					}
					else if (i < grilleSize / 2 && j >= grilleSize / 2)
					{
						//B
						if (B[i][j - grilleSize / 2] == 1)
						{
							encryptAt(j, i);
							position++;
						}
					}
				}
			}
		}

		public void EncryptLeftOrientation() {
			//encrypt 3 rotation             
			A = M4;
			B = M1;
			C = M2;
			D = M3;

			for (int j = 0; j < grilleSize; j++)
			{
				for (int i = 0; i < grilleSize; i++)
				{
					if (i < grilleSize / 2 && j < grilleSize / 2)
					{
						//B
						if (B[i][j] == 1)
						{
							encryptAt(j, i);
							position++;
						}
					}
					else if (i >= grilleSize / 2 && j < grilleSize / 2)
					{
						//C
						if (C[i - grilleSize / 2][j] == 2)
						{
							encryptAt(j, i);
							position++;
						}
					}
					else if (i >= grilleSize / 2 && j >= grilleSize / 2)
					{
						//D
						if (D[i - grilleSize / 2][j - grilleSize / 2] == 3)
						{
							encryptAt(j, i);
							position++;
						}
					}
					else if (i < grilleSize / 2 && j >= grilleSize / 2)
					{
						//A
						if (A[i][j - grilleSize / 2] == 0)
						{
							encryptAt(j, i);
							position++;
						}
					}
				}
			}
		}

		private void encryptAt(int j, int i) {
			int ciphertextIdx = j * grilleSize + i;
			int plaintextIdx = position;

			int runningPlaintextIdx = plaintextIdx;
			int runningCiphertextIdx = ciphertextIdx;
			while(runningCiphertextIdx < ciphertext.length) {
				if (runningPlaintextIdx >= plaintext.length) {
					ciphertext[runningCiphertextIdx] = 0;
					throw new RuntimeException("could not encrypt, plaintext is not of length of the square of grille size"); //$NON-NLS-1$
				} else {
					ciphertext[runningCiphertextIdx] = plaintext[runningPlaintextIdx];
				}
				
				runningCiphertextIdx += grilleSize*grilleSize;
				runningPlaintextIdx += grilleSize*grilleSize;
			}
		}

		public int[] Encrypt()
		{
			if (rotation == Rotation.Right)
			{
				EncryptTopOrientation();
				EncryptRightOrientation();
				EncryptBottomOrientation();
				EncryptLeftOrientation();
			}
			else if (rotation == Rotation.Left)
			{
				EncryptTopOrientation();
				EncryptLeftOrientation();
				EncryptBottomOrientation();
				EncryptRightOrientation();
			}
			return ciphertext;
		}

	}

	
	public static class GrilleDecrypt extends Grille {

		int[][] A;
		int[][] B;
		int[][] C;
		int[][] D;                                              
		private int[] ciphertext;
		private int[][] M1;
		private int[][] M2;
		private int[][] M3;
		private int[][] M4;
		private int position;

		public GrilleDecrypt(int[] ciphertext, int[][] key, int grilleSize, Rotation rotation) {
			super(key, grilleSize, rotation);
			this.ciphertext = ciphertext;
			this.plaintext = new int[ciphertext.length];
			this.position = 0;
			this.M1 = key;
			this.M2 = RotateMatrix(this.M1, grilleSize / 2);
			this.M3 = RotateMatrix(this.M2, grilleSize / 2);
			this.M4 = RotateMatrix(this.M3, grilleSize / 2);
		}
		public int[] Decrypt() {
			if(this.rotation == Rotation.Right)
			{
				DecryptTopOrientation();
				DecryptRightOrientation();
				DecryptBottomOrientation();
				DecryptLeftOrientation();
			}
			else if(rotation == Rotation.Left)
			{
				DecryptTopOrientation();
				DecryptLeftOrientation();
				DecryptBottomOrientation();
				DecryptRightOrientation();
			}
			return plaintext;
		}
		
		public void DecryptTopOrientation() {
			//decrypt 0 rotation
			A = M1;
			B = M2;
			C = M3;
			D = M4;

			for (int j = 0; j < grilleSize; j++)
			{
				for (int i = 0; i < grilleSize; i++)
				{
					if (i < grilleSize / 2 && j < grilleSize / 2)
					{
						//A
						if (A[i][j] == 0)
						{
							decryptAt(j, i);
							position++;
						}
					}
					else if (i >= grilleSize / 2 && j < grilleSize / 2)
					{
						//B
						if (B[i - grilleSize / 2][j] == 1)
						{
							decryptAt(j, i);
							position++;
						}
					}
					else if (i >= grilleSize / 2 && j >= grilleSize / 2)
					{
						//C
						if (C[i - grilleSize / 2][j - grilleSize / 2] == 2)
						{
							decryptAt(j, i);
							position++;
						}
					}
					else if (i < grilleSize / 2 && j >= grilleSize / 2)
					{
						//D
						if (D[i][j - grilleSize / 2] == 3)
						{
							decryptAt(j, i);
							position++;
						}
					}
				}
			}
		}

		public void DecryptRightOrientation() {
			//decrypt 1 rotation                                            
			A = M2;
			B = M3;
			C = M4;
			D = M1;

			for (int j = 0; j < grilleSize; j++)
			{
				for (int i = 0; i < grilleSize; i++)
				{
					if (i < grilleSize / 2 && j < grilleSize / 2)
					{
						//D
						if (D[i][j] == 3)
						{
							decryptAt(j, i);
							position++;
						}
					}
					else if (i >= grilleSize / 2 && j < grilleSize / 2)
					{
						//A
						if (A[i - grilleSize / 2][j] == 0)
						{
							decryptAt(j, i);
							position++;
						}
					}
					else if (i >= grilleSize / 2 && j >= grilleSize / 2)
					{
						//B
						if (B[i - grilleSize / 2][j - grilleSize / 2] == 1)
						{
							decryptAt(j, i);
							position++;
						}
					}
					else if (i < grilleSize / 2 && j >= grilleSize / 2)
					{
						//C
						if (C[i][j - grilleSize / 2] == 2)
						{
							decryptAt(j, i);
							position++;
						}
					}
				}
			}
		}
		private void decryptAt(int j, int i) {
			int ciphertextIdx = j * grilleSize + i;
			int plaintextIdx = position;

			int runningPlaintextIdx = plaintextIdx;
			int runningCiphertextIdx = ciphertextIdx;
			while(runningCiphertextIdx < ciphertext.length) {
				if (runningCiphertextIdx >= ciphertext.length) {
					throw new RuntimeException("could not decrypt, ciphertext is not of length of the square of grille size"); //$NON-NLS-1$
				} else {
					plaintext[runningPlaintextIdx] = ciphertext[runningCiphertextIdx];
				}
//				plaintext[position] = ciphertext[j * grilleSize + i]
				
				runningCiphertextIdx += grilleSize*grilleSize;
				runningPlaintextIdx += grilleSize*grilleSize;
			}
		}

		public void DecryptBottomOrientation() {
			//decrypt 2 rotation 
			A = M3;
			B = M4;
			C = M1;
			D = M2;

			for (int j = 0; j < grilleSize; j++)
			{
				for (int i = 0; i < grilleSize; i++)
				{
					if (i < grilleSize / 2 && j < grilleSize / 2)
					{
						//C
						if (C[i][j] == 2)
						{
							decryptAt(j, i);
							position++;
						}
					}
					else if (i >= grilleSize / 2 && j < grilleSize / 2)
					{
						//D
						if (D[i - grilleSize / 2][j] == 3)
						{
							decryptAt(j, i);
							position++;
						}
					}
					else if (i >= grilleSize / 2 && j >= grilleSize / 2)
					{
						//A
						if (A[i - grilleSize / 2][j - grilleSize / 2] == 0)
						{
							decryptAt(j, i);
							position++;
						}
					}
					else if (i < grilleSize / 2 && j >= grilleSize / 2)
					{
						//B
						if (B[i][j - grilleSize / 2] == 1)
						{
							decryptAt(j, i);
							position++;
						}
					}
				}
			}
		}

		public void DecryptLeftOrientation() {
			//decrypt 3 rotation 
			A = M4;
			B = M1;
			C = M2;
			D = M3;

			for (int j = 0; j < grilleSize; j++)
			{
				for (int i = 0; i < grilleSize; i++)
				{
					if (i < grilleSize / 2 && j < grilleSize / 2)
					{
						//B
						if (B[i][j] == 1)
						{
							decryptAt(j, i);
							position++;
						}
					}
					else if (i >= grilleSize / 2 && j < grilleSize / 2)
					{
						//C
						if (C[i - grilleSize / 2][j] == 2)
						{
							decryptAt(j, i);
							position++;
						}
					}
					else if (i >= grilleSize / 2 && j >= grilleSize / 2)
					{
						//D
						if (D[i - grilleSize / 2][j - grilleSize / 2] == 3)
						{
							decryptAt(j, i);
							position++;
						}
					}
					else if (i < grilleSize / 2 && j >= grilleSize / 2)
					{
						//A
						if (A[i][j - grilleSize / 2] == 0)
						{
							decryptAt(j, i);
							position++;
						}
					}
				}
			}
		}

	}
	

	/// <summary>
	/// Outputs the grille key
	/// </summary>
	/// <param name="key"></param>
	/// <param name="size"></param>
	/// <returns></returns>
	private static String ConvertGrilleKeyToString(int[][] key, int size)
	{
		StringBuilder builder = new StringBuilder();

		for (int y = 0; y < size; y++)
		{
			for (int x = 0; x < size; x++)
			{
				builder.append(key[x][y] + " "); //$NON-NLS-1$
			}
			if (y < size - 1)
			{
				builder.append("\n"); //$NON-NLS-1$
			}
		}
		return builder.toString();
	}

	/// <summary>
	/// Rotates an array by 90°
	/// </summary>
	/// <param name="matrix"></param>
	/// <param name="n"></param>
	/// <returns></returns>
	private static int[][] RotateMatrix(int[][] matrix, int n)
	{
		int[][] ret = new int[n][n];

		for (int i = 0; i < n; ++i)
		{
			for (int j = 0; j < n; ++j)
			{
				ret[i][j] = matrix[j][n - i - 1];
			}
		}
		return ret;
	}

	/// <summary>
	/// Maps a given string into the "numberspace" defined by the alphabet
	/// </summary>
	/// <param name="text"></param>
	/// <param name="alphabet"></param>
	/// <returns></returns>
	public static int[] MapTextIntoNumberSpace(String text, String alphabet)
	{
		var numbers = new int[text.length()];
		var position = 0;
		for (var c : text.toCharArray())
		{
			numbers[position] = alphabet.indexOf(c);
			position++;
		}
		return numbers;
	}

	/// <summary>
	/// Maps a given array of numbers into the "textspace" defined by the alphabet
	/// </summary>
	/// <param name="numbers"></param>
	/// <param name="alphabet"></param>
	/// <returns></returns>
	public static String MapNumbersIntoTextSpace(int[] numbers, String alphabet) {
		return MapNumbersIntoTextSpace(numbers, alphabet, -1);
	}
	public static String MapNumbersIntoTextSpace(int[] numbers, String alphabet, int length)
	{
		var builder = new StringBuilder();
		var position = 0;
		for (var i : numbers)
		{
			builder.append(alphabet.charAt(i));
			position++;
			if (position == length)
			{
				break;
			}
		}
		return builder.toString();
	}

}

