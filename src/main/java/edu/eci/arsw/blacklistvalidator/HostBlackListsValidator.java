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
        LinkedList<SearchServerThread> threadsList = new LinkedList<>();
        SearchServerThread search = new SearchServerThread(ipaddress,a,b);
        int tOcurrencesCount = 0;
        
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        
        int tCheckedListsCount=0;
        
        if(skds.getRegisteredServersCount()%n == 0) {
        	
        	for(int i=0;i<n;i++) {
        		a = skds.getRegisteredServersCount()/n * i;
        		b = skds.getRegisteredServersCount()/n * (i+1);
        		search = new SearchServerThread(ipaddress,a,b);
    			threadsList.add(search);
    			}
        }
        else {
        	
        	for(int i=0;i<n;i++) {
        		a = skds.getRegisteredServersCount()/n * i;
        		b = skds.getRegisteredServersCount()/n * (i+1);
        		if(n-1 == i) {
        				b = skds.getRegisteredServersCount();
        			}
        		
        		search = new SearchServerThread(ipaddress,a,b);
    			threadsList.add(search);
        	}
        }
        

        for(SearchServerThread thread:threadsList){
            thread.start();
        }
        for(SearchServerThread thread:threadsList){
            try {
                thread.join();
                tOcurrencesCount += thread.noOcurrencesCount();
                tCheckedListsCount +=  thread.nOcurrences();
                blackListOcurrences.addAll(thread.getServers());
            } catch(Exception e) {
                System.out.println("Error");
            }
        }
        
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
