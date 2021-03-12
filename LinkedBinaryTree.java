package WarrenBinaryTree;

public class LinkedBinaryTree <E> extends AbstractBinaryTree<E>{

	protected static class Node<E> implements Position<E>
	{
		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;

		public Node(E e, Node<E> parent, Node<E> left, Node<E> right)
		{
			this.element = e;
			this.parent = parent;
			this.left = left;
			this.right = right;
		}

		//accessors
		public E getElement()
		{
			return this.element;
		}
		public Node<E> getParent()
		{
			return this.parent;
		}
		public Node<E> getLeft()
		{
			return this.left;
		}
		public Node<E> getRight()
		{
			return this.right;
		}

		// setters
		public void setElement(E e)
		{
			this.element = e;
		}
		public void setParent(Node<E> p)
		{
			this.parent = p;
		}
		public void setLeft(Node<E> l)
		{
			this.left = l;
		}
		public void setRight(Node<E> r)
		{
			this.right = r;
		}
	}

	protected Node<E> createNode(E e, Node<E> p, Node<E> l,Node<E> r)
	{
		return new Node<E>(e,p,l,r);
	}

	protected Node<E> root = null;
	private int size = 0;

	public LinkedBinaryTree(){}

	protected Node<E> validate(Position<E> p) throws IllegalArgumentException
	{
		if ( !(p instanceof Node))
			throw new IllegalArgumentException();
		Node<E> node = (Node<E>) p;
		if (node.getParent() == node)
			throw new IllegalArgumentException();
		return node;
	}

	public int size()
	{
		return size;
	}

	public Position<E> root()
	{
		return this.root;
	}

	public Position<E> parent(Position<E> p) throws IllegalArgumentException
	{
		Node<E> node = validate(p);
		return node.getParent();
	}

	public Position<E> left(Position<E> p) throws IllegalArgumentException
	{
		Node<E> node = validate(p);
		return node.getLeft();
	}

	public Position<E> right(Position<E> p) throws IllegalArgumentException
	{
		Node<E> node = validate(p);
		return node.getRight();
	}

	public Position<E> addRoot(E e) throws IllegalStateException
	{
		if (!isEmpty())
			throw new IllegalStateException("Tree is not empty");

		root = createNode(e,null,null,null);
		size = 1;
		return root;
	}

	public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException
	{
		Node<E> parent = validate(p);
		if (parent.getLeft() !=null)
			throw new IllegalArgumentException();

		Node<E> child = createNode(e,parent,null,null);
		parent.setLeft(child);
		size++;
		return child;
	}

	public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException
	{
		Node<E> parent = validate(p);
		if (parent.getRight() != null)
			throw new IllegalArgumentException();

		Node<E> child = createNode(e,parent,null,null);
		parent.setRight(child);
		size++;
		return child;
	}

	public E set(Position<E> p , E e) throws IllegalArgumentException
	{
		Node<E> node = validate(p);
		E temp = node.getElement();
		node.setElement(e);
		return temp;
	}

	public void attach(Position<E> p, LinkedBinaryTree<E> t1, 
			LinkedBinaryTree<E> t2) throws IllegalArgumentException
	{
		Node<E> node = validate(p);
		if (isInternal(p))
			throw new IllegalArgumentException("p must be a leaf");

		size += t1.size() + t2.size();

		if (!t1.isEmpty())
		{
			t1.root.setParent(node);
			node.setLeft(t1.root);
			t1.root = null;
			t1.size = 0;
		}

		if (!t2.isEmpty())
		{
			t2.root.setParent(node);
			node.setRight(t2.root);
			t2.root = null;
			t2.size = 0;
		}
	}

	public E remove(Position<E> p) throws IllegalArgumentException
	{
		Node<E> node = validate(p);
		if (numChildren(p) == 2)
			throw new IllegalArgumentException("p has two children");

		Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());

		if (child != null)
			child.setParent(node.getParent());
		if (node == root)
			root = child;
		else
		{
			Node<E> parent = node.getParent();
			if (node == parent.getLeft())
				parent.setLeft(child);
			else
				parent.setRight(child);
		}
		size--;
		E temp = node.getElement();
		node.setElement(null);
		node.setLeft(null);
		node.setRight(null);
		node.setParent(node);

		return temp;
	}

	// Add method
	public void add(E e){
		
		//Add to Root if the tree is empty
		if(isEmpty()){
			addRoot(e);
		} 
		else{ //Uses recursive add method to traverse through the tree, when it is not empty
			addAfter(root, e);
		}
	}

	// Recursive add method
	private void addAfter(Node<E> recent, E e){
		// Changes its elements to the added element, if the current node is empty,
		if(recent.element == null){
			recent.setElement(e);
		}

		// If the added element is less than the current node's element
		if((int) e < (int) recent.element){
			// If the current node has no left child, set it to the added element
			if(recent.left == null){
				Node<E> newNode = new Node(e, recent, null, null);
				recent.setLeft(newNode);
			}
			else{ // If the current node has a left child, recall the addAfter method to check if added element is greater or less than the child
				addAfter(recent.left, e);
			}
		} // If the added element is greater than the current node's element
		else if((int) e > (int) recent.element){
			// If the current node has no right child, set it to the added element
			if(recent.right == null){
				Node<E> newNode = new Node(e, recent, null, null);
				recent.setRight(newNode);
			}
			else{ // If the current node has a right child, recall the addAfter method to check if added element is greater or less than the child
				addAfter(recent.right, e);
			}
		}
	}

	public void print() {
		for (Position p: this.positions()) {
			System.out.println(p.getElement());
		}
	}

	public boolean find (int e) {
		boolean flag=false;
		for (Position p: this.positions()) { //checks all positions of the tree

			if ((int)e == (int)p.getElement()) { //checks if the entered integer is inside the binary tree with each traversal during the for loop 
				flag = true; //changes the place holder variable if the entered element is found
				break;	//stops the loop as the element is found
			}

		}
		return flag;
	}
}
