package sgs;
import java.util.ArrayList;
import java.util.List;


public class SgsUtils {
	public static void desc(SgsBean sgsBean,int whichOne){
//		sgsBean.countAll[whichOne]=sgsBean.countAll[whichOne]-1;
		List<Integer> countAll=sgsBean.getCountAll();
		int newVal=sgsBean.getCountAll().get(whichOne)-1;
		//防止小于0
		if(newVal<=0){
			newVal=0;
		}
		countAll.set(whichOne, newVal);
		sgsBean.setCountAll(countAll);
	}
	public static final int jiuMax=5;
	public static final int taoMax=12;
	public static final int wuxieMax=7;
	public static final int wanjianMax=3;
	public static final int nanmanMax=1;
	private static List<Integer> sjsAllMax=new ArrayList<Integer>();
	static{
		sjsAllMax.add(jiuMax);
		sjsAllMax.add(taoMax);
		sjsAllMax.add(wuxieMax);
		sjsAllMax.add(wanjianMax);
		sjsAllMax.add(nanmanMax);
	}
	public static void add(SgsBean sgsBean,int whichOne){
		List<Integer> countAll=sgsBean.getCountAll();
		int newVal=sgsBean.getCountAll().get(whichOne)+1;
		//防止过大越界
		if(newVal>sjsAllMax.get(whichOne)){
			newVal=sjsAllMax.get(whichOne);
		}
		countAll.set(whichOne, newVal);
		sgsBean.setCountAll(countAll);
	}
}
