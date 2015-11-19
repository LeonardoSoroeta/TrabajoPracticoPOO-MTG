package ar.edu.itba.Magic.Backend.Interfaces;

/** Implemented by spells that go on the spell stack */
public interface Spell {

	public void sendToStack();

	public void resolveInStack();
	
	public void counterSpell();

}
