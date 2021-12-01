//-----BEGIN DISCLAIMER-----
/*******************************************************************************
* Copyright (c) 2014, 2021 JCrypTool Team and Contributors
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
//-----END DISCLAIMER-----
package edu.kit.iks.zudoku;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.JPanel;

@SuppressWarnings("serial") // Objects of this class are not meant to be serialized. 
public abstract class CardStack extends JPanel {
	private SudokuField parent;
	private Stack<Card> cards;
	
	public CardStack(SudokuField parent, Point location) {
		super();
		
		assert(parent != null);
		this.parent = parent;
		
		setBounds(new Rectangle(location.x, location.y, ZudokuConfig.STACK_WIDTH, ZudokuConfig.STACK_HEIGHT));		
		cards = new Stack<Card>();
		
		setOpaque(true);
	}
	
	public SudokuField getParent() {
		return parent;
	}
	
	/*
	 * Stack operations.
	 */
	public boolean isEmpty() {
		return this.cards.isEmpty();
	}
	
	public void pushCard(Card card) {
		this.cards.push(card);
	}
	
	public Card popCard() {
		return this.cards.pop();
	}
	
	public Card topCard() {
		try {
			return this.cards.peek(); 
		} catch(EmptyStackException e) {
			return null;
		}
	}
	
	/*
	 * request to place a card in the tray
	 * pos is a pivot position (not necessarily the cards position, 
	 * might be the mouse pos as well.
	 */
	abstract public CardStack placeCard(Card card, Point pos);
	
	public void destroy() {
		for(Card card : cards) {
			this.getParent().removeCard(card);
		}
	}
}
