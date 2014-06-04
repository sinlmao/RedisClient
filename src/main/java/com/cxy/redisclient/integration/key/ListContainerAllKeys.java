package com.cxy.redisclient.integration.key;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.cxy.redisclient.domain.Node;
import com.cxy.redisclient.domain.NodeType;
import com.cxy.redisclient.integration.JedisCommand;

public abstract class ListContainerAllKeys extends JedisCommand {
	protected int db;
	protected String container;
	protected Set<Node> keys = new TreeSet<Node>();
	
	public Set<Node> getKeys() {
		return keys;
	}
	
	public ListContainerAllKeys(int id, int db, String container) {
		super(id);
		this.db = db;
		this.container = container;
	}

	
	@Override
	public void command() {
		jedis.select(db);
		Set<String> nodekeys = getResult();
		
		Iterator<String> it = nodekeys.iterator();
		while (it.hasNext()) {
			String nextKey = it.next();
			NodeType nodeType = getNodeType(nextKey);

			Node node = new Node(nextKey, nodeType);
			keys.add(node);
		}
	}
	protected abstract Set<String> getResult();
	
	
}
