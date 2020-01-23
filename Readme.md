# The maze of waze
OOP-Final Task.
# About this project 
This project is a game based on directed weighted graph (Ex2).
The goal of the game is to catch as many thieves as you can, in minimum moves.
# The base of the game:
The police car (robots) can move on the directed weighted graph.
The graph is located on Ariel city in Israel (each vertex in the graph has the real coordinates of Ariel city).
# Game manual mode:
In the manual mode you can choose which robot to move and where to move it by entering the robot id and the vertex destination's id.
# Game Auto mode:
In the auto mode the robots are moving automaticly by using Dkjistra algorithm to find their shortest path.
# Elements in the game:
* Fruits: displaying as prisoner\theives. Each fruit has different value, the higher the value the more speed the robot gains.
* Robots: displaying as police car. Each robot has speed, the more fruits with higher value the robot grabs, the higher his speed is.
* Graph: game based on directed weighted graph.
* Levels: there are levels in the game between 0 to 23, the higher the level is, the harder to pass it.
# KML file:
In the end of each game you get KML text file in data folder. You can open the kml file on google earth and see the location on real place and your gameplay recored on it.
# Game GUI:
In the game we are using StdDraw class (which is using JFrame) to display our GUI.
you can choose your mode to play and also you can see you statistics in the game.

# Game statistics:
You can see your statistics in the game:
* My game info - your best score in each level and how many games you played.
* My game rank - your rank in the server in each level (if you played online).

# Game server:
There is a server for this game. you can log in with your ID number and your score will be saved on the server. Then you can see your game stats on the server

# For more infromation:
See the wiki page!

# Authors:
* Arthur Boltak
* Zohar Zrihen
