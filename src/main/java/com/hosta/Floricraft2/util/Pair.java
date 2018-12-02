package com.hosta.Floricraft2.util;

public class Pair<T, U> {

	private T left;
	private U right;
	
	public Pair(T left, U right)
	{
		this.setLeft(left);
		this.setRight(right);
	}
	
	public void setLeft(T left)
	{
		this.left = left;
	}
	
	public T getLeft()
	{
		return this.left;
	}

	public void setRight(U right)
	{
		this.right = right;
	}
	
	public U getRight()
	{
		return this.right;
	}
}
