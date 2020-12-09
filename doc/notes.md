#Basic rules
####Setting up the game

Each player gets a ship, and this ship can have many rooms, but must have 
exactly one of each room type. Each player determines the starting power
allocation for their rooms and weapons, and starts with weapons uncharged
and shields maxed.
Each ship has They also each get three crew members.
Each ship begins with 12 hull point. The turn order is decided randomly.

####Objective
Destroy the enemy ship by reducing its hull points to zero or killing its crew.
Don't let the same happen to you.

##How to play

####Turn actions
1. First, you may reallocate your power among your weapons,
shields, and engine rooms. You may then allocate the power from your weapons
room to your weapons.

2. Next, you may move any crewmate from its current room to any adjacent room.

3. Lastly, you may fire any of your fully charged weapons at a selected enemy room.
When a weapon is fired, it loses all charge and the attack is resolved.

####Resolving an attack
1. There is probability (enemy engine power * 10) that the attack misses,
in which case nothing happens. This is only possible if the Pilot room is 
undamaged and manned.
2. If it hits, if the enemy ship has more than 0 shield, reduce the enemy
shield by (weapon damage), or to 0 if more than (shield) damage is dealt.
In this case, nothing else happens.
3. If it doesn't hit the shield, it hits the selected room, dealing (weapon damage)
hull point damage, (weapon damage) room damage, and 
(weapon damage) crew damage to crew in the room. 

####Ending the turn
On the end of your turn:
- add one charge to each weapon that is currently powered and isn't at max charge,
and remove all charge from any weapon that is not powered.
- Set your max shield to be equal to (1/2 * current shield power). If your shield is larger than your max shield, reduce your shield to your max shield.
If your shield is smaller than your max shield, regain one shield.
- Each crewmate that is in a damaged room repairs that room by one level.

##Information/Definitions

####Room Types

- weapons (level 4)
- shields (level 4)
- pilot (level 1)
- engine (level 2)

####Weapon Types

- small laser (1 power, 1 damage, 1 recharge time)
- large laser (2 power, 3 damage, 2 recharge time)

####Room damage
If a room is damaged, the maximum amount of power it can receive is reduced
by that amount. The base amount is equal to the room's level.

####Crew damage
Each crewmate has 4 hit points, and if it runs out of hit points,
the crewmate dies and is removed from play. 