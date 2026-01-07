public class Position{
    private int row;
    private int col;
    private int sqr;
    public Position(int row, int col){
        this.row = row;
        this.col = col;
        sqr = (row - (row%3)) + (col/3);
    }
    
    public int getRow(){
        return row;
    }
    
    public int getCol(){
        return col;
    }
    
    public int getSqr(){
        return sqr;
    }
    
    @Override
    public String toString(){
        return "(" + row + "," + col + "," + sqr + ")";
    }
}