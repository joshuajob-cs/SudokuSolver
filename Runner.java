import java.util.Arrays;
import java.util.ArrayList;
public class Runner{
    public static void main(String[] args){
        String[] puzzle = {"4_2______", "____6____", "_589_____", "_9__5__8_", "___34__51", "_________", "__16__32_", "_8_1_____", "3_62__9_4"};
        SudukoSolver p1 = new SudukoSolver(puzzle);
        printPuz(p1.toString());
        p1.solve();
        printPuz(p1.toString());
        String[] puzzle2 = {"_________", "___16__52", "_7_5_4__6", "39_______", "62____39_", "_______6_", "9___3____", "_5_71_94_", "2__6__5_7"};
        SudukoSolver p2 = new SudukoSolver(puzzle2);
        printPuz(p2.toString());
        p2.solve();
        printPuz(p2.toString());
        String[] puzzle3 = {"8________", "__36_____", "_7__9_2__", "_5___7___", "____457__", "___1___3_", "__1____68", "__85___1_", "_9____4__"};
        SudukoSolver p3 = new SudukoSolver(puzzle3);
        printPuz(p3.toString());
        p3.solve();
        printPuz(p3.toString());
    }
    
    public static void printPuz(String str){
        String[] rows = str.split("/n");
        for(int i = 0; i < 9; i++){
            System.out.println(rows[i]);
        }
        System.out.println("");
    }
}