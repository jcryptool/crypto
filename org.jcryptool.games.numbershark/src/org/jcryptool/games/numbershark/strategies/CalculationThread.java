//-----BEGIN DISCLAIMER-----
/*******************************************************************************
* Copyright (c) 2011, 2021 JCrypTool Team and Contributors
* 
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
//-----END DISCLAIMER-----
package org.jcryptool.games.numbershark.strategies;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.jcryptool.core.logging.utils.LogUtil;

/**
 * Starts the calculation Thread
 * 
 * @author Johannes Spaeth
 * @version 0.9.5
 */
public class CalculationThread implements IRunnableWithProgress {
    private int min = 2;
    private int max = 100;
    private int stoppedAt = 1000;
    private int selectedStrategy = 0;

    public CalculationThread(int min, int max, int selectedStrategy) {
        this.min = min;
        this.max = max;
        this.selectedStrategy = selectedStrategy;
    }

    @Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        try {
            switch (selectedStrategy) {
            case 0:
                break;
            case 1:
                monitor.beginTask(Messages.ProgressDialog_0, IProgressMonitor.UNKNOWN);
                ZahlenhaiBestwerte.main(min, max, monitor);
                outputTable = ZahlenhaiBestwerte.getOutput();
                stoppedAt = ZahlenhaiBestwerte.getStoppedAt();
                break;

            case 2:
                monitor.beginTask(Messages.ProgressDialog_1, IProgressMonitor.UNKNOWN);
                MaximizeStrategy calc = new MaximizeStrategy(min, max, monitor);
                outputTable = calc.getOutput();
                stoppedAt = calc.getStoppedAt();
                break;

            case 3:
                monitor.beginTask(Messages.ProgressDialog_1, IProgressMonitor.UNKNOWN);
                VanNekStrategy calc1 = new VanNekStrategy(min, max, monitor);
                outputTable = calc1.getOutput();
                stoppedAt = calc1.getStoppedAt();
                break;

            case 4:
                monitor.beginTask(Messages.ProgressDialog_1, IProgressMonitor.UNKNOWN);
                Schu1Strategy calcSchu1 = new Schu1Strategy(min, max, monitor);
                outputTable = calcSchu1.getOutput();
                stoppedAt = calcSchu1.getStoppedAt();
                break;
            }
        } catch (InterruptedException ex) {
            LogUtil.logError(ex);
        }
        monitor.done();

        if (monitor.isCanceled()) {
            throw new InterruptedException("The long running operation was cancelled");
        }
    }

    public String[][] getSharkOutput() {
        return outputTable;
    }

    public int getStoppedAt() {
        return stoppedAt;
    }

    private static int[][] readOptimalMoves() {
    	var resource = CalculationThread.class.getClassLoader().getResourceAsStream("org/jcryptool/games/numbershark/strategies/optimalmoves.txt");
    	var lines = new LinkedList<String>();
    	try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
            while (reader.ready()) {
                String line = reader.readLine();
                if(! line.trim().isEmpty()) {
                	lines.add(line);
                }
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	int[][] result = new int[lines.size()][];
    	for (int i = 0; i < result.length; i++) {
    		var line = lines.get(i);
    		var split = line.split(",");
    		var sequence = new LinkedList<Integer>();
    		for (int j = 0; j < split.length; j++) {
				String part = split[j];
				int number = Integer.parseInt(part);
				sequence.add(number);
			}
    		var subarr = new int[sequence.size()];
    		for (int j = 0; j < subarr.length; j++) {
				subarr[j] = sequence.get(j);
			}
    		result[i] = subarr;
		}
    	return result;
    }
    private static int[][] optimalMoves = readOptimalMoves(); 
    private static String[][] outputTable = makeOutputTableFromRaw(optimalMoves);

	private static String[][] makeOutputTableFromRaw(int[][] opt) {
		String[][] result = new String[opt.length][5];
		for (int i = 0; i < opt.length; i++) {
			int[] optSeq = opt[i];
			int n = i+2;
			int allNumbersSum = 0;
			for (int j = 1; j <= n; j++) {
				allNumbersSum = allNumbersSum + j;
			}
			int sum = 0;
			String repr = "";
			for (int j = 0; j < optSeq.length; j++) {
				sum = sum + optSeq[j];
				if(repr.length() == 0) {
					repr = "" + optSeq[j];
				} else {
					repr = repr + "," + optSeq[j];
				}
			}
			int sharkSum = allNumbersSum - sum;
			result[i][0] = "" + n;
			result[i][1] = "" + sum;
			result[i][2] = "" + sharkSum;
			result[i][3] = repr;
			result[i][4] = "0ms";
		}
		return result;
	}

}
