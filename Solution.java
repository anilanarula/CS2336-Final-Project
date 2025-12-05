import java.util.Scanner;

/* ============================
   FREEZE: Interfaces & Driver
   ============================ */

// FREEZE CODE BEGINS
/**
 * The ListItem interface defines the basic information that any item
 * (such as a TaskItem) must have in this project.
 *
 * It makes sure every item stored inside lists, queues, stacks,
 * heaps, or trees has the same required fields.
 *
 * Each item must have:
 * - an ID
 * - a description
 * - a due date
 * - a priority
 * - a readable string form
 *
 * This interface is implemented by TaskItem.
 */
interface ListItem {

	/**
	 * Returns the unique ID of the item.
	 * @return item ID as a String
	 */
	String getId();

	/**
	 * Returns the description of the item.
	 * @return description text
	 */
	String getDescription();

	/**
	 * Returns the due date of the item in YYYY-MM-DD format.
	 * @return due date as a String
	 */
	String getDueDate();

	/**
	 * Returns the priority number of the item.
	 * Lower number means higher priority.
	 * @return priority value
	 */
	int getPriority();

	/**
	 * Returns a readable string version of the item.
	 * Used for printing lists or debugging.
	 * @return the item as a formatted String
	 */
	String toString();
}

/**
 * The IarrayList interface describes the basic operations for a simple
 * resizable(dynamic) list structure. 
 * It defines how items can be added, accessed,
 * updated, removed, and checked for size or emptiness.
 *
 * Any class that implements this interface must manage its own internal
 * array and grow/shrink it when needed.
 *
 * This is a simplified version of Java’s ArrayList, created for learning
 * purposes in this project.
 *
 * @param <T> the type of elements stored in the list
 */
interface IarrayList<T> {

	/**
	 * Adds an item to the end of the list.
	 * @param item the element to add
	 */
	void add(T item);

	/**
	 * Returns the item stored at the given index.
	 * @param index the position of the item to get
	 * @return the item at the given index
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	T get(int index);

	/**
	 * Replaces the item at the given index with a new item.
	 * @param index the position to update
	 * @param item the new item to place at the position
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	void set(int index, T item);

	/**
	 * Removes and returns the item at the given index.
	 * Elements after this index should shift left.
	 * @param index the position of the item to remove
	 * @return the removed item
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	T removeAt(int index);

	/**
	 * Returns the number of items stored in the list.
	 * @return the size of the list
	 */
	int size();

	/**
	 * Checks if the list has zero items.
	 * @return true if empty, false otherwise
	 */
	boolean isEmpty();
}

/**
 * The IlinkedList interface describes the basic operations for a simple
 * singly linked list. This structure uses nodes linked together instead of
 * an array, making it efficient for adding or removing items at the front.
 *
 * Any class that implements this interface must maintain a head pointer
 * (and optionally a tail pointer) to manage the linked list.
 *
 * This simplified linked list is mainly used in this project to build
 * queue and stack structures such as the Scheduler and UndoRedoManager.
 *
 * @param <T> the type of elements stored in the list
 */
interface IlinkedList<T> {

	/**
	 * Inserts an item at the beginning of the list.
	 * @param item the element to add
	 */
	void addFirst(T item);

	/**
	 * Inserts an item at the end of the list.
	 * @param item the element to add
	 */
	void addLast(T item);

	/**
	 * Removes and returns the first item in the list.
	 * @return the removed element
	 * @throws java.util.NoSuchElementException if the list is empty
	 */
	T removeFirst();

	/**
	 * Returns the total number of items in the list.
	 * @return the size of the list
	 */
	int size();

	/**
	 * Checks if the list has zero items.
	 * @return true if empty, false otherwise
	 */
	boolean isEmpty();
}


/**
 * The AbstractListManager class defines the complete set of operations
 * that any task-management system must support.  
 *
 * This abstract class acts as a **blueprint** for the final ToDoListManager.
 * Students must implement all of these operations by using:
 * - **TaskList (ArrayList)** for storing tasks,
 * - **TaskLinkedList** to build **Queue** and **Stack** structures,
 * - **Scheduler** (queue) for scheduled tasks,
 * - **UndoRedoManager** (stack) for undo/redo actions,
 * - **PriorityTaskManager** (max-heap) for high-priority tasks,
 * - **TaskTree** (BST) for DFS/BFS traversal of tasks ordered by description.
 *
 * Each method below represents a required feature for the final project.
 *
 * @param <T> any class that implements ListItem (such as TaskItem)
 */
abstract class AbstractListManager<T extends ListItem> {

	// ---------------------- Core CRUD Operations ----------------------

	/**
	 * Add a new task into the task list.
	 * @param item the task to add
	 */
	public abstract void addItem(T item);

	/**
	 * Remove a task from the list using its unique ID.
	 * @param taskID the ID of the task to remove
	 */
	public abstract void removeItem(String taskID);

	/**
	 * Update an existing task's information using a replacement object.
	 * @param idtaskID the ID of the task to update
	 * @param newItem the updated task object
	 */
	public abstract void updateItem(String idtaskID, T newItem);


	// ---------------------- Task Completion ----------------------

	/**
	 * Mark a task as DONE.
	 * @param id the ID of the task to complete
	 * @return true if successfully marked done, false otherwise
	 */
	public abstract boolean completeTask(String id);

	/**
	 * Remove all DONE tasks from the main task list.
	 */
	public abstract void removeCompletedTasks();


	// ---------------------- Searching ----------------------

	/**
	 * Search for a task using its ID.
	 * @param taskID the unique id to find
	 * @return the matching task or null if not found
	 */
	public abstract T searchById(String taskID);

	/**
	 * Search tasks that match part or all of a description.
	 * @param keyword the text to match inside the description
	 * @return a list of matching tasks
	 */
	public abstract TaskList<T> searchByDescription(String keyword);

	/**
	 * Get tasks that match a specific status (TODO, DONE, SCHEDULED),
	 * sorted by priority.
	 * @param status the status to filter by
	 * @return a sorted list of tasks
	 */
	public abstract TaskList<T> getTasksByStatusSortedByPriority(String status);

	/**
	 * Return all tasks in their stored order.
	 * @return all tasks in a TaskList
	 */
	public abstract TaskList<T> getAllTasks();


	// ---------------------- Sorting Operations ----------------------

	/**
	 * Sort tasks alphabetically based on description.
	 */
	public abstract void sortByDescription();

	/**
	 * Sort tasks by due date (soonest first).
	 */
	public abstract void sortByDueDate();

	/**
	 * Sort tasks by priority (natural order: lower → higher).
	 */
	public abstract void sortByPriority();


	// ---------------------- Undo / Redo (Stack) ----------------------

	/**
	 * Undo the last add/remove operation.
	 */
	public abstract void undo();

	/**
	 * Redo the last undone operation.
	 */
	public abstract void redo();


	// ---------------------- Scheduling (Queue) ----------------------

	/**
	 * Add an existing task (already in the system) to the schedule queue.
	 * @param taskID the id of the task to schedule
	 * @return true if added successfully
	 */
	public abstract boolean scheduleTask(String taskID);

	/**
	 * Add a brand-new task directly into the schedule queue.
	 * @param task the task object to insert
	 */
	public abstract void scheduleTask(T task);

	/**
	 * Removes the next task from the scheduling queue(FIFO), marks it as DONE, 
	 * and returns it. 
	 *
	 * @return the next scheduled task that has been processed and marked as DONE,
	 *         or null if the queue is empty
	 */
	public abstract ListItem processNextScheduledTask();


	// ---------------------- BST Traversals ----------------------

	/**
	 * Traverse the task tree using **Depth-First Search**, normally producing
	 * alphabetical order by description.
	 */
	public abstract void traverseBSTDFS();

	/**
	 * Traverse the task tree using **Breadth-First Search**, showing tasks
	 * level by level as stored inside the BST.
	 */
	public abstract void traverseBSTBFS();


	// ---------------------- Priority Queue (Heap) ----------------------

	/**
	 * Return the highest-priority TODO task stored in the heap.
	 * @return the task with the maximum priority value
	 */
	public abstract T getNextHighPriorityTask();

	/**
	 * Print the internal heap representation (array layout).
	 */
	public abstract void displayHeap();

}

/**
 * The Solution class runs the To-Do List program.
 *
 * <p>This program reads commands from standard input. Each line must follow
 * a specific format. The program then calls the correct method in the
 * ToDoListManager and prints the result.</p>
 *
 * <p>The program ends when the command "EXIT" is read.</p>
 */
public class Solution {

	/**
	 * The main method that reads commands and runs the To-Do List system.
	 *
	 * @param args not used
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ToDoListManager<TaskItem> manager = new ToDoListManager<>();

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line == null) break;
			line = line.trim();
			if (line.isEmpty()) continue;

			String[] p = line.split(";", -1);
			String cmd = p[0];

			try {
				switch (cmd) {

				/** ADD a new task */
				case "ADD":
					manager.addItem(new TaskItem(p[1], p[2], p[3], Integer.parseInt(p[4])));
					System.out.println("TASK ADDED");
					break;

					/** UPDATE an existing task */
				case "UPDATE":
					manager.updateItem(p[1], new TaskItem(p[1], p[2], p[3], Integer.parseInt(p[4])));
					System.out.println("TASK UPDATED");
					break;

					/** REMOVE a task */
				case "REMOVE":
					manager.removeItem(p[1]);
					System.out.println("TASK REMOVED");
					break;

					/** DISPLAY all tasks */
				case "DISPLAY":
					TaskList<? extends ListItem> all = manager.getAllTasks();
					if (all.size() == 0) System.out.println("NOT FOUND");
					else for (int i = 0; i < all.size(); i++) System.out.println(all.get(i).toString());
					break;

					/** SEARCH for a task by ID */
				case "SEARCH_ID":
					ListItem found = manager.searchById(p[1]);
					System.out.println(found == null ? "NOT FOUND" : found.toString());
					break;

					/** SEARCH for tasks by description */
				case "SEARCH_DESC":
					TaskList<? extends ListItem> res = manager.searchByDescription(p[1]);
					if (res.size() == 0) System.out.println("NOT FOUND");
					else for (int i = 0; i < res.size(); i++) System.out.println(res.get(i).toString());
					break;

					/** SORT tasks by description */
				case "SORT_DESC":
					manager.sortByDescription();
					System.out.println("SORTED");
					break;

					/** SORT tasks by due date */
				case "SORT_DUE":
					manager.sortByDueDate();
					System.out.println("SORTED");
					break;

					/** SORT tasks by priority */
				case "SORT_PRIO":
					manager.sortByPriority();
					System.out.println("SORTED");
					break;

					/** UNDO the last action */
				case "UNDO":
					manager.undo();
					System.out.println("UNDO");
					break;

					/** REDO the undone action */
				case "REDO":
					manager.redo();
					System.out.println("REDO");
					break;

					/** Mark a task as COMPLETE */
				case "COMPLETE":
					boolean done = manager.completeTask(p[1]);
					System.out.println(done ? "TASK COMPLETED" : "NOT FOUND");
					break;

					/** Remove all completed tasks */
				case "REMOVE_COMPLETED":
					manager.removeCompletedTasks();
					System.out.println("COMPLETED REMOVED");
					break;

					/** Get tasks by status and sort by priority */
				case "GET_STATUS":
					TaskList<TaskItem> filtered = manager.getTasksByStatusSortedByPriority(p[1]);
					if (filtered.size() == 0) System.out.println("NOT FOUND");
					else for (int i = 0; i < filtered.size(); i++) System.out.println(filtered.get(i).toString());
					break;

					/** Traverse the BST with DFS (in-order) */
				case "BST_DFS":
					manager.traverseBSTDFS();
					break;

					/** Traverse the BST with BFS */
				case "BST_BFS":
					manager.traverseBSTBFS();
					break;

					/** Schedule an existing task by ID */
				case "SCHEDULE_ID":
					System.out.println(manager.scheduleTask(p[1]) ? "TASK SCHEDULED" : "NOT FOUND");
					break;

					/** Schedule a new task */
				case "SCHEDULE":
					manager.scheduleTask(new TaskItem(p[1], p[2], p[3], Integer.parseInt(p[4])));
					System.out.println("NEW TASK SCHEDULED");
					break;


					/** Process the next SCHEDULED task in the scheduling queue: removes it from the queue, marks it as DONE **/
				case "PROCESS_NEXT":
					TaskItem next = manager.processNextScheduledTask();
					System.out.println(next == null ? "NOT FOUND" : next.toString());
					break;

					/** Get next high priority task with TODO status*/
				case "GET_NEXT_PRIORITY":
					TaskItem top = manager.getNextHighPriorityTask();
					System.out.println(top == null ? "NOT FOUND" : top.toString());
					break;

					/** DISPLAY the heap (only TODO tasks) */
				case "DISPLAY_HEAP":
					manager.displayHeap();
					break;

					/** EXIT the program */
				case "EXIT":
					System.out.println("GOODBYE");
					sc.close();
					return;

					/** Unknown command */
				default:
					System.out.println("UNKNOWN COMMAND");
				}

			} catch (Exception ex) {
				System.out.println("ERROR: " + ex.getMessage());
			}
		}

		sc.close();
	}
}


// FREEZE CODE ENDS


//------------------------------
//TaskItem
//------------------------------

/**
 * TaskItem represents one task in the to-do list.
 *
 * <p>Students: complete and update this class if needed.</p>
 * <ul>
 *   <li>Each task has an id, description, due date, priority, and status.</li>
 *   <li>Status can be "TODO", "SCHEDULED", or "DONE".</li>
 *   <li>You may add extra helper methods if your design needs them.</li>
 *   <li>This class also implements Comparable — think about how tasks should be compared.</li>
 * </ul>
 */
class TaskItem implements ListItem, Comparable<TaskItem> {
	private String id;
	private String description;
	private String dueDate;
	private int priority;
	private String status; // "TODO", "SCHEDULED" and "DONE"

  // Constructor used by Solution
	public TaskItem(String id, String description, String dueDate, int priority) {
		this.id = id;
		this.description = description;
		this.dueDate = dueDate;
		this.priority = priority;
		this.status = "TODO";
	}

	// copy constructor
	public TaskItem(TaskItem other) {
		this.id = other.id;
		this.description = other.description;
		this.dueDate = other.dueDate;
		this.priority = other.priority;
		this.status = other.status;
	}

	//Getters
  @Override
	public String getId() { 
    return id; 
  }

	@Override
	public String getDescription() { 
    return description; 
  }

	@Override
	public String getDueDate() { 
    return dueDate; 
  }

	@Override
	public int getPriority() { 
    return priority; 
  }

	public String getStatus() { 
    return status; 
  }

	// setters
	public void setDescription(String description) { 
    this.description = description; 
  }

	public void setDueDate(String dueDate) { 
    this.dueDate = dueDate; 
  }

	public void setPriority(int priority) { 
    this.priority = priority; 
  }

	public void setStatus(String status) { 
    if (status == null) 
      return;
    this.status = status; 
  }

	//toString for output
  // Format: id;description;dueDate;priority;status
  @Override
  public String toString() {
    return "ID: "+ id +", Desc: " +description+", Due: " + dueDate+", Priority: " +priority+", Status: "+status;
  }

	//Tasks are compared by description (case-insensitive), then by due date, then by priority, then by ID
  @Override
public int compareTo(TaskItem other) {
    return Integer.compare(this.priority, other.priority);
}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TaskItem)) return false;
		TaskItem t = (TaskItem) o;
		return this.id.equals(t.id);
	}
}

//------------------------------
//TaskList
//------------------------------

/**
 * TaskList is a simple dynamic array (like a small ArrayList).
 * <p>
 * Students: You must complete this class as needed for the project.
 * It stores items and grows when more space is needed.
 * </p>
 *
 * <p>Hints:</p>
 * <ul>
 *   <li>Use an internal array to store items.</li>
 *   <li>Double the array size when it becomes full.</li>
 *   <li>Follow the IarrayList interface rules carefully.</li>
 *   <li>Make sure removeAt() shifts items correctly.</li>
 *   <li>Make sure to use generics safely when returning T.</li>
 * </ul>
 *
 * @param <T> The type of item stored inside this list.
 */
class TaskList<T> implements IarrayList<T>{
  private T[] data;
  private int size;
  private static final int INITIAL_CAP = 8;
  
  @SuppressWarnings("unchecked")
  public TaskList() {
    data = (T[]) new Object[INITIAL_CAP];  
    size = 0;
  }
  
  @Override
  public void add(T item) {
    if (size >= data.length) {
      resize();
    }
    data[size++] = item;
  }
  
  @Override
  public T get(int index) {
    if (index < 0 || index >= size) 
      throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
    return data[index];  // data is T[] so no cast needed
  }
  
  @Override
  public void set(int index, T item) {
    if (index < 0 || index >= size) 
      throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
    data[index] = item;
  }
  
  @Override
  public T removeAt(int index) {
    if (index < 0 || index >= size) 
      throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
    T removed = data[index];  // No cast needed
    
    // shift elements left to fill the gaps
    for (int i = index; i < size - 1; i++){ 
      data[i] = data[i + 1];
    }
    data[size - 1] = null; // prevent memory leak
    size--;
    return removed;
  }
  
  @Override
  public int size() { 
    return size; 
  }
  
  @Override
  public boolean isEmpty() { 
    return size == 0; 
  }
  
  @SuppressWarnings("unchecked")
  private void resize() {
    T[] newData = (T[]) new Object[data.length * 2]; 
    System.arraycopy(data, 0, newData, 0, size);
    data = newData;
  }
}

//------------------------------
//TaskLinkedList
//------------------------------

/**
 * TaskLinkedList is a simple singly linked list implementation.
 * <p>
 * Students: You must complete this class as needed. 
 * It implements IlinkedList interface, which provides basic operations
 * like adding/removing elements at the start or end, and checking size.
 * </p>
 *
 * <p>Hints:</p>
 * <ul>
 *   <li>Use an internal Node class with a data field and a next reference.</li>
 *   <li>Keep track of head and tail references for efficiency.</li>
 *   <li>Keep a size counter to quickly return list size.</li>
 *   <li>addFirst() inserts at the head.</li>
 *   <li>addLast() inserts at the tail.</li>
 *   <li>removeFirst() removes the head node and returns its data.</li>
 *   <li>isEmpty() should return true if the head is null.</li>
 * </ul>
 *
 * @param <T> The type of item stored inside this linked list.
 */
class TaskLinkedList<T> implements IlinkedList<T>{
  private class Node {
		T data;
		Node next;
		Node(T d) { 
      this.data = d; this.next = null; 
    }
	}

	private Node head;
	private Node tail;
	private int size;

	public TaskLinkedList() { 
    head = tail = null; 
    size = 0; 
  }

	@Override
	public void addFirst(T item) {
		Node n = new Node(item);
		if (isEmpty()) { 
      head = tail = n; 
    }
		else { 
      n.next = head; 
      head = n; 
    }
		size++;
	}

	@Override
	public void addLast(T item) {
		Node n = new Node(item);
		if (isEmpty()) { 
      head = tail = n; 
    }
		else { 
      tail.next = n; 
      tail = n; 
    }
		size++;
	}

	@Override
	public T removeFirst() {
		if (isEmpty()) 
      return null;
		T val = head.data;
		head = head.next;
		if (head == null){ 
      tail = null;
    }
		size--;
		return val;
	}

	@Override
	public int size() { 
    return size; 
  }

	@Override
	public boolean isEmpty() { 
    return size == 0; 
  }
 
	public void clear() {
		head = tail = null;
		size = 0;
	}

}


//------------------------------
//Scheduler
//------------------------------

/**
 * Scheduler implements a simple FIFO queue using TaskLinkedList.
 * <p>
 * This class is used to manage scheduled tasks in the ToDoListManager.
 * Students: You should complete this class using the TaskLinkedList you implemented.
 * </p>
 *
 * <p>Hints:</p>
 * <ul>
 *   <li>Use a TaskLinkedList internally to store items in FIFO order.</li>
 *   <li>enqueue() adds an item to the end of the queue.</li>
 *   <li>dequeue() removes and returns the first item in the queue.</li>
 *   <li>isEmpty() returns true if the queue has no items.</li>
 * </ul>
 *
 * @param <T> The type of items stored in the queue.
 */
class Scheduler<T>{
	private TaskLinkedList<T> queue;

  public Scheduler() {
		queue = new TaskLinkedList<>();
	}

	// enqueue: add to tail
	public void enqueue(T item) {
		queue.addLast(item);
	}

	// dequeue: remove from head
	public T dequeue() {
		if (queue.isEmpty()) 
      return null;
		return queue.removeFirst();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}
}

//------------------------------
//UndoRedoManager
//------------------------------

/**
 * UndoRedoManager implements a simple stack using TaskLinkedList.
 * <p>
 * This class is used to manage undo and redo operations for task modifications
 * in the ToDoListManager. Each push stores a snapshot of a task list or state,
 * and pop restores the most recent state.
 * </p>
 *
 * <p>Hints for students:</p>
 * <ul>
 *   <li>Use TaskLinkedList internally to implement stack behavior (LIFO).</li>
 *   <li>push() adds a new element to the top of the stack.</li>
 *   <li>pop() removes and returns the top element of the stack.</li>
 *   <li>isEmpty() returns true if the stack has no elements.</li>
 * </ul>
 *
 * @param <T> The type of objects stored in the undo/redo stack.
 */
class UndoRedoManager<T>{
	private TaskLinkedList<T> stack;

  public UndoRedoManager() {
		stack = new TaskLinkedList<>();
	}

	// push to top
	public void push(T item) {
		stack.addFirst(item);
	}

	// pop from top
	public T pop() {
		if (stack.isEmpty()) 
      return null;
		return stack.removeFirst();
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}

	public void clear() {
		stack.clear();
	}
}

//------------------------------
//PriorityTaskManager
//------------------------------

/**
 * PriorityTaskManager implements a max-heap using TaskList to manage tasks
 * based on their priority. Only tasks with status "TODO" are stored in the heap.
 * <p>
 * This class allows students to insert tasks, extract the highest-priority task,
 * display all tasks in the heap, and build the heap from an existing list of tasks.
 * </p>
 *
 * <p>Hints for students:</p>
 * <ul>
 *   <li>Use TaskList internally to implement the heap.</li>
 *   <li>buildHeap() initializes the heap from a list of tasks (filter only TODO tasks).</li>
 *   <li>insert() inserts a new task into the heap and maintains max-heap property.</li>
 *   <li>extractMax() removes and returns the task with the highest priority.</li>
 *   <li>displayHeap() prints the tasks in heap order.</li>
 *   <li>higherPriority() compares two tasks and returns true if the first has higher priority.</li>
 * </ul>
 *
 * @param <T> The type of tasks stored in the heap, must extend TaskItem
 */
class PriorityTaskManager<T extends TaskItem> {
  private TaskList<T> heap;
  
  public PriorityTaskManager() {
    heap = new TaskList<>();
  }
  
  public void clear() {
    heap = new TaskList<>();
  }
  
  // Only accept TODO tasks
  public void insert(T item) {
    if (item == null) return;
    if (!"TODO".equals(item.getStatus())) return;
    
    heap.add(item);
    bubbleUp(heap.size() - 1);
  }
  
  // Extract max and skip any non-TODO tasks
  public T extractMax() {
    if(heap.isEmpty()) return null;
    T max = heap.get(0);
    T last = heap.removeAt(heap.size() -1);

    if(!heap.isEmpty()) {
        heap.set(0, last);
        sinkDown(0);
    }
    return max;
  }
  
  public void displayHeap() {
    if(heap.isEmpty()) return;
    for(int i = 0; i < heap.size(); i++) {
        System.out.println(heap.get(i));
    }
  }
  
  // Swim up to restore heap property
  private void bubbleUp(int index) {
    while (index > 0) {
        int parentIdx = (index - 1) / 2;
        T current = heap.get(index);
        T parent = heap.get(parentIdx);
        
        // Compare by PRIORITY VALUE, not compareTo
        if (current.getPriority() > parent.getPriority()) {
            swap(index, parentIdx);
            index = parentIdx;
        } else {
            break;
        }
    }
}
  
  // Sink down to restore heap property
  private void sinkDown(int index) {
    int size = heap.size();
    while (true) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int largest = index;
        
        // Compare by PRIORITY VALUE, not compareTo
        if (left < size && heap.get(left).getPriority() > heap.get(largest).getPriority()) {
            largest = left;
        }
        if (right < size && heap.get(right).getPriority() > heap.get(largest).getPriority()) {
            largest = right;
        }
        
        if (largest != index) {
            swap(index, largest);
            index = largest;
        } else {
            break;
        }
    }
  }
  
  private void swap(int i, int j) {
    T temp = heap.get(i);
    heap.set(i, heap.get(j));
    heap.set(j, temp);
  }
}

//------------------------------
//TaskTree (BST with DFS and BFS traversal)
//------------------------------

/**
 * TaskTree implements a Binary Search Tree (BST) for storing tasks based on their
 * description (alphabetical order). It allows DFS (in-order) and BFS (level-order)
 * traversals to visualize tasks.
 *
 * <p>Students should use TaskLinkedList as a queue for BFS traversal.</p>
 *
 * <p>Hints for students:</p>
 * <ul>
 *   <li>Each TreeNode stores a TaskItem.</li>
 *   <li>buildTree() constructs the BST from a given TaskList.</li>
 *   <li>insert() adds a new task to the tree, maintaining BST properties.</li>
 *   <li>dfsPrint() performs an in-order traversal to print tasks alphabetically.</li>
 *   <li>bfsPrint() performs a level-order traversal to print tasks by tree levels.</li>
 * </ul>
 *
 * @param <T> The type of tasks stored in the tree, must extend TaskItem
 */
class TaskTree<T extends TaskItem> {
    // Node class
    class TreeNode {
        T val;
        TreeNode left, right;
        TreeNode(T v) { 
            val = v; 
            left = right = null; 
        }
    }
    private TreeNode root;

    public TaskTree() {
        root = null;
    }

    // insert by description (alphabetical)
    public void insert(T task) {
        if (task == null) 
          return;
        root = insertRec(root, task);
    }

    private TreeNode insertRec(TreeNode node, T task) {
        if (node == null) return new TreeNode(task);
        String a = task.getDescription();
        String b = node.val.getDescription();
        // equal goes RIGHT now
        if (a.compareToIgnoreCase(b) < 0) {
            node.left = insertRec(node.left, task);
        } 
        else {
            node.right = insertRec(node.right, task);
        }
        return node;
    }

    // build tree from TaskList
    public void buildTree(TaskList<T> tasks) {
        clear();
        for (int i = 0; i < tasks.size(); i++) 
          insert(tasks.get(i));
    }

    // in-order DFS -> alphabetical by description
    public void dfsPrint() {
      if (root == null) return;
		  dfsRec(root);
	  }

    private void dfsRec(TreeNode node) {
        if (node == null) 
          return;
        dfsRec(node.left);
        // print description only
        System.out.println(node.val.getDescription());
        dfsRec(node.right);
    }

    // BFS level-order using TaskLinkedList as queue
    public void bfsPrint() {
        if (root == null) 
          return;
        TaskLinkedList<TreeNode> q = new TaskLinkedList<>();
        q.addLast(root);
        while (!q.isEmpty()) {
            TreeNode node = q.removeFirst();
            // print description only
            System.out.println(node.val.getDescription());
            if (node.left != null) 
              q.addLast(node.left);
            if (node.right != null) 
              q.addLast(node.right);
        }
    }

    public void clear() {
        root = null;
    }
}

//------------------------------
//ToDoListManager (implements all behaviors)
//------------------------------

/**
 * ToDoListManager is the main class managing tasks and implements all required operations:
 * adding, updating, removing tasks; undo/redo history; scheduling; priority access;
 * searching; sorting; marking tasks as done; and tree-based traversal.
 *
 * <p>It uses:</p>
 * <ul>
 *   <li>TaskList (dynamic array) to store active tasks</li>
 *   <li>UndoRedoManager (stack behavior) for undo/redo history</li>
 *   <li>Scheduler (queue) for FIFO scheduled tasks</li>
 *   <li>PriorityTaskManager (max-heap) to get high-priority tasks</li>
 *   <li>TaskTree (BST) for searching and sorted views</li>
 * </ul>
 *
 * <p>Students should implement missing functionality or modify behavior as needed, 
 * especially in scheduling, priority management, undo/redo, and task completion.</p>
 *
 * <p>Hints:</p>
 * <ul>
 *   <li>Use snapshot() to store a copy of the task list before modifications for undo/redo.</li>
 *   <li>Remember to rebuild the heap whenever tasks are added, removed, or updated.</li>
 *   <li>Use taskTree.buildTree(taskList) before traversals (DFS or BFS).</li>
 *   <li>Filtering and sorting can be done via simple loops or bubble sort for now.</li>
 *   <li>Mark the first SCHEDULED tasks in the queue as DONE before enqueueing or processing.</li>
 * </ul>
 *
 * @param <T> The type of tasks, must extend TaskItem
 */
class ToDoListManager<T extends TaskItem> extends AbstractListManager<T> {

	/** Active tasks list */
	private TaskList<T> taskList;                  
	/** Undo history stack */
	private UndoRedoManager<TaskList<T>> undoHistory;
	/** Redo history stack */
	private UndoRedoManager<TaskList<T>> redoHistory;
	/** FIFO queue for scheduled tasks */
	private Scheduler<T> scheduledTasks;           
	/** Max-heap for high-priority tasks */
	private PriorityTaskManager<T> highPriorityTasks;
	/** BST for searching and sorted display */
	private TaskTree<T> taskTree;

  public ToDoListManager() {
		taskList = new TaskList<>();
		undoHistory = new UndoRedoManager<>();
		redoHistory = new UndoRedoManager<>();
		scheduledTasks = new Scheduler<>();
		highPriorityTasks = new PriorityTaskManager<>();
		taskTree = new TaskTree<>();
	}

  // Helper: create deep copy snapshot for undo/redo
  @SuppressWarnings("unchecked")
	private TaskList<T> createSnapshot() {
		TaskList<T> snapshot = new TaskList<>();
		for(int i = 0; i < taskList.size(); i++) {
			TaskItem original = taskList.get(i);
			snapshot.add((T) new TaskItem(original));
		}
		return snapshot;
	}

  // Helper: save state before modification
	private void saveState() {
		undoHistory.push(createSnapshot());
		redoHistory.clear();
	}

  // Helper: swap elements (used in sorting)
  private void swap(int i, int j) {
		T temp = taskList.get(i);
		taskList.set(i, taskList.get(j));
		taskList.set(j, temp);
	}

	// CRUD Operations

  @Override
  public void addItem(T item) {
    if (item == null) return;
    saveState();
    taskList.add(item);
  }

  @Override
  public void removeItem(String taskID) {
    if (taskID == null) return;
    saveState();
    for (int i = 0; i < taskList.size(); i++) {
      if (taskList.get(i).getId().equals(taskID)) {
        taskList.removeAt(i);
        return;
      }
    }
  }

  @Override
  public void updateItem(String idtaskID, T newItem) {
    if (idtaskID == null || newItem == null) return;
    saveState();
    for (int i = 0; i < taskList.size(); i++) {
      if (taskList.get(i).getId().equals(idtaskID)) {
        taskList.set(i, newItem);
        return;
      }
    }
  }

  // Task Completion

  @Override
  public boolean completeTask(String id) {
    if (id == null) return false;
    for (int i = 0; i < taskList.size(); i++) {
      T task = taskList.get(i);
      if (task.getId().equals(id)) {
        saveState();
        task.setStatus("DONE");
        return true;
      }
    }
    return false;
  } 

  @Override
  public void removeCompletedTasks() {
    saveState();
    // Loop backwards to avoid index shifting issues
    for (int i = taskList.size() - 1; i >= 0; i--) {
      if ("DONE".equalsIgnoreCase(taskList.get(i).getStatus())) {
        taskList.removeAt(i);
      }
    }
  }

  // Searching 

  @Override
  public T searchById(String taskID) {
    if (taskID == null) return null;
    for (int i = 0; i < taskList.size(); i++) {
      if (taskList.get(i).getId().equals(taskID)) {
        return taskList.get(i);
      }
    }
    return null;
  }

  @Override
  public TaskList<T> searchByDescription(String keyword) {
    TaskList<T> results = new TaskList<>();
    if (keyword == null) return results;
    for (int i = 0; i < taskList.size(); i++) {
      if (taskList.get(i).getDescription().contains(keyword)) {
        results.add(taskList.get(i));
      }
    }
    return results;
  }

  @Override
  public TaskList<T> getTasksByStatusSortedByPriority(String status) {
    TaskList<T> results = new TaskList<>();
    if (status == null) return results;
    
    // Filter by status
    for (int i = 0; i < taskList.size(); i++) {
      if (taskList.get(i).getStatus().equalsIgnoreCase(status)) {
        results.add(taskList.get(i));
      }
    }
    
    // Bubble sort by priority (ascending)
    for (int i = 0; i < results.size() - 1; i++) {
      for (int j = 0; j < results.size() - i - 1; j++) {
        if (results.get(j).getPriority() > results.get(j + 1).getPriority()) {
          T temp = results.get(j);
          results.set(j, results.get(j + 1));
          results.set(j + 1, temp);
        }
      }
    }
    
    return results;
  }

  @Override
  public TaskList<T> getAllTasks() {
    return taskList; 
  }

  // Sorting Operations

  @Override
  public void sortByDescription() {
    // Bubble sort by description (case-sensitive)
    for (int i = 0; i < taskList.size() - 1; i++) {
      for (int j = 0; j < taskList.size() - i - 1; j++) {
        if (taskList.get(j).getDescription().compareTo(taskList.get(j + 1).getDescription()) > 0) {
          swap(j, j + 1);
        }
      }
    }
  }

  @Override
  public void sortByDueDate() {
    // Bubble sort by due date
    for (int i = 0; i < taskList.size() - 1; i++) {
      for (int j = 0; j < taskList.size() - i - 1; j++) {
        if (taskList.get(j).getDueDate().compareTo(taskList.get(j + 1).getDueDate()) > 0) {
          swap(j, j + 1);
        }
      }
    }
  }

  @Override
  public void sortByPriority() {
    // Bubble sort by priority (ascending)
    for (int i = 0; i < taskList.size() - 1; i++) {
      for (int j = 0; j < taskList.size() - i - 1; j++) {
        if (taskList.get(j).getPriority() > taskList.get(j + 1).getPriority()) {
          swap(j, j + 1);
        }
      }
    }
  }

  // Undo / Redo (Stack)
  @Override
  public void undo() {
    if(undoHistory.isEmpty()) return;
    redoHistory.push(taskList);
    taskList = undoHistory.pop();
  }

  @Override
  public void redo() {
    if(redoHistory.isEmpty()) return;
    undoHistory.push(taskList);
    taskList = redoHistory.pop();
  }

  // Scheduling (Queue) 

  @Override
  public boolean scheduleTask(String taskID) {
    if (taskID == null) return false;
    T item = searchById(taskID);
    if (item != null) {
      item.setStatus("SCHEDULED");
      scheduledTasks.enqueue(item);
      return true;
    }
    return false;
  }

  @Override
  public void scheduleTask(T task) {
    if (task == null) return;
    saveState();
    task.setStatus("SCHEDULED");
    taskList.add(task);
    scheduledTasks.enqueue(task);
  }

  @Override
  public T processNextScheduledTask() {  // Return T, not ListItem
    if(scheduledTasks.isEmpty()) 
      return null;
    T task = scheduledTasks.dequeue();
    task.setStatus("DONE");
    return task;
  }

  // BST Traversals

  @Override
  public void traverseBSTDFS() {
    taskTree.buildTree(taskList);
    taskTree.dfsPrint();
  }

  @Override
  public void traverseBSTBFS() {
    taskTree.buildTree(taskList);
    taskTree.bfsPrint();
  }

  // Priority Queue (Heap)

  @Override
  public T getNextHighPriorityTask() {
    highPriorityTasks.clear();
    // Only TODO tasks go into the heap
    for (int i = 0; i < taskList.size(); i++) {
      if ("TODO".equalsIgnoreCase(taskList.get(i).getStatus())) {
        highPriorityTasks.insert(taskList.get(i));
      }
    }
    return highPriorityTasks.extractMax();
  }

  @Override
  public void displayHeap() {
    highPriorityTasks.clear();
    for (int i = 0; i < taskList.size(); i++) {
        if ("TODO".equalsIgnoreCase(taskList.get(i).getStatus())) {
            highPriorityTasks.insert(taskList.get(i));
        }
    }
    highPriorityTasks.displayHeap();
}
}