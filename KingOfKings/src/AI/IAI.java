package AI;

import GameClient.ClientMessages;

public abstract class IAI {

	protected String AIName;
	protected int AINum;
	protected ClientMessages cmsg;
	protected AIVision vision;
	
	public IAI(String AIName,int AINum,ClientMessages cmsg) {
		
		this.AIName = AIName;
		this.AINum = AINum;
		this.cmsg = cmsg;
		vision = new AIVision(AINum);
	}
	public abstract void StartAI();
}
