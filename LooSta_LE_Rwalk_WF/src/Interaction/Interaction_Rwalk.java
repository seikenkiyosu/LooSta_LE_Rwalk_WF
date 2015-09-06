package Interaction;
import Agent.Agent_Rwalk;

public class Interaction_Rwalk {
	public static void interaction(Agent_Rwalk P, Agent_Rwalk Q, int s){
		//Rule1
		if(P.IsLeader() && Q.IsLeader()){ 
			P.TimerReset(s); Q.TimerReset(s); 
			Q.ChangeState(false);
			return;
		}
		//Rule2
		if(P.IsLeader() && !Q.IsLeader()){ 
			P.TimerReset(s); Q.TimerReset(s);
			return;
		}
		//Rule3
		if(!P.IsLeader() && Q.IsLeader()){ 
			P.TimerReset(s); Q.TimerReset(s);
			return;
		}
		//Rule4
		if(!P.IsLeader() && !Q.IsLeader() && P.IsTimeout() && Q.IsTimeout()){ 
			P.TimerReset(s); Q.TimerReset(s); 
			P.ChangeState(true);
			return;
		}
		//Rule5
		if(!P.IsLeader() && !Q.IsLeader()){
			if(P.gettimer() > Q.gettimer()){
				P.Countdown();
				Q.TimerForRule5(P.gettimer());
			}
			else{
				Q.Countdown();
				P.TimerForRule5(Q.gettimer());
			}
			return;
		}
	}
}
