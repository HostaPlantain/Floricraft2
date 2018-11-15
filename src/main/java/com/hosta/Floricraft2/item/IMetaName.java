package com.hosta.Floricraft2.item;

public interface IMetaName {
	
	String[] getSubNames();
	default String getSubName(int i) {return this.getSubNames()[i];}
	default int countSubItems()  {return this.getSubNames().length;}
}
