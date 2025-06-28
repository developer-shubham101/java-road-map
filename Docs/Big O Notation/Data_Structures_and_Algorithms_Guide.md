# Data Structures and Algorithms (DSA) Guide

## Table of Contents
1. [Introduction to DSA](#introduction-to-dsa)
2. [Basic Data Structures](#basic-data-structures)
3. [Advanced Data Structures](#advanced-data-structures)
4. [Searching Algorithms](#searching-algorithms)
5. [Sorting Algorithms](#sorting-algorithms)
6. [Graph Algorithms](#graph-algorithms)
7. [Dynamic Programming](#dynamic-programming)
8. [Problem-Solving Strategies](#problem-solving-strategies)

## Introduction to DSA

Data Structures and Algorithms are fundamental concepts in computer science that help us organize and process data efficiently. Understanding DSA is crucial for writing optimized code and solving complex programming problems.

### Key Concepts
- **Data Structure**: A way to organize and store data for efficient access and modification
- **Algorithm**: A step-by-step procedure to solve a problem
- **Time Complexity**: How the runtime grows with input size
- **Space Complexity**: How much memory an algorithm uses

## Basic Data Structures

### 1. Arrays

Arrays are the most basic data structure - a collection of elements stored at contiguous memory locations.

```java
public class ArrayExample {
    public static void main(String[] args) {
        // Declaration and initialization
        int[] numbers = {1, 2, 3, 4, 5};
        
        // Accessing elements - O(1)
        System.out.println("First element: " + numbers[0]);
        
        // Traversing array - O(n)
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
        
        // Enhanced for loop
        for (int num : numbers) {
            System.out.print(num + " ");
        }
    }
}
```

**Time Complexities:**
- Access: O(1)
- Search: O(n)
- Insertion: O(n)
- Deletion: O(n)

### 2. Linked Lists

A linked list is a linear data structure where elements are stored in nodes, and each node points to the next node.

```java
class Node {
    int data;
    Node next;
    
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class LinkedList {
    Node head;
    
    // Insert at end - O(n)
    public void insertAtEnd(int data) {
        Node newNode = new Node(data);
        
        if (head == null) {
            head = newNode;
            return;
        }
        
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }
    
    // Insert at beginning - O(1)
    public void insertAtBeginning(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }
    
    // Delete node - O(n)
    public void deleteNode(int key) {
        Node temp = head, prev = null;
        
        if (temp != null && temp.data == key) {
            head = temp.next;
            return;
        }
        
        while (temp != null && temp.data != key) {
            prev = temp;
            temp = temp.next;
        }
        
        if (temp == null) return;
        
        prev.next = temp.next;
    }
    
    // Print list - O(n)
    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}
```

**Time Complexities:**
- Access: O(n)
- Search: O(n)
- Insertion at beginning: O(1)
- Insertion at end: O(n)
- Deletion: O(n)

### 3. Stacks

A stack is a LIFO (Last In, First Out) data structure.

```java
import java.util.Stack;

public class StackExample {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        
        // Push operation - O(1)
        stack.push(1);
        stack.push(2);
        stack.push(3);
        
        // Pop operation - O(1)
        System.out.println("Popped: " + stack.pop());
        
        // Peek operation - O(1)
        System.out.println("Top element: " + stack.peek());
        
        // Check if empty - O(1)
        System.out.println("Is empty: " + stack.isEmpty());
    }
}

// Custom Stack Implementation
class CustomStack<T> {
    private Node<T> top;
    
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
        }
    }
    
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
    }
    
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T data = top.data;
        top = top.next;
        return data;
    }
    
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return top.data;
    }
    
    public boolean isEmpty() {
        return top == null;
    }
}
```

**Time Complexities:**
- Push: O(1)
- Pop: O(1)
- Peek: O(1)
- Search: O(n)

### 4. Queues

A queue is a FIFO (First In, First Out) data structure.

```java
import java.util.LinkedList;
import java.util.Queue;

public class QueueExample {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        
        // Enqueue operation - O(1)
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        
        // Dequeue operation - O(1)
        System.out.println("Dequeued: " + queue.poll());
        
        // Peek operation - O(1)
        System.out.println("Front element: " + queue.peek());
        
        // Check if empty - O(1)
        System.out.println("Is empty: " + queue.isEmpty());
    }
}

// Custom Queue Implementation
class CustomQueue<T> {
    private Node<T> front, rear;
    
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
        }
    }
    
    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        
        if (rear == null) {
            front = rear = newNode;
            return;
        }
        
        rear.next = newNode;
        rear = newNode;
    }
    
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        
        T data = front.data;
        front = front.next;
        
        if (front == null) {
            rear = null;
        }
        
        return data;
    }
    
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return front.data;
    }
    
    public boolean isEmpty() {
        return front == null;
    }
}
```

**Time Complexities:**
- Enqueue: O(1)
- Dequeue: O(1)
- Peek: O(1)
- Search: O(n)

## Advanced Data Structures

### 1. Binary Trees

A binary tree is a hierarchical data structure where each node has at most two children.

```java
class TreeNode {
    int data;
    TreeNode left, right;
    
    TreeNode(int data) {
        this.data = data;
        left = right = null;
    }
}

public class BinaryTree {
    TreeNode root;
    
    // Insert a node - O(log n) average, O(n) worst case
    public void insert(int data) {
        root = insertRec(root, data);
    }
    
    private TreeNode insertRec(TreeNode root, int data) {
        if (root == null) {
            root = new TreeNode(data);
            return root;
        }
        
        if (data < root.data) {
            root.left = insertRec(root.left, data);
        } else if (data > root.data) {
            root.right = insertRec(root.right, data);
        }
        
        return root;
    }
    
    // Inorder traversal - O(n)
    public void inorderTraversal(TreeNode root) {
        if (root != null) {
            inorderTraversal(root.left);
            System.out.print(root.data + " ");
            inorderTraversal(root.right);
        }
    }
    
    // Preorder traversal - O(n)
    public void preorderTraversal(TreeNode root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorderTraversal(root.left);
            preorderTraversal(root.right);
        }
    }
    
    // Postorder traversal - O(n)
    public void postorderTraversal(TreeNode root) {
        if (root != null) {
            postorderTraversal(root.left);
            postorderTraversal(root.right);
            System.out.print(root.data + " ");
        }
    }
    
    // Search a node - O(log n) average, O(n) worst case
    public boolean search(TreeNode root, int data) {
        if (root == null || root.data == data) {
            return root != null;
        }
        
        if (data < root.data) {
            return search(root.left, data);
        }
        
        return search(root.right, data);
    }
}
```

**Time Complexities:**
- Insert: O(log n) average, O(n) worst case
- Search: O(log n) average, O(n) worst case
- Delete: O(log n) average, O(n) worst case
- Traversal: O(n)

### 2. Hash Tables

A hash table is a data structure that implements an associative array abstract data type.

```java
import java.util.HashMap;
import java.util.Map;

public class HashTableExample {
    public static void main(String[] args) {
        // Using Java's HashMap
        Map<String, Integer> hashMap = new HashMap<>();
        
        // Insert - O(1) average
        hashMap.put("apple", 1);
        hashMap.put("banana", 2);
        hashMap.put("cherry", 3);
        
        // Get - O(1) average
        System.out.println("Value for apple: " + hashMap.get("apple"));
        
        // Check if key exists - O(1) average
        System.out.println("Contains banana: " + hashMap.containsKey("banana"));
        
        // Remove - O(1) average
        hashMap.remove("banana");
        
        // Iterate through entries
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

// Custom Hash Table Implementation
class CustomHashTable<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;
        
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private Entry<K, V>[] table;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;
    
    @SuppressWarnings("unchecked")
    public CustomHashTable() {
        table = new Entry[DEFAULT_CAPACITY];
        size = 0;
    }
    
    public void put(K key, V value) {
        int index = getIndex(key);
        Entry<K, V> entry = table[index];
        
        while (entry != null) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
            entry = entry.next;
        }
        
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;
        size++;
    }
    
    public V get(K key) {
        int index = getIndex(key);
        Entry<K, V> entry = table[index];
        
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        
        return null;
    }
    
    public void remove(K key) {
        int index = getIndex(key);
        Entry<K, V> entry = table[index];
        Entry<K, V> prev = null;
        
        while (entry != null) {
            if (entry.key.equals(key)) {
                if (prev == null) {
                    table[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                size--;
                return;
            }
            prev = entry;
            entry = entry.next;
        }
    }
    
    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
}
```

**Time Complexities:**
- Insert: O(1) average, O(n) worst case
- Search: O(1) average, O(n) worst case
- Delete: O(1) average, O(n) worst case

## Searching Algorithms

### 1. Linear Search

```java
public class LinearSearch {
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1; // Element not found
    }
    
    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        int target = 22;
        
        int result = linearSearch(arr, target);
        if (result != -1) {
            System.out.println("Element found at index: " + result);
        } else {
            System.out.println("Element not found");
        }
    }
}
```

**Time Complexity:** O(n)
**Space Complexity:** O(1)

### 2. Binary Search

```java
public class BinarySearch {
    // Iterative binary search
    public static int binarySearchIterative(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                return mid;
            }
            
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1; // Element not found
    }
    
    // Recursive binary search
    public static int binarySearchRecursive(int[] arr, int target, int left, int right) {
        if (left > right) {
            return -1;
        }
        
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target) {
            return mid;
        }
        
        if (arr[mid] < target) {
            return binarySearchRecursive(arr, target, mid + 1, right);
        } else {
            return binarySearchRecursive(arr, target, left, mid - 1);
        }
    }
    
    public static void main(String[] args) {
        int[] arr = {2, 3, 4, 10, 40, 50, 60, 70};
        int target = 10;
        
        int result = binarySearchIterative(arr, target);
        if (result != -1) {
            System.out.println("Element found at index: " + result);
        } else {
            System.out.println("Element not found");
        }
    }
}
```

**Time Complexity:** O(log n)
**Space Complexity:** O(1) for iterative, O(log n) for recursive

## Sorting Algorithms

### 1. Bubble Sort

```java
public class BubbleSort {
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap elements
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            
            // If no swapping occurred, array is sorted
            if (!swapped) {
                break;
            }
        }
    }
    
    public static void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original array:");
        printArray(arr);
        
        bubbleSort(arr);
        System.out.println("Sorted array:");
        printArray(arr);
    }
}
```

**Time Complexity:** O(n²)
**Space Complexity:** O(1)

### 2. Quick Sort

```java
public class QuickSort {
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
    
    public static void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original array:");
        printArray(arr);
        
        quickSort(arr, 0, arr.length - 1);
        System.out.println("Sorted array:");
        printArray(arr);
    }
}
```

**Time Complexity:** O(n log n) average, O(n²) worst case
**Space Complexity:** O(log n)

### 3. Merge Sort

```java
public class MergeSort {
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            
            merge(arr, left, mid, right);
        }
    }
    
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        
        for (int i = 0; i < n1; i++) {
            leftArray[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = arr[mid + 1 + j];
        }
        
        int i = 0, j = 0, k = left;
        
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }
        
        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }
    
    public static void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original array:");
        printArray(arr);
        
        mergeSort(arr, 0, arr.length - 1);
        System.out.println("Sorted array:");
        printArray(arr);
    }
}
```

**Time Complexity:** O(n log n)
**Space Complexity:** O(n)

## Graph Algorithms

### 1. Graph Representation

```java
import java.util.*;

public class Graph {
    private Map<Integer, List<Integer>> adjacencyList;
    
    public Graph() {
        adjacencyList = new HashMap<>();
    }
    
    public void addVertex(int vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }
    
    public void addEdge(int source, int destination) {
        adjacencyList.get(source).add(destination);
        // For undirected graph, uncomment the next line
        // adjacencyList.get(destination).add(source);
    }
    
    public List<Integer> getNeighbors(int vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }
    
    public Set<Integer> getAllVertices() {
        return adjacencyList.keySet();
    }
    
    public void printGraph() {
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (Integer neighbor : entry.getValue()) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }
}
```

### 2. Depth-First Search (DFS)

```java
public class DFS {
    public static void dfs(Graph graph, int startVertex) {
        Set<Integer> visited = new HashSet<>();
        dfsRecursive(graph, startVertex, visited);
    }
    
    private static void dfsRecursive(Graph graph, int vertex, Set<Integer> visited) {
        visited.add(vertex);
        System.out.print(vertex + " ");
        
        for (int neighbor : graph.getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(graph, neighbor, visited);
            }
        }
    }
    
    public static void dfsIterative(Graph graph, int startVertex) {
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        
        stack.push(startVertex);
        
        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                System.out.print(vertex + " ");
                
                for (int neighbor : graph.getNeighbors(vertex)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }
}
```

**Time Complexity:** O(V + E) where V is vertices and E is edges
**Space Complexity:** O(V)

### 3. Breadth-First Search (BFS)

```java
public class BFS {
    public static void bfs(Graph graph, int startVertex) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        
        visited.add(startVertex);
        queue.offer(startVertex);
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");
            
            for (int neighbor : graph.getNeighbors(vertex)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
    }
}
```

**Time Complexity:** O(V + E)
**Space Complexity:** O(V)

## Dynamic Programming

### 1. Fibonacci with Memoization

```java
public class DynamicProgramming {
    // Fibonacci with memoization
    public static long fibonacci(int n) {
        long[] memo = new long[n + 1];
        return fibonacciHelper(n, memo);
    }
    
    private static long fibonacciHelper(int n, long[] memo) {
        if (n <= 1) {
            return n;
        }
        
        if (memo[n] != 0) {
            return memo[n];
        }
        
        memo[n] = fibonacciHelper(n - 1, memo) + fibonacciHelper(n - 2, memo);
        return memo[n];
    }
    
    // Fibonacci with tabulation
    public static long fibonacciTabulation(int n) {
        if (n <= 1) {
            return n;
        }
        
        long[] dp = new long[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }
    
    public static void main(String[] args) {
        int n = 50;
        System.out.println("Fibonacci(" + n + ") = " + fibonacci(n));
        System.out.println("Fibonacci(" + n + ") = " + fibonacciTabulation(n));
    }
}
```

**Time Complexity:** O(n)
**Space Complexity:** O(n)

### 2. Longest Common Subsequence (LCS)

```java
public class LongestCommonSubsequence {
    public static int lcs(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return dp[m][n];
    }
    
    public static void main(String[] args) {
        String text1 = "ABCDGH";
        String text2 = "AEDFHR";
        
        System.out.println("LCS length: " + lcs(text1, text2));
    }
}
```

**Time Complexity:** O(m * n)
**Space Complexity:** O(m * n)

## Problem-Solving Strategies

### 1. Two Pointers Technique

```java
public class TwoPointers {
    // Find pair with given sum in sorted array
    public static int[] findPair(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            int sum = arr[left] + arr[right];
            
            if (sum == target) {
                return new int[]{left, right};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return new int[]{-1, -1}; // No pair found
    }
    
    // Remove duplicates from sorted array
    public static int removeDuplicates(int[] arr) {
        if (arr.length == 0) return 0;
        
        int writeIndex = 1;
        
        for (int readIndex = 1; readIndex < arr.length; readIndex++) {
            if (arr[readIndex] != arr[readIndex - 1]) {
                arr[writeIndex] = arr[readIndex];
                writeIndex++;
            }
        }
        
        return writeIndex;
    }
}
```

### 2. Sliding Window Technique

```java
public class SlidingWindow {
    // Maximum sum of subarray of size k
    public static int maxSumSubarray(int[] arr, int k) {
        if (k > arr.length) return -1;
        
        int maxSum = 0;
        int currentSum = 0;
        
        // Calculate sum of first window
        for (int i = 0; i < k; i++) {
            currentSum += arr[i];
        }
        maxSum = currentSum;
        
        // Slide the window
        for (int i = k; i < arr.length; i++) {
            currentSum = currentSum - arr[i - k] + arr[i];
            maxSum = Math.max(maxSum, currentSum);
        }
        
        return maxSum;
    }
    
    // Longest substring without repeating characters
    public static int longestSubstringWithoutRepeating(String s) {
        int[] charCount = new int[128];
        int left = 0, maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            charCount[s.charAt(right)]++;
            
            while (charCount[s.charAt(right)] > 1) {
                charCount[s.charAt(left)]--;
                left++;
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}
```

## Summary

This guide covers the fundamental concepts of Data Structures and Algorithms with Java implementations. Key takeaways:

1. **Choose the right data structure** based on your use case
2. **Understand time and space complexity** for algorithm analysis
3. **Practice problem-solving** using different techniques
4. **Optimize your solutions** by considering trade-offs

### Common Time Complexities Summary

| Algorithm/Operation | Best Case | Average Case | Worst Case |
|-------------------|-----------|--------------|------------|
| Linear Search | O(1) | O(n) | O(n) |
| Binary Search | O(1) | O(log n) | O(log n) |
| Bubble Sort | O(n) | O(n²) | O(n²) |
| Quick Sort | O(n log n) | O(n log n) | O(n²) |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) |
| Hash Table Operations | O(1) | O(1) | O(n) |
| Binary Tree Operations | O(log n) | O(log n) | O(n) |

Remember that understanding DSA is crucial for writing efficient code and solving complex programming problems. Practice regularly and try to implement these concepts in real-world scenarios. 