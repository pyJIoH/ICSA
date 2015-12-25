package icsa.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/keywords")
public class Keywords {

	@GET
	public String getKeywords(){
		return "Corvu NG, SQL";
	}
}
