import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

class Main {
	
	public static void main(String[] args) {
		String test1, test2, test3, test4;
		
		// EJERCICIO 1
		
		test1 = "()";
		test2 = "(){()[(){}][]}";
		test3 = "[(])";
		test4 = "({})]";
		
		formatTest(test1, brackets(test1), "Ejercicio1");
		formatTest(test2, brackets(test2), "");
		formatTest(test3, brackets(test3), "");
		formatTest(test4, brackets(test4), "");
		// EJERCICIO 2
		
		test1 = "A+B*C";
		test2 = "A*(B+C)/D-E";
		test3 = "(A+B)*(C-D)/H";
		test4 = "(A+B)*(C-D)+(F-G)/H";
		
		formatTest(test1, postfixNotation(test1), "\nEjercicio2");
		formatTest(test2, postfixNotation(test2), "");
		formatTest(test3, postfixNotation(test3), "");
		formatTest(test4, postfixNotation(test4), "");
		// EJERCICIO 3
		
		test1 = "abc$d@ef$@g$";
		formatTest(test1, postfixNotationQueue(test1), "\nEjercicio 3");
		// EJERCICIO 4
		
		test1 = "abc#de##f#ghi#jklmn#op#";
		formatTest(test1, backSpace(test1), "\nEjercicio 4");
		
		// EJERCICIO 5
		int [] testArr = {4, -1, 5, 2, 3};
		formatTest(arrToString(testArr), "" + interviewWait(testArr), "\nEjercicio 5");
		// EJERCICIO 6
		
		int [][] testArr2 = {{1, 4, 5}, {1, 3, 4}, {2, 6}};
		String arrStringInput = '[' + arrToString(testArr2[0]) + ',' + arrToString(testArr2[1]) + ',' + arrToString(testArr2[2]) + ']';
		formatTest(arrStringInput, "" + arrToString(mergeSortedList(testArr2)), "\nEjercicio 6");
	}
	
	
	/*
	 * [EJERCICIO 1] (Stack)
	 * Verifica la integridad de una cadena con llaves, parentesis y corchetes 
	 * Retorna correcto si tiene el patron correcot, caso contrario retorn incorrecto.
	 */
	public static String brackets(String string) {
	
		Stack<Character> stack = new Stack<Character>();
		
		for (int i = 0; i < string.length(); i++) {
			
			char charItem = string.charAt(i);
			
			if(charItem == '{' || charItem == '(' || charItem == '[') {
				stack.push(charItem);
			}else {
				
				if(stack.isEmpty()) return "incorrecto";
				
				char peek = stack.peek();
				
				if(peek == '{' && charItem == '}') {
					stack.pop();
				}else if(peek == '(' && charItem == ')') {
					stack.pop();
				}else if(peek == '[' && charItem == ']') {
					stack.pop();
				}else {
					return "incorrecto";
				}
			}
		}
		
		return stack.isEmpty()? "correcto": "incorrecto";
	}
	
	/*
	 * [EJERCICIO 2](Stack)
	 * 
	 */
	public static String postfixNotation(String string) {
		
		String response = "";
		Stack<Character> stack = new Stack<Character>();
		
		for (int i = 0; i < string.length(); i++) {
			
			char charItem = string.charAt(i);
			
			if(charItem != '+' && charItem != '-' && charItem != '*' && charItem != '/' && charItem != '(' && charItem != ')') {
				response += charItem;
			}else if(charItem == '(') {
				stack.push(charItem);
			}else if(charItem == ')') {
				
				char unstackChar = stack.pop();
				
				while(unstackChar != '(') {
					response += unstackChar;
					
					if(stack.isEmpty()) 
						break;
					unstackChar = stack.pop();
				}
			}else {
				while(!stack.isEmpty() && precedence(stack.peek()) <= precedence(charItem)) 
					response += stack.pop();
				
				stack.push(charItem);
			}
		}
		
		while(!stack.isEmpty()) 
			response += stack.pop();
		
		return response;
	}
	
	private static int precedence(char character) {
		
		if(character == '+' || character == '-') 
			return 2;
		
		if(character == '/' || character == '*') 
			return 1;
		
		return 3;
	}
	
	
	/* [EJERCICIO 3](Queue)
	 * 
	 * 
	 */
	public static String postfixNotationQueue(String string) {
		
		Queue<Character> queue = new LinkedList<Character>();
		String response = "";
		boolean lowerCase = true;
		
		for (int i = 0; i < string.length(); i++) {
			
			char charItem = string.charAt(i);
			
			if(charItem == '$') {
				while(!queue.isEmpty()) {
					response += queue.remove();
				}
			}else if(charItem == '@') {
				
				lowerCase = lowerCase?false:true;
				Queue<Character> temp = new LinkedList<Character>();
				
				while(!queue.isEmpty()) {
					char tempChar = queue.remove();
					
					if(lowerCase)
						tempChar = Character.toLowerCase(tempChar);
					else
						tempChar = Character.toUpperCase(tempChar);
					
					temp.add(tempChar);
				}
				
				while(!temp.isEmpty()) {
					queue.add(temp.remove());
				}
			}else {
				if(lowerCase)
					charItem = Character.toLowerCase(charItem);
				else 
					charItem = Character.toUpperCase(charItem);
				
				queue.add(charItem);
			}
			
		}
		
		return response;
	}
	
	/*
	 * [EJERCICIO 4](Dequeue)
	 * Retorna una cadena con la respuesta
	 * Encola una cada caracter de una cadena ingresada
	 * cuando encuentra el caracter # desencola
	 */
	public static String backSpace(String string) {
		
		Deque<Character> dequeue = new LinkedList<Character>();
		String response = "";
		
		for (int i = 0; i < string.length(); i++) {
			
			char charItem = string.charAt(i);
			if(charItem == '#') {
				dequeue.removeLast();
			}else {
				dequeue.add(charItem);
			}
		}
		
		
		while(!dequeue.isEmpty()) {
			response += dequeue.remove();
		}
		
		return response;
	}
	
	/*
	 * [EJERCICIO 5]
	 * 
	 */
	public static int interviewWait(int [] places) {
		Deque<Integer> dequeue = new LinkedList<Integer>();
		int tiempo = 0;
		
		for (int i = 0; i < places.length; i++) 
			dequeue.add(places[i]);
		
		while(!dequeue.isEmpty()) {
			
			if(dequeue.getFirst() == -1 || dequeue.getLast() == -1) {
				break;
			}else {
				if(dequeue.getFirst() < dequeue.getLast()) {
					tiempo += dequeue.remove();
				}else {
					tiempo += dequeue.removeLast();
				}
			}

		}
	
		return tiempo;
	}
	
	/*
	 * [EJERCICIO 6]
	 * 
	 */
	public static int [] mergeSortedList(int [][] array) {
		PriorityQueue<Integer> prioQueue = new PriorityQueue<Integer>();
		int size = 0;
		
		for (int i = 0; i < array.length; i++) {
			
			size += array[i].length;
			
			for (int j = 0; j < array[i].length; j++) 
				prioQueue.add(array[i][j]);
			
		}

		int [] responseArr = new int[size];
		int count = 0;
		
		while(!prioQueue.isEmpty()) {
			responseArr[count] = prioQueue.remove();
			count++;
		}
		
		return responseArr;
	}
	
	
	// Otro
	public static String arrToString(int [] arr) {
		
		String response = "[";
		
		for (int i = 0; i < arr.length; i++) {
			
			response += arr[i];

			if(i != arr.length - 1)
				response += ", ";

		}
		
		return response + "]";
	}
	
	public static void formatTest(String input, String output, String title) {
		System.out.println(title);
		System.out.println("Input: " + input);
		System.out.println("Output: " + output);
	}
}