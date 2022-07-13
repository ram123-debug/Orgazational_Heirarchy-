import java.io.*; 
import java.util.*; 

// Tree node
//Implemenating generic tree node
class Node {
	int id;
	Vector<Node> children=new Vector<>();
	Node parent;
	int level;
	int size;
	Node(int data)
	 {
		this.id=data;
		this.children=new Vector<>();
		this.level=1;
		this.size=1;
     } 
}



// implementation of AVL tree node
class NodeA{
	NodeA right;
	NodeA left;
	int height;
	Node Aid; // id in avl tree
}

// Implemenating AVL tree
class AVLTree{
	NodeA roota;
	AVLTree(Node n){
		this.roota=new NodeA();
		this.roota.Aid=n;
		this.roota.height=0;

	}
	// Helper function for createNewNode
	public NodeA createNewNode(Node d){
		NodeA a = new NodeA();
		a.Aid=d;
		
		a.left=null;
		a.right=null;
		return a;

	}
	// helper function for chekcing max value
	public int maxval(int x, int y){
		if( x > y ){
			return x;
		}
		return y;

	}
	
	public int height(NodeA node){
		if( node != null ){
			return node.height;
		}
		return -1;
	}
	// rotate right case
	public NodeA rotateright( NodeA y){
		NodeA x=y.left;
		NodeA t2=x.right;

		
		y.left= t2;
		x.right=y;
		int a=maxval(height(y.right) , height(y.left));
		y.height = 1 + a;
		int b=maxval(height(x.right) , height(x.left));
		x.height = 1 + b;



		return x;

	}
// Rotating left case
	public NodeA rotateleft(NodeA x){
		NodeA y=x.right;
		NodeA t2= y.left;

		x.right=t2;
		y.left=x;
		
		
		int b=maxval(height(x.right) , height(x.left));
		x.height = 1 + b;
		int a=maxval(height(y.right) , height(y.left));
		y.height = 1 + a;
		
		return y;

	}
	// Helper function for balancing the tree
	int getBalance(NodeA node) 
    { 
        if (node == null) 
            return 0; 
        return height(node.left) - height(node.right); 
    } 
	
	// Searching method with node and id
	public Node search(NodeA temp,int id) throws IllegalIDException{
		if(temp==null){
			throw new IllegalIDException("Id not exist");
		}
		if(temp.Aid.id==id){
			return temp.Aid;

		}
		else if(temp.Aid.id>id){
			
			return search(temp.left,id);
		}
		else {
			return search(temp.right,id);
		}


		
		 	
	}
	// searching only with id
	public Node search(int id)throws IllegalIDException{
		return search(roota, id);
	}
 // Inserting the avl node
	public NodeA insertA(NodeA node, Node Aid) throws IllegalIDException{
		
		if(node==null){
			return createNewNode(Aid);
		}
		if ( Aid.id > node.Aid.id ) {
			node.right = insertA( node.right , Aid );
		}
		
		else if (Aid.id < node.Aid.id ) {
			node.left = insertA(node.left, Aid);
		}
		else {
			// throw exception employee with given id already exist
			throw new IllegalIDException("Node exists");
		}
		node.height = 1 + maxval ( height(node.left), height( node.right) ) ;
		//need to rebalance the tree

		int heightdiff = height(node.left)-height(node.right);

		//four possible cases LL,LR,RL,RR
		//Left left 
		if(heightdiff > 1 && Aid.id < node.left.Aid.id){
			return rotateright(node);
		}
		//left right
		if(heightdiff > 1 && Aid.id > node.left.Aid.id){
			node.left= rotateleft(node.left);
			return rotateright(node);
		}
		//right right
		if(heightdiff < -1 && Aid.id > node.right.Aid.id){
			return rotateleft(node);
		}
		//right left
		if(heightdiff < -1 && Aid.id < node.right.Aid.id){
			node.right = rotateright(node.right);
			return rotateleft(node);
		}
		return node;


	}
	public void insert(Node n)throws IllegalIDException{
		roota=insertA(roota, n);
		

	}
	NodeA minValue(NodeA node){
		NodeA current=node;
		while(current.left!=null){
			current=current.left;
		}
		return current;
	}
	int numchild(NodeA node){
		
		if(node.left==null){
			if(node.right==null){
				return 0;
			}
			return 1;

		}
		else if(node.right==null){
			if(node.left==null){
				return 0;
			}
			return 1;

		}
		return 2;

	}
	// deleting the node
	NodeA deleteN(NodeA root, Node Aid) throws IllegalIDException {
		
		if(root.Aid==null){
			throw new IllegalIDException("Node not exist");

		}
		else if(Aid.id<root.Aid.id){

			root.left = deleteN(root.left , Aid);
		}else if( Aid.id> root.Aid.id){
			root.right=deleteN(root.right , Aid);
		}else {
			int c=numchild(root);
			if(c==0){
				root=null;
			}else if(c==1){
				if(root.left==null){
					root=root.right;
				}
				else root=root.left;
			}else{
				NodeA temp =minValue(root.right);
				root.Aid =temp.Aid;
				root.right =deleteN(root.right , temp.Aid);

			}




		}
		
		if(root ==null){
			return root;
		}
		root.height = 1 + maxval ( height(root.left), height( root.right) ) ;
		int balance=getBalance(root);
		// If this node becomes unbalanced, then there are 4 cases 
        // Left Left Case 
        if (balance > 1 && getBalance(root.left) >= 0) 
            return rotateright(root); 
  
        // Left Right Case 
        if (balance > 1 && getBalance(root.left) < 0) 
        { 
            root.left = rotateleft(root.left); 
            return rotateright(root); 
        } 
  
        // Right Right Case 
        if (balance < -1 && getBalance(root.right) <= 0) 
            return rotateleft(root); 
  
        // Right Left Case 
        if (balance < -1 && getBalance(root.right) > 0) 
        { 
            root.right = rotateright(root.right); 
            return rotateleft(root); 
        }
		return root;


	}
	public void delete(Node n) throws IllegalIDException{
		
		roota=deleteN(roota, n);
	}
	

	


	
}


public class OrgHierarchy implements OrgHierarchyInterface{

//root node
Node root;
//NodeA of  Avl tree
AVLTree avlsearch;

public boolean isEmpty(){
	
	if(root==null){
		return true;
	}
	return false;
} 

public int size(){
	return root.size;
}

public int level(int id) throws IllegalIDException{
	Node employee=avlsearch.search(id);
	return employee.level;

} 

public void hireOwner(int id) throws NotEmptyException{
	if(isEmpty()){
		root=new Node(id);
		avlsearch=new AVLTree(root);
		
	}
	else throw new NotEmptyException("");
}

public void hireEmployee(int id, int bossid) throws IllegalIDException{
	
	
	Node n=avlsearch.search(bossid);  // searching for boss id
	Node newemployee=new Node(id); // creating new employ node
	newemployee.parent=n;
	n.children.add(newemployee);
	newemployee.level=n.level+1; // Increameting level
	avlsearch.insert(newemployee);
	root.size+=1;


} 

public void fireEmployee(int id) throws IllegalIDException{
	
	if(id==root.id){
		throw new IllegalIDException("Owner cannot be deleted");
	}
	

	Node fireemploy=avlsearch.search(id);
	
	avlsearch.delete( fireemploy);
	fireemploy.parent.children.remove(fireemploy);
	root.size=root.size-1;


}
public void fireEmployee(int id, int manageid) throws IllegalIDException{
	
	Node fireemployee=avlsearch.search(id);
	Node newboss=avlsearch.search(manageid);
	if(id==root.id){
		throw new IllegalIDException("Owner cannot be deleted");
	}
	if(manageid==root.id){
		throw new IllegalIDException("boss can't be manageid");
	}
	if(fireemployee.level!=newboss.level){
		throw new IllegalIDException("New boss should be on same level");
	}
	for(int i=0;i<fireemployee.children.size();i++){
		Node n=fireemployee.children.elementAt(i);
		n.parent=newboss;
		newboss.children.add(n);
	}
	avlsearch.delete( fireemployee);
	Node n=fireemployee.parent;
	
	n.children.remove(fireemployee);
	root.size=root.size-1;

} 

public int boss(int id) throws IllegalIDException{  // throwing both Illegal id exception and EmptyTreeException
	
	if(id==root.id){
		return -1;
	}
	Node n=avlsearch.search(id);
	return n.parent.id;
}

public int lowestCommonBoss(int id1, int id2) throws IllegalIDException{ // throwing both Illegal id exception and EmptyTreeException
	
	if(id1==root.id||id2==root.id){
		return -1;
	}
	Node employee1=avlsearch.search(id1);
	Node employee2=avlsearch.search(id2);

	int leveldiff=employee1.level-employee2.level;
	if(leveldiff>0){
		while(leveldiff>0){
			employee1=employee1.parent;
			leveldiff--;

		}
	}else if(leveldiff<0){
		while(leveldiff<0){
			employee2=employee2.parent;
			leveldiff++;

		}	}
	
	while(employee1.parent.id!=employee2.parent.id){
		employee1=employee1.parent;
		employee2=employee2.parent;

	}
	
	return employee1.parent.id;

}
// helper method for toString method 
public Vector<Integer> BFS(Node n){
	Vector<Node> v=new Vector<>();
	Vector<Integer> v2=new Vector<>();
	v.add(n);
	int a=0;
	int last_level=n.level;

	while(a<v.size()){
		Node temp=v.get(a);
		v.addAll(temp.children);
		if(temp.level==last_level+1){
			v2.add(-1);
			last_level++;
		}
		v2.add(temp.id);
		a++;
	}
	return v2;
}

public String toString(int id) throws IllegalIDException{ 
	
	String s=new String();

	Node n=avlsearch.search(id);
	Vector<Integer> v=new Vector<>();
	v=BFS(n);
	v.add(-1);
	Vector<Integer> v2=new Vector<>();
	for(Integer i :v){
		if(i==-1){
			Collections.sort(v2);
			for(Integer j :v2){
				s+=j.toString()+" ";
			}
			s=s.substring(0, s.length()-1)+",";
			v2=new Vector<>();
			continue;

		}
		else{
			v2.add(i);
		}
	}
	return s.substring(0, s.length()-1);
}

}
