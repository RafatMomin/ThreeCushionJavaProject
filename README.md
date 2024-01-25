## COM S 227: Project 2
The purpose of this assignment is to give you lots of practice working with conditional logic and
managing the internal state of a class. You'll create one class, called ThreeCushion, that models
the game of three-cushion billiards. The game is played on a billiard table and involves two
players competing to be the first to reach a predetermined number of points. A player scores a
point by making a successful shot. A shot is successful when the player strikes their cue ball with
a cue stick, resulting in the cue ball impacting the tables cushions at least three time, while also
striking into the other two balls. The minimum three cushion impacts must happen before the
second ball is struck. Cushions surround the table on which the game is played (there are no
pockets on the table, like in pool). The shot does not end until all balls stop motion. There are
three balls; the players each chose a different cue ball from either white or yellow, the third ball
is red. A player continues to play at the table, taking shots until they fail to score a point or they
commit a foul. The failure or foul results in the end of one inning of the game, at which time the
other player takes over taking shots at the table .