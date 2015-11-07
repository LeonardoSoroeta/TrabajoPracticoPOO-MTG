package ar.edu.itba.Magic.Backend;

import ar.edu.itba.Magic.Backend.Interfaces.Enum.Attribute;
import ar.edu.itba.Magic.Backend.Permanents.Creature;
import ar.edu.itba.Magic.Backend.Permanents.Land;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.Magic.Backend.Player;

public class CreaturesFight {
	
	// es un S I N G L E T O N
	

	public CreaturesFight(){
		
	}

	public void realizeFight(List<Creature> attack, List<Creature> defense, Player att, Player def){
		
		for( Creature attacker: attack ){			
			
			Creature defender = defense.get(attack.indexOf(attacker));
			
			if ( defender == null){
				
				def.decreaseHealth(attacker.getAttack());
				
			}
			
			else{
			
			// el attacker pega si puede
			
				if( (attacker.containsAttribute(Attribute.FLYING) && defender.containsAttribute(Attribute.FLYING)) || !defender.containsAttribute(Attribute.FLYING))
				defender.takeDamage(attacker.getAttack());
				
				
			// el defender pega si puede
				
				if (defender.containsAttribute(Attribute.FLYING) || (!defender.containsAttribute(Attribute.FLYING) && !defender.containsAttribute(Attribute.FLYING)) || defender.containsAttribute(Attribute.REACH) || !attackerWalk( attacker, def) ){
		
					if( attacker.containsAttribute(Attribute.FIRST_STRIKE)){
					
						if(defender.getDefense() < defender.getDamageMarker() )
							attacker.takeDamage(defender.getAttack());
						}
			
						else attacker.takeDamage(defender.getAttack());
				}
				
				
				
				// si hay TRAMBLE se aplica el danio de vida
				int defdam = defender.getDamageMarker() - defender.getDefense();
				int attdam = attacker.getDamageMarker() - attacker.getDefense();
				
				if( attacker.containsAttribute(Attribute.TRAMPLE)){
					
					if( defdam > 0)
						def.decreaseHealth(defdam);
				}
				
				if( defender.containsAttribute(Attribute.TRAMPLE)){
				
					if( attdam > 0)
						att.decreaseHealth(attdam);
				}
				
				
				// tapea si tiene que
				if( attacker.containsAttribute(Attribute.TAPS_ON_ATTACK)){
					attacker.tap();
				}
		
				// mueren :(
				if (defdam >= 0)
					defender.destroy();
				if (attdam >= 0)
					attacker.destroy();
			}
			
		}	
		
		
		
	}
	
	public boolean attackerWalk( Creature creature, Player defensor){
		
		List<Attribute> walks= new ArrayList();
		walks.add(Attribute.FORESTWALK);
		walks.add(Attribute.ISLANDWALK);
		walks.add(Attribute.MOUNTAINWALK);
		walks.add(Attribute.PLAINSWALK);
		walks.add(Attribute.SWAMPWALK);
		
		for( Attribute walk: walks){
			for( Land land: defensor.getLands())
		
				if (creature.containsAttribute(walk))
			if( land.getName == walk)
				
				return true;
		
		}
		return false;
	}
	
	
	
	
}

/*
....si tapped: no puede atacar (sera un atributo?)

atributos
.....SUMMONING_SICKNESS (mareo de invocacion) : no puede atacar
.....CAN_ATTACK: <--

LOS DE ARRIBA DEBEN CHEQUEARSE ANTES DE SER PASADOS PARA ATACAR. porque??? porque directamente no se pueden elegir
en los demas casos los elijo y de ultima pegan danio 0. 


------FLY: solo recibe danios de otros FLY
------REACH: bloquea voladores
------TAPS_ON_ATTACK: se da vuelta cuando ataca
------"tierra "WALK: no puede ser bloqueada si tiene el " " 


------FIRST_ATTACK
------TRAMPLE: si mata, dania la vida

*/