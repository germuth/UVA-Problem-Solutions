package ca.germuth.acm_pacnw_2008.PropBot;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * PropBot
 * ACM ICPC Pacific Northwest Regionals 2008 
 * Problem J
 * 
 * TODO
 * my java form was too slow, even though i had many more considerations than C DFS approach.
 * perhaps it is the fact that i used BFS not DFS, or something else i am missing.
 * must come back 
 * C++ solution taken from here:
 * http://blog.csdn.net/u012860063/article/details/39860551
 * @author Aaron
 *
 */
class Main {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		
		int cases = s.nextInt();
		
		for(int a = 0; a < cases; a++){
			int seconds = s.nextInt();
			double xF = s.nextDouble();
			double yF = s.nextDouble();
			
			x = xF;
			y = yF;
			ti = seconds;
			d = dis(0,0,x,y);  
	        dfs(0,0,0,0);  
	        System.out.printf("%.6f\n", d);
//	        printf("%.6lf\n",d);  
//			LinkedList<State> openList = new LinkedList<State>();
//			State start = new State(0.0, 0.0, 0, Direction.R);
//			openList.add(start);
			
//			HashMap<String, Integer> visited = new HashMap<String, Integer>();
//			visited.put(start.dir.name() + start.x + start.y + "", 0);
			
//			double minDist = Integer.MAX_VALUE;
//			double[][] minSoFar = new double[100][100];
//			for(double[] row: minSoFar){
//				Arrays.fill(row, Integer.MAX_VALUE);
//			}
//			DecimalFormat f = new DecimalFormat("%.1f");
//			while(!openList.isEmpty()){
//				State curr = openList.pop();
//				
//				double currDist = curr.distTo(xF, yF);
//				if(currDist < minDist){
//					minDist = currDist;
//				}
//				if(curr.sec == seconds){
//					continue;
//				}
//				
//				State turn = new State(curr);
//				State move = new State(curr);
//				//try turning
//				if(turn.numSpins < 7){
//					turn.turn();
//					turn.numSpins++;
//					turn.sec++;
////					turn.history.add(curr);
////					String state = turn.dir.name() + f.format(turn.x) + f.format(turn.y);
////					if(!visited.containsKey(state) || (visited.containsKey(state) && (visited.get(state) > turn.sec))){
////						visited.put(state, turn.sec);
//						openList.add(turn);
////					}
//				}
//				//try moving
//				move.move();
//				move.numSpins = 0;
//				move.sec++;
////				move.history.add(curr);
//				//determine how far away we are
//				double newDist = Math.sqrt(move.distTo(xF, yF));
//				//determine how far we could move with remaining time
//				double maxDist = (seconds - move.sec)*10;
//				//only consider moving here if we could get back close enough to yield a closer distance
//				double potentialDistanceAway = newDist - maxDist;
//				if(potentialDistanceAway <= Math.sqrt(minDist)){
////					String state = move.dir.name() + f.format(move.x) + f.format(move.y);
////					if(!visited.containsKey(state) || (visited.containsKey(state) && (visited.get(state) > turn.sec))){
////						visited.put(state, move.sec);
//						openList.add(move);
////					}			
//				}
//			}
			
//			System.out.printf("%.6f", Math.sqrt(minDist));
//			System.out.println("");
//			System.out.printf("%.6f", minDist);
		}
		s.close();
	}
	
	static double x;
	static double y;
	static double d;
	static double ti;
	static double dd = 7.071067812;
	public static double dis(double x1, double y1, double x2, double y2)  
	{  
	    return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));  
	}  
	  
	public static void dfs(double x1,double y1,int dir,int tt)  
	{  
	    double aa;  
	    dir %= 8;  
	    aa=dis(x1,y1,x,y);  
	    if(aa < d)  
	    {  
	        d = aa;  
	    }  
	    if(tt==ti ||(aa-10*(ti-tt)>d))//如果剩下的步数就算走直线都比当前的d大就不用往下搜了  
	    {  
	        return ;  
	    }  
	    if(dir == 0)//沿+x走10cm  
	    {  
	        dfs(x1+10,y1,dir,tt+1);  
	    }  
	    else if(dir == 1)//沿+x偏-y为45°方向走10cm  
	    {  
	        dfs(x1+dd,y1-dd,dir,tt+1);  
	    }  
	    else if(dir == 2)//沿-y走10cm  
	    {  
	        dfs(x1,y1-10,dir,tt+1);  
	    }  
	    else if(dir == 3)//沿-y偏-x为45°方向走10cm  
	    {  
	        dfs(x1-dd,y1-dd,dir,tt+1);  
	    }  
	    else if(dir == 4)//沿-x走10cm  
	    {  
	        dfs(x1-10,y1,dir,tt+1);  
	    }  
	    else if(dir == 5)//沿-x偏+y为45°方向走10cm  
	    {  
	        dfs(x1-dd,y1+dd,dir,tt+1);  
	    }  
	    else if(dir == 6)//沿+y走10cm  
	    {  
	        dfs(x1,y1+10,dir,tt+1);  
	    }  
	    else if(dir == 7)//沿+y偏+x为45°方向走10cm  
	    {  
	        dfs(x1+dd,y1+dd,dir,tt+1);  
	    }  
	    dfs(x1,y1,dir+1,tt+1);  
	}  
}class State{
	//sin(A) = opposite / hypotenuse
	//opposite = sin(45) * hypotenuse
	//opposite = 7.07...
	static final double DIAG = 7.071067812;
	double x;
	double y;
	int sec;
	Direction dir;
	int numSpins;
//	ArrayList<State> history;
	public State(double xx, double yy, int seco, Direction d){
		x = xx;
		y = yy;
		sec = seco;
		dir = d;
		numSpins = 0;
//		history = new ArrayList<State>();
	}
	
	public State(State dup){
		x = dup.x;
		y = dup.y;
		sec = dup.sec;
		dir = dup.dir;
		numSpins = dup.numSpins;
//		history = new ArrayList<State>();
//		history.addAll(dup.history);
	}
	
	public double distTo(double xFinal, double yFinal){
		return Math.pow(xFinal - x, 2) + Math.pow(yFinal - y, 2);
	}
	
	public void turn(){
		switch(dir){
		case UL: dir = Direction.U; break;
		case U: dir = Direction.UR; break;
		case UR: dir = Direction.R; break;
		case R: dir = Direction.DR; break;
		case DR: dir = Direction.D; break;
		case D: dir = Direction.DL; break;
		case DL: dir = Direction.L; break;
		case L: dir = Direction.UL; break;
		}
	}
	
	public void move(){
		switch(dir){
		case UL: x -= DIAG; y += DIAG; break;
		case U: y += 10; break;
		case UR :x += DIAG; y += DIAG;  break;
		case R: x += 10; break;
		case DR: x += DIAG; y -= DIAG;  break;
		case D: y -= 10; break;
		case DL: x -= DIAG; y -= DIAG; ; break;
		case L: x -= 10; break;
		}
	}

	@Override
	public String toString() {
		return this.dir.name() + " " + this.x + " " + this.y;
	}
	
}enum Direction{
	UL, U, UR, R, DR, D, DL, L
}