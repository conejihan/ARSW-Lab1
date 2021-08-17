/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import edu.eci.arsw.threads.CountThread;
import edu.eci.arsw.threads.SearchServerThread;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    private int a;
    private int b;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int n){
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        LinkedList<Thread> threadsList = new LinkedList<>();
        SearchServerThread search = new SearchServerThread(ipaddress,a,b);
        int tOcurrencesCount = 0;
        
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        
        int tCheckedListsCount=0;
        
        if(skds.getRegisteredServersCount()%n == 0) {
        	
        	for(int i=0;i<n;i++) {
        		this.a = skds.getRegisteredServersCount()/n * i;
        		this.b = skds.getRegisteredServersCount()/n + a;
        
        		search = new SearchServerThread(ipaddress,a,b);
        		Thread segmento = new Thread(search);
        		threadsList.add(segmento);
        	}
        }
        else {
        	
        	for(int i=0;i<n;i++) {
        		if(i==n-1) {
        			this.b = skds.getRegisteredServersCount();
        		}
        		else {
        			this.b = skds.getRegisteredServersCount()/n + a;
        		}
        		this.a = skds.getRegisteredServersCount()/n * i;
        		search = new SearchServerThread(ipaddress,a,b);
        		Thread segmento = new Thread(search);
        		threadsList.add(segmento);
        	}
        }
        
        for(int i=0; i<n; i++) {
        	threadsList.get(i).start();
        	try {

        		threadsList.get(i-1).join();

        		} catch (InterruptedException ie) {

        		}
        }
        
        tCheckedListsCount += search.nOcurrences();
    	tOcurrencesCount += search.noOcurrencesCount();
        if (tOcurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{tCheckedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
