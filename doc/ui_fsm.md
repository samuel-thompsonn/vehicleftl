##### instructions
1. target room with weapon
2. send crewmate to room
3. adjust power of room
4. adjust power of weapon
5. pause

##### states
state 0: select
- click on charged weapon x: go to 1.x
- click on crewmate x: switch to 2.x
- click on power: execute 3
- right click charged on weapon x: execute 4.x
- click on uncharged weapon x: execute 4.x

state 1.x: targeting with weapon x
- click on other weapon y: switch to targeting with weapon y
- right click on weapon x: execute 4.x, switch to 0
- click on enemy room: execute 1, switch to state 0
- right click: switch to state 0

state 2.x: command crewmate x
- click on weapon y: switch to targeting with weapon y
- right click on room: execute 2
- click on crewmate z: switch to 2.x
- click on power: execute 3, switch to 0
- click anywhere else: switch to 0

