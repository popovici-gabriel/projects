package healthcard;

public class LinkList {
	/**
	 * Referinta catre prima legatura 
	 */
	Link first;	
	/**
	 * Constructor
	 */
	public LinkList () {		
		first = null; // lista nu contine inca legaturi 
	}
	/**
	 * Insert un nod in capul listei
	 * @param buffer(APDU buffer)
	 * 
	 */
	public void insert (byte[] buffer, short bufferlen){
		//creez o noua legatura in lista		
		Link newLink = new Link();
		/*
		 * Mut buffer in cimpul data din record idata
		 */
		newLink.iData.update(buffer, bufferlen);
		newLink.next = first;//noua legatura va fi primul element in lista (capul listei)
		first = newLink; // first va indica noua legatura
	}				
}
