# Battlecraft

My first attempt at making a voxel game. It really was just a series of applications for testing "picking" (find 3d object behind mouse) & rendering.

You will need to reference slick, jinput, lwjgl, and lwjgl_util found in the Dependencies folder. For lwjgl and jinput you need to reference the appropriate native libraries (depending on your OS) in your build path.

### Applications

- Battlecraft [link](src/wars/Battlecraft.java)
	- A rendering test for a voxel, face visibilty testing, and a skybox
	
![Battlecraft](Battlecraft_Battlecraft.png]
	
- Game [link](src/wars/Game.java)
	- A test application which renders the game FPS with images
	
![Game](Battlecraft_Game.png]
 
- Game2d [link](src/wars/Game2d.java)
	- A topdown voxel-like world. Its 2d that appears 3d with some distance shading and custom perspective calculations
	
![Game2d](Battlecraft_Game2d.png]

- GamePick [link](src/wars/GamePick.java)
	- A simple application testing picking
	
![GamePick](Battlecraft_GamePick.png]

- WorldEdit [link](src/wars/WorldEdit.java)
	- A simple application to test picking and shaping landscape
	
![WorldEdit](Battlecraft_WorldEdit.png]

- WorldOfMinecraft [link](src/wars/WorldOfMinecraft.java)
	- A more complete attempt at a 3d voxel world
	
![WorldOfMinecraft](Battlecraft_WorldOfMinecraft.png]