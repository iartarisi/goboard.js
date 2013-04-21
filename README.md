# goboard.js

A ClojureScript project which should generate a Go board in JavaScript using HTML canvas.

## Usage

To try, just download the [goboard.js](https://raw.github.com/mapleoin/goboard.js/master/goboard.js) file and include it in a `script` tag on your webpage. You can then call the `goboard.draw` function.

`goboard.draw(element_id, stones, playing, last_x, last_y)` attributes explained:

- `element_id` - DOM element ID of the canvas element where the Go board will be drawn

- `stones` - a javascript one-dimensional Array object of 19*19 integers indicating the stones on the board. Each of them can be: 0 - no stone, 1 - black stone, 2 - white stone.

- `playing` (optional) - if given should be an integer: 1 - black, 2 - white which indicates the current player's color and that it is her turn to move.

- `last_move_x` (optional) - if given, indicates the board X coordinate of the last move, must be an integer between 0 and 18 (left to right)

- `last_move_y` (optional) - if given, indicates the board Y coordinate of the last move, must be an integer between 0 and 18 (top to bottom)


You can also try downloading the [example.html](https://raw.github.com/mapleoin/goboard.js/master/goboard.js) file which will show the example screenshot below.

![Screenshot](https://raw.github.com/mapleoin/goboard.js/master/screenshot.png)

## License

Copyright © 2013 Ionuț Arțăriși

Distributed under the Eclipse Public License, the same as Clojure.
