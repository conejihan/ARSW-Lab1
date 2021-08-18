package edu.eci.arsw.threads;
import java.lang.*;
import java.util.Collection;
import java.util.LinkedList;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class SearchServerThread extends Thread {
	private static final int BLACK_LIST_ALARM_COUNT=5;
	private String ipaddress;
	private int ocurrencesCount;
	private int checkedListsCount;
	private int a;
	private int b;
	LinkedList<Integer> blackListOcurrences=new LinkedList<>();
	
	public SearchServerThread(String ipaddress, int a, int b) {
		this.ipaddress = ipaddress;
		this.a = a;
		this.b = b;
		this.blackListOcurrences=new LinkedList<>();
	}
	
	public int nOcurrences() {
		return checkedListsCount;
	}
	
	public int noOcurrencesCount() {
		return ocurrencesCount;
	}
	
	
	public void run() {
		HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
	
        
		
		for (int i=a;i<b && ocurrencesCount<BLACK_LIST_ALARM_COUNT;i++){
            checkedListsCount+=1;
            
            if (skds.isInBlackListServer(i, ipaddress)){
                
                blackListOcurrences.add(i);
                
                ocurrencesCount+=1;
            }

        }
		
	}

	public Collection<? extends Integer> getServers() {
		return blackListOcurrences;
	}
}
