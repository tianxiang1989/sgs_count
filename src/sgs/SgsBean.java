package sgs;
import java.util.ArrayList;
import java.util.List;


public class SgsBean {
	//ArrayList<Integer> list=new ArrayList<Integer>();
	private List<Integer> countAll=new ArrayList<Integer>();
	public SgsBean(){
		init();
	}
	public void init(){
		//æ∆Ã“Œﬁ–∏≥ı ºªØ
		countAll.clear();
		countAll.add(SgsUtils.jiuMax);
		countAll.add(SgsUtils.taoMax);
		countAll.add(SgsUtils.wuxieMax);
		countAll.add(SgsUtils.wanjianMax);
		countAll.add(SgsUtils.nanmanMax);
	}
	
	public List<Integer> getCountAll() {
		return countAll;
	}
	public void setCountAll(List<Integer> countAll) {
		this.countAll = countAll;
	}
}
