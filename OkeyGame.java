/**
 * Parinaz Jafaripourbaghali  22201152
 */
import java.util.Arrays;
import java.util.Random;

public class OkeyGame {

    Player[] players;
    Tile[] tiles;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;
    int currentTile = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[104];
        int currentTile = 0;

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 13; i++) {
            for (int j = 0; j < 2; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
    }

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already sorted
     */
    public void distributeTilesToPlayers() {
        
    
        for (int i = 0; i <= 3; i++) { //for each player
            int tilesToDistribute ;
    
            if (i == 0) {
                tilesToDistribute = 15;//my tiles should be 15, as I stat the game...
            } else {
                tilesToDistribute = 14; //for computer players
            }
    
            for (int j = 0; j < tilesToDistribute; j++) {
                players[i].playerTiles[j] = tiles[i * 15 + j];
                players[i].numberOfTiles++;
                tiles[i * 15 + j] = null;
            }
        }
        
    }
    

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        //CHANGED/FIXED
        players[currentPlayerIndex].addTile(lastDiscardedTile);
        return lastDiscardedTile.toString();

    }

    // duplicate of getLastDiscardedTile method that returns Tile 
    public Tile getLastDiscardedTile2(){
        players[currentPlayerIndex].addTile(lastDiscardedTile);
        return lastDiscardedTile;
    }

    
    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        //to get the index of the last tile
        int lastTileIndex = -1; 
        //in case if the element is not null, then it changes the index for the last tile
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != null) {
                lastTileIndex = i;
            }
        }
        
        if (lastTileIndex != -1) {
            Tile topTile = tiles[lastTileIndex];
            players[currentPlayerIndex].addTile(topTile);
            tiles[lastTileIndex] = null; // so tile is removed
            return topTile.toString();
        }
        
        return null;
    }

    // duplicate of getTopTile method that returns Tile 
    public Tile getTopTile2(){
        //to get the index of the last tile
        int lastTileIndex = -1; 
        //in case if the element is not null, then it changes the index for the last tile
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != null) {
                lastTileIndex = i;
            }
        }
        
        if (lastTileIndex != -1) {
            Tile topTile = tiles[lastTileIndex];
            players[currentPlayerIndex].addTile(topTile);
            tiles[lastTileIndex] = null; // so tile is removed
            return topTile;
        }
        
        return null;

    }
        ////////////////////////////Amina////////////////////////////////////
    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {

        Random rand = new Random();
        //shuffle method ??????????/
        for (int i = tiles.length-1; i > 0; i--) {
            int randTileIndex = rand.nextInt(i + 1); 
            
            //REMINDER - MAYBE I CAN USE THE SETTLED SHUFFLE METHOD INSTEAD, (?RECHANGE) ????????????????????????????         
            Tile current = tiles[i];
            tiles[i] = tiles[randTileIndex];
            tiles[randTileIndex] = current;
        }

    }
        ////////////////////////////Amina////////////////////////////////////
    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game. Use calculateLongestChainPerTile method to get the
     * longest chains per tile.
     * To win, you need one of the following cases to be true:
     * - 8 tiles have length >= 4 and remaining six tiles have length >= 3 the last one can be of any length
     * - 5 tiles have length >= 5 and remaining nine tiles have length >= 3 the last one can be of any length
     * These are assuming we check for the win condition before discarding a tile
     * The given cases do not cover all the winning hands based on the original
     * game and for some rare cases it may be erroneous but it will be enough
     * for this simplified version
     */
    public boolean didGameFinish() {

    boolean gameIsOver = true;
    int[] longestChainPerTile = players[currentPlayerIndex].calculateLongestChainPerTile();
    int fourOrMore = 0;
    int fiveOrMore = 0;
    int threeOrMore = 0;

        for (int i = 0; i < longestChainPerTile.length; i++){
            if ( longestChainPerTile[i] >= 4){
                fourOrMore++;
            }
            if (longestChainPerTile[i] >= 5){
                fiveOrMore++;
            }
            if (longestChainPerTile[i] >= 3){
                threeOrMore++;
            }
        }
       //if the last one can be any lenth, so every tile length should be >=, ???????????
        if (((fourOrMore >= 8) && threeOrMore >= 6) || (fiveOrMore >= 5 && threeOrMore >= 9)){
            return gameIsOver;
        }
    
        return !gameIsOver;
    }

    /* Rida
     * TODO: Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You may choose randomly or consider if the discarded tile is useful for
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() {
        Random rand = new Random();
        int randomInt = rand.nextInt(2);
        if (randomInt == 0){
            players[currentPlayerIndex].addTile(getTopTile2());
            System.out.println("The computer picks from tiles.");
            
        }
        else{
            players[currentPlayerIndex].addTile(getLastDiscardedTile2());
            System.out.println("The computer picks from discarded tiles.");
        }
        
    }
    

    /* Rida
     * TODO: Current computer player will discard the least useful tile.
     * For this use the findLongestChainOf method in Player class to calculate
     * the longest chain length per tile of this player,
     * then choose the tile with the lowest chain length and discard it
     * this method should print what tile is discarded since it should be
     * known by other players
     */
    public void discardTileForComputer() {//CHANGED

    
    Tile[] currPLayerTiles = players[currentPlayerIndex].getTiles();

    int lowestChainLength = Integer.MAX_VALUE;
    int discardTileIndex = -1;

    for (int i = 0; i < players[currentPlayerIndex].numberOfTiles; i++) {
        Tile currentTile = currPLayerTiles[i];
        int currPlayerLCofCurrTile = players[currentPlayerIndex].findLongestChainOf(currentTile);

        if (currPlayerLCofCurrTile < lowestChainLength) {
            lowestChainLength = currPlayerLCofCurrTile; //if less, then update the index of discarded tile
            discardTileIndex = i;
        }
    }

    Tile tileToDiscard = currPLayerTiles[discardTileIndex]; 
    discardTile(discardTileIndex);
    System.out.println("The tile "+tileToDiscard.toString() + " is discarded.");
    }

    /* Rida
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {

    //CHANGED/FIXED
        lastDiscardedTile = players[currentPlayerIndex].getAndRemoveTile(tileIndex);
        players[currentPlayerIndex].numberOfTiles--;
    }

    public void currentPlayerSortTilesColorFirst() {
        players[currentPlayerIndex].sortTilesColorFirst();
    }

    public void currentPlayerSortTilesValueFirst() {
        players[currentPlayerIndex].sortTilesValueFirst();
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
