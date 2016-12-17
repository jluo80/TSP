package components;

public class UF {
	private int[] id = null;
	private int[] size = null;
	private int count = 0;
	
	public UF(int count){
		this.count = count;
		id = new int[count];
		size = new int[count];
		for(int i=0;i<count;i++){
			id[i]=i;
			size[i] = 1;
		}
	}
	
	public int count(){
		return count;
	}
	
	public int find(int p){
		while(p!=id[p]){
			id[p]=id[id[p]];
			p=id[p];
		}
		return p;
	}
	
	public boolean connected(int p, int q){
		return find(p)==find(q);
	}
	
	public void union(int p, int q){
		int id_p = find(p);
		int id_q = find(q);
		if(id_p == id_q) return;
		if(size[p]<size[q]){
			for (int i = 0; i < id.length; i++){
				if (id[i] == id_p) id[i] = id_q;  
			}
			size[q]+=size[p];
		}else{
			for (int i = 0; i < id.length; i++){
				if (id[i] == id_q) id[i] = id_p;  
			}
			size[p]+=size[q];
		}

        count--; 
	        
	}
}
