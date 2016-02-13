package id.franspratama.geol.integration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import id.franspratama.geol.core.pojo.WFMTicketAndWorkOrder;

@Component
public class WFMTicketAndWOParser implements Parser<WFMTicketAndWorkOrder>{

	@Override
	public List<WFMTicketAndWorkOrder> parse(File file) {
		
		Scanner scanner;
		List<WFMTicketAndWorkOrder> ttAndWoList = new ArrayList<>();
		
		try {
			scanner = new Scanner(file);
			if( !scanner.hasNextLine() ){
				return null;
			}
			//skip first line
			scanner.nextLine();
			
			while( scanner.hasNextLine() ){
				
				String currentLine = scanner.nextLine();
				String[] splitedLine = currentLine.split("#",-1);
				
				for(String line : splitedLine){
					System.out.print( line+"\t" );
				}
				System.out.println();
				
				WFMTicketAndWorkOrder ttAndWO = new WFMTicketAndWorkOrder();
									  ttAndWO.setTicketId( splitedLine[0] );
									  ttAndWO.setWoId( splitedLine[1] );
									  ttAndWO.setPic( splitedLine[2] );
									  ttAndWO.setDeviceId( splitedLine[3] );
									  ttAndWO.setSiteId( splitedLine[4] );
									  ttAndWO.setInsertTime( new Date() );
									  ttAndWO.setNotificationType( splitedLine[5] );
									  
				ttAndWoList.add( ttAndWO );
			}
			
			scanner.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return ttAndWoList;
	}

}
