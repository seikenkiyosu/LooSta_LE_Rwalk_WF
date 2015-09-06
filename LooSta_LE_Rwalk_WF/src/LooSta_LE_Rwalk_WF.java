import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Random;
import Agent.Agent_Rwalk;
import Interaction.Interaction_Rwalk;
import RandamPackage.*;

class LooSta_LE_Rwalk_WF{
	public static final int Gridsize = 50;	//フィールドのサイズ(Gridsize*Gridsizeの正方形)
	public static final int Roundnum = 10000000;
	public static final int DataNum = 100;	//データ数
	
	public static final int r = 1;	//vの速度の上限
	public static final int DistanceforInteraction = 1;	//interactionができる距離

	public static final int s = 480;			//96�ȏ��3n�ȏ�
	public static final int n_from = 10;	//nはn_from～n_toのデータをとる
	public static final int n_to = 59;	
	
	public static String RandomMethod = "Rwalk";	//Torus or RWP(Random Way Point)
	public static String name = System.getProperty("user.name");
	
	public static String DataPath = "\\Users\\" + name + "\\Dropbox\\Data\\";
	public static String WritingPath = DataPath + "Data_" + RandomMethod + "\\";

	
	public static void main(String args[]){
		Random random = new Random();
        File file = new File(WritingPath);
        if (!file.exists()) {
            System.out.println("ディレクトリが存在しません。");
            System.exit(-1);
        }

		for(int n=n_from; n<=n_to; n++){
			Agent_Rwalk agent[] = new Agent_Rwalk[n];
			int CTsum=0, HTsum=0;
			double CTave=0.0 , HTave=0.0;
			
			for(int Data=0; Data < DataNum; Data++){
				int CT = 0, HT = 0;
				boolean HT_count_flag = false, CT_count_flag = true;
				
				for(int i=0; i<n; i++)
					agent[i] = new Agent_Rwalk(random.nextBoolean(), random.nextInt(Gridsize)+random.nextDouble(), random.nextInt(Gridsize)+random.nextDouble(), s);
				
				for(int i=0; i<Roundnum; i++){
					int leadercount=0;
					//リーダの数をかぞえる
					for(int j=0; j<n; j++) if(agent[j].IsLeader()){ leadercount++; }
					
					//Holding Timeが終了したらぬける
					if(leadercount!=1 && HT_count_flag==true){ break; }
	//				System.out.println("the number of leaders = " + leadercount);
					
					if(leadercount==1){ 
								HT_count_flag = true;
								CT_count_flag = false;
								break;
							}
					
					if(HT_count_flag==true) HT++;
					if(CT_count_flag==true) CT++;
				
					while(true){					//リーダが決定するまで
						/*交流*/
						int p = random.nextInt(n);		//interactionをするagentをランダムで選択
						int q = RandomWay_Rwalk.RandamPickNearAgent( p, n, agent, DistanceforInteraction);		//p�Ƌ���1�ȓ��ɂ���m�[�h�̒���(���id�̒Ⴂ)�m�[�h��q�ɑ��
						if(q != -1) { 	//pの周りにinteractionが可能なAgentが見つかったとき
							Interaction_Rwalk.interaction(agent[p], agent[q], s);	
//							for(int j=0; j<n; j++) agent[j].Countdown();	//交流したagentのtimerをデクリメント
							break;						//次のラウンドへ
						}
						/*移動*/
						for(int j=0; j<n; j++){					//各Agentをランダムに移動させる
							agent[j].Vchange( r, random.nextInt(360)+random.nextDouble() );
							agent[j].ShiftPointForTorus(Gridsize);	//移動
						}
					}
				}
				CTsum += CT;
				HTsum += HT;
				System.out.println("( " + RandomMethod + " " + r + "R_" + DistanceforInteraction + "DI_" + Gridsize + "GS_" + DataNum + "DN"
						+ "  s:" + s +  ", n:" + n_from + "~" + n_to + " )"
						+ "\t n = " + n
						+ ", Data number = " + (Data+1)
						+ ",\tCT = " + CT + ",\tHT = " + HT);
			}	//for(int Data=0; Data < DataNum; Data++) 終了
			CTave = (double)CTsum / DataNum;
			HTave = (double)HTsum / DataNum;
			/*ファイル書き込みのための処理*/
			try{
				String svalue = new Integer(s).toString();
				String nfromvalue = new Integer(n_from).toString();
				String ntovalue = new Integer(n_to).toString();
	
		        File fileCT = new File(WritingPath + "_CT_" + "_s=" + svalue + "_n=from" + nfromvalue + "to" + ntovalue
		        		+ RandomMethod + r + "R_" + DistanceforInteraction + "DI_" + Gridsize + "GS_" + DataNum + "DN" + "_" + ".txt");
//		        File fileHT = new File(WritingPath + "_HT_" + "_s=" + svalue + "_n=from" + nfromvalue + "to" + ntovalue
//		        		+ RandomMethod + r + "R_" + DistanceforInteraction + "DI_" + Gridsize + "GS_" + DataNum + "DN" + "_" + ".txt");
	
		        if(!fileCT.exists()){ fileCT.createNewFile(); }
//		        if(!fileHT.exists()){ fileHT.createNewFile(); }
	
		        PrintWriter pwCT = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileCT, true)));
//		        PrintWriter pwHT = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileHT, true)));
	
		        String stringCTave = String.valueOf(CTave);
//		        String stringHTave = String.valueOf(HTave);
	
		        pwCT.write(stringCTave + "\r\n");
//		        pwHT.write(stringHTave + "\r\n");
	
		        pwCT.close();
//		        pwHT.close();
	
		      }catch(IOException e){
		        System.out.println(e);
		      }
		}	//n_from-to終了
	}	//メイン終了
}	//クラス終了
