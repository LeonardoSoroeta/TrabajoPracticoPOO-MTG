package ar.edu.itba.Magic.Backend.Enums;

public enum MatchState {
	// el estado antes del primer update()
	GAME_OVER,
	
	// el jugador solo puede clickear en uno de los botones de mana a la izquierda. luego se ejecuta
	// match.returnSelectedTarget(Color manaSelected); ayudaria si el front no hace nada si el jugador
	// cliqueo sobre un mana que dice 0
	// el front tiene que tener un boton Cancel que ejecuta match.cancelManaPayment();
	AWAITING_CASTING_MANA_PAYMENT,
	AWAITING_ABILITY_MANA_PAYMENT,
	
	// el jugador tiene que seleccionar alguna carta o algun permanent o lo que sea, luego se ejecuta
	// returnSelectedTarget(Object selectedTarget)
	// o puede clickear el boton Cancel y se ejecuta match.cancelTargetSelection();
	AWAITING_CASTING_TARGET_SELECTION,
	AWAITING_ABILITY_TARGET_SELECTION,
	
	// el jugador puede hacer click derecho > play card en una carta o click derecho > activate ability en un permanent
	// en cuyo caso se ejecuta el card.playCard() o el permanent.getAbility().executeOnActivation();
	// cuando el jugador no quiere hacer mas nada hace click en Done y ahi se ejecuta match.playerDoneClicking();
	AWAITING_MAIN_PHASE_ACTIONS,
	
	// el activePlayer puede hacer play card en una instantcard o activate ability de algun ability;
	// o hacer click en Done y ahi se ejecuta match.playerDoneClicking();
	// en este estado hay que comunicarse con el stack para dibujar sus objetos en pantalla
	AWAITING_STACK_ACTIONS,
	
	// el activePlayer selecciona una criatura propia y luego match.returnSelectedTarget(selectedCreature)
	// o hacer click en Done y ahi se ejecuta match.playerDoneClicking();
	// el back se fija que haya seleccionado una criatura que puede atacar
	AWAITING_ATTACKER_SELECTION,
	
	// el activePlayer (que ahora va a ser el defensor) selecciona una criatura propia y luego 
	// match.returnSelectedTarget(selectedCreature)
	// o hacer click en Done y ahi se ejecuta match.playerDoneClicking();
	// el back se fija que haya seleccionado una criatura que puede defender
	AWAITING_BLOCKER_SELECTION,
	
	// el activePlayer (que todavia va a ser el defensor) selecciona una criatura atacante para bloquear 
	// con el ultimo blocker que eligio, y luego match.returnSelectedTarget(selectedCreature)
	// el back se fija que haya seleccionado una criatura valida
	AWAITING_ATTACKER_TO_BLOCK_SELECTION,
	
	// el activePlayer selecciona una carta de su mano para descartar y luego 
	// match.returnSelectedTarget(selectedCreature)
	AWAITING_CARD_TO_DISCARD_SELECTION,
	
	// muestra imagen de mana burn espera q el chabon haga click en Done
	AWAITING_MANA_BURN_ACKNOWLEDGEMENT,
	
	AWAITING_STARTING_PHASE_YES_OR_NO_CONFIRMATION,
	
	AWAITING_SAVE_GAME_YES_OR_NO_CONFIRMATION,
	
	AWAITING_MULLIGAN_CONFIRMATION
}
