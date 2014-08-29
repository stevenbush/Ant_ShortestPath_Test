package test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stack<Integer> stack = new Stack<>();
		Queue<Integer> queue = new LinkedList<>();

		for (int i = 0; i < 10; i++) {
			stack.push(i);
			queue.add(i);
		}

		System.out.println("stack----");
		for (int i = 0; i < 10; i++) {
			System.out.println(stack.pop());
		}

		System.out.println("queue----");
		for (int i = 0; i < 10; i++) {
			System.out.println(queue.poll());
		}
	}

}
