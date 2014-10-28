package ca.germuth.acm_southeastusa_2013.div2.E;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * Count Your Cousins
 * ACM ICPC SouthEast USA Regionals 2013
 * Problem E Division 2
 * 
 * Solved by simply taking in each number as string and iterating over each character.
 * If the sum is above 9, then a carry must take place
 * @author Germuth
 */
class Main {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		for (;;) {
			int n = s.nextInt();
			int k = s.nextInt();
			if (n == 0) {
				break;
			}
			int[] tree = new int[n];
			for (int i = 0; i < n; i++) {
				tree[i] = s.nextInt();
			}
			
			int lastVal = -1;
			Node kNode = null;
			int parentIndex = 0;
			
			ArrayList<Node> currentLayer = new ArrayList<Node>();
			ArrayList<Node> parentLayer = new ArrayList<Node>();
			
			for(int i = 0; i < tree.length; i++){
				int currVal = tree[i];
				Node curr = new Node(currVal, null, new ArrayList<Node>());
				
				//find kNode
				if(currVal == k){
					kNode = curr;
				}
				
				//handle first case, root node, no parent
				if(lastVal == -1){
					currentLayer.add(curr);
					parentLayer.add(curr);
					curr.parent = null;
					lastVal = currVal;
					continue;
				}
				
				if(lastVal + 1 == currVal){
					curr.parent = parentLayer.get(parentIndex);
					parentLayer.get(parentIndex).children.add(curr);
					
					currentLayer.add(curr);
				}else{
					//two cases, either we are switching levels, or switching parents
					//only switch level if at last parent

					//switching levels
					if(parentIndex == parentLayer.size() - 1){
						parentLayer = new ArrayList<Node>(currentLayer);
						currentLayer.clear();
						currentLayer.add(curr);
						parentIndex = 0;
						
						curr.parent = parentLayer.get(parentIndex);
						parentLayer.get(parentIndex).children.add(curr);
						
					}
					//switching parents
					else{
						parentIndex++;
						
						curr.parent = parentLayer.get(parentIndex);
						parentLayer.get(parentIndex).children.add(curr);
						
						currentLayer.add(curr);
					}
				}
				
				lastVal = currVal;
			}
			
			//time to count cousins
			int cousins = 0;
			if(kNode.parent == null || kNode.parent.parent == null){
				System.out.println("0");				
			}else{
				Node grandDaddy = kNode.parent.parent;
				for(Node uncle: grandDaddy.children){
					if(uncle == kNode.parent){
						continue;
					}
					cousins += uncle.children.size();
				}
				System.out.println(cousins);
			}
		}
		
		s.close();
	}
}class Node{
	int val;
	Node parent;
	ArrayList<Node> children;
	public Node(int v, Node p, ArrayList<Node> ch){
		this.val = v;
		this.parent = p;
		this.children = ch;
	}
	@Override
	public String toString() {
		return val + "";
	}
	
}
