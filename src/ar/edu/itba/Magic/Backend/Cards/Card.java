package ar.edu.itba.Magic.Backend.Cards;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Enums.CardType;
import ar.edu.itba.Magic.Backend.Enums.Color;
import ar.edu.itba.Magic.Backend.Mechanics.Mechanics;

/**
 * Created by Martin on 31/10/2015.
 */
public abstract class Card {
    private Player controller;
    private CardType cardType;
    private Mechanics ability;

    public Card(CardType cardType, Mechanics ability) {
        this.cardType = cardType;
        this.ability = ability;
    }
    
    public void setController(Player controller) {
    	this.controller = controller;
    }

    public Player getController() {
        return this.controller;
    }

    public CardType getCardType() {
        return this.cardType;
    }

    public Color getColor() {
        return this.cardType.getColor();
    }

    public Integer getColoredManaCost() {
        return this.cardType.getColoredManaCost();
    }

    public Integer getColorlessManaCost() {
        return this.cardType.getColorlessManaCost();
    }

    public Mechanics getAbility() {
        return this.ability;
    }

    public boolean containsAbility() {
        return this.ability != null;
    }

    public abstract void playCard();
}

