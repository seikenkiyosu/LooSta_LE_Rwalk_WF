package Agent;
public class Agent_Rwalk {

	private boolean leader;
	private int timer;
	private double x;
	private double y; 
	private double vx;
	private double vy;

	
	public Agent_Rwalk(boolean initleader, double inix, double iniy, int s) {
		this.leader = initleader;	
		this.timer = s;
		this.x = inix; this.y = iniy;
		this.vx = 0; this.vy = 0;
	}
	
	public boolean IsLeader(){ return this.leader; }
	public int gettimer(){ return this.timer; }
	public double getx(){ return this.x; }
	public double gety(){ return this.y; }
	
	public void ShiftPointForRWP(){		//�����_���E�F�C�|�C���g�ł̈�b�Ԃ�shift
		this.x += this.vx;
		this.y += this.vy;
	}
	
	public void ShiftPointForTorus(int Gridsize){	//�g�[���X���ł̈�b�Ԃ�shift
		this.x += this.vx;
		if(this.x > Gridsize) this.x %= Gridsize;
		if(this.x <  0) this.x += Gridsize;
		this.y += this.vy;
		if(this.y > Gridsize) this.y %= Gridsize;
		if(this.y <  0) this.y += Gridsize;
	}
	
	public void Vchange(double r, double theta){	//���x�ω�
		this.vx = r*Math.cos(theta);
		this.vy = r*Math.sin(theta);
	}
	
	
	public void Countdown(){
		if(this.timer > 0) this.timer--;
	}
	
	public boolean IsTimeout(){
		if(this.timer==0) return true;
		else return false;
	}
	
	public void TimerReset(int s){
		this.timer = s;
	}
	
	public void TimerForRule5(int f){
		this.timer = f;
	}
	
	public void ChangeState(boolean isleader){
		this.leader = isleader;
	}
	
	public void ShowPoint() {		//���݂̍��W��\��
		System.out.print("x = " + this.x);
		System.out.println("\t\ty = " + this.y);
	}
}
