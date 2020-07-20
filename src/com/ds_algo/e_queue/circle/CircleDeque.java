package com.ds_algo.e_queue.circle;
@SuppressWarnings("unchecked")
public class CircleDeque<T> {
	private int front;
	private int size;
	private T[] elements;
	private static final int DEFAULT_CAPACITY = 10;
	
	public CircleDeque() {
		elements = (T[]) new Object[DEFAULT_CAPACITY];
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[index(i)] = null;
		}
		front = 0;
		size = 0;
	}

	/**
	 * 从尾部入队
	 * @param element
	 */
	public void enQueueRear(T element) {
		ensureCapacity(size + 1);
		elements[index(size)] = element;
		size++;
	}

	/**
	 * 从头部出队
	 */
	public T deQueueFront() {
		T frontElement = elements[front];
		elements[front] = null;
		front = index(1);
		size--;
		return frontElement;
	}

	/**
	 * 从头部入队
	 */
	public void enQueueFront(T element) {
		ensureCapacity(size + 1);
		front = index(-1);
		elements[front] = element;
		size++;
	}

	/**
	 * 从尾部出队
	 */
	public T deQueueRear() {
		int rearIndex = index(size - 1);
		T rear = elements[rearIndex];
		elements[rearIndex] = null;
		size--;
		return rear;
	}

	public T front() {
		return elements[front];
	}

	public T rear() {
		return elements[index(size - 1)];
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("CircleDeque{capacity=").append(elements.length)
		.append(" size=").append(size)
		.append(" front=").append(front)
		.append(", [");
		for (int i = 0; i < elements.length; i++) {
			if (i != 0) {
				string.append(", ");
			}
			
			string.append(elements[i]);
		}
		string.append("]}");
		return string.toString();
	}
	
	private int index(int index) {
		index += front;
		if (index < 0) {
			return index + elements.length;
		}
		return index - (index >= elements.length ? elements.length : 0);
	}
	
	/**
	 * 保证要有capacity的容量
	 * @param capacity
	 */
	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity) return;
		
		// 新容量为旧容量的1.5倍
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		T[] newElements = (T[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[index(i)];
		}
		elements = newElements;
		
		// 重置front
		front = 0;
	}
}
