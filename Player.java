/**
 * Parinaz Jafaripourbaghali  22201152
 */
public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * This method calculates the longest chain per tile to be used when checking the win condition
     */
    public int[] calculateLongestChainPerTile() {
        // keep a seperate copy of the tiles since findLongestChainOf sorts them
        Tile[] tilesCopy = new Tile[numberOfTiles];
        for (int i = 0; i < numberOfTiles; i++) {
            tilesCopy[i] = playerTiles[i];
        }

        // make the calculations
        int[] chainLengths = new int[numberOfTiles];
        for (int i = 0; i < numberOfTiles; i++) {
            chainLengths[i] = findLongestChainOf(tilesCopy[i]);
        }

        // revert the playerTiles to its original form
        for (int i = 0; i < numberOfTiles; i++) {
            playerTiles[i] = tilesCopy[i];
        }

        return chainLengths;
    }

    /*
     * TODO: finds and returns the longest chain of tiles that can be formed
     * using the given tile. a chain of tiles is either consecutive numbers
     * that have the same color or the same number with different colors
     * some chain examples are as follows:
     * 1B 2B 3B
     * 5Y 5B 5R 5K
     * 4Y 5Y 6Y 7Y 8Y
     * You can use canFormChainWith method in Tile class to check if two tiles can make a chain
     * based on color order and value order. Use sortTilesColorFirst() and sortTilesValueFirst()
     * methods to sort the tiles of this player then find the position of the given tile t.
     * check how many adjacent tiles there are starting from the tile poisition.
     * Note that if you start a chain with matching colors it should continue with the same type of match
     * and if you start a chain with matching values it should continue with the same type of match
     * use the different values canFormChainWith method returns.
     */
    public int findLongestChainOf(Tile t) {
        int tilePosition;
        sortTilesColorFirst();
        tilePosition = findPositionOfTile(t);

        // TODO: find the longest chain starting from tilePosition going left and right

        int longestChainColorFirst = 1;

        for(int i = tilePosition; i < numberOfTiles ; i++)
        {
            if( t.canFormChainWith(playerTiles[i]) == 1)
            {
                int k = 1;
                while (k != 0 && i+k < numberOfTiles){ //******PARINAZ***** added the i+k < numberOfTiles to check not exceed from 15 limit.
                    if (t.canFormChainWith(playerTiles[i + k]) !=1 || i + k == 14){
                        i = i + k;
                        t = playerTiles[i];
                        k = 0;  
                    }
                    else{
                        k++;
                        longestChainColorFirst++;
                    }
                }
            }
            
        }

        sortTilesValueFirst();
        tilePosition = findPositionOfTile(t);
        
        // TODO: find the longest chain starting from tilePosition going left and right

        int longestChainValueFirst = 1;

        for(int i = tilePosition; i < numberOfTiles ; i++)
        {
            if( t.canFormChainWith(playerTiles[i]) == 2)
            {
                int k = 1;
                while (k != 0 && i + k < numberOfTiles){
                    if (t.canFormChainWith(playerTiles[i + k]) !=2){//*****PARINAZ***** I removed || i + k == 14
                        i = i + k;
                        t = playerTiles[i];


                        k = 0;  
                    }
                    else{
                        k++;
                        longestChainColorFirst++;
                    }
                }
            }
            
        }


        if(longestChainColorFirst > longestChainValueFirst) {
            return longestChainColorFirst;
        }
        else{
            return longestChainValueFirst;
        }
    }

    /*
     * TODO: removes and returns the tile in given index
     */
    public Tile getAndRemoveTile(int index) {//*****PARINAZ***** fixed it.
//        Tile[] array = new Tile[numberOfTiles-1];
//        Tile tileToBeRemoved = playerTiles[index];
//        for(int m = 0 ; m<index ; m++){
//            array[m] = playerTiles[m];
//        }
//        for(int n = index+1 ; n < numberOfTiles ; n++){
//            array[n-1] = playerTiles[n];
//        }
//
//        return tileToBeRemoved;
        Tile tileToBeRemoved = playerTiles[index];
        //shifting the tile to put the empty place to the end of length
        for (int i = index; i < numberOfTiles - 1; i++) {
            playerTiles[i] = playerTiles[i + 1];
        }
        //numberOfTiles--;
        return tileToBeRemoved;

    }

    /*
     * TODO: adds the given tile at the end of playerTiles array, should also
     * update numberOfTiles accordingly. Make sure the player does not try to
     * have more than 15 tiles at a time
     */
    public void addTile(Tile t) {
        if(numberOfTiles<15){
            playerTiles[numberOfTiles] = t;
            numberOfTiles++;
        }
    }
//    public void addTile(Tile t)
//    {
//        if( numberOfTiles < tilesOfPlayer.length)
//        {
//            tilesOfPlayer[numberOfTiles] = t;
//            numberOfTiles++;
//        }
//    }

    /*
     * TODO: uses bubble sort to sort playerTiles in increasing color and value
     * value order: 1 < 2 < ... < 12 < 13
     * color order: Y < B < R < K
     * color is more important in this ordering, a sorted example:
     * 3Y 3Y 6Y 7Y 1B 2B 3B 3B 10R 11R 12R 2K 4K 5K
     * you can use compareToColorFirst method in Tile class for comparing
     * you are allowed to use Collections.sort method
     */
    public void sortTilesColorFirst() {
        for (int j = 0; j < playerTiles.length ; j++){
            for (int i = 1; i < playerTiles.length-j; i++){
            //Sorted tiles according to tiles' colors.
            if (playerTiles[i].compareToColorFirst(playerTiles[i-1]) == -1){
                Tile changeTile = new Tile(playerTiles[i].value, playerTiles[i].color);
                playerTiles[i] = playerTiles[i-1];
                playerTiles[i-1] = changeTile;
            }
        }
    }

        //Other loop replaning sorting according to values.
        for (int j = 0; j < playerTiles.length; j++){
        for (int i = 1; i < playerTiles.length-j; i++){
            if (playerTiles[i].compareToColorFirst(playerTiles[i-1]) == 0 && playerTiles[i].value > playerTiles[i-1].value){
                Tile changeTile = new Tile(playerTiles[i].value, playerTiles[i].color);
                playerTiles[i] = playerTiles[i+1];
                playerTiles[i-1] = changeTile;
            }
        }
    }
    }

    /*
     * TODO: uses bubble sort to sort playerTiles in increasing value and color
     * value order: 1 < 2 < ... < 12 < 13
     * color order: Y < B < R < K
     * value is more important in this ordering, a sorted example:
     * 1B 2B 2K 3Y 3Y 3B 3B 4K 5K 6Y 7Y 10R 11R 12R
     * you can use compareToValueFirst method in Tile class for comparing
     * you are allowed to use Collections.sort method
     */
    public void sortTilesValueFirst() {
        for (int j = 0; j < playerTiles.length ; j++){
            for (int i = 1; i < playerTiles.length-j; i++){
            //Sorted tiles according to tiles' values.
            if (playerTiles[i-1].compareToValueFirst(playerTiles[i]) == 1){
                Tile changeTile = new Tile(playerTiles[i-1].value, playerTiles[i-1].color);
                playerTiles[i-1] = playerTiles[i];
                playerTiles[i] = changeTile;
            }
        }
    }

        //Other loop replaning sorting according to colors.
        for (int j = 0; j < playerTiles.length; j++){
            for (int i = 1; i < playerTiles.length-j; i++){
            if (playerTiles[i].compareToColorFirst(playerTiles[i-1]) == 1 && playerTiles[i].value == playerTiles[i-1].value){
                Tile changeTile = new Tile(playerTiles[i].value, playerTiles[i].color);
                playerTiles[i] = playerTiles[i-1];
                playerTiles[i-1] = changeTile;
            }
        }
    }
    }

    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].matchingTiles(t)) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
