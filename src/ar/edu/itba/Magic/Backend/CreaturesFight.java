package src.ar.edu.itba.Magic.Backend;


import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.classfile.Attribute;

import ar.edu.itba.Magic.Backend.Creature;
import ar.edu.itba.Magic.Backend.Land;
import ar.edu.itba.Magic.Backend.Player;

public class CreaturesFight {
	
	// es un S I N G L E T O N
	

	public CreaturesFight(){
		
	}

	public void realizeFight(List<Creature> attack, List<Creature> defense, Player att, Player def){
		
		for( Creature attacker: attack ){
			Creature defender = defense.get(attack.indexOf(attacker));
			
			
			// el attacker pega si puede
				if( (attacker.containsAttribute(CAN_FLY) && defender.containsAttribute(CAN_FLY)) || !defender.containsAttribute(CAN_FLY))
				defender.takeDamage(attacker.getAttack());
				
				
			// el defender pega si puede
				if (defender.containsAttribute(CAN_FLY) || (!defender.containsAttribute(CAN_FLY) && !defender.containsAttribute(CAN_FLY)) || defender.containsAttribute(REACH) || !attackerWalk( attacker, def) ){
		
					if( attacker.containsAttribute(FIRST_ATTACK)){
					
						if(defender.getDefense() < defender.getDamageMarker() )
							attacker.takeDamage(defender.getAttack());
						}
			
						else attacker.takeDamage(defender.getAttack());
				}
				
				
				
				// si hay TRAMBLE se aplica el danio de vida
				int defdam = defender.getDamageMarker() - defender.getDefense();
				int attdam = attacker.getDamageMarker() - attacker.getDefense();
				
				if( attacker.containsAttribute(TRAMPLE)){
					
					if( defdam > 0)
						def.decreaseHealth(aux);
				}
				
				if( defender.containsAttribute(ar.edu.itba.Magic.Backend.Interfaces.Enum.Attribute.TRAMPLE)){
				
					if( attdam > 0)
						att.decreaseHealth(aux);
				}
				
				
				// tapea si tiene que
				if( attacker.containsAttribute(TAP_ON_ATTACK)){
					attacker.tap();
				}
		
				// mueren :(
				if (defdam >= 0)
					defender.destroy();
				if (attdam >= 0)
					attacker.destroy();
		}
		
		
		
	}
	
	public boolean attackerWalk( Creature creature, Player defensor){
		
		List<Attribute> walks= new ArrayList();
		walks.add(SWAMP_WALK);
		walks.add(WALK2);
		walks.add(WALK3);
		walks.add(WALK4);
		walks.add(WALK5);
		
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