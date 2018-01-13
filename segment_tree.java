//I'm preparing for applying for summer intern, and this's what I'm learning recently, 
//I think it's a interesting thought, so I use this as a code example.

package letecode;

class SegTreeNode{
    int val = -1;
    SegTreeNode left = null;
    SegTreeNode right = null;
    int addMark = 0;//delay update mark
}
public class segment_tree {
	final int INFINITE = Integer.MAX_VALUE;
	final int MAXNUM = 1000;
	SegTreeNode root;
	/*
	bulid a segment tree
	arr: input array
	istart：array's start index
	iend：array's end index
	*/
	void build(SegTreeNode root, int arr[], int istart, int iend)
	{
	    root.addMark = 0;//----set a delay mark
	    if(istart == iend)//leaf node
	        root.val = arr[istart];
	    else
	    {
	        int mid = (istart + iend) / 2;
	        build(root.left, arr, istart, mid);//build the left
	        build(root.right, arr, mid+1, iend);//build the right
	        //backtraking buil the root
	        root.val = Math.min(root.left.val, root.right.val);
	    }
	}

	/*
	功能：当前节点的标志域向孩子节点传递
	root: 当前线段树的根节点下标
	*/
	void pushDown(SegTreeNode root)
	{
	    if(root.addMark != 0)
	    {
	        //设置左右孩子节点的标志域，因为孩子节点可能被多次延迟标记又没有向下传递
	        //所以是 “+=”
	        root.left.addMark += root.addMark;
	        root.right.addMark += root.addMark;
	        //根据标志域设置孩子节点的值。因为我们是求区间最小值，因此当区间内每个元
	        //素加上一个值时，区间的最小值也加上这个值
	        root.left.val += root.addMark;
	        root.right.val += root.addMark;
	        //传递后，当前节点标记域清空
	        root.addMark = 0;
	    }
	}

	/*
	root：the current root
	[nstart, nend]: the current interval
	[qstart, qend]: query interval
	*/
	int query(SegTreeNode root, int nstart, int nend, int qstart, int qend)
	{
	    //check if there is intersected interval
	    if(qstart > nend || qend < nstart)
	        return INFINITE;
	    //the node is in the interval
	    if(qstart <= nstart && qend >= nend)
	        return root.val;
	    //query in the left tree and right tree
	    pushDown(root); //----transfer the add mark
	    int mid = (nstart + nend) / 2;
	    return Math.min(query(root.left, nstart, mid, qstart, qend),
	               query(root.right, mid + 1, nend, qstart, qend));

	}

	/*
	update all nodes' value in one interval
	root：current 
	[nstart, nend]: the current interval
	[ustart, uend]: interval to be updated
	addVal: new value need to be added
	*/
	void update(SegTreeNode root, int nstart, int nend, int ustart, int uend, int addVal)
	{
	    if(ustart > nend || uend < nstart)
	        return ;
	    if(ustart <= nstart && uend >= nend)
	    {
	        root.addMark += addVal;
	        root.val += addVal;
	        return ;
	    }
	    pushDown(root);
	    //update the left and right
	    int mid = (nstart + nend) / 2;
	    update(root.left, nstart, mid, ustart, uend, addVal);
	    update(root.right, mid+1, nend, ustart, uend, addVal);
	    //backtraking to update the value
	    root.val = Math.min(root.left.val, root.right.val);
	}
	
	/*
	update a node's value
	root：current root node
	[nstart, nend]: the current interval
	index: index of the updated node
	addVal: additional value
	*/
	void updateOne(SegTreeNode root, int nstart, int nend, int index, int addVal)
	{
	    if(nstart == nend)
	    {
	        if(index == nstart)
	            root.val += addVal;
	        return;
	    }
	    int mid = (nstart + nend) / 2;
	    if(index <= mid)//update the left subtree
	        updateOne(root.left, nstart, mid, index, addVal);
	    else updateOne(root.right, mid+1, nend, index, addVal);
	    //backtracking to update the parents' value
	    root.val = Math.min(root.left.val, root.right.val);
	}
}
