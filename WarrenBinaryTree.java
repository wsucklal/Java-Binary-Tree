package WarrenBinaryTree;
import java.util.Scanner;

public class WarrenBinaryTree {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);

		LinkedBinaryTree <Integer> binaryTree = new LinkedBinaryTree<>();

		for(int i = 0;i<9;i++)
		{
			System.out.println("Please enter an integer to add to the binary tree:");
			binaryTree.add(input.nextInt());
		}

		binaryTree.print();
		System.out.println(" ");
		System.out.println("What would you like to find?");

		System.out.println( "In Tree: " + binaryTree.find(input.nextInt()));

	}
}
