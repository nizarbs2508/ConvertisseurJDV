package com.ans.jaxb;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeListWrapper implements NodeList {

	private final List<Element> nodes;

	public NodeListWrapper(List<Element> nodes) {
		this.nodes = nodes;
	}

	@Override
	public Node item(int index) {
		return (index >= 0 && index < nodes.size()) ? nodes.get(index) : null;
	}

	@Override
	public int getLength() {
		return nodes.size();
	}
}