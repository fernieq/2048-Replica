Name: Yiluo Qin
cs8bwacr
Program Description: for this PA, our main purpose is to let the players to have
the opportunity to play the actual 2048 game on the screen! With that being said,
I mean we have created ways to make the tiles colorful, with each tile has its 
accordingly number. We can also check the current score on the screen. Bascially,
we have made it possible to visualize the game instead of just making it merely
funtional.The way to accomplish this goal is to treat each little tile of the 
game single element, and we want to put all these small pieces together and show
them on a literal stage which allows the players to see the game.Every time we 
press the arrow keys, we also need to make sure we update the tiles and the 
score correctly! And if the player decides to quit the game, he can simply press
"S" key to quit the game!

Short response:
1. mkdir -p fooDir/barDir allows us to do it
2. For example, if I use * with rm in the current directory such as "rm *xxx*",
it will remove all the files in the current directory that have the string 
"xxx" in their name.
3. gvim -p *.java
4. a static method means a method belongs to the class. For example, in the 
GridPane class, there is setHalignment(Node child, HPos value) static method,
even we are inside another class we can get the specific method by doing 
something like GridPane.setHalignment with the arguments passed in. 
5. Because the student is drawing many similar objects with many similar traits,
she should use the knowledge of iheritance. We should create a parent abstract 
class which provides abstract methods such as drawcolor or drawName methods.
Then, we should inherit the abstarct class and make concrete classes of different
shapes. Inside each subclass, we override the abstract methods from the parent 
class and draw each individual shape's specific color and shapes, etc. For a 
shape with different colors, we should even inherit a Square class, and each of 
its children should override its Color method. At the same time, each different
squares of different colors will share their parent's draw method because they
have the same shape.
