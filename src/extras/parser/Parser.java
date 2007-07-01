import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.net.*;

/**
 * A leiro fajlok beolvsasaert es ertelmezeseert felelos osztaly.
 * {@link ParserCommand} interfacet megvalosito parancsokat kepes eltarolni,
 * amelyeket a megfelelo parancsszo kiolvasasanak hatasara meghiv.
 */
class Parser {
	private HashMap commands;

	public Parser() {
		commands = new HashMap();
	}

	/**
	 * A parameterkent megadott fajlt preprocesszalja.
	 * A parameterkent megadott fajlbol eltunteti az osszes kommentart, ures sort,
	 * felesleges whitespace karaktereket, es visszater egy, csupan a parancsokat
	 * tartalmazo string listaval.
	 *
	 * @param file A beolvasando fajl neve
	 * @return A beolvasott parancsok listaja
	 */
	private Vector preprocess(InputStream stream) throws Exception {
		Vector lines = new Vector();
		BufferedReader input;
		try {
			input = new BufferedReader(new InputStreamReader(stream));
		} catch(Exception e) {
			throw new Exception("Unable to load file");
		}
		String line;
		boolean inComment = false;
		while((line = input.readLine()) != null) {
			line = line.replaceAll("\\s+", " ");
			line = line.replaceAll("^ ", "");
			StringBuffer linebuf = new StringBuffer(line);
			int begin = (inComment)?0:linebuf.indexOf("/*");
			if(begin != -1) {
				int end = linebuf.indexOf("*/");;
				if(end == -1) {
					end = linebuf.length();
					inComment = true;
				} else {
					end += 2;
					inComment = false;
				}
				linebuf.delete(begin, end);
			}
			if((begin = linebuf.indexOf("//")) != -1)
				linebuf.delete(begin, linebuf.length());
			if(!linebuf.toString().equals("")) 
				lines.add(linebuf.toString());
		}
		return lines;
	}

	/**
	 * Ertelmezi a parameterben megadott fajlt.
	 * Eloszor preprocesszalja, majd az igy kinyert parancsokat
	 * megprobalja lefuttatni.
	 *
	 * @param fileName az ertelmezendo fajl.
	 */
	public void parse(InputStream stream) throws Exception {
		//File file;
		//file = new File(getClass().getResource(fileName).getFile());
		//file = urlToFile(getClass().getResource(fileName));
		//file = new File(fileName);
		//InputStream fr = getClass().getResourceAsStream(fileName);
		Vector lines = preprocess(stream);

		for(Iterator i = lines.iterator(); i.hasNext();) {
			String line = (String)i.next();
			StringTokenizer tokens = new StringTokenizer(line);
			ParserCommand command = (ParserCommand)commands.get(tokens.nextToken());
			
			if(command == null)
				throw new Exception("Unkown command '"+line+"'");
			
			String[] args = null;
			
			if(tokens.countTokens() > 0) {
				args = new String[tokens.countTokens()];
				int j = 0;
				while(tokens.hasMoreTokens()) {
					args[j++] = tokens.nextToken();
				}
			}
			command.doCommand(args);
		}
	}

	public void addCommand(String commandString, ParserCommand command) {
		commands.put(commandString, command);
	}
}
