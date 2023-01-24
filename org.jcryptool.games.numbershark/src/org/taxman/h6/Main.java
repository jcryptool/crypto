/*
 * Copyright (c) Brian Chess 2019-2023.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.taxman.h6;

import org.taxman.h6.frame.FrameSolver;
import org.taxman.h6.frame.GreedySolver;
import org.taxman.h6.search.SearchManager;
import org.taxman.h6.oldsearch.OldSearch;
import org.taxman.h6.game.Solution;
import org.taxman.h6.game.Solver;
import org.taxman.h6.game.VerificationException;
import org.taxman.h6.util.Memo;
import org.taxman.h6.util.Stopwatch;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.IntStream;

public class Main {
    private final Arguments args;

    private static class BoardRange {
        public final int low;
        public final int high;

        private BoardRange(int low, int high) {
            this.low = low;
            this.high = high;
        }

        public IntStream range() {
            return IntStream.rangeClosed(low, high);
        }
    }

    private static class Arguments {
        public final boolean showGame;
        public final boolean showHelp;
        public final boolean csv;
        public final boolean moves;
        public final int debugLevel;
        public final int memoizationSizeLimitMillions;
        public final boolean announceGame;
        public final boolean quiet;
        public final boolean greedy;
        public final boolean oldSearch;
        public final List<BoardRange> boardRanges = new ArrayList<>();

        public Arguments(String[] argStrings) {
            Iterator<String> iter = Arrays.stream(argStrings).iterator();
            int debugLevel = -1;
            int memoizationSizeLimitMillions = Memo.sizeLimitMillions;
            boolean showGame = false;
            boolean showHelp = (argStrings.length == 0);
            boolean csv = false;
            boolean moves = false;
            boolean quiet = false;
            boolean greedy = false;
            boolean oldSearch = false;

            try {
                while (iter.hasNext()) {
                    String arg = iter.next();
                    switch (arg) {
                        case "-d":
                        case "--debug":
                            debugLevel = Integer.parseInt(iter.next());
                            break;
                        case "-q":
                        case "--quiet":
                            quiet = true;
                            break;
                        case "-s":
                        case "--show":
                            showGame = true;
                            break;
                        case "-m":
                        case "--moves":
                            moves = true;
                            break;
                        case "-h":
                        case "--help":
                            showHelp = true;
                            break;
                        case "-c":
                        case "--csv":
                            csv = true;
                            showGame = false;
                            break;
                        case "-g":
                        case "--greedy":
                            greedy = true;
                            break;
                        case "-o":
                        case "--old":
                            oldSearch = true;
                            break;
                        case "-l":
                        case "--limit":
                            memoizationSizeLimitMillions = Integer.parseInt(iter.next());
                            break;
                        default:
                            if (arg.startsWith("-")) {
                                throw new IllegalArgumentException("unrecognized argument: " + arg);
                            } else if (arg.contains("-")) {
                                int dashSpot = arg.indexOf("-");
                                var lowBoard = Integer.parseInt(arg.substring(0, dashSpot));
                                var highBoard = Integer.parseInt(arg.substring(dashSpot + 1));
                                boardRanges.add(new BoardRange(lowBoard, highBoard));
                            } else {
                                var b = Integer.parseInt(arg);
                                boardRanges.add(new BoardRange(b, b));
                            }
                    }
                }
            }
            catch (NumberFormatException e) {
                System.err.println(e.getMessage() + " is not a valid integer");
                showHelp = true;
            }
            catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                showHelp = true;
            }

            if (boardRanges.size() == 0 && !showHelp) {
                System.err.println("no games to play");
                showHelp = true;
            }

            quiet = quiet && debugLevel < 0;
            showGame = showGame && !quiet;
            csv = csv && debugLevel < 0;

            this.debugLevel = debugLevel;
            this.memoizationSizeLimitMillions = memoizationSizeLimitMillions;
            this.quiet = quiet;
            this.showGame = showGame;
            this.showHelp = showHelp;
            this.moves = moves;
            this.csv = csv;
            this.greedy = greedy;
            this.oldSearch = oldSearch;
            this.announceGame = showGame || debugLevel > -1;
        }

        public IntStream boardSizeRange() {
            return boardRanges.stream()
                    .flatMapToInt(BoardRange::range);
        }

    }

    private Main(String[] args) {
        this.args = new Arguments(args);
    }

    private String helpString() {
        return  "TAXMAN\n"+
                "     taxman [options] [board_size ...]\n" +
                "\n"+
                "  board_size can be an integer such as 10 or a range of integers such as 10-20."+
                "\n"+
                "  The command line options are as follows:\n"+
                "    -c --csv\n"+
                "        Output summary statistics in CSV format.\n"+
                "    -d --debug [level]\n"+
                "        Display debug information as part of playing games.\n"+
                "    -g, --greedy\n" +
                "        Use a greedy algorithm instead of an optimal solver\n"+
                "    -h, --help\n"+
                "        Show this message and quit without playing any games.\n"+
                "    -l, --limit\n"+
                "        Old search memoization cache size limit in millions (defaults to 10)\n"+
                "    -m --moves\n"+
                "        Output the list of moves\n"+
                "    -o --old\n"+
                "        Use the search procedure from 2021\n"+
                "    -q, --quiet\n"+
                "        Don't show the game\n"+
                "    -s, --show\n"+
                "        Show the game\n"+
                "\n";
    }

    private String getNowTimestamp() {
        return ZonedDateTime.now().toString();
    }

    private void playGame(Solver solver, int n) {
        var name = "n="+n;
        if (args.announceGame) {
            var timestamp = (args.debugLevel > 0) ? " at " + getNowTimestamp() : "";
            System.out.printf("\nplaying %s%s\n", name, timestamp);
            System.out.flush();
        }
        System.gc(); // trying to get more precise timings on playing individual games
        var stopwatch = new Stopwatch().start();
        var sln = solver.solve(n);
        stopwatch.stop();
        if (args.csv) {
            outputCsv(n, stopwatch);
        } else {
            if (args.showGame) sln.display(System.out);
            if (!args.quiet) System.out.printf("played %s in %,.1f seconds\n", name, stopwatch.seconds());
            if (args.moves) System.out.println(showMoves(n, sln));
        }
    }

    private Solver makeSolver() {
        if (args.greedy) return new GreedySolver(4);
        return new FrameSolver();
    }

    private void summary(Solver solver, Stopwatch sw) {
        solver.printInternalsReport(System.out);
        long gameCount = args.boardSizeRange().count();
        String g1 =  gameCount == 1 ? "game" : "games";
        System.out.printf("played %d %s in %.1f seconds\n", gameCount, g1, sw.seconds());
    }

    private String showMoves(int n, Solution sln) {
        return String.format("  { \"n\": %d, \"score\": %d, \"moves\": %s },", n, sln.score(), sln.moves);
    }

    private void outputCsvHeader() {
        System.out.println("board, time (seconds)");
    }

    private void outputCsv(int n, Stopwatch stopwatch) {
        System.out.printf(" %4d, %4.2f\n", n, stopwatch.seconds());
        System.out.flush();
    }

    private void setUpDebug() {
        if (args.debugLevel > 0) {
            OldSearch.printSummary = true;
            FrameSolver.printAccelerations = true;
            GreedySolver.printPromotions = true;
        }
        if (args.debugLevel > 1) {
            SearchManager.debugPrintSummary = true;
            FrameSolver.printSearch = true;
            GreedySolver.printUpgrades = true;
        }
        if (args.debugLevel > 2) {
            OldSearch.printStatsPerTarget = true;
            FrameSolver.printAccelerationFailures = true;
        }
        if (args.debugLevel > 3) {
            SearchManager.debugPrintDetail = true;
            OldSearch.printSearchQueueStats = true;
        }
        if (args.debugLevel > 4) {
            FrameSolver.printFrames = true;
            FrameSolver.printCacheStats = true;
            Memo.trackHits = true;
        }
        if (args.debugLevel > 5) {
            SearchManager.debugPrintFineGrainDetail = true;
        }

    }

    private void go() {
        if (args.showHelp) {
            System.out.println(helpString());
            return;
        }
        if (args.memoizationSizeLimitMillions != Memo.sizeLimitMillions) {
            Memo.sizeLimitMillions = args.memoizationSizeLimitMillions;
            System.out.printf("memoization cache size limit set to %d million", Memo.sizeLimitMillions);
        }

        setUpDebug();
        if (args.oldSearch) {
            FrameSolver.useOldSearch = true;
            if (args.debugLevel > 0) System.out.println("using 2021 search procedure");
        }
        if (args.csv) outputCsvHeader();
        Stopwatch bigWatch = new Stopwatch().start();
        var solver = makeSolver();
        try {
            args.boardSizeRange().forEach(n -> playGame(solver, n));
            bigWatch.stop();
            if (!args.csv && args.boardSizeRange().count() > 1) summary(solver, bigWatch);
        } catch (VerificationException ve) {
            System.out.println("ERROR: verification failed: " + ve.getMessage());
            System.out.println("BAD SOLUTION: " + ve.solutionString());
        }
    }

    /**
     * Simple solver interface for use by systems that want to include Taxman as a library
     * @param n: the game to play
     * @return an array of moves that result in an optimal score
     */
    public static int[] solve(int n) {
        var main = new Main(new String[]{});
        var solver = main.makeSolver();
        var sln = solver.solve(n);
        return sln.moves.toArray();
    }

    public static void main(String[] argStrings) {
        new Main(argStrings).go();
    }
}