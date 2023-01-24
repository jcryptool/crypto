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

package org.taxman.h6.search;

import org.taxman.h6.util.TxSet;
import org.taxman.h6.util.TxUnmodifiableSet;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;



public class BranchExplorer extends RecursiveAction {
    public final Branch branchForCompute;
    public final SearchManager searchManager;

    public BranchExplorer(Branch branch, SearchManager searchManager) {
        this.branchForCompute = branch;
        this.searchManager = searchManager;
    }

    @Override
    protected void compute() {
        try {
            explore(branchForCompute);
        } catch (Throwable t) {
            searchManager.reportBranchException(t);
        }
    }

    private void explore(Branch branch) {
        if (SearchManager.debugPrintFineGrainDetail) {
            System.out.printf("      exploring branch %s\n", branch);
        }
        var branchCount = searchManager.branchCounter.incrementAndGet();
        if (SearchManager.debugPrintDetail && branchCount % SearchManager.STATUS_REPORT_FREQUENCY_IN_BRANCHES == 0) {
            searchManager.statusReporter.report();
        }

        TxUnmodifiableSet alreadyPromoted = branch.getAlreadyPromoted();
        int alreadyPromotedSum = alreadyPromoted.sum();
        TxSet currentCandidates = TxSet.of(branch.getCandidates());
        var state = branch.gameState;
        int maxAdditionalPromotions = searchManager.maxAdditionalPromotions(state, alreadyPromoted, currentCandidates);
        var explorers = new ArrayList<BranchExplorer>();

        // Descend through the candidates, promote each in turn, and examine the effects.
        // Stop if it becomes clear this branch can't meet the target.
        for (int n = currentCandidates.max(); n > 0;  n = currentCandidates.nextHighest(n)) {

            // What's the most we can hope to score with the contents of currentCandidates?
            // If it's not enough to get us to the target, then we're done exploring this branch
            int upperBoundForCurrentCandidates =
                    upperBound(state, alreadyPromoted, currentCandidates, maxAdditionalPromotions);

            // stop searching if the remaining candidates aren't going to be enough
            if (upperBoundForCurrentCandidates + alreadyPromotedSum < getCurrentSearchMinimum()) {
                break;
            }

            currentCandidates.remove(n);
            var newState = state.promote(n);
            if (newState != null) {
                TxUnmodifiableSet newPromoted = TxUnmodifiableSet.or(alreadyPromoted, n);
                // Promoting n was successful, so continue the search
                int maxNextMoves = searchManager.maxAdditionalPromotions(newState, newPromoted, currentCandidates);
                var candidatesForNewState = makeNewCandidateList(n, maxNextMoves, newState,
                        currentCandidates, alreadyPromoted);
                int upperBoundForNewState = upperBound(newState, newPromoted, candidatesForNewState, maxNextMoves);
                int upperBoundForN = alreadyPromotedSum + n + upperBoundForNewState;
                var newExplorer = createBranch(newState, newPromoted, candidatesForNewState, upperBoundForN);
                if (newExplorer != null) explorers.add(newExplorer);
            }
        }
        invokeAll(explorers);
    }

    private TxUnmodifiableSet makeNewCandidateList(int n, int maxNextMoves, FrameSet newFrameSet,
                                                   TxSet branchCandidates, TxSet alreadyPromoted) {
        // step 1: only take candidates that are still in the frame set and that have arrived at this level
        var filtered = newFrameSet.narrowCandidateSet(branchCandidates);

        // step 2: remove candidates we know won't work with n
        filtered = searchManager.ube.removePrecluded(filtered, n, alreadyPromoted);

        // step 3: make sure individual candidates are actually promotable. This is an expensive operation,
        // so only do it for the number of candidates we expect we'll be able to promote.
        int hits = 0;
        int nFrameNum = newFrameSet.mapToFrame(n);
        // it appears this filter isn't worth running if the only thing that changed was in frames 1 and 0
        if (nFrameNum > 1) {
            for (int x = filtered.max(); x > 0; x = filtered.nextHighest(x)) {
                int xFrameNum = newFrameSet.mapToFrame(x);
                if ((xFrameNum != nFrameNum && xFrameNum + 1 != nFrameNum) || newFrameSet.canPromote(x)) {
                    if (++hits == maxNextMoves) break;
                } else {
                    filtered.remove(x);
                }
            }
        }

        return filtered.unmodifiable();
    }

    private int upperBound(FrameSet gameState, TxSet alreadyPromoted, TxSet newCandidates, int maxPromotions) {
        return searchManager.ube.upperBound(gameState, newCandidates, maxPromotions, alreadyPromoted);
    }

    private BranchExplorer createBranch(FrameSet state, TxSet newPromoted, TxSet nextCandidates,
                                        int upperBoundForNewBranch) {
        BranchExplorer result = null;
        if (upperBoundForNewBranch > getCurrentSearchMinimum()) {
            if (newPromoted.sum() > getCurrentSearchMinimum()) {
                searchManager.increaseSearchMinimum(newPromoted);
            }
            result = new BranchExplorer(new Branch(state, newPromoted, nextCandidates), searchManager);
        }
        return result;
    }

    private int getCurrentSearchMinimum() {
        return searchManager.bestSolutionSum;
    }
}