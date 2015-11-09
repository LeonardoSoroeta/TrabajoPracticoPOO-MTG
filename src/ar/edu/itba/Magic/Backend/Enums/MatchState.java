package ar.edu.itba.Magic.Backend.Enums;

public enum MatchState {
	// el jugador solo puede clickear en uno de los botones de mana a la izquierda. luego se ejecuta
	// match.returnSelectedTarget(Color manaSelected); no pasa nada si clikea en un 0 o un color no valido, de
	// eso se fija el back
	// nunca se deberia llegar a este estado si el jugador no tiene suficiente mana asi que una vez que el match
	// esta en este estado el jugador tiene que pagar
	REQUESTING_MANA_PAYMENT,
	
	// el jugador tiene que seleccionar alguna carta o algun permanent o lo que sea, luego se ejecuta
	// returnSelectedTarget(Object selectedTarget)
	// o puede clickear el boton Cancel y se ejecuta match.cancelTargetSelection();
	REQUESTING_TARGET_SELECTION_BY_ABILITY,
	
	// el jugador puede hacer click derecho > play card en una carta o click derecho > activate ability en un permanent
	// en cuyo caso se ejecuta el card.playCard() o el permanent.getAbility().executeOnActivation();
	// cuando el jugador no quiere hacer mas nada hace click en Done y ahi se ejecuta match.playerDoneClicking();
	REQUESTING_MAIN_PHASE_ACTIONS,
	
	// el activePlayer puede hacer play card en una instantcard o activate ability de algun ability;
	// o hacer click en Done y ahi se ejecuta match.playerDoneClicking();
	REQUESTING_STACK_ACTIONS,
	
	// el activePlayer selecciona una criatura propia y luego match.returnSelectedTarget(selectedCreature)
	// o hacer click en Done y ahi se ejecuta match.playerDoneClicking();
	// el back se fija que haya seleccionado una criatura que puede atacar
	REQUESTING_ATTACKER_SELECTION,
	
	// el activePlayer (que ahora va a ser el defensor) selecciona una criatura propia y luego 
	// match.returnSelectedTarget(selectedCreature)
	// o hacer click en Done y ahi se ejecuta match.playerDoneClicking();
	// el back se fija que haya seleccionado una criatura que puede defender
	REQUESTING_BLOCKER_SELECTION,
	
	// el activePlayer (que todavia va a ser el defensor) selecciona una criatura atacante para bloquear 
	// con el ultimo blocker que eligio, y luego match.returnSelectedTarget(selectedCreature)
	// el back se fija que haya seleccionado una criatura valida
	REQUESTING_ATTACKER_TO_BLOCK_SELECTION,
	
	// el activePlayer selecciona una carta de su mano para descartar y luego 
	// match.returnSelectedTarget(selectedCreature)
	REQUESTING_CARD_TO_DISCARD_SELECTION,
}
