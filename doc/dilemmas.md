problem: in order to have the first data file which defines the looks for everything as a function of
	input and state, i need to have nicknames for looks that a visualizer can have.
	right now, my solution is to have a list of retrievable functions and put the index there.
	but that is not very scalable, and it basically means that i have to enumerate every possible look inside the visualizer.
	also, it makes it so that the nicknames suck, and I want better nicknames. however, if i have better nicknames,
	then that requires enumerating the nicknames in the .java file which is not good since we don't want to have to 
	open the .java file.

solution: I can make a new object for each look, and that object takes a large number of parameters from the 
		visualizer and modifies the look of the visualizer, so it's essentially a wrapper on a function.
		this can be a different type of object per visualizer, but they should all share one common interface,
		which is how they show up in the map returned by  the reader.
		Then wait a second, how would the visualizer ever know what kind of object is being returned by the 
		reader? That would require downcasting, and I refuse to do that as a solution.
		so the ultimate solution would probably be to have some sort of generic interface for visualizers, 
		but at the moment I really don't want to do that one when there's a solution looking at me, so I will
		do the next best thing:

		I will make void methods within the .java file, then use reflection and maybe nicknames to reference them.
		or even for now, I will make the same list as shows up in room visualizer, but add nicknames.

everything that can be highlighted and what state highlights it:

wait oh actually i have to be uhhh make it so that the weapon you have selected / crewmember you have selected is highlighted


