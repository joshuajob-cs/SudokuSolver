import java.util.Arrays;
import java.util.ArrayList;
public class SudukoSolver{
    private String[] startPuz;
    private String[] endPuz;
    private Position[] unknowns;
    public SudukoSolver(String[] puzzle){
        startPuz = new String[9];
        for(int i = 0; i < 9; i++){
            startPuz[i] = puzzle[i];
        }
        endPuz = new String[9];
        for(int i = 0; i < 9; i++){
            endPuz[i] = puzzle[i];
        }
        setUnknowns(puzzle);
    }

    public String[] getStart(){
        return startPuz;
    }

    public String[] getEnd(){
        return endPuz;
    }

    public void setUnknowns(String[] puzzle){
        int count = 0;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(puzzle[i].substring(j,j+1).equals("_")){
                    count++;
                }
            }
        }
        unknowns = new Position[count];
    }

    public Position[] getUnknowns(){
        return unknowns;
    }

    public ArrayList<String> getSols(Position pos, String[] puzzle){
        String[] nums = {"1","2","3","4","5","6","7","8","9"};
        ArrayList<String> ret = new ArrayList<String>(Arrays.asList(nums));
        for(int i = 0; i < 9; i++){
            ret.remove(puzzle[pos.getRow()].substring(i, i+1));
        }
        for(int i = 0; i < 9; i++){
            ret.remove(puzzle[i].substring(pos.getCol(), pos.getCol()+1));
        }
        for(int i = pos.getSqr() - (pos.getSqr()%3); i <= (pos.getSqr() - (pos.getSqr()%3)) + 2; i++){
            for(int j = (pos.getSqr()%3) * 3; j <= ((pos.getSqr()%3) * 3) + 2; j++){
                ret.remove(puzzle[i].substring(j, j+1));
            }
        }
        return ret;
    }

    public Position getMinSols(String[] puzzle){
        int min = 10;
        Position ret = null;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(puzzle[i].substring(j,j+1).equals("_")){
                    if(getSols(new Position(i,j), puzzle).size() < min){
                        ret = new Position(i,j);
                        min = getSols(new Position(i,j), puzzle).size();
                    }
                }
            }
        }
        return ret;
    }

    public void solveKnown(){
        Position pos = null;
        while((getMinSols(endPuz) != null)&&(getSols(getMinSols(endPuz), endPuz).size() == 1)){
            pos = getMinSols(endPuz);
            endPuz[pos.getRow()] = changeChar(pos.getCol(), endPuz[pos.getRow()], getSols(pos, endPuz).get(0));
        }
        setUnknowns(endPuz);
    }

    public void solveGuess(int[] guess){
        String[] tempPuz = new String[9];//set Temporary Puzzle to End Puzzle
        for(int i = 0; i < 9; i++){
            tempPuz[i] = endPuz[i];
        }
        int count = 0;
        Position pos = getMinSols(tempPuz);//Find position with least possibilities
        unknowns[count] = pos;//Store position in unknowns for future use
        while((count < unknowns.length)&&(getSols(pos, tempPuz).size() > 0)){//While solutions exsist and not all filled in
            tempPuz[pos.getRow()] = changeChar(pos.getCol(), tempPuz[pos.getRow()], getSols(pos, tempPuz).get(guess[count]));
            //Change the specified position to one of the possible solutions as specified by index count of guess
            count++;
            pos = getMinSols(tempPuz);//Find position with least possibilities now that tempPuz has been altered
            if(count < unknowns.length){
                unknowns[count] = pos;//Store position in unknowns for future use
            }
        }
        if(count >= unknowns.length){//If everything is filled in, puzzle is solved!
            endPuz = new String[9];
            for(int i = 0; i < 9; i++){
                endPuz[i] = tempPuz[i];
            }
        }
        else{//Otherwise, something was guessed incorrectly
            int[] nextGuess = new int[guess.length];//Set nextGuess to be the same as guess
            for(int i = 0; i < guess.length; i++){
                nextGuess[i] = guess[i];
            }
            boolean unchanged = true;
            count --;//Set count to index of most recently changed position
            pos = unknowns[count];//Position becomes most recently changed position
            while(unchanged){//Repeat this until nextGuess is changed
                tempPuz[pos.getRow()] = changeChar(pos.getCol(), tempPuz[pos.getRow()], "_");//Replace position with "-"
                if(nextGuess[count] + 1 < getSols(pos, tempPuz).size()){//If trying another guess at this position is possible
                    nextGuess[count]++;//Change guess at this position
                    unchanged = false;//End while loop
                    solveGuess(nextGuess);//Restart the process
                }
                else{ //If all guesses at this position have been used
                    nextGuess[count] = 0;//Start Over guesses at this position
                    count--;//Set count to index of previous guess
                    pos = unknowns[count];//Position becomes previous position
                }
            }
        }
    }

    public String changeChar(int index, String str, String ad){
        return str.substring(0,index) + ad + str.substring(index + 1);
    }

    @Override
    public String toString(){
        String ret = "";
        for(int i =0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                ret += endPuz[i].substring(j,j+1);
                if((j%3 == 2)&&(j != 8)){
                    ret += ", "; 
                }
                else{
                    ret += "  ";
                }
            }
            ret += "/n";
        }
        return ret;
    }

    public void solve(){
        solveKnown();
        if(getUnknowns().length > 0){
        solveGuess(new int[getUnknowns().length]);
    }
    }
}