package ar.edu.itba.Magic.Backend.Card;
import ar.edu.itba.Magic.Backend.Ability;
import ar.edu.itba.Magic.Backend.Player;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.CardName;
import ar.edu.itba.Magic.Backend.Interfaces.Enum.Color;

/**
 * Created by Martin on 31/10/2015.
 */
public abstract class Card {
    private Player controller;
    private CardName cardName;
    private Color color;
    private Integer coloredManaCost;
    private Integer colorlessManaCost;
    private Ability ability;

    public Card(CardName cardName, Color color, Integer coloredManaCost, Integer colorlessManaCost, Ability ability) {
        this.cardName = cardName;
        this.color = color;
        this.coloredManaCost = coloredManaCost;
        this.colorlessManaCost = colorlessManaCost;
        this.ability = ability;
    }

    public void setController(Player controller) {
        this.controller = controller;
    }

    public Player getController() {
        return this.controller;
    }

    public CardName getCardName() {
        return this.cardName;
    }

    public Color getColor() {
        return this.color;
    }

    public Integer getColoredManaCost() {
        return this.coloredManaCost;
    }

    public Integer getColorlessManaCost() {
        return this.colorlessManaCost;
    }

    public Ability getAbility() {
        return this.ability;
    }

    public boolean containsAbility() {
        return this.ability != null;
    }

    public abstract void playCard();
}

