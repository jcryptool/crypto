// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.ECDH.data;

/**
 * This class contains all the (small) curves that can be used
 * for cryptography with the ECDH demo.
 * @author Geert-Jan Smulders
 * @version 0.1
 */
public class Curves {
	public static final int[][] ECFp = {
		{3, 0, 1},
		{3, 0, 2},
		{5, 2, 2},
		{5, 2, 3},
		{5, 3, 1},
		{5, 3, 4},
		{7, 1, 2},
		{7, 1, 5},
		{7, 2, 2},
		{7, 2, 5},
		{7, 4, 2},
		{7, 4, 5},
		{11, 2, 3},
		{11, 2, 8},
		{11, 6, 1},
		{11, 6, 10},
		{11, 7, 4},
		{11, 7, 7},
		{11, 8, 2},
		{11, 8, 9},
		{11, 10, 5},
		{11, 10, 6},
		{13, 1, 3},
		{13, 1, 10},
		{13, 3, 3},
		{13, 3, 10},
		{13, 4, 2},
		{13, 4, 11},
		{13, 9, 3},
		{13, 9, 10},
		{13, 10, 2},
		{13, 10, 11},
		{13, 12, 2},
		{13, 12, 11},
		{17, 3, 8},
		{17, 3, 9},
		{17, 5, 1},
		{17, 5, 16},
		{17, 6, 6},
		{17, 6, 11},
		{17, 7, 3},
		{17, 7, 14},
		{17, 10, 5},
		{17, 10, 12},
		{17, 11, 7},
		{17, 11, 10},
		{17, 12, 4},
		{17, 12, 13},
		{17, 14, 2},
		{17, 14, 15},
		{19, 1, 3},
		{19, 1, 16},
		{19, 4, 5},
		{19, 4, 14},
		{19, 5, 2},
		{19, 5, 17},
		{19, 6, 5},
		{19, 6, 14},
		{19, 7, 3},
		{19, 7, 16},
		{19, 9, 5},
		{19, 9, 14},
		{19, 11, 3},
		{19, 11, 16},
		{19, 16, 2},
		{19, 16, 17},
		{19, 17, 2},
		{19, 17, 17},
		{23, 5, 6},
		{23, 5, 17},
		{23, 7, 5},
		{23, 7, 18},
		{23, 10, 9},
		{23, 10, 14},
		{23, 11, 7},
		{23, 11, 16},
		{23, 14, 4},
		{23, 14, 19},
		{23, 15, 11},
		{23, 15, 12},
		{23, 17, 3},
		{23, 17, 20},
		{23, 19, 8},
		{23, 19, 15},
		{23, 20, 2},
		{23, 20, 21},
		{23, 21, 10},
		{23, 21, 13},
		{23, 22, 1},
		{23, 22, 22},
		{29, 2, 4},
		{29, 2, 25},
		{29, 3, 5},
		{29, 3, 24},
		{29, 8, 3},
		{29, 8, 26},
		{29, 10, 12},
		{29, 10, 17},
		{29, 11, 9},
		{29, 11, 20},
		{29, 12, 11},
		{29, 12, 18},
		{29, 14, 6},
		{29, 14, 23},
		{29, 15, 14},
		{29, 15, 15},
		{29, 17, 13},
		{29, 17, 16},
		{29, 18, 8},
		{29, 18, 21},
		{29, 19, 1},
		{29, 19, 28},
		{29, 21, 7},
		{29, 21, 22},
		{29, 26, 2},
		{29, 26, 27},
		{29, 27, 10},
		{29, 27, 19},
		{31, 1, 1},
		{31, 1, 30},
		{31, 2, 15},
		{31, 2, 16},
		{31, 4, 8},
		{31, 4, 23},
		{31, 5, 1},
		{31, 5, 30},
		{31, 7, 8},
		{31, 7, 23},
		{31, 8, 4},
		{31, 8, 27},
		{31, 9, 4},
		{31, 9, 27},
		{31, 10, 15},
		{31, 10, 16},
		{31, 14, 4},
		{31, 14, 27},
		{31, 16, 2},
		{31, 16, 29},
		{31, 18, 2},
		{31, 18, 29},
		{31, 19, 15},
		{31, 19, 16},
		{31, 20, 8},
		{31, 20, 23},
		{31, 25, 1},
		{31, 25, 30},
		{31, 28, 2},
		{31, 28, 29},
		{37, 1, 17},
		{37, 1, 20},
		{37, 3, 12},
		{37, 3, 25},
		{37, 4, 12},
		{37, 4, 25},
		{37, 7, 2},
		{37, 7, 35},
		{37, 9, 15},
		{37, 9, 22},
		{37, 10, 17},
		{37, 10, 20},
		{37, 11, 9},
		{37, 11, 28},
		{37, 12, 15},
		{37, 12, 22},
		{37, 16, 15},
		{37, 16, 22},
		{37, 21, 16},
		{37, 21, 21},
		{37, 25, 16},
		{37, 25, 21},
		{37, 26, 17},
		{37, 26, 20},
		{37, 27, 9},
		{37, 27, 28},
		{37, 28, 16},
		{37, 28, 21},
		{37, 30, 12},
		{37, 30, 25},
		{37, 33, 2},
		{37, 33, 35},
		{37, 34, 2},
		{37, 34, 35},
		{37, 36, 9},
		{37, 36, 28},
		{41, 3, 18},
		{41, 3, 23},
		{41, 6, 3},
		{41, 6, 38},
		{41, 7, 4},
		{41, 7, 37},
		{41, 11, 8},
		{41, 11, 33},
		{41, 12, 20},
		{41, 12, 21},
		{41, 13, 1},
		{41, 13, 40},
		{41, 14, 13},
		{41, 14, 28},
		{41, 15, 19},
		{41, 15, 22},
		{41, 17, 11},
		{41, 17, 30},
		{41, 19, 12},
		{41, 19, 29},
		{41, 22, 15},
		{41, 22, 26},
		{41, 24, 17},
		{41, 24, 24},
		{41, 26, 7},
		{41, 26, 34},
		{41, 27, 6},
		{41, 27, 35},
		{41, 28, 9},
		{41, 28, 32},
		{41, 29, 16},
		{41, 29, 25},
		{41, 30, 10},
		{41, 30, 31},
		{41, 34, 5},
		{41, 34, 36},
		{41, 35, 14},
		{41, 35, 27},
		{41, 38, 2},
		{41, 38, 39},
		{43, 1, 21},
		{43, 1, 22},
		{43, 4, 4},
		{43, 4, 39},
		{43, 6, 21},
		{43, 6, 22},
		{43, 9, 8},
		{43, 9, 35},
		{43, 10, 11},
		{43, 10, 32},
		{43, 11, 8},
		{43, 11, 35},
		{43, 13, 1},
		{43, 13, 42},
		{43, 14, 16},
		{43, 14, 27},
		{43, 15, 4},
		{43, 15, 39},
		{43, 16, 11},
		{43, 16, 32},
		{43, 17, 11},
		{43, 17, 32},
		{43, 21, 2},
		{43, 21, 41},
		{43, 23, 8},
		{43, 23, 35},
		{43, 24, 4},
		{43, 24, 39},
		{43, 25, 2},
		{43, 25, 41},
		{43, 31, 16},
		{43, 31, 27},
		{43, 35, 1},
		{43, 35, 42},
		{43, 36, 21},
		{43, 36, 22},
		{43, 38, 1},
		{43, 38, 42},
		{43, 40, 2},
		{43, 40, 41},
		{43, 41, 16},
		{43, 41, 27},
		{47, 5, 5},
		{47, 5, 42},
		{47, 10, 23},
		{47, 10, 24},
		{47, 11, 12},
		{47, 11, 35},
		{47, 13, 17},
		{47, 13, 30},
		{47, 15, 8},
		{47, 15, 39},
		{47, 19, 15},
		{47, 19, 32},
		{47, 20, 7},
		{47, 20, 40},
		{47, 22, 20},
		{47, 22, 27},
		{47, 23, 11},
		{47, 23, 36},
		{47, 26, 3},
		{47, 26, 44},
		{47, 29, 21},
		{47, 29, 26},
		{47, 30, 18},
		{47, 30, 29},
		{47, 31, 14},
		{47, 31, 33},
		{47, 33, 9},
		{47, 33, 38},
		{47, 35, 16},
		{47, 35, 31},
		{47, 38, 22},
		{47, 38, 25},
		{47, 39, 1},
		{47, 39, 46},
		{47, 40, 4},
		{47, 40, 43},
		{47, 41, 19},
		{47, 41, 28},
		{47, 43, 10},
		{47, 43, 37},
		{47, 44, 2},
		{47, 44, 45},
		{47, 45, 6},
		{47, 45, 41},
		{47, 46, 13},
		{47, 46, 34},
		{53, 2, 25},
		{53, 2, 28},
		{53, 3, 7},
		{53, 3, 46},
		{53, 5, 22},
		{53, 5, 31},
		{53, 8, 12},
		{53, 8, 41},
		{53, 12, 3},
		{53, 12, 50},
		{53, 14, 19},
		{53, 14, 34},
		{53, 18, 14},
		{53, 18, 39},
		{53, 19, 6},
		{53, 19, 47},
		{53, 20, 17},
		{53, 20, 36},
		{53, 21, 18},
		{53, 21, 35},
		{53, 22, 26},
		{53, 22, 27},
		{53, 23, 5},
		{53, 23, 48},
		{53, 26, 1},
		{53, 26, 52},
		{53, 27, 23},
		{53, 27, 30},
		{53, 30, 9},
		{53, 30, 44},
		{53, 31, 15},
		{53, 31, 38},
		{53, 32, 10},
		{53, 32, 43},
		{53, 33, 20},
		{53, 33, 33},
		{53, 34, 21},
		{53, 34, 32},
		{53, 35, 4},
		{53, 35, 49},
		{53, 39, 13},
		{53, 39, 40},
		{53, 41, 16},
		{53, 41, 37},
		{53, 45, 11},
		{53, 45, 42},
		{53, 48, 24},
		{53, 48, 29},
		{53, 50, 2},
		{53, 50, 51},
		{53, 51, 8},
		{53, 51, 45},
		{59, 2, 1},
		{59, 2, 58},
		{59, 6, 26},
		{59, 6, 33},
		{59, 8, 8},
		{59, 8, 51},
		{59, 10, 19},
		{59, 10, 40},
		{59, 11, 10},
		{59, 11, 49},
		{59, 13, 20},
		{59, 13, 39},
		{59, 14, 15},
		{59, 14, 44},
		{59, 18, 27},
		{59, 18, 32},
		{59, 23, 3},
		{59, 23, 56},
		{59, 24, 28},
		{59, 24, 31},
		{59, 30, 22},
		{59, 30, 37},
		{59, 31, 18},
		{59, 31, 41},
		{59, 32, 5},
		{59, 32, 54},
		{59, 33, 24},
		{59, 33, 35},
		{59, 34, 4},
		{59, 34, 55},
		{59, 37, 12},
		{59, 37, 47},
		{59, 38, 29},
		{59, 38, 30},
		{59, 39, 11},
		{59, 39, 48},
		{59, 40, 25},
		{59, 40, 34},
		{59, 42, 23},
		{59, 42, 36},
		{59, 43, 14},
		{59, 43, 45},
		{59, 44, 21},
		{59, 44, 38},
		{59, 47, 16},
		{59, 47, 43},
		{59, 50, 7},
		{59, 50, 52},
		{59, 52, 17},
		{59, 52, 42},
		{59, 54, 6},
		{59, 54, 53},
		{59, 55, 13},
		{59, 55, 46},
		{59, 56, 2},
		{59, 56, 57},
		{59, 58, 9},
		{59, 58, 50},
		{61, 1, 6},
		{61, 1, 55},
		{61, 3, 22},
		{61, 3, 39},
		{61, 4, 13},
		{61, 4, 48},
		{61, 5, 13},
		{61, 5, 48},
		{61, 9, 21},
		{61, 9, 40},
		{61, 12, 7},
		{61, 12, 54},
		{61, 13, 6},
		{61, 13, 55},
		{61, 14, 5},
		{61, 14, 56},
		{61, 15, 7},
		{61, 15, 54},
		{61, 16, 18},
		{61, 16, 43},
		{61, 19, 22},
		{61, 19, 39},
		{61, 20, 18},
		{61, 20, 43},
		{61, 22, 2},
		{61, 22, 59},
		{61, 25, 18},
		{61, 25, 43},
		{61, 27, 16},
		{61, 27, 45},
		{61, 34, 7},
		{61, 34, 54},
		{61, 36, 15},
		{61, 36, 46},
		{61, 39, 22},
		{61, 39, 39},
		{61, 41, 15},
		{61, 41, 46},
		{61, 42, 2},
		{61, 42, 59},
		{61, 45, 15},
		{61, 45, 46},
		{61, 46, 16},
		{61, 46, 45},
		{61, 47, 6},
		{61, 47, 55},
		{61, 48, 5},
		{61, 48, 56},
		{61, 49, 16},
		{61, 49, 45},
		{61, 52, 13},
		{61, 52, 48},
		{61, 56, 21},
		{61, 56, 40},
		{61, 57, 21},
		{61, 57, 40},
		{61, 58, 2},
		{61, 58, 59},
		{61, 60, 5},
		{61, 60, 56},
		{67, 1, 28},
		{67, 1, 39},
		{67, 4, 23},
		{67, 4, 44},
		{67, 6, 13},
		{67, 6, 54},
		{67, 9, 19},
		{67, 9, 48},
		{67, 10, 10},
		{67, 10, 57},
		{67, 14, 23},
		{67, 14, 44},
		{67, 15, 6},
		{67, 15, 61},
		{67, 16, 17},
		{67, 16, 50},
		{67, 17, 30},
		{67, 17, 37},
		{67, 19, 6},
		{67, 19, 61},
		{67, 21, 13},
		{67, 21, 54},
		{67, 22, 10},
		{67, 22, 57},
		{67, 23, 2},
		{67, 23, 65},
		{67, 24, 30},
		{67, 24, 37},
		{67, 25, 16},
		{67, 25, 51},
		{67, 26, 30},
		{67, 26, 37},
		{67, 29, 28},
		{67, 29, 39},
		{67, 33, 6},
		{67, 33, 61},
		{67, 35, 10},
		{67, 35, 57},
		{67, 36, 18},
		{67, 36, 49},
		{67, 37, 28},
		{67, 37, 39},
		{67, 39, 18},
		{67, 39, 49},
		{67, 40, 13},
		{67, 40, 54},
		{67, 47, 2},
		{67, 47, 65},
		{67, 49, 23},
		{67, 49, 44},
		{67, 54, 16},
		{67, 54, 51},
		{67, 55, 16},
		{67, 55, 51},
		{67, 56, 17},
		{67, 56, 50},
		{67, 59, 18},
		{67, 59, 49},
		{67, 60, 19},
		{67, 60, 48},
		{67, 62, 17},
		{67, 62, 50},
		{67, 64, 2},
		{67, 64, 65},
		{67, 65, 19},
		{67, 65, 48},
		{71, 7, 25},
		{71, 7, 46},
		{71, 11, 11},
		{71, 11, 60},
		{71, 13, 27},
		{71, 13, 44},
		{71, 14, 32},
		{71, 14, 39},
		{71, 17, 18},
		{71, 17, 53},
		{71, 21, 30},
		{71, 21, 41},
		{71, 22, 20},
		{71, 22, 51},
		{71, 23, 14},
		{71, 23, 57},
		{71, 26, 9},
		{71, 26, 62},
		{71, 28, 13},
		{71, 28, 58},
		{71, 31, 7},
		{71, 31, 64},
		{71, 33, 1},
		{71, 33, 70},
		{71, 34, 6},
		{71, 34, 65},
		{71, 35, 5},
		{71, 35, 66},
		{71, 39, 4},
		{71, 39, 67},
		{71, 41, 33},
		{71, 41, 38},
		{71, 42, 10},
		{71, 42, 61},
		{71, 44, 17},
		{71, 44, 54},
		{71, 46, 19},
		{71, 46, 52},
		{71, 47, 29},
		{71, 47, 42},
		{71, 51, 21},
		{71, 51, 50},
		{71, 52, 3},
		{71, 52, 68},
		{71, 53, 15},
		{71, 53, 56},
		{71, 55, 12},
		{71, 55, 59},
		{71, 56, 28},
		{71, 56, 43},
		{71, 59, 16},
		{71, 59, 55},
		{71, 61, 8},
		{71, 61, 63},
		{71, 62, 26},
		{71, 62, 45},
		{71, 63, 35},
		{71, 63, 36},
		{71, 65, 23},
		{71, 65, 48},
		{71, 66, 24},
		{71, 66, 47},
		{71, 67, 34},
		{71, 67, 37},
		{71, 68, 2},
		{71, 68, 69},
		{71, 69, 31},
		{71, 69, 40},
		{71, 70, 22},
		{71, 70, 49},
		{73, 1, 20},
		{73, 1, 53},
		{73, 2, 34},
		{73, 2, 39},
		{73, 3, 19},
		{73, 3, 54},
		{73, 4, 14},
		{73, 4, 59},
		{73, 6, 25},
		{73, 6, 48},
		{73, 8, 20},
		{73, 8, 53},
		{73, 9, 29},
		{73, 9, 44},
		{73, 12, 6},
		{73, 12, 67},
		{73, 16, 34},
		{73, 16, 39},
		{73, 18, 31},
		{73, 18, 42},
		{73, 19, 25},
		{73, 19, 48},
		{73, 23, 6},
		{73, 23, 67},
		{73, 24, 19},
		{73, 24, 54},
		{73, 25, 18},
		{73, 25, 55},
		{73, 27, 2},
		{73, 27, 71},
		{73, 32, 14},
		{73, 32, 59},
		{73, 35, 16},
		{73, 35, 57},
		{73, 36, 13},
		{73, 36, 60},
		{73, 37, 14},
		{73, 37, 59},
		{73, 38, 6},
		{73, 38, 67},
		{73, 41, 13},
		{73, 41, 60},
		{73, 46, 19},
		{73, 46, 54},
		{73, 48, 25},
		{73, 48, 48},
		{73, 49, 2},
		{73, 49, 71},
		{73, 50, 16},
		{73, 50, 57},
		{73, 54, 18},
		{73, 54, 55},
		{73, 55, 34},
		{73, 55, 39},
		{73, 57, 31},
		{73, 57, 42},
		{73, 61, 16},
		{73, 61, 57},
		{73, 64, 20},
		{73, 64, 53},
		{73, 65, 29},
		{73, 65, 44},
		{73, 67, 18},
		{73, 67, 55},
		{73, 69, 13},
		{73, 69, 60},
		{73, 70, 2},
		{73, 70, 71},
		{73, 71, 31},
		{73, 71, 42},
		{73, 72, 29},
		{73, 72, 44},
		{79, 1, 28},
		{79, 1, 51},
		{79, 2, 30},
		{79, 2, 49},
		{79, 4, 13},
		{79, 4, 66},
		{79, 5, 35},
		{79, 5, 44},
		{79, 8, 3},
		{79, 8, 76},
		{79, 9, 34},
		{79, 9, 45},
		{79, 10, 2},
		{79, 10, 77},
		{79, 11, 25},
		{79, 11, 54},
		{79, 13, 13},
		{79, 13, 66},
		{79, 16, 25},
		{79, 16, 54},
		{79, 18, 20},
		{79, 18, 59},
		{79, 19, 20},
		{79, 19, 59},
		{79, 20, 36},
		{79, 20, 43},
		{79, 21, 34},
		{79, 21, 45},
		{79, 22, 24},
		{79, 22, 55},
		{79, 23, 28},
		{79, 23, 51},
		{79, 25, 24},
		{79, 25, 55},
		{79, 26, 3},
		{79, 26, 76},
		{79, 31, 30},
		{79, 31, 49},
		{79, 32, 24},
		{79, 32, 55},
		{79, 36, 35},
		{79, 36, 44},
		{79, 38, 35},
		{79, 38, 44},
		{79, 40, 16},
		{79, 40, 63},
		{79, 42, 20},
		{79, 42, 59},
		{79, 44, 37},
		{79, 44, 42},
		{79, 45, 3},
		{79, 45, 76},
		{79, 46, 30},
		{79, 46, 49},
		{79, 49, 34},
		{79, 49, 45},
		{79, 50, 37},
		{79, 50, 42},
		{79, 51, 16},
		{79, 51, 63},
		{79, 52, 25},
		{79, 52, 54},
		{79, 55, 28},
		{79, 55, 51},
		{79, 62, 13},
		{79, 62, 66},
		{79, 64, 37},
		{79, 64, 42},
		{79, 65, 36},
		{79, 65, 43},
		{79, 67, 16},
		{79, 67, 63},
		{79, 72, 2},
		{79, 72, 77},
		{79, 73, 36},
		{79, 73, 43},
		{79, 76, 2},
		{79, 76, 77},
		{83, 2, 31},
		{83, 2, 52},
		{83, 5, 13},
		{83, 5, 70},
		{83, 6, 36},
		{83, 6, 47},
		{83, 8, 1},
		{83, 8, 82},
		{83, 13, 20},
		{83, 13, 63},
		{83, 14, 14},
		{83, 14, 69},
		{83, 15, 9},
		{83, 15, 74},
		{83, 18, 7},
		{83, 18, 76},
		{83, 19, 22},
		{83, 19, 61},
		{83, 20, 21},
		{83, 20, 62},
		{83, 22, 12},
		{83, 22, 71},
		{83, 24, 39},
		{83, 24, 44},
		{83, 32, 8},
		{83, 32, 75},
		{83, 34, 41},
		{83, 34, 42},
		{83, 35, 38},
		{83, 35, 45},
		{83, 39, 33},
		{83, 39, 50},
		{83, 42, 35},
		{83, 42, 48},
		{83, 43, 37},
		{83, 43, 46},
		{83, 45, 19},
		{83, 45, 64},
		{83, 46, 32},
		{83, 46, 51},
		{83, 47, 40},
		{83, 47, 43},
		{83, 50, 26},
		{83, 50, 57},
		{83, 52, 6},
		{83, 52, 77},
		{83, 53, 4},
		{83, 53, 79},
		{83, 54, 24},
		{83, 54, 59},
		{83, 55, 3},
		{83, 55, 80},
		{83, 56, 29},
		{83, 56, 54},
		{83, 57, 28},
		{83, 57, 55},
		{83, 58, 17},
		{83, 58, 66},
		{83, 60, 11},
		{83, 60, 72},
		{83, 62, 25},
		{83, 62, 58},
		{83, 66, 30},
		{83, 66, 53},
		{83, 67, 18},
		{83, 67, 65},
		{83, 71, 16},
		{83, 71, 67},
		{83, 72, 27},
		{83, 72, 56},
		{83, 73, 15},
		{83, 73, 68},
		{83, 74, 5},
		{83, 74, 78},
		{83, 76, 10},
		{83, 76, 73},
		{83, 79, 23},
		{83, 79, 60},
		{83, 80, 2},
		{83, 80, 81},
		{83, 82, 34},
		{83, 82, 49},
		{89, 3, 21},
		{89, 3, 68},
		{89, 6, 18},
		{89, 6, 71},
		{89, 7, 5},
		{89, 7, 84},
		{89, 12, 10},
		{89, 12, 79},
		{89, 13, 15},
		{89, 13, 74},
		{89, 14, 17},
		{89, 14, 72},
		{89, 15, 37},
		{89, 15, 52},
		{89, 19, 3},
		{89, 19, 86},
		{89, 23, 36},
		{89, 23, 53},
		{89, 24, 34},
		{89, 24, 55},
		{89, 26, 38},
		{89, 26, 51},
		{89, 27, 33},
		{89, 27, 56},
		{89, 28, 40},
		{89, 28, 49},
		{89, 29, 7},
		{89, 29, 82},
		{89, 30, 19},
		{89, 30, 70},
		{89, 31, 26},
		{89, 31, 63},
		{89, 33, 4},
		{89, 33, 85},
		{89, 35, 30},
		{89, 35, 59},
		{89, 37, 14},
		{89, 37, 75},
		{89, 38, 28},
		{89, 38, 61},
		{89, 41, 39},
		{89, 41, 50},
		{89, 43, 32},
		{89, 43, 57},
		{89, 46, 20},
		{89, 46, 69},
		{89, 48, 9},
		{89, 48, 80},
		{89, 51, 27},
		{89, 51, 62},
		{89, 52, 31},
		{89, 52, 58},
		{89, 54, 41},
		{89, 54, 48},
		{89, 56, 42},
		{89, 56, 47},
		{89, 58, 6},
		{89, 58, 83},
		{89, 59, 23},
		{89, 59, 66},
		{89, 60, 29},
		{89, 60, 60},
		{89, 61, 25},
		{89, 61, 64},
		{89, 62, 35},
		{89, 62, 54},
		{89, 63, 43},
		{89, 63, 46},
		{89, 65, 1},
		{89, 65, 88},
		{89, 66, 22},
		{89, 66, 67},
		{89, 70, 13},
		{89, 70, 76},
		{89, 74, 12},
		{89, 74, 77},
		{89, 75, 44},
		{89, 75, 45},
		{89, 76, 24},
		{89, 76, 65},
		{89, 77, 16},
		{89, 77, 73},
		{89, 82, 8},
		{89, 82, 81},
		{89, 83, 11},
		{89, 83, 78},
		{89, 86, 2},
		{89, 86, 87},
		{97, 1, 5},
		{97, 1, 92},
		{97, 2, 43},
		{97, 2, 54},
		{97, 3, 44},
		{97, 3, 53},
		{97, 4, 40},
		{97, 4, 57},
		{97, 6, 29},
		{97, 6, 68},
		{97, 8, 44},
		{97, 8, 53},
		{97, 9, 38},
		{97, 9, 59},
		{97, 11, 2},
		{97, 11, 95},
		{97, 12, 36},
		{97, 12, 61},
		{97, 16, 29},
		{97, 16, 68},
		{97, 18, 3},
		{97, 18, 94},
		{97, 22, 41},
		{97, 22, 56},
		{97, 24, 38},
		{97, 24, 59},
		{97, 25, 43},
		{97, 25, 54},
		{97, 27, 24},
		{97, 27, 73},
		{97, 31, 3},
		{97, 31, 94},
		{97, 32, 36},
		{97, 32, 61},
		{97, 33, 37},
		{97, 33, 60},
		{97, 35, 5},
		{97, 35, 92},
		{97, 36, 13},
		{97, 36, 84},
		{97, 43, 40},
		{97, 43, 57},
		{97, 44, 16},
		{97, 44, 81},
		{97, 47, 7},
		{97, 47, 90},
		{97, 48, 3},
		{97, 48, 94},
		{97, 49, 31},
		{97, 49, 66},
		{97, 50, 40},
		{97, 50, 57},
		{97, 53, 36},
		{97, 53, 61},
		{97, 54, 7},
		{97, 54, 90},
		{97, 61, 5},
		{97, 61, 92},
		{97, 62, 13},
		{97, 62, 84},
		{97, 64, 38},
		{97, 64, 59},
		{97, 65, 16},
		{97, 65, 81},
		{97, 66, 31},
		{97, 66, 66},
		{97, 70, 43},
		{97, 70, 54},
		{97, 72, 24},
		{97, 72, 73},
		{97, 73, 37},
		{97, 73, 60},
		{97, 75, 29},
		{97, 75, 68},
		{97, 79, 31},
		{97, 79, 66},
		{97, 81, 41},
		{97, 81, 56},
		{97, 85, 16},
		{97, 85, 81},
		{97, 86, 44},
		{97, 86, 53},
		{97, 88, 37},
		{97, 88, 60},
		{97, 89, 2},
		{97, 89, 95},
		{97, 91, 41},
		{97, 91, 56},
		{97, 93, 7},
		{97, 93, 90},
		{97, 94, 2},
		{97, 94, 95},
		{97, 95, 24},
		{97, 95, 73},
		{97, 96, 13},
		{97, 96, 84}
	};
}
